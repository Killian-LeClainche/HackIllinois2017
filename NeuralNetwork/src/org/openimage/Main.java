package org.openimage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openimage.genetic.GeneticAlgorithm;

public class Main
{
	
	public static ExecutorService taskExecutor;

	public static void main(String[] args) throws IOException
	{
		taskExecutor = Executors.newFixedThreadPool(Math.max(Runtime.getRuntime().availableProcessors() - 1, 1));
		
		GeneticAlgorithm genAlg = new GeneticAlgorithm();
	}

}
