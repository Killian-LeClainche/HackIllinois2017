package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import main.Param;

/**
 * This class loads the parameters for the application from the params.ini into a Param object.
 * 
 * @author Jarett Lee
 */
public class ParamReader
{
	/**
	 * Read a param.ini file and output a Param object.
	 */
	public static Param loadParamsFile()
	{
		Wini ini = null;
		Param param = null;
		
		try
		{
			ini = new Wini(new File("params.ini"));
			param = new Param(ini);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InvalidFileFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		assert param != null;
		return param;
	}
	
	/*
	 * Test for the file.
	 */
	public static void main(String[] args)
	{
		Param param = loadParamsFile();
		System.out.println(param);
	}
}
