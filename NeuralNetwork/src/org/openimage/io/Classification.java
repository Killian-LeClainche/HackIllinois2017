package org.openimage.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Classification 
{
	
	/**
	 * Creates a Classification Object by reading the contents inside a folder and produces the appropriate input node vales.
	 * @param classFolder : The folder whose contents (sub files) shall be read and converted
	 * @return The Classification Object.
	 * @throws IOException : thrown when IO reading produces an error.
	 */
	public static Classification create(File classFolder) throws IOException
	{
		//name of the folder will be the name of the classification.
		String name = classFolder.getName();
		
		//Variables to be used.
		File[] subFiles = classFolder.listFiles();
		double[][] values = new double[subFiles.length][];
		BufferedImage image = null;
		
		//Iterates through all sub files,
		for(int i = 0; i < subFiles.length; i++)
		{
			File f = subFiles[i];
			
			//read the image.
			image = ImageIO.read(f);
			values[i] = new double[image.getWidth() * image.getHeight()];
			
			//calculate luminance of the photo
			for(int j = 0; j < values[i].length; j ++) 
			{
				int bits = image.getRGB(j / image.getWidth(), j % image.getWidth());
				double red = (bits >> 16) & 0xFF;
				double green = (bits >> 8) & 0xFF;
				double blue = bits & 0xFF;
				values[i][j] = 0.2126 * red + 0.7152 * green + 0.0722 * blue;
			}
		}
		
		return new Classification(name, values);
	}
	
	/**
	 * Name of the classification found by the name of the folder.
	 */
	private String classification;
	/**
	 * The converted data to RAM exclusive luminance.
	 */
	private double[][] imageInputNodes;
	
	/**
	 * Constructor of the Classification, it should be kept private.
	 * @param name : Name of the classification
	 * @param values : All the image data
	 */
	private Classification(String name, double[][] values)
	{
		classification = name;
		imageInputNodes = values;
	}
	
	/**
	 * @return Name of the classification.
	 */
	public String getName()
	{
		return classification;
	}
	
	/**
	 * @param index : returns the image data associated with the particular index.
	 * @return
	 */
	public double[] getImageInputNodes(int index)
	{
		return imageInputNodes[index];
	}
	
	/**
	 * @return Size of the classifications pool.
	 */
	public int getPoolSize()
	{
		return imageInputNodes.length;
	}

}
