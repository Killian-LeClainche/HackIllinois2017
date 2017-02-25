package org.openimage.server.network;

import java.io.IOException;
import java.net.Socket;

public class ServerNetwork extends Network
{

	private String uuid;

	public ServerNetwork(Socket clientSocket) throws IOException
	{
		connect(clientSocket);
		uuid = clientSocket.getRemoteSocketAddress().toString();
	}

	public String getUUID()
	{
		return uuid;
	}

}
