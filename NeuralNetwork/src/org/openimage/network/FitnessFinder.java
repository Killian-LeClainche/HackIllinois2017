package org.openimage.network;

import org.openimage.genetic.GeneticAlgorithm;
import org.openimage.genetic.Genome;

/**
 * This class finds the fitness of a genome.
 * 
 * @author Jarett Lee
 */
public class FitnessFinder implements Runnable
{
	/**
	 * Genome this FitnessFinder will calculate the genome for.
	 */
	private Genome genome;
	
	/**
	 * Neural Network developed from this genome.
	 */
	private NeuralNetwork neuralNetwork;
	
	/**
	 * The base genetic algorithm that uses this FitnessFinder.
	 */
	private GeneticAlgorithm geneticAlgorithm;

	/**
	 * FitnessFinder Constructor
	 * @param i : index of the genome
	 * @param gen : the genome
	 * @param genetic : the algorithm that calculates.
	 */
	public FitnessFinder(Genome gen, GeneticAlgorithm genetic)
	{
		this.genome = gen;
		this.geneticAlgorithm = genetic;
	}
	
	/**
	 * Function called from taskExecutor that'll calculate the fitness value.
	 */
	public void run()
	{
		this.neuralNetwork = new NeuralNetwork(genome);
		this.genome.fitness = find(geneticAlgorithm.getClassificationNames(), geneticAlgorithm.getClassifications());
	}

	/**
	 * 
	 * @param classificationNames
	 *            The name of the classifications
	 * @param classifications
	 *            A list of random samplings from the pools.
	 * @return
	 */
	public double find(String[] classificationNames, double[][][] classifications)
	{
		double fitnessTotal = 0.0;
		int result;

		//iterate through all classifications and the images from the sample for it.
		//compare expected with neural networks and add.
		for(int i = 0; i < classifications.length; i++)
		{
			for(int j = 0; j < classifications[i].length; j++)
			{
				result = neuralNetwork.classify(classifications[i][j]);
				if(result == i)
				{
					fitnessTotal++;
				}
			}
		}

		return fitnessTotal;
	}
}
