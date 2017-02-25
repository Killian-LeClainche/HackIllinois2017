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

	public String classify(double[][] imageInputNodes)
	{
		int index = findHeaviestWeightIndex(imageInputNodes);
		return classifications.get(index);
	}

	/**
	 * Give the fitness of the algorithm from a single input.
	 * 
	 * TODO Assess the inaccuracy of the non-expected false values.
	 * 
	 * @param imageInputNodes
	 * @param expected
	 * @return
	 */
	public int findHeaviestWeightIndex(double[][] imageInputNodes)
	{
		reset();

		int ticks = 0;

		int row = imageInputNodes.length - Param.BLOCK_SIZE + 1;
		int col = imageInputNodes[0].length - Param.BLOCK_SIZE + 1;

		double[] trackedOutputValues = new double[outputs.size()];

		for (int i = 0; i + Param.BLOCK_SIZE < row; i += Param.BLOCK_SIZE)
		{
			for (int j = 0; j + Param.BLOCK_SIZE < col; j += Param.BLOCK_SIZE)
			{
				// TODO Actual network tick
				tick();

				// Write the output of the node to the tracker
				int count = 0;
				Iterator<Node> outputIter = outputs.iterator();
				while (outputIter.hasNext())
				{
					Node node = outputIter.next();
					trackedOutputValues[count++] = node.getValue();
				}

				ticks++;
			}
		}

		for (int i = 0; i < Param.FITNESS_CASE_SIZE; i++)
		{
			// TODO Actual network tick
			tick();

			// Write the output of the node to the tracker
			int count = 0;
			Iterator<Node> outputIter = outputs.iterator();
			while (outputIter.hasNext())
			{
				Node node = outputIter.next();
				trackedOutputValues[count++] = node.getValue();
			}

			ticks++;
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

	private void tick()
	{
		Iterator<Node> nodeIter1 = completeNodeList.iterator();
		while (nodeIter1.hasNext())
		{
			Node node = nodeIter1.next();
			node.activate();
		}

		/*
		 * Iterator<Node> nodeIter2 = completeNodeList.iterator(); while
		 * (nodeIter2.hasNext()) { Node node = nodeIter2.next(); node. }
		 */
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
