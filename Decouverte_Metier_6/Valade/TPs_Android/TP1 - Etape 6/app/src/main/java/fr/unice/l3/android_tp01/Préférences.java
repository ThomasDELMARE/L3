package fr.unice.l3.android_tp01;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Préférences {
    public static String surnom = "DELMARE";
    public static String serveur = "2.15.255.168";
    public static String port = "10101";

    public static final String SURNOM = "surnom";
    public static final String SERVEUR = "serveur";
    public static final String PORT = "port";

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

        myIntent.putExtra(serveur, obtenirServeur());
        myIntent.putExtra(surnom, obtenirSurnom());
        myIntent.putExtra(port, obtenirPort());

        c.startActivity(myIntent);
        return myIntent;
    }

    boolean reçoit(Intent data){
        String nouveauSurnom = data.getStringExtra("SURNOM");
        String nouveauPort = data.getStringExtra("PORT");
        String nouveauServeur = data.getStringExtra("SERVEUR");

        if(nouveauSurnom != null || nouveauSurnom == "")
        {
            changerSurnom(nouveauSurnom);
            return true;
        }
        if(nouveauPort != null || nouveauPort == "")
        {
            changerPort(nouveauPort);
            return true;
        }
        if(nouveauServeur != null || nouveauServeur == "")
        {
            changerServeur(nouveauServeur);
            return true;
        }
        else{
            return false;
        }
    }

}
