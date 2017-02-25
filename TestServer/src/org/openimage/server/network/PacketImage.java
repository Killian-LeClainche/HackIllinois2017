package org.openimage.server.network;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openimage.server.ServerStart;

public class PacketImage extends Packet
{
	
	private int width, height;
	private int[][] pixels;
	
	public PacketImage() {}
	
	public PacketImage(BufferedImage image)
	{
		width = image.getWidth();
		height = image.getHeight();
		
		pixels = new int[width][height];
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				pixels[i][j] = image.getRGB(i, j);
			}
		}
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException 
	{
		output.writeInt(width);
		output.writeInt(height);
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				output.writeInt(pixels[i][j]);
			}
		}
	}

	@Override
	public void copy(DataInputStream data) throws IOException 
	{
		width = data.readInt();
		height = data.readInt();
		
		pixels = new int[width][height];
		
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				pixels[i][j] = data.readInt();
			}
		}
	}

	@Override
	public void handle(Network network) 
	{
		ServerStart.server.handleImageReceive(pixels);
	}

}
