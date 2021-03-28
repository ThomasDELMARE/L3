package fr.unice.l3.formes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public abstract class AFormeGeometrique extends JComponent {
	Point ancrage;
	Color couleurTrait = Color.black;
	AlignementHorizontal horizontal = AlignementHorizontal.GAUCHE;
	AlignementVertical vertical = AlignementVertical.HAUT;
	int largeur, hauteur;
	int demiEpaisseur = 1;
	
	static Color selected = new Color(248,255,232,128);
	
	boolean selectionne = false;
	
	AFormeGeometrique(Point ancrage, int largeur, int hauteur) {
		this.ancrage = new Point(ancrage);
		this.largeur = largeur;
		this.hauteur = hauteur;
		setOpaque(false);
		 miseAJourBornes();
	}
	
	abstract double surface();
	abstract double perimetre();
	public int getLargeur() { return this.largeur; }
	public int getHauteur() { return this.hauteur; }
	
	public Point getCoinSuperieurGauche() {
		int x = horizontal.adapt(ancrage.getX(), getLargeur());
		int y = vertical.adapt(ancrage.getY(), getHauteur());
		
		return new Point(x, y);
	}
	
	public void setHorizontal(AlignementHorizontal horizontal) {
		this.horizontal = horizontal;
		 miseAJourBornes();
	}
	public AlignementHorizontal getHorizontal() {
		return horizontal;
	}
	
	public void setVertical(AlignementVertical vertical) {
		this.vertical = vertical;
		 miseAJourBornes();
	}
	
	public AlignementVertical getVertical() {
		return vertical;
	}
	
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		
		if (selectionne)
		{
			gr.setColor(selected);
			gr.fillRect(0, 0, getWidth(), getHeight());
		}
		
		gr.setColor(couleurTrait);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(demiEpaisseur*2+1));
		
		horizontal.decaler(gr, largeur);
		vertical.decaler(gr, hauteur);
		gr.translate(demiEpaisseur, demiEpaisseur);
		ancrage.dessineToi(g);
		gr.translate(-demiEpaisseur, -demiEpaisseur);
		vertical.recaler(gr, hauteur);
		horizontal.recaler(gr, largeur);
	}
	
	public Color getCouleurTrait() {
		return couleurTrait;
	}
	
	public void setCouleurTrait(Color couleurTrait) {
		this.couleurTrait = couleurTrait;
	}
	
	Point getAncrage() {
		return ancrage;
	}
	
	@Override
	public String toString() {
		return "["+getAncrage()+"; "+getLargeur()+"x"+getHauteur()+"]";
	}
	
	public int getEpaisseur() {
		return demiEpaisseur;
	}

	public void setEpaisseur(int epaisseur) {
		this.demiEpaisseur = epaisseur;
		miseAJourBornes();
	}
	
	/*
	 * Changement de Nom miseAJourPosition à miseAJourBornes
	 */
	private void miseAJourBornes()
	{
		setSize(largeur+demiEpaisseur*2+1, hauteur+demiEpaisseur*2+1);
		setPreferredSize(getSize());
		setMaximumSize(getSize());
		setMinimumSize(getSize());
		
		Point p = getCoinSuperieurGauche();
		setLocation(p.getX()-demiEpaisseur, p.getY()-demiEpaisseur);
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
		 miseAJourBornes();
	}

	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
		 miseAJourBornes();
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}
}
