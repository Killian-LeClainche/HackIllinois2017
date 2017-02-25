package org.openimage.server;

import java.awt.Color;

import org.openimage.server.network.PacketImage;

public class Server 
{
	
	/**
	 * Main launch of the server implementation, updates network listeners and can compute anything you particularly think the server
	 * should be doing.
	 */
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
	public void handleImageReceive(int[][] data)
	{
		int width = data.length;
		int height = data[0].length;
		//setting exponent of IEEE 754 double representation to -1
		for(int i = 0; i < width; i++) 
		{
			for(int j = 0; j < height; j++) {
				String bits = "0x3f";
				
			}
		}
	}

}
