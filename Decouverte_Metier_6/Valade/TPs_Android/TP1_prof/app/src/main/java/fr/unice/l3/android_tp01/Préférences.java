package fr.unice.l3.android_tp01;

public class Préférences {
    private String surnom = "DELMARE";

    public String obtenirSurnom() {
        return surnom;
    }
    public void changerSurnom(String surnom) {
        this.surnom = surnom;
    }
}
