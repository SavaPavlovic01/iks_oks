package iks_oks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Worker extends Thread {
	
	private Socket client1;
	private Socket client2;
	
	private ObjectInputStream in1;
	private ObjectOutputStream out1;
	private ObjectInputStream in2;
	private ObjectOutputStream out2;
	
	
	public static int cnt=0;
	
	public Worker(Socket client1,Socket client2) {
		this.client1=client1;
		this.client2=client2;
		/* 
		try {
			
			this.out1=new ObjectOutputStream(client1.getOutputStream());
			this.in1=new ObjectInputStream(client1.getInputStream());
			this.out2=new ObjectOutputStream(client2.getOutputStream());
			this.in2=new ObjectInputStream(client2.getInputStream());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	public void run() {
		/* 
		out1.writeObject(true);
		out1.writeObject(cnt++);

		out2.writeObject(false);
		out2.writeObject(cnt++);
		*/
		new ServerListener(client1, client2,true).start();
		new ServerListener(client2, client1,false).start();
	}

}
