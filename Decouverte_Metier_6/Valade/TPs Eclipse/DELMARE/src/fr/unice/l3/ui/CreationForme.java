package fr.unice.l3.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import fr.unice.l3.formes.Ellipse;
import fr.unice.l3.formes.Point;
import fr.unice.l3.formes.Rectangle;

public class CreationForme implements ActionListener{

	private EditeurImage editeurImage;
	
	public CreationForme(EditeurImage editeurImage) {
		// TODO Auto-generated constructor stub
		this.editeurImage = editeurImage;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String commande = e.getActionCommand();
		JPanel zone = editeurImage.getZoneDessin();
		
		switch(commande) {
		case "Rectangle":
			/*
			int x = (int) (Math.random()*zone.getWidth()*0.8);
			int width = (int) (Math.random()*zone.getWidth()*0.2);
			int y = (int) (Math.random()*zone.getHeight()*0.8);
			int height = (int) (Math.random()*zone.getHeight()*0.2);
			zone.add(new Rectangle(new Point(x,y), width, height));
			*/
			Rectangle rec = new Rectangle(new Point(10,20), 30,40);
			zone.add(rec);
			break;
			
		case "Ellipse":
			Ellipse ell = new Ellipse(new Point(10,20), 30,40);
			zone.add(ell);
			break;
		
		default:
			break;
		}
		zone.repaint();
	}
	
	
	
}
