package main;

import java.util.Iterator;
import java.util.List;

import org.openimage.io.ImageInput;
import org.openimage.io.InputFiles;

import genAlg.GeneticAlgorithm;
import genAlg.Genome;
import util.ParamReader;

public class Main
{
	public static final Param PARAM;
	
	static
	{
		PARAM = ParamReader.loadParamsFile();
	}

	public static void main(String[] args)
	{
		
		Genome parent = Genome.getSeed();
	}
}
