package org.openimage.server;

import java.awt.Color;

import org.openimage.server.network.PacketImage;

public class Server 
{
	
	/**
	 * Main launch of the server implementation, updates network listeners and can compute anything you particularly think the server
	 * should be doing.
	 */
	
	private double[] values;
	
	public void run()
	{
		new Thread()
		{
			public void run()
			{
				while(!ServerStart.stopServerThread)
				{
					try 
					{
						for(String s : ServerStart.serverListener.playerNetworks.keySet())
						{
							ServerStart.serverListener.playerNetworks.get(s).update(.05d);
						}
						Thread.sleep(50);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	/**
	 * Called from network/PacketImage when the packet is being handled.
	 */
	public void handleImageReceive(int[] pixels, int width, int height)
	{
		values = new double[width * height];
		
		//Stores each pixel data in 64 bits in IEEE 754 double representation
		//Exponent is set to -1, 0x3FE
		//rgb values are stored in the mantissa
		for(int i = 0; i < width * height; i++) 
		{
				int rgb = pixels[i];
				long bits = rgb;
				bits >>= 11; //shifting into mantissa position
				bits |= 0x3FE; //setting exponent to -1
				values[i] = Double.longBitsToDouble(bits);
		}
		
		/*
		//Alternative implementation, storing two pixels per value
		for(int i = 0; i < width*height; i+=2) 
		{
				int rgb1 = pixels[i];
				int rgb2 = 0;
				if(i != width*height-1)
				{
					rgb2 = data[i+1];
				}
	
				long bits = rgb2;
				bits >>= 8; //shifting to make room for first pixel
				bits |= rgb1;
				bits >>= 11; //shifting into mantissa position
				bits |= 0x3FE; //setting exponent to -1
				values[i] = Double.longBitsToDouble(bits);
		}*/
	}
	
	public double[] getValues() {
		return values;
	}

}
