package org.openimage.server;

import java.io.IOException;

public class ServerStart
{
	
	public static void main(String[] args) throws IOException
	{
		ServerListener listener = new ServerListener();
		listener.launch();
	}

}
