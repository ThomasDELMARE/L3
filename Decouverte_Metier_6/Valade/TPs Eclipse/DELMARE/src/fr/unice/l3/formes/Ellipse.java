package fr.unice.l3.formes;

import java.awt.Graphics;

public class Ellipse extends AFormeGeometrique {
	
	public Ellipse(Point centre, int largeur, int hauteur) {
		super(centre, largeur, hauteur);
		setVertical(AlignementVertical.MILIEU);
		setHorizontal(AlignementHorizontal.MILIEU);
	}
	
	@Override
	double surface() {
		return Math.PI*getLargeur()*getHauteur()/4;
	}

	@Override
	double perimetre() {
		return Math.PI*(getLargeur() + getHauteur())/2;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawOval(demiEpaisseur, demiEpaisseur, getLargeur(), getHauteur());
	}

	Point getCentre() {
		return super.getAncrage();
	}
	
	@Override
	public String toString() {
		return "Ellipse"+super.toString();
	}
}
