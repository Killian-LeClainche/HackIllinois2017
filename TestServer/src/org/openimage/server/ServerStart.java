package org.openimage.server;

import java.io.IOException;

import org.openimage.server.network.ServerListener;

public class ServerStart
{
	
	public static ServerListener serverListener;
	public static Server server;
	
	public static void main(String[] args) throws IOException
	{
		serverListener = ServerListener.create(8888);
		serverListener.launch();
		
		server = new Server();
	}

}
