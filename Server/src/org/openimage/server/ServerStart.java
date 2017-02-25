package org.openimage.server;

public class ServerStart
{
	
	public static void main(String[] args)
	{	
		long time = System.nanoTime();
		for(int i = 0; i < 100000000000L; i++)
		{
			int j = i + 321;
		}
		System.out.println((System.nanoTime() - time) / 1000000000D);
	}

}
