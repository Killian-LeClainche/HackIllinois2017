package main;

import java.util.Iterator;
import java.util.List;

import fileSystem.ImageInput;
import fileSystem.InputFiles;
import genAlg.GeneticAlgorithm;
import genAlg.Genome;
import network.NeuralNetwork;
import util.Param;

public class Main
{
	public static void main(String[] args)
	{
		
		Genome parent = Genome.getSeed();
		GeneticAlgorithm genAlg = new GeneticAlgorithm(parent);
		List<ImageInput> inputList = InputFiles.getInputs();
		
		while (true)
		{
			// TODO Add threadpool and network
			NeuralNetwork network = new NeuralNetwork(genAlg.generateChild());
			Iterator<ImageInput> inputIter = inputList.iterator();
			ImageInput input = inputIter.next();
		}
	}
}
