package org.openimage.genetic;

import java.util.ArrayList;

/**
 * Contains the Genome of a NeuralNet and the fitness of the network.
 *
 */
public class Genome implements Comparable<Genome>
{
	public ArrayList<Double> weights;
	public double fitness;

	public Genome()
	{
		fitness = 0;
	}

	public Genome(ArrayList<Double> weights, double fitness)
	{
		this.weights = weights;
		this.fitness = fitness;
	}

	@Override
	public int compareTo(Genome g)
	{
		if (this.fitness < g.fitness)
		{
			return -1;
		} else if (this.fitness > g.fitness)
		{
			return 1;
		} else
		{
			return 0;
		}

	}

	public ArrayList<Double> getWeights()
	{
		return weights;
	}
}
