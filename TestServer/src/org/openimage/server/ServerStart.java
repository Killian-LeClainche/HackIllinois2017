package org.openimage.server;

import java.io.IOException;
import java.util.Scanner;

import org.openimage.server.network.ServerListener;

public class ServerStart
{
	
	public static boolean stopServerThread = false;
	
	public static ServerListener serverListener;
	public static Server server;
	
	public static void main(String[] args) throws IOException
	{
		serverListener = ServerListener.create(8888);
		serverListener.launch();
		
		server = new Server();
		server.run();
		
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		stopServerThread = true;
	}

}
