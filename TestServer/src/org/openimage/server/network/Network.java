package org.openimage.server.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


public abstract class Network
{
	
	private ConcurrentLinkedQueue<Packet> packets;

	protected Socket socket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;

	protected LinkedBlockingQueue<Packet> packetsToSend;

	private boolean isConnected = true;

	public Network()
	{
		packetsToSend = new LinkedBlockingQueue<Packet>();
		packets = new ConcurrentLinkedQueue<Packet>();
	}

	protected void connect(Socket connectionSocket) throws IOException
	{
		socket = connectionSocket;
		if(socket != null)
		{
			socket.setTcpNoDelay(true);
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			outputStream.flush();

			if(inputStream == null || outputStream == null)
			{
				socket.close();
				throw new IOException("");
			}
		}
		else
		{
			throw new IOException("");
		}
	}

	public void validate()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try 
				{
					while(isConnected)
					{
						int packet = inputStream.readShort();
						byte[] data = new byte[inputStream.readInt()];
						inputStream.readFully(data);
						packets.offer(Packet.wrap(packet, data));
					}
				} 
				catch (IOException | ReflectiveOperationException e) 
				{
					e.printStackTrace();
					if(e instanceof SocketException)
					{
						isConnected = false;
					}
				}
			}
		}.start();
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					while(isConnected)
					{
						Packet packetToSend = packetsToSend.take();
						ByteArrayOutputStream data = new ByteArrayOutputStream();
						DataOutputStream dataStream = new DataOutputStream(data);
						outputStream.writeShort(packetToSend.getHeader());
						packetToSend.writeData(dataStream);
						outputStream.writeInt(data.size());
						data.writeTo(outputStream);
						outputStream.flush();
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
					if(e instanceof SocketException)
					{
						isConnected = false;
					}
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void update(double delta)
	{
		Packet nextPacket = null;
		while((nextPacket = packets.poll()) != null)
		{
			nextPacket.handle(this);
		}
	}

	public void sendPacket(Packet packetToSend)
	{
		try 
		{
			packetsToSend.put(packetToSend);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}

	public void invalidate() throws IOException 
	{
		socket.close();
	}

	public boolean isConnected()
	{
		return isConnected;
	}

}
