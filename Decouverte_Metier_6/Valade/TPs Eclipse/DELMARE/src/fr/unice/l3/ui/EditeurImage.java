package fr.unice.l3.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.unice.l3.formes.AlignementHorizontal;
import fr.unice.l3.formes.AlignementVertical;
import fr.unice.l3.formes.Ellipse;
import fr.unice.l3.formes.Point;
import fr.unice.l3.formes.Rectangle;

public class EditeurImage extends JFrame {
	JPanel zoneDessin ;
	JPanel zoneOutils ;
	
	String [] formes = { "Rectangle", "Ellipse" };

	
	public EditeurImage()
	{
		super("Editeur d'image");
		setSize(350,350);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		initialise();
	}

	private void initialise() {
		
		Container pane = getContentPane();
		zoneDessin = new JPanel(null);
		zoneDessin.setSize(350,350);
		zoneDessin.setPreferredSize(zoneDessin.getSize());
		pane.add("Center", zoneDessin);
				
		// Créer une zone d'outils pour contenir les barres d'outils
		zoneOutils = new JPanel();
		// On le place sur la gauche du panel
		pane.add("West", zoneOutils);

		// Le panel doit avoir un box Layout
		BoxLayout box = new BoxLayout(zoneOutils, BoxLayout.Y_AXIS);
		zoneOutils.setLayout(box);
		// On configure la couleur du fond
		zoneOutils.setBackground(Color.white);
		// On configure la couleur des bordures
		zoneOutils.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.black));
		
		barreOutils();
		
	}
	
	private void barreOutils()
	{
		JButton [] outils = new JButton[formes.length];
		
		Dimension dimensionPreferee = new Dimension(0,0);
		
		for(int i = 0; i < formes.length; i++)
		{
			outils[i] = new JButton(formes[i]);
			
			// calcul de dimension tous identiques
			Dimension dimensionActuelle = outils[i].getPreferredSize();
			if (dimensionActuelle.width > dimensionPreferee.width) {
				dimensionPreferee.width = dimensionActuelle.width;
			}
			if (dimensionActuelle.height > dimensionPreferee.height) {
				dimensionPreferee.height = dimensionActuelle.height;
			}
			
			// On ajoute les listeners
			outils[i].setActionCommand(formes[i]);
			// TODO : Ajouter le listener 
			outils[i].addActionListener(CreationForme.actionPerformed(formes[i]));
			
			zoneOutils.add(outils[i]);
		}
		
		for(JButton b : outils)
		{
			b.setMaximumSize(dimensionPreferee);
		}		
		
		zoneOutils.add(Box.createVerticalBox());
	}

	public static void main(String[] args) {
		EditeurImage editeur = new EditeurImage();
		editeur.pack();
		editeur.setVisible(true);
	}

	public JPanel getZoneDessin() {
		return zoneDessin;
	}

	public void setZoneDessin(JPanel zoneDessin) {
		this.zoneDessin = zoneDessin;
	}

	public JPanel getZoneOutils() {
		return zoneOutils;
	}

	public void setZoneOutils(JPanel zoneOutils) {
		this.zoneOutils = zoneOutils;
	}

	public String[] getFormes() {
		return formes;
	}

	public void setFormes(String[] formes) {
		this.formes = formes;
	}
}
