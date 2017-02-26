package org.openimage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openimage.genetic.Genome;
import org.openimage.io.SamplePool;
import org.openimage.network.NeuralNetwork;

public class Test
{
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader("neuralNetwork.txt"));
		String line = null;
		
		List<Double> weights = new ArrayList<>();
		while((line = reader.readLine()) != null)
		{
			weights.add(Double.parseDouble(line.split(":")[1]));
		}
		
		Genome genome = new Genome(weights);
		NeuralNetwork network = new NeuralNetwork(genome);
		SamplePool pool = SamplePool.create(new File("images"));
		
		double[][][] images = new double[2][][];
		images[0] = pool.getSamplePool(0, pool.getPoolSize(0));
		images[1] = pool.getSamplePool(1, pool.getPoolSize(1));
		
		int count = 0;
		for(int i = 0; i < images[0].length; i++)
		{
			int result = network.classify(images[0][i]);
			if(result == 0)
			{
				count++;
			}
		}
		System.out.println("Number of Classified Not Correct: " + count);
		
		count = 0;
		for(int i = 0; i < images[1].length; i++)
		{
			int result = network.classify(images[1][i]);
			if(result == 1)
			{
				count++;
			}
		}
		System.out.println("Number of Classified Smile Correct: " + count);
		
		reader.close();
	}

}
