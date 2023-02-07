package iks_oks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Worker extends Thread {
	
	private Socket client;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	
	public static int cnt=0;
	
	public Worker(Socket client) {
		this.client=client;
		
		try {
			this.out=new ObjectOutputStream(client.getOutputStream());
			this.in=new ObjectInputStream(client.getInputStream());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		try {
			out.writeObject(cnt++);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
