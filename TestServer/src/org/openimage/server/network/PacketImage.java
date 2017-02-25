package org.openimage.server.network;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.openimage.server.ServerStart;

public class PacketImage extends Packet
{
	
	private int width, height;
	private int[] pixels;
	
	public PacketImage() {}
	
	public PacketImage(BufferedImage image)
	{
		width = image.getWidth();
		height = image.getHeight();
		
		pixels = new int[width*height];
		
		int x = 0;
		for(int i = 0; i < width; i++)
		{
			for(int j = 0; j < height; j++)
			{
				pixels[x] = image.getRGB(i, j);
				x++;
			}
		}
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException 
	{
		output.writeInt(width);
		output.writeInt(height);
		
		for(int i = 0; i < width * height; i++)
		{
			output.writeInt(pixels[i]);
		}
	}

	@Override
	public void copy(DataInputStream data) throws IOException 
	{
		width = data.readInt();
		height = data.readInt();
		
		pixels = new int[width * height];
		
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = data.readInt();
		}
	}

	@Override
	public void handle(Network network) 
	{
		//TEST HERE, WILL BE REMOVED!
		/*System.out.println("RECEIVED IMAGE");
		System.out.print("WIDTH: " + width + " HEIGHT: " + height + " IS VALID: ");
		boolean flag = false;
		for(int i = 0; i < pixels.length && !flag; i++)
		{
			for(int j = 0; j < pixels[i].length && !flag; j++)
			{
				if(pixels[i][j] != 0)
					flag = true;
			}
		}
		System.out.println(flag + " TIME RECEIVED: " + System.currentTimeMillis());
		*/
		//CONTINUE TO NON-TEST CODE
		ServerStart.server.handleImageReceive(pixels, width);
	}

}
