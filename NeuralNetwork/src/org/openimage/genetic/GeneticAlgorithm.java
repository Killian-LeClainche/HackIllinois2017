package org.openimage.genetic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Future;

import org.openimage.Main;
import org.openimage.Param;
import org.openimage.io.SamplePool;
import org.openimage.network.FitnessFinder;

/**
 * This class generates new Genomes from a parent Genome.
 * 
 */
public class GeneticAlgorithm
{
	private SamplePool samplePool;
	
	private ArrayList<Genome> population; // Holds the entire population of
											// genomes
	private int populationSize; // Size of the population
	private int chromosomeLength; // Weights per chromosome
	private int fittestGenome; // index of the best genome in population

	// Learning Statistics (current population)
	private int generationCount;
	private double totalFitness;
	private double bestFitness;
	private double averageFitness;
	private double worstFitness;

	// Evolutionary Probabilities
	private double mutationRate;
	private double crossoverRate;

	private String[] classificationNames; //we have to figure out how to get this 
	private double[][][] classifications; // this too
	
	public GeneticAlgorithm() throws IOException
	{
		samplePool = SamplePool.create(new File("images"));
		classificationNames = new String[samplePool.getClassificationSize()];
		classifications = new double[samplePool.getClassificationSize()][][];
	}


	// Evolutonary Methods

	/**
	 * Performs crossover and outputs the resulting genomes according to the
	 * genetic algorithm's crossover rate.
	 * 
	 * @param mother
	 *            the first genome used to perform crossover
	 * @param father
	 *            the second genome used to perform crossover
	 * @param child1
	 *            storage location for the first output genome
	 * @param child2
	 *            storage location for the second output genome
	 */
	private void crossover(ArrayList<Double>mother, ArrayList<Double>father, ArrayList<Double>child1, ArrayList<Double>child2)
	{
		Random generator = new Random();
		//If the random value is outside the crossover rate or parents are the same, do not crossover
		if ( (generator.nextDouble() > crossoverRate) || (mother == father)) 
		{
			child1 = mother;
			child2 = father;

			return;
		}
		//determine the crossover point on the chromosome
		int crossoverPoint = generator.nextInt(chromosomeLength);
		
		//create new offspring
		for(int i = 0; i < crossoverPoint; i++)
		{
			child1.add(mother.get(i));
			child2.add(father.get(i));

		}
		for (int i = crossoverPoint; i < mother.size(); i++)
		{
			child1.add(father.get(i));
			child2.add(mother.get(i));
		}

		return;
		
	}

	/**
	 * Performs mutation on a chromosome according to the 
	 * genetic algorithm's mutation rate. Mutates by perturbing 
	 * chromosome's weights by no more than MAX_PERTURBATION
	 * @param chromosome
	 */
	private void mutate(ArrayList<Double>chromosome)
	{
		Random generator = new Random();
		//Traverse chromosome and perturb weights based on mutation rate
		for(int i = 0; i < chromosome.size(); i++)
		{
			if(generator.nextDouble() < mutationRate)
			{
				chromosome.set(i, chromosome.get(i) + ((generator.nextDouble()-generator.nextDouble()) * MAX_PERTURBATION));
			}
		}
	}

	/**
	 * Selects a chromosome using Roulette selection
	 * @return chromosome to be used in crossover
	 */
	private Genome getChromosomeRoulette()
	{
		
		return null;
	}
	/** 
	 * Introduces elitism by selecting the most fit genomes 
	 * to propagate into the next generation
	 * Precondition: population is sorted by fitness
	 * @param n the number of most fit genomes to select
	 * @param numCopies number of copies of each n most fit genomes
	 * @param population the output population of most fit genomes
	 */
	private void grabNBestGenomes(int n, int numCopies, ArrayList<Genome>population)
	{
		while(n > 0)
		{
			int genomeIndex = populationSize - 1 - n; //index of the most fit genome not currently added numCopies times
			for(int i = 0; i < numCopies; i++)
			{
				population.add(this.population.get(genomeIndex));
			}
			n--;
		}
	}
	
	/**
	 * Computes fitness statistics for current generation stored in 
	 * totalFitness, averageFitness, bestFitness, worstFitness
	 */
	private void computeStatistics()
	{
		
	}
	
	/**
	 * Resets all relevant variables for a new generation
	 */
	private void reset()
	{
		totalFitness = 0;
		averageFitness = 0;
		bestFitness = 0;
		worstFitness = 0;
	}
	
	/**
	 * Calculates one generation's fitnesses, 
	 * moving the network forward one generation
	 * @param oldPopulation the population of the current network, 
	 * to be modified using fitness values then returned
	 * @return then new population
	 */
	public ArrayList<Genome> epoch(ArrayList<Genome> oldPopulation)
	{		
		for(int i = 0; i < classificationNames.length; i++)
		{
			classificationNames[i] = samplePool.getClassificationName(i);
			classifications[i] = samplePool.getSamplePool(i, samplePool.getPoolSize(i) / 2);
		}
		
		for(int i = 0; i < oldPopulation.size() - 1; i++)
		{
			Main.taskExecutor.execute(new FitnessFinder(oldPopulation.get(i), this));
		}
		Future<?> future = Main.taskExecutor.submit(new FitnessFinder(oldPopulation.get(oldPopulation.size() - 1), this));
		
		while(future.isDone())
		{
			try
			{
				Thread.sleep(1);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		Collections.sort(oldPopulation);
		
		//Create new population
		//Future feature: optimize to reuse old population arrayList
		ArrayList<Genome> newPopulation = null;
		
		return newPopulation;
	}

	public ArrayList<Genome> getPopulation() 
	{
		return population;
	}
	
	/**
	 * Finds the average fitness of the current generation
	 * @return average fitness
	 */
	double averageFitness()
	{
		return totalFitness / populationSize;
	}
	
	double bestFitness()
	{
		return bestFitness;
	}
	
	public String[] getClassificationNames()
	{
		return classificationNames;
	}
	
	public double[][][] getClassifications()
	{
		return classifications;
	}
}
