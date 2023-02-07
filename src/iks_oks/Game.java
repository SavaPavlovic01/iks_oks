package iks_oks;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Game extends JFrame {
	
	private final int width=500;
	private final int height=800;
	
	private String name;
	private Socket client;
	private int id;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Field[][] matrix=new Field[3][3];
	
	//private Field field=new Field();
	private JPanel center=new JPanel();
	private JButton reset=new JButton("reset");
	private String turn="X";
	private boolean done=false;

	private int cntTurn=0;
	
	public String getTurn() {
		return turn;
	}
	
	public boolean getDone() {return done;}
	
	public Game(Socket client,String name) {
		super();
		this.client=client;
		this.name=name;
		
		try {
			this.out=new ObjectOutputStream(client.getOutputStream());
			this.in=new ObjectInputStream(client.getInputStream());
			
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
	
	public void updateM() {
		cntTurn++;
		repaint();
		checkWin();
		checkDraw();
		if(turn.equals("X")) turn="O";
		else turn="X";
	}
	
	public void reset() {
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				matrix[i][j].setType("N");
			}
		}
		done=false;
		turn="X";
		reset.setEnabled(false);
		cntTurn=0;
		repaint();
	}
	
	
}
