package org.openimage.genetic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

import org.openimage.Main;
import org.openimage.Param;
import org.openimage.io.SamplePool;
import org.openimage.network.FitnessFinder;
import org.openimage.network.NeuralNetwork;
/**
 * This class generates new Genomes from a parent Genome.
 * 
 */
public class GeneticAlgorithm
{
	private SamplePool samplePool;

	private List<Genome> population; // Holds the entire population of
	// genomes
	private int populationSize; // Size of the population
	private int genomeLength; // Weights per genome

	private double totalFitness;
	private double bestFitness;
	private double averageFitness;
	private double worstFitness;

	// Evolutionary Probabilities
	private double mutationRate;
	private double crossoverRate;

	private int count = 1;

	private String[] classificationNames; //we have to figure out how to get this 
	private double[][][] classifications; // this too

	public GeneticAlgorithm() throws IOException
	{
		//generate the sample pool inside the images file
		samplePool = SamplePool.create(new File("images"));
		
		//classification pool creation
		classificationNames = new String[samplePool.getClassificationSize()];
		classifications = new double[samplePool.getClassificationSize()][][];

		//set the names
		for(int i = 0; i < classificationNames.length; i++)
		{
			classificationNames[i] = samplePool.getClassificationName(i);
		}

		population = new ArrayList<Genome>();
		populationSize = 63;

		//generate the genomes
		for(int i = 0; i < populationSize; i++)
		{
			Genome g = new NeuralNetwork().getGenome();
			g.id = i;
			population.add(g);	
		}

		mutationRate = .2;
		crossoverRate = 0.7;
		genomeLength = population.get(0).getWeights().size();
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
	private void crossover(List<Double>mother, List<Double>father, List<Double>child1, List<Double>child2)
	{
		Random generator = new Random();
		//If the random value is outside the crossover rate or parents are the same, do not crossover
		//determine the crossover point on the genome
		int crossoverPoint = generator.nextInt(genomeLength);

		//create new offspring
		for(int i = 0; i < crossoverPoint; i++)
		{
			Double d = new Double(mother.get(i));
			child1.add(d);
			Double d2 = new Double(father.get(i));
			child2.add(d2);
		}
		for (int i = crossoverPoint; i < mother.size(); i++)
		{
			Double d = new Double(father.get(i));
			child1.add(d);
			Double d2 = new Double(mother.get(i));
			child2.add(d2);
		}
		
	}

	/**
	 * Performs mutation on a genome according to the 
	 * genetic algorithm's mutation rate. Mutates by perturbing 
	 * genome's weights by no more than MAX_PERTURBATION
	 * @param genome
	 */
	private void mutate(List<Double>genome)
	{
		Random generator = new Random();
		//Traverse genome and perturb weights based on mutation rate
		for(int i = 0; i < genome.size(); i++)
		{
			if(generator.nextDouble() < mutationRate)
			{
				genome.set(i, Math.max(Math.min(genome.get(i) + ((generator.nextDouble()-generator.nextDouble()) * Param.MAX_PERTURBATION), 1), -1));
			}
		}
	}

	/**
	 * Selects a genome using roulette wheel sampling 
	 * (random selection with evolutionary benefits)
	 * @return genome to be used in crossover
	 */
	private Genome getGenomeRoulette()
	{
		//generate a random number between 0 & total fitness count
		double slice = (double)(Math.max(Math.random() - 0.1, 0) * totalFitness);

		//this will be set to the chosen genome
		Genome selectedGenome = null;

		//sum the fitness of genomes
		double cumulativeFitness = 0;

		for (int i = 0; i < populationSize; i++)
		{
			cumulativeFitness += population.get(i).fitness;

			//if the cumulative fitness > random number return the genome at 
			//this point
			if (cumulativeFitness >= slice)
			{
				selectedGenome = population.get(i);
				break;
			}
		}
		return selectedGenome;
	}

	/** 
	 * Introduces elitism by selecting the most fit genomes 
	 * to propagate into the next generation
	 * Precondition: population is sorted by fitness
	 * @param n the number of most fit genomes to select
	 * @param numCopies number of copies of each n most fit genomes
	 * @param population the output population of most fit genomes
	 */
	private void grabNBestGenomes(int n, int numCopies, List<Genome>population)
	{
		//iterate through to n, taking best genomes and propogating them to next generation.
		int index = 0;
		while(index < n)
		{
			int genomeIndex = index; //index of the most fit genome not currently added numCopies times
			for(int i = 0; i < numCopies; i++)
			{
				Genome genome = new Genome(this.population.get(genomeIndex).weights, this.population.get(genomeIndex).id);
				population.add(genome);
			}
			index++;
		}
	}

	/**
	 * Computes fitness statistics for current generation stored in 
	 * totalFitness, averageFitness, bestFitness, worstFitness
	 */
	private void computeStatistics()
	{
		//lambda function for total fitness.
		population.forEach(g -> totalFitness += g.fitness);
		
		//other variables.
		averageFitness = totalFitness / population.size();
		bestFitness = population.get(0).fitness;
		worstFitness = population.get(population.size() - 1).fitness;
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
	public List<Genome> epoch()
	{
		//Reset fitness variables
		reset();

		if(-- count == 0 || bestFitness > 125 * .9)
		{
			//generate a random sample for all classifications.
			for(int i = 0; i < classificationNames.length; i++)
			{
				classifications[i] = samplePool.getSamplePool(i, 125);
			}
			count = 300;
			System.out.println("NEW POOL!");
		}

		//create a future list 
		List<Future<?>> futures = new ArrayList<Future<?>>();

		//add all the futures to monitor to guarantee that all Fitness's have been found.
		for(int i = 0; i < population.size(); i++)
		{
			futures.add(Main.taskExecutor.submit(new FitnessFinder(population.get(i), this)));
		}

		//wait until all futures are done.
		while(futures.size() != 0)
		{
			for(int i = 0; i < futures.size(); i++)
			{
				if(futures.get(i).isDone())
				{
					futures.remove(i);
					i--;
				}
			}
		}

		//sort population for scaling and elitism
		Collections.sort(population);
		//compute the test cases
		computeStatistics();

		//ArrayList to hold new population
		//Future feature: optimize to reuse old population arrayList
		List<Genome> newPopulation = new ArrayList<Genome>();

		//Add the fittest genomes back in for elitism
		grabNBestGenomes(Param.NUM_ELITE, Param.NUM_ELITE_COPIES, newPopulation);

		//Genetic Algorithm Loop
		//repeat until a new population is generated
		while (newPopulation.size() < populationSize)
		{
			//Use two genomes
			Genome mother = getGenomeRoulette();
			Genome father = getGenomeRoulette();
						
			
			//create some offspring via crossover
			List<Double> child1 = null;
			List<Double> child2 = null;

			//if mother and father are same genome or it's over the crossover rate
			if((mother == father) || Math.random() > crossoverRate)
			{
				child1 = mother.getWeights();
				child2 = father.getWeights();
			}
			else
			{
				//else replicate evolution crossing over
				child1 = new ArrayList<Double>();
				child2 = new ArrayList<Double>();
				crossover(mother.weights, father.weights, child1, child2);
			}

			//now we mutate
			mutate(child1);
			mutate(child2);

			//now copy into vecNewPop population
			newPopulation.add(new Genome(child1));
			newPopulation.add(new Genome(child2));
		}
		//finished so assign new pop back into population
		population = newPopulation;

		return newPopulation;
	}

	/**
	 * Get population of genomes
	 * @return
	 */
	public List<Genome> getPopulation() 
	{
		return population;
	}

	/**
	 * Get the best fitness of this generation
	 * @return
	 */
	public double getBestFitness()
	{
		return bestFitness;
	}

	/**
	 * Get the average fitness of the genomes of this generation
	 * @return
	 */
	public double getAverageFitness()
	{
		return averageFitness;
	}

	/**
	 * Get the worst fitness of this generation
	 * @return
	 */
	public double getWorstFitness()
	{
		return this.worstFitness;
	}

	/**
	 * Get the total fitness of this generation of genomes.
	 * @return
	 */
	public double getTotalFitness()
	{
		return this.totalFitness;
	}

	/**
	 * All the classifications
	 * @return
	 */
	public String[] getClassificationNames()
	{
		return classificationNames;
	}

	/**
	 * All the classifications sample pool
	 * @return
	 */
	public double[][][] getClassifications()
	{
		return classifications;
	}
}
