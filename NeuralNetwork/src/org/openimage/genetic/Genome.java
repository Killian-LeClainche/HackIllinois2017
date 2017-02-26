package org.openimage.genetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the Genome of a NeuralNet and the fitness of the network.
 *
 */
public class Genome implements Comparable<Genome>
{
	public List<Double> weights;
	public double fitness;

	public Genome()
	{
		fitness = 0;
	}

	public Genome(List<Double> list)
	{
		this.weights = list;
		this.fitness = 0;
	}

	@Override
	public int compareTo(Genome g)
	{
		if (this.fitness < g.fitness)
		{
			return 1;
		} else if (this.fitness > g.fitness)
		{
			return -1;
		} else
		{
			return 0;
		}

	}

	public List<Double> getWeights()
	{
		return weights;
	}
}
