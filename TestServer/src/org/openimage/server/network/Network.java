package org.openimage.server.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.openimage.server.ServerStart;


public abstract class Network
{
	
	/**
	 * Socket connection from this computer to other
	 */
	protected Socket socket;
	
	/**
	 * Input stream reading data from output of other computer. Used to generate packets and handle them.
	 */
	private DataInputStream inputStream;
	/**
	 * Output stream sending data to other computer, wrapped in Packets for ease of use.
	 */
	private DataOutputStream outputStream;
	
	/**
	 * Packets received, maintains concurrency because of multi-threading.
	 */
	private ConcurrentLinkedQueue<Packet> packets;
	/**
	 * Packets sent, maintains concurrency and holds threads that seek to add to it.
	 */
	protected LinkedBlockingQueue<Packet> packetsToSend;

	/**
	 * Variable determining whether the network is still connected.
	 */
	private boolean isConnected = true;

	/**
	 * Constructor of the abstract Network class.
	 */
	public Network()
	{
		packetsToSend = new LinkedBlockingQueue<Packet>();
		packets = new ConcurrentLinkedQueue<Packet>();
	}

	/**
	 * Called by system that seeks to establish a connection to another network.
	 * @param connectionSocket : The Socket Object with the address and port of the establishing network.
	 * @throws IOException : Issues that arise during the connection will cause this throw to be returned.
	 */
	protected void connect(Socket connectionSocket) throws IOException
	{
		socket = connectionSocket;
		
		//check validatity of connectionSocket and establish the fundamentals.
		if(socket != null)
		{
			socket.setTcpNoDelay(true);
			
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			
			outputStream.flush();

			if(inputStream == null || outputStream == null)
			{
				//make sure the socket is at least closed.
				socket.close();
				
				//has no information regarding IOException yet.
				throw new IOException("");
			}
		}
		else
		{
			//has no information regarding IOException yet.
			throw new IOException("");
		}
	}

	/**
	 * Called after connect method has been successfully called. Used to establish the direct connection to the input and
	 * output streams required to communicate with the network.
	 */
	public void validate()
	{
		//multi-threading is essential for this communication.
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					//infinite loop so long as server is upheld and connection is established.
					while(isConnected && !ServerStart.stopServerThread)
					{
						//used to determine which packet the proceeding data pertains to.
						int packet = inputStream.readShort();
						
						//length of the data needed.
						byte[] data = new byte[inputStream.readInt()];
						inputStream.readFully(data);
						packets.offer(Packet.wrap(packet, data));
					}
				}
				catch (IOException | ReflectiveOperationException e) 
				{
					e.printStackTrace();
					
					//Packet.wrap uses reflection and throws that content, so we need to guarantee that
					//the exception does not pertain to the reflection.
					
					if(e instanceof SocketException)
					{
						isConnected = false;
					}
				}
			}
		}.start();
		
		//multi-threading is essential for this communication.
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					//infinite loop so long as server is upheld and connection is established.
					while(isConnected && !ServerStart.stopServerThread)
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
