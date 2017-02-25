package org.openimage.network;

import java.util.Iterator;
import java.util.List;

import org.openimage.genetic.Genome;
import org.openimage.io.Classification;

public class FitnessFinder
{
	private NeuralNetwork network;

	public FitnessFinder(Genome genome)
	{
		this(new NeuralNetwork(genome));
	}

	public FitnessFinder(NeuralNetwork network)
	{
		this.network = network;
	}

	public double run(List<Classification> trainingSet)
	{
		double fitnessTotal = 0.0;

		Iterator<Classification> trainingIter = trainingSet.iterator();
		while (trainingIter.hasNext())
		{
			Classification trainingElem = trainingIter.next();

			double[][] imageInputNodes = trainingElem.getImageInputNodes();
			String expected = trainingElem.getClassification();

			double result = network.fitnessTest(imageInputNodes, expected);

			fitnessTotal += result;
		}

		return fitnessTotal;
	}
}
