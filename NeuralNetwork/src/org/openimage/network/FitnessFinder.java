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
	private Genome genome;
	private NeuralNetwork neuralNetwork;
	private GeneticAlgorithm geneticAlgorithm;

	/**
	 * The constructor generates a networks from the genome
	 * 
	 * @param genome
	 */
	public FitnessFinder(Genome gen, GeneticAlgorithm genetic)
	{
		this.genome = gen;
		this.neuralNetwork = new NeuralNetwork(gen);
		this.geneticAlgorithm = genetic;
	}
	
	public void run()
	{
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
