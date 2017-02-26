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
		//creation of thread pool
		taskExecutor = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors() - 1, 1));
		
		//generate the genetic algorithm
		final GeneticAlgorithm genAlg = new GeneticAlgorithm();
		System.out.println("Finished loading images...");
		//create the separate thread for the application to run through (monitoring the input is on the main thread for closing)
		new Thread()
		{
			public void run()
			{
				int gen = 0;
				System.out.println("Let's begin");
				while(flag)
				{
					gen++;
					
					//run through generation
					genAlg.epoch();
					
					//print each generation for viewing pleasure
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
		
		//scan for any input to close the system down.
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		flag = false;
		scanner.close();
	}

	/**
	 * Printing the genome into neuralNetwork.txt
	 * @param genome : the genome to be printed
	 */
	public static void print(Genome genome)
	{
		try
		{
			//writer to neuralNetwork
			BufferedWriter writer = new BufferedWriter(new FileWriter("neuralNetwork.txt"));

			//retreive the genome's weights.
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
