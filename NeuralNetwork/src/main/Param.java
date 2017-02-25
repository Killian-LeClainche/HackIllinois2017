package main;

import org.ini4j.Ini;
import org.ini4j.Wini;

/**
 * This class contains the parameters for the application.
 * 
 * @author Jarett Lee
 */
public class Param
{
	private final int blockSize;

	public Param(Wini ini)
	{
		Ini.Section section = ini.get("start");
		blockSize = Integer.parseInt(section.get("blockSize"));
	}

	public int getBlockSize()
	{
		return blockSize;
	}
	
	public String toString()
	{
		return "" + blockSize;
	}
}
