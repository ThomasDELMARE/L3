package fr.unice.l3.ui;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import fr.unice.l3.formes.*;

public class EditeurImage extends JFrame {
	
	JPanel zoneDessin;
	
	public EditeurImage() {
		super("Editeur d'image");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(350,350);
		this.initialise();
	}
	
	public static void main (String[] args){		
		new EditeurImage().setVisible(true);
	}
	
	private void initialise() {
		this.zoneDessin = new JPanel(null);
		Container pane = getContentPane();
			
		pane.add(this.zoneDessin);
		
		// Ajout des deux ellipses
		this.zoneDessin.add(new Ellipse(new Point(-300,0),50,50));
		this.zoneDessin.add(new Ellipse(new Point(-100,0),50,50));
		
		// Ajout des deux rectangles
		this.zoneDessin.add(new Rectangle(new Point(300,0),75,50));
		this.zoneDessin.add(new Rectangle(new Point(100,0),75,50));
		
		this.setContentPane(pane);
		
	}	
}
