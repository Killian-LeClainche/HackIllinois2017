package org.openimage.network;

import org.openimage.genetic.Genome;
import org.openimage.io.SamplePool;

/**
 * This class finds the fitness of a genome.
 * 
 * @author Jarett Lee
 */
public class FitnessFinder
{
	private NeuralNetwork network;

	/**
	 * The constructor generates a networks from the genome
	 * 
	 * @param genome
	 */
	public FitnessFinder(Genome genome)
	{
		this.network = new NeuralNetwork(genome);
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
		String result;

		for(int i = 0; i < classifications.length; i++)
		{
			for(int j = 0; j < classifications[i].length; j++)
			{
				result = network.classify(classifications[i][j]);
				if(result.equals(classificationNames[i]))
				{
					fitnessTotal++;
				}
			}
		}

		return fitnessTotal;
	}
}
