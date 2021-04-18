package fr.unice.l3.android_tp01;

import android.content.Context;
import android.content.Intent;

public class Préférences {
    public static String surnom = "DELMARE";

    // Serveur prof
    // public static String serveur = "88.160.63.150";
    // public static String port = "40102";

    // Serveur Toto
    public static String serveur = "2.15.255.168";
    public static String port = "10101";

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

    public Intent obtenirRéglages(Context c){
        Intent myIntent = new Intent(c, Reglages.class);

        myIntent.putExtra(SERVEUR_KEY, obtenirServeur());
        myIntent.putExtra(SURNOM_KEY, obtenirSurnom());
        myIntent.putExtra(PORT_KEY, obtenirPort());

        return myIntent;
    }

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

}
