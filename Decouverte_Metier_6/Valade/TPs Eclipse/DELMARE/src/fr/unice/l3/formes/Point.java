package fr.unice.l3.formes;

import java.awt.Color;
import java.awt.Graphics;

public class Point {
	protected int x, y;
	protected Color couleur = Color.blue.darker();
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	public Point() {
		this(0,0);
	}
	public void dessineToi(Graphics g) {
		Color c = g.getColor();
		g.setColor(couleur);
		g.fillRect(-2, -2, 5, 5);
				
		g.setColor(c);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String toString() {
		return "["+x+","+y+"]";
	}
}
