package org.openimage.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openimage.Param;
import org.openimage.genetic.Genome;

/**
 * TODO
 *
 * @author Jarett Lee
 */
public class NeuralNetwork
{
	private List<Node> inputs;
	private List<Node> outputs;
	private List<Layer> layerList;

	/**
	 * Initialized empty values for the new NeuralNet.
	 * 
	 * @param genome
	 */
	public NeuralNetwork(Genome genome)
	{
		inputs = new ArrayList<Node>(Param.BLOCK_SIZE);
		instantiateInputs();
		outputs = new ArrayList<Node>(Param.CATEGORY_NUM);
		instantiateOutputs();
		layerList = new ArrayList<Layer>();
		instantiateHiddenLayer(genome.getWeights());
	}

	/**
	 * Initialized empty values for the new NeuralNet.
	 * 
	 * @param genome
	 */
	public NeuralNetwork()
	{
		inputs = new ArrayList<Node>(Param.BLOCK_SIZE);
		instantiateInputs();
		outputs = new ArrayList<Node>(Param.CATEGORY_NUM);
		instantiateOutputs();
		layerList = new ArrayList<Layer>();
		instantiateHiddenLayer();
	}

	/**
	 * This method outputs the classification of the most likely node.
	 * 
	 * @param imageInputNodes
	 *            A 2D array represented as a 1D array
	 * @return
	 */
	public int classify(double[] imageInputNodes)
	{
		return findHeaviestWeightIndex(imageInputNodes);
	}

	/**
	 * This method finds the most likely classification of the most likely node.
	 * 
	 * @param imageInputNodes
	 * @param expected
	 * @return
	 */
	public int findHeaviestWeightIndex(double[] imageInputNodes)
	{
		reset();

		int n = (int) Math.sqrt(imageInputNodes.length);

		double[] trackedOutputValues = new double[outputs.size()];

		for (int row = 0; row + Param.BLOCK_SIZE < n + 1; row += Param.BLOCK_SIZE)
		{
			for (int col = 0; col + Param.BLOCK_SIZE < n + 1; col += Param.BLOCK_SIZE)
			{
				tick(imageInputNodes, row, col, n);

				// Write the output of the node to the tracker
				int count = 0;
				Iterator<Node> outputIter = outputs.iterator();
				while (outputIter.hasNext())
				{
					Node node = outputIter.next();
					trackedOutputValues[count++] += node.getValue();
				}
			}
		}

		for (int i = 0; i < Param.FITNESS_CASE_SIZE; i++)
		{
			tick();

			// Write the output of the node to the tracker
			int count = 0;
			Iterator<Node> outputIter = outputs.iterator();
			while (outputIter.hasNext())
			{
				Node node = outputIter.next();
				trackedOutputValues[count++] += node.getValue();
			}
		}
		
		int maxIndex = 0;
		for (int i = 1; i < trackedOutputValues.length; i++)
		{
			if (trackedOutputValues[maxIndex] < trackedOutputValues[i])
			{
				maxIndex = i;
			}
		}

		return maxIndex;
	}

	private void instantiateHiddenLayer(List<Double> arrayList)
	{
		int size = Param.BLOCK_SIZE * Param.BLOCK_SIZE;

		// Add largest layer
		Layer inputLayer = new Layer(inputs);
		Layer preLayer = new Layer(size, 0);

		List<Double> weightList = arrayList.subList(0, size * (size + 1));
		preLayer.connectAfter(inputLayer, weightList);

		int index = size;
		layerList.add(preLayer);
		
		Layer layer;
		
		int preSize = size;
		size /= 2;
		
		while (size > Param.CATEGORY_NUM*4)
		{
			layer = new Layer(size, size/4);

			weightList = arrayList.subList(index, index + preSize * (size + 1));
			index += preSize * size;
			layer.connectAfter(preLayer, weightList);

			layerList.add(layer);

			preLayer = layer;
			preSize = size;
			size /= 2;
		}

		weightList = arrayList.subList(index, arrayList.size());
		Layer outputLayer = new Layer(outputs);
		outputLayer.connectAfter(preLayer, weightList);
	}
	
	private void instantiateHiddenLayer()
	{
		int size = Param.BLOCK_SIZE * Param.BLOCK_SIZE;

		// Add largest layer
		Layer inputLayer = new Layer(inputs);
		Layer preLayer = new Layer(size, 0);

		preLayer.connectAfter(inputLayer);
		preLayer.normalizeWeights();
		
		layerList.add(preLayer);

		Layer layer;
		
		size /= 2;
		
		while (size > Param.CATEGORY_NUM*4)
		{
			layer = new Layer(size, size/4);

			layer.connectAfter(preLayer);
			layer.normalizeWeights();

			layerList.add(layer);

			preLayer = layer;
			size /= 2;
		}
		
		Layer outputLayer = new Layer(outputs);
		outputLayer.connectAfter(preLayer);
		outputLayer.normalizeWeights();
	}
	
	private void instantiateInputs()
	{
		for (int i = 0; i < Param.BLOCK_SIZE * Param.BLOCK_SIZE; i++)
		{
			inputs.add(new Neuron());
		}
	}

	private void instantiateOutputs()
	{
		for (int i = 0; i < Param.CATEGORY_NUM; i++)
		{
			outputs.add(new Neuron());
		}
	}
	
	public Genome getGenome()
	{
		List<Double> list = new ArrayList<Double>();
		for (Layer layer : layerList)
		{
			list.addAll(layer.getWeights());
		}
		Layer outputLayer = new Layer(outputs);
		list.addAll(outputLayer.getWeights());
		return new Genome(list);
	}

	/**
	 * 
	 * @param imageInputNodes
	 *            2D array represented as a 1D array
	 * @param row
	 * @param col
	 * @param n
	 *            width of the array
	 */
	private void tick(double[] imageInputNodes, int row, int col, int n)
	{
		Iterator<Node> nodeIter = inputs.iterator();

		int k;
		for (int i = row; i < row + Param.BLOCK_SIZE; i++)
		{
			for (int j = col; j < col + Param.BLOCK_SIZE; j++)
			{
				Node node = nodeIter.next();
				k = toIndex(row, col, n);
				node.setValue(imageInputNodes[k]);
			}
		}

		tick();
	}

	private void tick()
	{
		Iterator<Layer> layerIter = layerList.iterator();
		while (layerIter.hasNext())
		{
			Layer layer = layerIter.next();
			layer.activate();
		}
		Iterator<Node> outputIter = outputs.iterator();
		while (outputIter.hasNext())
		{
			Node node = outputIter.next();
			node.activate();
		}

	}

	/**
	 * This method resets the network by setting each node value to zero.
	 */
	private void reset()
	{
		Iterator<Layer> layerIter = layerList.iterator();
		while (layerIter.hasNext())
		{
			Layer layer = layerIter.next();
			layer.reset();
		}
	}

	public static int toIndex(int row, int col, int n)
	{
		return col + (row * n);
	}
	
	@Override
	public String toString()
	{
		String out = "";
		for (Node node : inputs)
		{
			out += node.getValue() + "\t";
		}
		out += "\n";
		for (Layer layer : layerList)
		{
			for (Node node : layer.nodes)
			{
				out += node.getValue() + "\t";
			}
			out += "\n";
		}
		for (Node node : outputs)
		{
			out += node.getValue() + "\t";
		}
		
		return out;
	}
	
	public static void main(String args[])
	{
		NeuralNetwork seed = new NeuralNetwork();
		System.out.println(seed.getGenome().weights);
	}
	
	/*
	public static void main(String args[])
	{
		NeuralNetwork seed = new NeuralNetwork();
		NeuralNetwork n1 = new NeuralNetwork(seed.getGenome());
		NeuralNetwork n2 = new NeuralNetwork(n1.getGenome());
		List<Double> d1 = n1.getGenome().weights;
		List<Double> d2 = n2.getGenome().weights;
		Iterator<Double> i1 = d1.iterator();
		Iterator<Double> i2 = d2.iterator();
		int count = 0;
		while (i1.hasNext() && i2.hasNext())
		{
			Double val1 = i1.next();
			Double val2 = i2.next();

			if (!val1.equals(val2))
			{
				System.out.println(count + "\t" + val1 + "\t" + val2);
			}
			count++;
		}
		System.out.println("Size: " + d1.size() + " " + d2.size());
		System.out.println("Equal: " + d1.equals(d2));
	}
	*/
}
