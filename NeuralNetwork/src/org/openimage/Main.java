package org.openimage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openimage.genetic.GeneticAlgorithm;
import org.openimage.genetic.Genome;

public class Main
{

	public static ExecutorService taskExecutor;
	private static boolean flag = true;
	
	public static void main(String[] args) throws IOException
	{
		taskExecutor = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors() - 1, 1));
		
		final GeneticAlgorithm genAlg = new GeneticAlgorithm();
		System.out.println("Finished loading images...");
		new Thread()
		{
			public void run()
			{
				int gen = 0;
				System.out.println("Let's begin");
				while(flag)
				{
					gen++;
					genAlg.epoch();
					System.out.print("Generation: " + gen + "\t");
					System.out.print("Best Fitness: " + genAlg.getBestFitness() + "\t");
					System.out.print("Total Fitness: " + genAlg.getTotalFitness() + "\t");
					System.out.print("Average Fitness: " + genAlg.getAverageFitness() + "\t");
					System.out.println("Worst Fitness: " + genAlg.getWorstFitness());

				}
				print(genAlg.getPopulation().get(0));
				System.exit(1);
			}
		}.start();
		
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		flag = false;
		scanner.close();
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
