package org.openimage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openimage.genetic.GeneticAlgorithm;
import org.openimage.genetic.Genome;

public class Main
{

	public static ExecutorService taskExecutor;

	public static void main(String[] args) throws IOException
	{
		taskExecutor = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors() - 1, 1));

		GeneticAlgorithm genAlg = new GeneticAlgorithm();
		while(true)
		{
			genAlg.epoch();
			System.out.println("Best Fitness: " + genAlg.getBestFitness());
			System.out.println("Total Fitness: " + genAlg.getTotalFitness());
			System.out.println("Average Fitness: " + genAlg.getAverageFitness());
			System.out.println("Worst Fitness: " + genAlg.getWorstFitness());

		}
		print(genAlg.getPopulation(0));
	}

	public static void print(Genome genome)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("neuralNetwork.txt"));

			List<Double> weights = genome.getWeights();
			for(int i = 0; i < weights.size(); i++)
			{
				writer.append(i + ":" + weights.get(i));
				writer.newLine();
			}
			writer.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}
