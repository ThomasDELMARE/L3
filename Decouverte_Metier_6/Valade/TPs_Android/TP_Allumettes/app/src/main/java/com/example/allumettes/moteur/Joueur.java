package com.example.allumettes.moteur;

public class Joueur {

    String nom;

    public void changerNom(String nouveauNom){
        this.nom = nouveauNom;
    };

    public String obtenirNom(){
        return nom;
    };

    public String toString(){
        return nom;
    };

    // Le joueur basique ne prend qu'une allumette
    public int jouer(int nbRestantes){
        return 1;
    };

    public void temporiser() throws InterruptedException {
        Thread.sleep(1000);
    };
}
