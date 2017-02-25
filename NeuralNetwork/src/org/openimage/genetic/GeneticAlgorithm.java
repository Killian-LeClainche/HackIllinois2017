package org.openimage.genetic;

import java.util.ArrayList;

import org.openimage.network.FitnessFinder;

/**
 * This class generates new Genomes from a parent Genome.
 * 
 */
public class GeneticAlgorithm
{
	private Genome parent;
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
		String[] classificationNames = null; //we have to figure out how to get this 
		double[][][] classifications = null; // this too
		
		//For each genome in the old population, create a 
		//new FitnessFinder and dispatch it to a thread
		//Store all fitnesses in an array for analysis
		
		//EXAMPLE
		FitnessFinder finder = new FitnessFinder(oldPopulation.get(0));
		double fitness = finder.find(classificationNames, classifications);
		//END EXAMPLE
		
		//Create new population
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
}
