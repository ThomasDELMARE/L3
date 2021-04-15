package fr.unice.l3.formes;

import java.awt.Graphics;

public enum AlignementHorizontal {
	GAUCHE, MILIEU, DROITE;
	
	int adapt(int x, int largeur) {
		switch(this) {
		case GAUCHE: return x;
		case MILIEU: return x - (largeur>>1);
		case DROITE: return x - largeur;
		}
		return x; // should not happen;
	}
	
	void translate(Graphics g, int largeur) {
		switch(this) {
		case GAUCHE: break;
		case MILIEU: g.translate(largeur/2, 0);break;
		case DROITE: g.translate(largeur, 0);break;
		}
	}
	
	void translateBack(Graphics g, int largeur) {
		switch(this) {
		case GAUCHE: break;
		case MILIEU: g.translate(-largeur/2, 0);break;
		case DROITE: g.translate(-largeur, 0);break;
		}
	}
}
