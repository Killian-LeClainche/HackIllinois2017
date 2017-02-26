package org.openimage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;
import org.openimage.network.Node;

/**
 * This class loads the parameters for the application from the params.ini into
 * a Param object.
 * 
 * @author Jarett Lee
 */
public class Param
{
	/**
	 * Size of the chunk of image that the neural network should read through.
	 */
	public static final int BLOCK_SIZE;
	
	/**
	 * Never used in current implementation
	 */
	public static final int FITNESS_CASE_SIZE;
	
	/**
	 * Number of outputs for the neural network
	 */
	public static final int CATEGORY_NUM;
	
	/**
	 * Variation of each mutation, supposedly .3 is the best value for maximal benifits.
	 */
	public static final double MAX_PERTURBATION;
	
	/**
	 * How many elites should you copy over to the next generation.
	 */
	public static final int NUM_ELITE_COPIES;
	
	/**
	 * The first few best genomes to be sent over to the next generation.
	 */
	public static final int NUM_ELITE;


	/**
	 * Random
	 */
	public static Random rng = new Random();


	static
	{
		//load the params file and set all the variables
		Wini ini = loadParamsFile();
		Ini.Section section = ini.get("start");
		BLOCK_SIZE = Integer.parseInt(section.get("blockSize"));
		FITNESS_CASE_SIZE = Integer.parseInt(section.get("fitnessCaseSize"));
		CATEGORY_NUM = Integer.parseInt(section.get("categoryNum"));
		MAX_PERTURBATION = new Double(section.get("maxPerturbation"));
		NUM_ELITE_COPIES = Integer.parseInt(section.get("numEliteCopies"));
		NUM_ELITE = Integer.parseInt(section.get("numElite"));

	}

	/**
	 * Read a param.ini file and output a Param object.
	 * 
	 * @return
	 */
	public static Wini loadParamsFile()
	{
		try
		{
			return new Wini(new File("params.ini"));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (InvalidFileFormatException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		throw new RuntimeException("No params file loaded");
	}
}
