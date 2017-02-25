package org.openimage.server;

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
	public void handleImageReceive()
	{
		System.out.println("RECEIVED!");
	}

}
