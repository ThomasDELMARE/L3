package fr.unice.l3.formes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

abstract class AFormeGeometrique extends JComponent {
	protected Point ancrage;
	protected Color couleurTrait = Color.black;
	protected AlignementHorizontal horizontal = AlignementHorizontal.GAUCHE;
	protected AlignementVertical vertical = AlignementVertical.HAUT;
	protected int largeur, hauteur;
	protected float epaisseur;
	private int demiEpaisseur;
	
	protected AFormeGeometrique(Point ancrage, int largeur, int hauteur) {
		this.ancrage = new Point(ancrage);
		this.largeur = largeur;
		this.hauteur = hauteur;
	}
	
	protected AFormeGeometrique(Point ancrage, int largeur, int hauteur, AlignementHorizontal horizontal, AlignementVertical vertical ) {
		this.initialise(ancrage, largeur, hauteur);
		this.horizontal = horizontal;
		this.vertical = vertical;
	}
	
	private void initialise(Point ancrage2, int largeur2, int hauteur2) {
		
	}

	abstract double surface();
	abstract double perimetre();
	final int getLargeur() { return this.largeur; }
	final int getHauteur() { return this.hauteur; }
	
	final Point getCoinSuperieurGauche() {
		int x = horizontal.adapt(ancrage.getX(), getLargeur());
		int y = vertical.adapt(ancrage.getY(), getHauteur());
		
		return new Point(x, y);
	}
	final public void setHorizontal(AlignementHorizontal horizontal) {
		this.horizontal = horizontal;
		
		this.miseAJourBornes();
	}
	final public AlignementHorizontal getHorizontal() {
		return horizontal;
	}
	final public void setVertical(AlignementVertical vertical) {
		this.vertical = vertical;
	}
	final public AlignementVertical getVertical() {
		return vertical;
	}
	
	public void dessineToi(Graphics g) {
		g.setColor(couleurTrait);
	}
	
	final public Color getCouleurTrait() {
		return couleurTrait;
	}
	final public void setCouleurTrait(Color couleurTrait) {
		this.couleurTrait = couleurTrait;
	}
	
	final protected Point getAncrage() {
		return ancrage;
	}
	
	public void paintComponent(Graphics2D g) {
		super.paintComponent(g);
		
		this.ancrage.dessineToi(g);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(epaisseur));
		g.setColor(this.couleurTrait);
		
		this.horizontal.translate(g, largeur);
		this.vertical.translate(g,hauteur);
		this.ancrage.dessineToi(g);
		this.horizontal.translateBack(g, hauteur);
		this.vertical.translateBack(g, largeur);

	}
	
	private void miseAJourBornes() {
        setSize(largeur+demiEpaisseur*2+1, hauteur+demiEpaisseur*2+1);
        setPreferredSize(getSize());
        setMaximumSize(getSize());
        setMinimumSize(getSize());

        Point p = getCoinSuperieurGauche();
        setLocation(p.getX()-demiEpaisseur, p.getY()-demiEpaisseur);
    }
	
	@Override
	public String toString() {
		return "["+getAncrage()+"; "+getLargeur()+"x"+getHauteur()+"]";
	}
}
