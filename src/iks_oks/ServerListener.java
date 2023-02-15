package iks_oks;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerListener extends Thread {
    
    private Socket client1;
	private Socket client2;
    static int cnt=0;
	
	private ObjectInputStream in1;
	private ObjectOutputStream out1;
	private ObjectInputStream in2;
	private ObjectOutputStream out2;

    private boolean first;

   
    public ServerListener(Socket client1,Socket client2,boolean first) {
		this.client1=client1;
		this.client2=client2;
		this.first=first;
        System.out.println("MADE");
		try {
			this.in1=new ObjectInputStream(client1.getInputStream());
            
            if(in1==null) System.out.println("NULL");
			this.out2=new ObjectOutputStream(client2.getOutputStream());
            if(out2==null) System.out.println("NULL");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    @Override
    public void run(){
        try {
            out2.writeObject(!first);
            
            out2.writeObject(cnt++);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while(true){
            try {
                Object i= in1.readObject();
                //System.out.println(i);
                out2.writeObject(i);
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
