package com.example.allumettes.moteur;

public class JeuxDesAllumettes {

    int nombreTotalAllumettes;
    int nombreAllumettesRestantes;
    int JoueurCourant = -1;
    int JoueurGagnant = -1;

    Joueur [] listeJoueurs = new Joueur[2];

    public JeuxDesAllumettes(){
        this.nombreTotalAllumettes = 21;
        this.nombreAllumettesRestantes = this.nombreTotalAllumettes;
    }

    public void réinitialiser(){
        nombreAllumettesRestantes = nombreTotalAllumettes;
        JoueurGagnant = -1;
        JoueurCourant = -1;
    };

    public boolean ajouterJoueur(Joueur J){
        if (!partieComplete()){
            if (listeJoueurs[0] == null) {
                listeJoueurs[0] = J;
            }
            else{
                listeJoueurs[1] = J;
            }
            return true;
        }
        else{
            return false;
        }
    };

    boolean partieComplete(){
        if(listeJoueurs[0] == null || listeJoueurs[1] == null){
            return false;
        }
        return true;
    };

    public int obtenirNbTotalAllumettes(){ return nombreTotalAllumettes; };

    public int obtenirNbAllumettesRestantes(){ return nombreAllumettesRestantes; };

    public boolean démarrer(boolean premierEnPremier){

        boolean partiePasCommencee = (nombreAllumettesRestantes == nombreTotalAllumettes);

        if(partiePasCommencee){
            if(premierEnPremier){
                JoueurCourant = 0;
            }
            else{
                JoueurCourant = 1;
            }
        }

        return partiePasCommencee;
    };

    public Joueur aQuiDeJouer(){

        if(JoueurCourant >= 0){
            return listeJoueurs[JoueurCourant];
        }

        return null;
    };

    public int jouerUnTour(){

        Joueur joueurActuel = aQuiDeJouer();
        int nbAllumettesJouees = 0;

        if(obtenirGagnant() == null && joueurActuel != null){
            nbAllumettesJouees = joueurActuel.jouer(obtenirNbAllumettesRestantes());
            this.nombreAllumettesRestantes -= nbAllumettesJouees;

            if(this.nombreAllumettesRestantes > 0){
                if(JoueurCourant == 0){
                    JoueurCourant = 1;
                }
                else{
                    JoueurCourant = 0;
                }
            }
            else{
                if(JoueurCourant == 0){
                    JoueurGagnant = 1;
                }
                else{
                    JoueurGagnant = 0;
                }

                JoueurCourant = -1;
            }
        }

        return nbAllumettesJouees;
    };

    public Joueur obtenirGagnant(){

        if(JoueurGagnant == 0 || JoueurGagnant == 1){
            return listeJoueurs[JoueurGagnant];
        }

        return null;
    };
}
