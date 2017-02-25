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
<<<<<<< HEAD
	
	
	public geneticAlgorithm(Genome parent)
=======

	public GeneticAlgorithm(Genome parent)
>>>>>>> e320fe5fa28a5df9337940ed6fa40feee524f9ac
	{
		this.parent = parent;
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
<<<<<<< HEAD
	private void crossover(ArrayList<Double>mother, ArrayList<Double>father, ArrayList<Double>child1, ArrayList<Double>child2)
=======
	private void Crossover(ArrayList<Double> mother, ArrayList<Double> father, ArrayList<Double> child1,
			ArrayList<Double> child2)
>>>>>>> e320fe5fa28a5df9337940ed6fa40feee524f9ac
	{

	}

	/**
<<<<<<< HEAD
	 * Performs mutation on a chromosome according to the 
	 * genetic algorithm's mutation rate. Mutates by perturbing 
	 * chromosome's weights by no more than MAX_PERTURBATION
	 * @param chromosome
	 */
	private void mutate(ArrayList<Double>chromosome)
=======
	 * Performs mutation on a chromosome according to the genetic algorithm's
	 * mutation rate. Mutates by perturbing chromosome's weights by no more than
	 * maxPerturbation
	 * 
	 * @param chromosome
	 */
	private void Mutate(ArrayList<Double> chromosome)
>>>>>>> e320fe5fa28a5df9337940ed6fa40feee524f9ac
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
<<<<<<< HEAD
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
=======

	// Elitism
	private void GrabNBestGenomes(int N, int numCopies, ArrayList<Genome> population)
>>>>>>> e320fe5fa28a5df9337940ed6fa40feee524f9ac
	{

	}
<<<<<<< HEAD
	
	private void reset()
=======

	private void ComputeStatistics()
>>>>>>> e320fe5fa28a5df9337940ed6fa40feee524f9ac
	{

	}
<<<<<<< HEAD
	
	public ArrayList<Genome> epoch(ArrayList<Genome> oldPopulation)
	{
		//For each genome in the old population, create a 
		//new FitnessFinder and dispatch it to a thread
		//Store all fitnesses in an array for analysis
		
		//EXAMPLE
		FitnessFinder finder = new FitnessFinder(oldPopulation.get(0));
		double fitness = finder.find();
		//END EXAMPLE
		
		//Create new population
		ArrayList<Genome> newPopulation;
		return newPopulation;
=======

	private void Reset()
	{

>>>>>>> e320fe5fa28a5df9337940ed6fa40feee524f9ac
	}
}
