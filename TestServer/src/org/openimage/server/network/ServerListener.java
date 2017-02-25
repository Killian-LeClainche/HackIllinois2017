package org.openimage.server.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.openimage.server.ServerStart;

public class ServerListener 
{
	
	public static ServerListener create(int port)
	{
		try
		{
			return new ServerListener(port);
		}
		catch(IOException e)
		{
			System.err.println("COULD NOT CREATE SERVER LISTENER!");
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<String, ServerNetwork> playerNetworks;
	private ServerSocket listener;
	
	private ServerListener(int port) throws IOException
	{
		listener = new ServerSocket(8888);
		playerNetworks = new HashMap<String, ServerNetwork>();
	}
	
	public void launch()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				Socket clientSocket;
				ServerNetwork newClient;
				while(!ServerStart.stopServerThread)
				{
					try 
					{
						clientSocket = listener.accept();
					} 
					catch (IOException e)
					{
						e.printStackTrace();
						continue;
					}
					try 
					{
						newClient = new ServerNetwork(clientSocket);
						if(playerNetworks.containsKey(newClient.getUUID()))
						{
							playerNetworks.get(newClient.getUUID()).invalidate();
							playerNetworks.remove(newClient.getUUID());
						}
						newClient.validate();
						playerNetworks.put(newClient.getUUID(), newClient);
					} 
					catch (IOException e)
					{
						e.printStackTrace();
						continue;
					}
				}
				try 
				{
					listener.close();
					for(String uuid : playerNetworks.keySet())
					{
						playerNetworks.get(uuid).invalidate();
					}
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

}
