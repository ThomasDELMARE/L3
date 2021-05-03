package com.example.allumettes.moteur;

public class JoueurIntelligent extends Joueur{

    // Le joueur intelligent réfléchit au nombre d'allumettes par rapport à l'état du jeu actuel
    public int jouer(int nbRestantes){
        int nb = nbRestantes;
        int nbAllumettesAPrendre = 0;

        while( nb % 4 != 1){
            nbAllumettesAPrendre += 1;
            nb -= 1;
        }

        if(nbAllumettesAPrendre == 0){
            return 1;
        }

        return nbAllumettesAPrendre;
    };
}
