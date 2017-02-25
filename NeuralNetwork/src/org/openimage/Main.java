package org.openimage;

import org.openimage.genetic.GeneticAlgorithm;
import org.openimage.genetic.Genome;

public class Main 
{
	
	public static void main(String[] args)
	{
		Genome parent = Genome.getSeed();
		GeneticAlgorithm genAlg = new GeneticAlgorithm(parent);
	}

}
