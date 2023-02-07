package iks_oks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	//private int port=4001;
	//private static int cnt=0;

	public Server() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		int port=4001;
		
		
		try {
			ServerSocket socket=new ServerSocket(port);
			
			while(true) {
				Socket client=socket.accept();
				new Worker(client).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
