package fr.unice.l3.android_tp01;

public class Préférences {

    private String surnom = "Thomas DELMARE";

    public String obtenirSurnom() {
        return surnom;
    }

    public void changerSurnom(String newSurnom) {
        this.surnom = newSurnom;
    }
}
