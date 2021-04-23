package fr.unice.l3.android_tp01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Préférences {
    public static String surnom = "DELMARE";

    // Serveur de monsieur Valade
    // public static String serveur = "88.160.63.150";
    // public static String port = "40102";

    // Serveur perso
    public static String serveur = "2.15.255.168";
    public static String port = "10101";

    // Clés nous permettant de passer des données
    public static final String SURNOM_KEY = "surnom";
    public static final String SERVEUR_KEY = "serveur";
    public static final String PORT_KEY = "port";

    public String obtenirServeur(){ return serveur;}

    public String obtenirPort(){ return port; }

    public String obtenirSurnom() {
        return surnom;
    }

    public void changerServeur(String newServeur){
        this.serveur = newServeur;
    }

    public void changerPort(String newPort){
        this.port = newPort;
    }

    public void changerSurnom(String nouveauSurnom){
        this.surnom = nouveauSurnom;
    }

    // Méthode permettant d'obtenir les règlages lors de l'envoi de ces derniers dans la page de paramètres.
    public Intent obtenirRéglages(Context c){
        Intent myIntent = new Intent(c, Reglages.class);

        myIntent.putExtra(SERVEUR_KEY, obtenirServeur());
        myIntent.putExtra(SURNOM_KEY, obtenirSurnom());
        myIntent.putExtra(PORT_KEY, obtenirPort());

        return myIntent;
    }

    // Méthode permettabt de recevoir les nouveaux paramétrages que l'utilisateur a fait.
    boolean reçoit(Intent data){
        String nouveauSurnom = data.getStringExtra(SURNOM_KEY);
        String nouveauPort = data.getStringExtra(PORT_KEY);
        String nouveauServeur = data.getStringExtra(SERVEUR_KEY);

        boolean result = false;

        if(nouveauSurnom != null && !nouveauSurnom.equals(""))
        {
            changerSurnom(nouveauSurnom);
            result = true;
        }
        if(nouveauPort != null && !nouveauPort.equals(""))
        {
            changerPort(nouveauPort);
            result = true;
        }
        if(nouveauServeur != null && !nouveauServeur.equals(""))
        {
            changerServeur(nouveauServeur);
            result = true;
        }
        return result;
    }

    // Méthode permettant de récupérer les valeurs entrées par l'utilisateur afin de ne pas les supprimer lorsqu'il tourne son écran.
    public void saveIn(Bundle b){
        b.putString(SURNOM_KEY, obtenirSurnom());
        b.putString(SERVEUR_KEY, obtenirServeur());
        b.putString(PORT_KEY, obtenirPort());
    }

    // Méthode permettant de restorer les valeurs entrées par l'utilisateur afin de ne pas les supprimer lorsqu'il tourne son écran.
    public void restoreFrom(Bundle b){
        changerSurnom(b.getString(SURNOM_KEY));
        changerPort(b.getString(PORT_KEY));
        changerServeur(b.getString(SERVEUR_KEY));
    }

}
