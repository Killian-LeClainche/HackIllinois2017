package org.openimage.genetic;

import java.util.ArrayList;

/**
 * This class generates new Genomes from a parent Genome.
 * 
 */
public class GeneticAlgorithm
{
	private Genome parent;
	private ArrayList<Genome> population; //Holds the entire population of genomes
	private int populationSize; //Size of the population
	private int chromosomeLength; //Weights per chromosome
	private int fittestGenome; //index of the best genome in population

	//Learning Statistics (current population)
	private int genomeCount;
	private double totalFitness;
	private double bestFitness;
	private double averageFitness;
	private double worstFitness;
	
	
	//Evolutionary Probabilities
	private double MutationRate;
	private double crossoverRate;
	
	
	public GeneticAlgorithm(Genome parent)
	{
		this.parent = parent;
	}

	public Genome generateChild()
	{
		return null;
	}
	
	//Evolutonary Methods
	
	/**
	 * Performs crossover and outputs the resulting genomes 
	 * according to the genetic algorithm's crossover rate.
	 * @param mother the first genome used to perform crossover
	 * @param father the second genome used to perform crossover
	 * @param child1 storage location for the first output genome
	 * @param child2 storage location for the second output genome
	 */
	private void Crossover(ArrayList<Double>mother, ArrayList<Double>father, ArrayList<Double>child1, ArrayList<Double>child2)
	{
		
	}
	
	/**
	 * Performs mutation on a chromosome according to the 
	 * genetic algorithm's mutation rate. Mutates by perturbing 
	 * chromosome's weights by no more than maxPerturbation
	 * @param chromosome
	 */
	private void Mutate(ArrayList<Double>chromosome)
	{
		
	}
	
	/**
	 * 
	 * @return
	 */
	private Genome GetChromosomeRoulette()
	{
		return null;
	}
	//Elitism
	private void GrabNBestGenomes(int N, int numCopies, ArrayList<Genome>population)
	{
		
	}
	
	private void ComputeStatistics()
	{
		
	}
	
	private void Reset()
	{
		
	}
}
