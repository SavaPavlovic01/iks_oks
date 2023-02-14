package iks_oks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	//private int port=4001;
	//private static int cnt=0;

	

	public Server() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		int port=4001;
		ArrayList<Socket> clients=new ArrayList<Socket>();
		
		try {
			ServerSocket socket=new ServerSocket(port);
			
			while(true) {
				Socket client=socket.accept();
				clients.add(client);
				if(clients.size()==2) new Worker(clients.remove(0),clients.remove(0)).start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
