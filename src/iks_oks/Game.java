package iks_oks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;

public class Game extends JFrame {
	
	private final int width=500;
	private final int height=800;
	
	private String name;
	private Socket client;
	private int id;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	public Field[][] matrix=new Field[3][3];
	
	//private Field field=new Field();
	private JPanel center=new JPanel();
	private JButton reset=new JButton("reset");
	private String turn="X";
	private boolean done=false;

	private int cntTurn=0;
	private boolean go=false;
	private boolean first=true;

	private Listener listener;
	public boolean OpWantsReset=false;
	public boolean wantsReset=false;

	public JTextArea chat=new JTextArea();
	public JTextField chat1=new JTextField();
	
	public String getTurn() {
		return turn;
	}
	
	public boolean getDone() {return done;}

	public ObjectInputStream getIn(){return in;}

	public ObjectOutputStream getOut(){return out;}

	public boolean getGo(){return go;}

	public void writeMessage(String message,Color color){
		Color colorOld=chat.getForeground();
		if(color==null) color=chat.getForeground();
		chat.setForeground(color);
		chat.append(message);
		chat.setForeground(colorOld);
	}
	
	public Game(Socket client,String name) {
		super();
		this.client=client;
		this.name=name;
		//this.first=first;

		try {
			this.out=new ObjectOutputStream(client.getOutputStream());
			this.in=new ObjectInputStream(client.getInputStream());
			first=(boolean) in.readObject();
			id=(int) in.readObject();
			System.out.println(id);
			System.out.println(name);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setVisible(true);
		this.setSize(width, height);
		this.setName("Iks oks");
		
		center.setLayout(new GridLayout(3,3,10,10));
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				
				Field fi=new Field(this,i,j);
				center.add(fi);
				matrix[i][j]=fi;
			}	
			
			
		}
		
		center.setBackground(Color.GRAY);
		
		//repaint();
		this.add(center,BorderLayout.CENTER);
		
		reset.setEnabled(false);
		this.add(reset,BorderLayout.SOUTH);
		
		reset.addActionListener((ae)->{
			reset();
		});
		
		//listener=new Listener(this);

		listener=new Listener(this);
		listener.start();

		go=first;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		

		JPanel panelEast=new JPanel();
		panelEast.setLayout(new BoxLayout(panelEast,BoxLayout.PAGE_AXIS));

		chat.setEditable(false);
		
		chat.setPreferredSize(new Dimension(20000,20000));

		JScrollPane sp = new JScrollPane(chat,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setPreferredSize(new Dimension(this.getWidth()/3,this.getHeight()-50));
		//chat.setSize(this.width/4, this.height);
		chat1.setPreferredSize(new Dimension(this.getWidth()/3,20));
		panelEast.add(sp);
		panelEast.add(chat1);

		this.add(panelEast,BorderLayout.EAST);

		chat1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String message=name+':'+chat1.getText()+'\n';
				
				chat.append(name+':'+chat1.getText()+'\n');
				chat1.setText("");
				try {
					out.writeObject(13);
					System.out.println(message);
					out.writeObject(message);
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}});

			
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		this.setBackground(Color.GREEN);
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				matrix[i][j].crtaj(g);
			}
		}
		
		
	}
	
	private void checkWin() {
		if(matrix[0][0].getType().equals(turn) && matrix[0][1].getType().equals(turn) 
				&& matrix[0][2].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
		
		if(matrix[1][0].getType().equals(turn) && matrix[1][1].getType().equals(turn) 
				&& matrix[1][2].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
		
		if(matrix[2][0].getType().equals(turn) && matrix[2][1].getType().equals(turn) 
				&& matrix[2][2].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
		
		if(matrix[0][0].getType().equals(turn) && matrix[1][0].getType().equals(turn) 
				&& matrix[2][0].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
		
		if(matrix[0][1].getType().equals(turn) && matrix[1][1].getType().equals(turn) 
				&& matrix[2][1].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
		
		if(matrix[0][2].getType().equals(turn) && matrix[1][2].getType().equals(turn) 
				&& matrix[2][2].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
		
		if(matrix[0][0].getType().equals(turn) && matrix[1][1].getType().equals(turn) 
				&& matrix[2][2].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
		
		if(matrix[0][2].getType().equals(turn) && matrix[1][1].getType().equals(turn) 
				&& matrix[2][0].getType().equals(turn) && !done ) {System.out.println(turn+" wins"); done=true; reset.setEnabled(true);}
	}

	private void checkDraw(){
		if(cntTurn==9){
			done=true;
			reset.setEnabled(true);
			System.out.println("Draw!");
		}
	}

	private void getPlay(){
		try {
			int i=(int) in.readObject();
			int j=(int) in.readObject();
			matrix[i][j].updateField();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateM(int i,int j) {
		cntTurn++;
		repaint();
		checkWin();
		checkDraw();
		try {
			
			if(go){
				Integer ii=i;
				Integer jj=j;
				out.writeObject(10);
				out.writeObject(ii);
				//System.out.println("send i");
				out.writeObject(jj);
				//System.out.println("sent j");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(turn.equals("X")) turn="O";
		else turn="X";

		//if(go){go=false;}else {go=true;}
		go=!go;

		
	}
	
	public void reset() {
		if(!wantsReset && OpWantsReset){
			try {
				out.writeObject(12);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		wantsReset=true;
		reset.setEnabled(false);
		if(OpWantsReset){
			for(int i=0;i<3;i++) {
				for(int j=0;j<3;j++) {
					matrix[i][j].setType("N");
				}
			}
			done=false;
			wantsReset=false;
			OpWantsReset=false;
			turn="X";
			reset.setEnabled(false);
			cntTurn=0;
			repaint();
			
		}else{
			try {
				out.writeObject(11);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	
}
