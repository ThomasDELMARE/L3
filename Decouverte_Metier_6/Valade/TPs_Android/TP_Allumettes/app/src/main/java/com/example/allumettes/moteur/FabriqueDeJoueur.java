package com.example.allumettes.moteur;

public class FabriqueDeJoueur {

    public enum TYPE_DE_JOUEUR { BASIC, RANDOM, INTELLIGENT; }

    public static Joueur obtenirUnJoueur(TYPE_DE_JOUEUR type){
        Joueur résultat;
        switch (type){
            case INTELLIGENT: résultat = new JoueurIntelligent(); break;
            case RANDOM: résultat = new JoueurRandom(); break;
            case BASIC: résultat = new Joueur(); break;
            default: résultat = new Joueur();
        }

        return résultat;
    }

    public static Joueur obtenirUnJoueur(){
        return new Joueur();
    }

}
