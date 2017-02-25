package org.openimage.server;

public class Server 
{
	
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
	
	public void handleImageReceive()
	{
		System.out.println("RECEIVED!");
	}

}
