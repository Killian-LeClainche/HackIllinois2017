package org.openimage.server;

import java.io.IOException;
import java.util.Scanner;

import org.openimage.server.network.Packet;
import org.openimage.server.network.PacketImage;
import org.openimage.server.network.ServerListener;

public class ServerStart
{
	
	public static boolean stopServerThread = false;
	
	public static ServerListener serverListener;
	public static Server server;
	
	public static void main(String[] args) throws IOException
	{
		System.out.println("INITIALIZING!");
		Packet.addPacket(PacketImage.class);
		
		System.out.println("STARTING!");
		serverListener = ServerListener.create(8888);
		serverListener.launch();
		
		System.out.println("LAUNCHED LISTENER!");
		
		server = new Server();
		server.run();
		
		System.out.println("LAUNCHED SERVER!");
		
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		
		System.out.println("CLOSING SYSTEM DOWN!");
		
		stopServerThread = true;
		System.exit(1);
	}

}
