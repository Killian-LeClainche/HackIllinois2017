package main;

import java.util.Iterator;
import java.util.List;

import fileSystem.ImageInput;
import fileSystem.InputFiles;
import genAlg.GeneticAlgorithm;
import genAlg.Genome;

public class Main
{
	public static void main(String[] args)
	{
		Genome parent = Genome.getSeed();
		List<ImageInput> inputList = InputFiles.getInputs();
		
		Iterator<ImageInput> inputIter = inputList.iterator();
		while (inputIter.hasNext())
		{
			ImageInput input = inputIter.next();
		}
	}
}
