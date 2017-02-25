package org.openimage.genetic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Future;

import org.openimage.Main;
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
	private int genomeCount;
	private double totalFitness;
	private double bestFitness;
	private double averageFitness;
	private double worstFitness;

	// Evolutionary Probabilities
	private double MutationRate;
	private double crossoverRate;

	private String[] classificationNames; //we have to figure out how to get this 
	private double[][][] classifications; // this too
	
	public GeneticAlgorithm() throws IOException
	{
		samplePool = SamplePool.create(new File("images"));
		classificationNames = new String[samplePool.getClassificationSize()];
		classifications = new double[samplePool.getClassificationSize()][][];
	}

	public Genome generateChild()
	{
		return null;
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

	}

	/**
	 * Performs mutation on a chromosome according to the 
	 * genetic algorithm's mutation rate. Mutates by perturbing 
	 * chromosome's weights by no more than MAX_PERTURBATION
	 * @param chromosome
	 */
	private void mutate(ArrayList<Double>chromosome)
	{

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
	 * @param n the number of most fit genomes to select
	 * @param numCopies 
	 * @param population the output population of highly fit genomes
	 */
	private void grabNBestGenomes(int n, int numCopies, ArrayList<Genome>population)
	{
		
	}
	
	private void computeStatistics()
	{
		population.forEach(genome -> averageFitness += genome.fitness);
		totalFitness = averageFitness;
		averageFitness /= population.size();
		bestFitness = population.get(0).fitness;
		worstFitness = population.get(population.size() - 1).fitness;
	}
	
	private void reset()
	{

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
		ArrayList<Genome> newPopulation;
		return newPopulation;
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
