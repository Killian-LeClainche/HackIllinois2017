package org.openimage.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A (image, classification) pair used to train the neural network or access the
 * fitness of the neural network
 *
 */
public class Classification
{

	public static Classification create(File classFolder) throws IOException
	{
		String name = classFolder.getName();

		File[] subFiles = classFolder.listFiles();
		double[][] values = new double[subFiles.length][];
		BufferedImage image = null;

		for (int i = 0; i < subFiles.length; i++)
		{
			File f = subFiles[i];

			image = ImageIO.read(f);
			values[i] = new double[image.getWidth() * image.getHeight()];

			for (int j = 0; j < values[i].length; j++)
			{
				long bits = image.getRGB(j / image.getWidth(), j % image.getWidth());
				bits &= 16777215;
				bits <<= 28; // shifting into mantissa position
				bits |= 0x3FE0000000000000L; // setting exponent to -1

				values[i][j] = Double.longBitsToDouble(bits);
			}
		}

		return new Classification(name, values);
	}

	private String classification;
	private double[][] imageInputNodes;

	private Classification(String name, double[][] values)
	{
		classification = name;
		imageInputNodes = values;
	}

	public String getClassification()
	{
		return classification;
	}

	public double[][] getImageInputNodes()
	{
		return imageInputNodes;
	}

}
