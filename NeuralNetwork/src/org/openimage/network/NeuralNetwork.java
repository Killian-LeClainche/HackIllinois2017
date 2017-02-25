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
	private List<String> classifications;
	private List<Node> completeNodeList;

	/**
	 * Initialized empty values for the new NeuralNet.
	 * 
	 * @param genome
	 */
	public NeuralNetwork(Genome genome)
	{
		inputs = new ArrayList<Node>();
		outputs = new ArrayList<Node>();
		createCompleteNodeList();
	}

	/**
	 * This method outputs the classification of the most likely node.
	 * 
	 * @param imageInputNodes
	 *            A 2D array represented as a 1D array
	 * @return
	 */
	public String classify(double[] imageInputNodes)
	{
		int index = findHeaviestWeightIndex(imageInputNodes);
		return classifications.get(index);
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
		
		for (int y = 0; y + Param.BLOCK_SIZE < n; y += Param.BLOCK_SIZE)
		{
			for (int x = 0; x + Param.BLOCK_SIZE < x; x += Param.BLOCK_SIZE)
			{
				tick(imageInputNodes, n, x, y);
	
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

	/**
	 * 
	 */
	private void createCompleteNodeList()
	{
		completeNodeList = null;
	}

	/**
	 * 
	 * @param imageInputNodes 2D array represented as a 1D array
	 * @param index The block number of the array.
	 * @param n width of the array
	 */
	private void tick(double[] imageInputNodes, int size, int row, int col)
	{
		Iterator<Node> nodeIter = inputs.iterator();
		
		int k;
		for (int i = row; i < row + Param.BLOCK_SIZE; i++)
		{
			for (int j = col; j < col + Param.BLOCK_SIZE; j++)
			{
				Node node = nodeIter.next();
				k = i + (j * size);
				node.setValue(imageInputNodes[k]);
			}
		}
		
		tick();
	}
	
	private void tick()
	{
		Iterator<Node> nodeIter1 = completeNodeList.iterator();
		while (nodeIter1.hasNext())
		{
			Node node = nodeIter1.next();
			node.activate();
		}
	}

	/**
	 * This method resets the network by setting each node value to zero.
	 */
	private void reset()
	{
		Iterator<Node> nodeIter1 = completeNodeList.iterator();
		while (nodeIter1.hasNext())
		{
			Node node = nodeIter1.next();
			node.reset();
		}
	}
}
