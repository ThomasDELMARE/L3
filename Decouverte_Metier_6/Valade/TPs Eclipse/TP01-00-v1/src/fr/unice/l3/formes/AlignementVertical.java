package fr.unice.l3.formes;

import java.awt.Graphics;

public enum AlignementVertical {
	HAUT, MILIEU, BAS;
	
	int adapt(int y, int hauteur) {
		switch(this) {
		case HAUT: return y;
		case MILIEU: return y - (hauteur>>1);
		case BAS: return y - hauteur;
		}
		return y;
	}
	
	void translate(Graphics g, int hauteur) {
		switch(this) {
		case HAUT: break;
		case MILIEU: g.translate(0, hauteur/2); break;
		case BAS: g.translate(0, hauteur);break;
		}
	}
	
	void translateBack(Graphics g, int hauteur) {
		switch(this) {
		case HAUT: break;
		case MILIEU: g.translate(0, -hauteur/2);break;
		case BAS: g.translate(0, -hauteur);break;
		}
	}
}
