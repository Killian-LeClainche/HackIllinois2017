package network;

import java.util.Iterator;
import java.util.List;

import org.openimage.io.Classification;

public class NetworkRunner
{
	private NeuralNetwork network;
	private double fitness;
	
	public NetworkRunner(NeuralNetwork network)
	{
		this.network = network;
		fitness = 0.0;
	}
	
	public void run(List<Classification> trainingSet)
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
		
	}
}
