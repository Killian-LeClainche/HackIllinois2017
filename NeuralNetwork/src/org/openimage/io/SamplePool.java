package org.openimage.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SamplePool
{

	/**
	 * Generates the Sample Pool from a given image folder that the system will
	 * read through and generate the data.
	 * 
	 * @param imageFolder
	 *            : Main folder that contains all the classifications.
	 * @return A Sample Pool Object for use.
	 * @throws IOException
	 *             : thrown when an Image cannot be properly read.
	 */
	public static SamplePool create(File imageFolder) throws IOException
	{
		// Find all sub folders
		File[] subFolders = imageFolder.listFiles(new FileFilter()
		{

			public boolean accept(File arg0)
			{
				return arg0.isDirectory();
			}
		});

		// Create the array and set each index to a Classification Object.
		Classification[] names = new Classification[subFolders.length];

		for (int i = 0; i < names.length; i++)
		{
			names[i] = Classification.create(subFolders[i]);
		}

		return new SamplePool(names);
	}

	/**
	 * Custom Random object that will be used to obtain a generations sample
	 * pool.
	 */
	private Random random;

	/**
	 * Array for all classifications including the not classification.
	 */
	private Classification[] classifications;

	/**
	 * Sample Pool Constructor, is private for a reason.
	 * 
	 * @param names
	 *            : All the classifications to be used.
	 */
	private SamplePool(Classification[] names)
	{
		random = new Random();
		classifications = names;
	}

	public int getClassificationSize()
	{
		return classifications.length;
	}

	public int getPoolSize(int index)
	{
		return classifications[index].getPoolSize();
	}

	public String getClassificationName(int index)
	{
		return classifications[index].getName();
	}

	/**
	 * 
	 * @param classification
	 * @param sampleSize
	 * @return
	 */
	public double[][] getSamplePool(int classification, int sampleSize)
	{
		// Create variables, one which is the classification sample, the other
		// is the random pool of samples inside it.
		Classification sample = classifications[classification];
		List<Integer> randomList = new LinkedList<Integer>();

		// Get unique random samples in the classificiation.
		while (randomList.size() < sampleSize)
		{
			int randomValue = random.nextInt(sample.getPoolSize());
			if (!randomList.contains(randomValue))
			{
				randomList.add(randomValue);
			}
		}

		// Clone all the data from the classification into a new array to
		// prevent any base edits to the sample data.
		double[][] samples = new double[sampleSize][];
		for (int i = 0; i < sampleSize; i++)
		{
			// get random samples data.
			double[] values = sample.getImageInputNodes(randomList.get(i));

			samples[i] = new double[values.length];

			for (int j = 0; j < values.length; j++)
			{
				samples[i][j] = values[j];
			}
		}

		return samples;
	}

}
