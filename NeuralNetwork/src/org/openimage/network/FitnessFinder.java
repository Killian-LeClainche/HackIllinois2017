package org.openimage.network;

import java.util.Iterator;
import java.util.List;

import org.openimage.genetic.Genome;
import org.openimage.io.Classification;

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
	public double find(List<Classification> trainingSet)
	{
		double fitnessTotal = 0.0;

		Iterator<Classification> trainingIter = trainingSet.iterator();
		while (trainingIter.hasNext())
		{
			Classification trainingElem = trainingIter.next();

			double[][] imageInputNodes = trainingElem.getImageInputNodes();
			String expected = trainingElem.getClassification();

			String result = network.classify(imageInputNodes);

			if (result.equals(expected))
			{
				fitnessTotal++;
			}
		}

		return fitnessTotal;
	}
}
