package org.openimage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

/**
 * This class loads the parameters for the application from the params.ini into
 * a Param object.
 * 
 * @author Jarett Lee
 */
public class Param
{
	public static final int BLOCK_SIZE;
	public static final int FITNESS_CASE_SIZE;
<<<<<<< HEAD
	public static Random rng = new Random();


=======
	public static final double MAX_PERTURBATION;
>>>>>>> e320fe5fa28a5df9337940ed6fa40feee524f9ac
	static
	{
		Wini ini = loadParamsFile();
		Ini.Section section = ini.get("start");
		BLOCK_SIZE = Integer.parseInt(section.get("blockSize"));
		FITNESS_CASE_SIZE = Integer.parseInt(section.get("fitnessCaseSize"));
		MAX_PERTURBATION = Integer.parseInt(section.get("maxPerturbation"));

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
