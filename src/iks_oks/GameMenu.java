package iks_oks;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Label;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameMenu extends JFrame{
	
	private final int width=500;
	private final int height=800;
	private JButton connect;
	private String host="localhost";
	private int port=4001;
	private String name="No Name";
	private Socket client;
	private JPanel panelCenter=new JPanel();
	private JTextField tf=new JTextField();
	private JButton saveUser;
	
	public GameMenu() {
		super();
		this.setVisible(true);
		this.setTitle("Iks oks");
		this.setSize(width, height);
		
		panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.PAGE_AXIS));
		panelCenter.add(new Label("User name:"));
		
		tf.setSize(200, 50);
		panelCenter.add(tf);
		
		saveUser=new JButton("Save username");
		saveUser.addActionListener((ae)->{
			name=tf.getText();
		});
		
		
		panelCenter.add(saveUser);
		
		connect=new JButton("Connect");
		connect.addActionListener((ae)->{
			connectToServer();
			new Game(client,name);
			this.dispose();
		});
		
		panelCenter.add(connect);
		
		this.add(panelCenter,BorderLayout.CENTER);
		
	}
	
	
	private void connectToServer() {
		// TODO Auto-generated method stub
		try {
			client=new Socket(host,port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		new GameMenu();
	}
	
}
