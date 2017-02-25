package org.openimage.network;

import java.util.Iterator;

import org.openimage.genetic.Genome;
import org.openimage.io.Classification;
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
	public double find(SamplePool pool)
	{
		double fitnessTotal = 0.0;

		int size = pool.getClassificationSize();
		for(int i = 0; i < size; i++)
		{
			int poolSize = pool.getPoolSize(i);
			double[][] imageDatas = pool.getSamplePool(i, (int) Math.sqrt(poolSize));
			
			String result = network.classify(imageDatas);
			if(result.equals(pool.getClassificationName(i)))
			{
				fitnessTotal++;
			}
		}
		
		return fitnessTotal;
	}
}
