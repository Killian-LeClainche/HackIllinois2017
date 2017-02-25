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
	 * @param trainingSet
	 *            A list of classification training images
	 * @return
	 */
	public double find(SamplePool pool, double[][] ... classifications)
	{
		double fitnessTotal = 0.0;

		for(int i = 0; i < classifications.length; i++)
		{
			String name = pool.getClassificationName(i);
			
			String result;
			
			for(int j = 0; j < classifications[i].length; j++)
			{
				result = network.classify(classifications[i][j]);
				if(result.equals(pool.getClassificationName(i)))
				{
					fitnessTotal++;
				}
			}
		}
		
		return fitnessTotal;
	}
}
