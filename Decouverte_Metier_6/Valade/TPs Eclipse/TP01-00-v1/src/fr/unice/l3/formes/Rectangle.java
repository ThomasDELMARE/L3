package fr.unice.l3.formes;

import java.awt.Graphics;

public class Rectangle extends AFormeGeometrique {
	public Rectangle(Point coin, int largeur, int hauteur) {
		super(coin, largeur, hauteur);
	}
	
	@Override
	double surface() {
		return getLargeur()*getHauteur();
	}

	@Override
	double perimetre() {
		return 2*(getLargeur() + getHauteur());
	}

	@Override
	public void dessineToi(Graphics g) {
		super.dessineToi(g);
		getAncrage().dessineToi(g);
		Point coin = getCoinSuperieurGauche() ;
		g.drawRect(coin.getX(), coin.getY(), getLargeur(), getHauteur());
		}
	
	@Override
	public String toString() {
		return "Rectangle"+super.toString();
	}
}
