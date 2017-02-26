package org.openimage.genetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the Genome of a NeuralNet and the fitness of the network.
 */
public class Genome implements Comparable<Genome>
{
	/**
	 * the list of the genome weights
	 */
	public List<Double> weights;

	/**
	 * fitness of the genome calculated in fitness finder
	 */
	public double fitness;

	/**
	 * Id of the genome, not entirely implemented currently.
	 */
	public int id;

	/**
	 * Default Constructor of Genome
	 */
	public Genome()
	{
		fitness = 0;
	}

	/**
	 * Constructor of Genome
	 * @param list : the list of weights you want to copy over to this genome.
	 * @param id : the id of the genome.
	 */
	public Genome(List<Double> list, int id)
	{
		this.weights = new ArrayList<Double>(list);
		this.fitness = 0;
		this.id = id;
	}

	/**
	 * Constructor of Genome
	 * @param list : the list of weights you want to copy over to this genome.
	 */
	public Genome(List<Double> list)
	{
		this(list,0);
	}

	@Override
	/**
	 * Compare to function for genome, 
	 * we invert the returns so sorting will provide earlier points in the array will be bigger values.
	 */
	public int compareTo(Genome g)
	{
		if (this.fitness < g.fitness)
			return 1;
		else if (this.fitness > g.fitness)
			return -1;
		return 0;

	}

	/**
	 * Get the weights of the genome.
	 * @return
	 */
	public List<Double> getWeights()
	{
		return weights;
	}
}
