package iks_oks;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Field extends JPanel {
	
	boolean painted=false;
	private String type="N";
	private Game parent;
	private int idI;
	private int idJ;
	
	public Field(Game parent,int idI,int idJ) {
		super();
		//this.setBackground(Color.BLUE);
		this.parent=parent;
		this.addAdapters();
		this.idI=idI;
		this.idJ=idJ;
	}
	
	private void drawX(Graphics g) {
		g.setColor(Color.RED);
		
		int offsetx=7;
		int offsety=30;
		
		int x1=this.getX()+offsetx;
		int y1=this.getY()+offsety;
		
		int x2=this.getX()+this.getWidth()+offsetx;
		int y2=this.getY()+this.getHeight()+offsety;
		
		g.drawLine(x1, y1, x2, y2);
		
		x1=this.getX()+offsetx;
		y1=this.getY()+this.getHeight()+offsety;
		
		x2=this.getX()+this.getWidth()+offsetx;
		y2=getY()+offsety;
		
		g.drawLine(x1, y1, x2, y2);
	}
	
	private void drawO(Graphics g) {
		g.setColor(Color.RED);
		
		int offsetX=7;
		int offsetY=30;
		
		int x=this.getX()+offsetX;
		int y=this.getY()+offsetY;
		int r1=this.getWidth();
		int r2=this.getHeight();
		
		g.drawOval(x, y, r1, r2);
		
		
	}
	
	public void crtaj(Graphics g) {
		
		if(type.equals("X")) drawX(g);
		
		if(type.equals("O")) drawO(g);
		
		//if(type.equals("N")) System.out.println("alo koj kurac");
		
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type=type;
	}
	
	private void addAdapters() {
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//System.out.println("pressed");
				if(parent.getDone()) return;
				if(type.equals("N")) {
					type=parent.getTurn();
					parent.updateM();					
				
				}
			}
			
		});
	}

}
