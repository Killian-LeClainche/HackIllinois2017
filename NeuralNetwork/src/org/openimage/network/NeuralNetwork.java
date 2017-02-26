package org.openimage.network;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openimage.Param;
import org.openimage.genetic.Genome;

/**
 * TODO
 * 
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
					trackedOutputValues[count++] = node.getValue();
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
				trackedOutputValues[count++] = node.getValue();
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
		// Add largest layer
		Layer inputLayer = new Layer(inputs);
		Layer preLayer = new Layer(Param.BLOCK_SIZE, 0);


		List<Double> weightList = null;
		weightList = arrayList.subList(0, Param.BLOCK_SIZE);
		for (int i = 0; i < Param.BLOCK_SIZE; i++)
		{
			arrayList.remove(0);
		}
		preLayer.addWeights(weightList);
		preLayer.connectAfter(inputLayer);

		layerList.add(preLayer);
		
		int size = Param.BLOCK_SIZE / 2;

		Layer layer;
		
		while (size > Param.CATEGORY_NUM)
		{
			layer = new Layer(size, 0);

			layer.connectAfter(preLayer);
			weightList = arrayList.subList(0, size);
			for (int i = 0; i < size; i++)
			{
				arrayList.remove(0);
			}
			layer.addWeights(arrayList);

			layerList.add(layer);

			preLayer = layer;
			size /= 2;
		}
		
		Layer outputLayer = new Layer(outputs);
		outputLayer.connectAfter(outputLayer);
	}
	
	private void instantiateHiddenLayer()
	{
		// Add largest layer
		Layer inputLayer = new Layer(inputs);
		Layer preLayer = new Layer(Param.BLOCK_SIZE, 0);


		List<Double> weightList = null;
		preLayer.addWeights(weightList);
		preLayer.connectAfter(inputLayer);

		layerList.add(preLayer);
		
		int size = Param.BLOCK_SIZE / 2;

		Layer layer;
		
		while (size > Param.CATEGORY_NUM)
		{
			layer = new Layer(size, 0);
			layer.connectAfter(preLayer);

			layerList.add(layer);

			preLayer = layer;
			size /= 2;
		}
		
		Layer outputLayer = new Layer(outputs);
		outputLayer.connectAfter(outputLayer);
	}
	
	private void instantiateInputs()
	{
		for (int i = 0; i < Param.BLOCK_SIZE; i++)
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

	public static void main(String args[])
	{
		double[] imageInputNodes = new double[16 * 16];
		List<Node> inputs = new ArrayList<Node>();
		for (int i = 0; i < 64; i++)
		{
			inputs.add(new Neuron());
		}

		int n = (int) Math.sqrt(imageInputNodes.length);

		for (int y = 0; y + Param.BLOCK_SIZE < n + 1; y += Param.BLOCK_SIZE)
		{
			for (int x = 0; x + Param.BLOCK_SIZE < n + 1; x += Param.BLOCK_SIZE)
			{
				System.out.print(y + "\t" + x + "\t\t");
			}
			System.out.println();
		}
		System.out.println();
		for (int y = 0; y + Param.BLOCK_SIZE < n + 1; y += Param.BLOCK_SIZE)
		{
			for (int x = 0; x + Param.BLOCK_SIZE < n + 1; x += Param.BLOCK_SIZE)
			{
				System.out.println(toIndex(y, x, n));
			}
		}
	}
}
