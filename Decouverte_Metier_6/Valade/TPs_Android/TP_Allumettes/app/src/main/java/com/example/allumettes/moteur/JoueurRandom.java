package com.example.allumettes.moteur;

import java.util.Random;

public class JoueurRandom extends Joueur {

    // Le joueur choisit un nombre au hasard entre 1 et 3
    public int jouer(int nbRestantes){
        int nb;
        Random random = new Random();

        if(nbRestantes > 3){
            nb = 1 + random.nextInt(2);
            return nb;
        }
        else if(nbRestantes == 1){
            return 1;
        }
        else if(nbRestantes == 2){
            nb = 1 + random.nextInt(1);
            return nb;
        }

        return 1;
    };
}
