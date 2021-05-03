package com.example.allumettes.contrôle;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.allumettes.moteur.JeuxDesAllumettes;
import com.example.allumettes.moteur.Joueur;
import com.example.allumettes.view.Allumettes;

public class Controleur implements TextWatcher {

    ScrollView scroll;
    TextView message;
    Allumettes allumettes;
    JeuxDesAllumettes règles;
    boolean encours;

    public Controleur(JeuxDesAllumettes règles, Allumettes allumettes, TextView logs, ScrollView scroll) {
        this.règles = règles;
        this.allumettes = allumettes;
        this.message = logs;
        this.scroll = scroll;

        this.message.addTextChangedListener(this);
    }

    public boolean démarrerPartie(){
        boolean résultat = !encours;
        Tache tache = new Tache();

        if(résultat){
            règles.réinitialiser();

            résultat = règles.démarrer(true);

            if(résultat) {
                allumettes.setNombreTotalAllumettes(règles.obtenirNbTotalAllumettes());
                tache.execute();

                encours = résultat;
            }

        }

        return résultat;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        scroll.fullScroll(View.FOCUS_DOWN);
    }

    // Classe abstraite messageDeProgression
    protected abstract class MessageDeProgression {
        abstract void miseAJour();
    }

    protected class MessageDeCoup extends MessageDeProgression{
        Joueur j;
        int nb;

        public String toString(){
            return j.toString() + " a joué : " + nb + "\n \n";
        }

        @Override
        void miseAJour() {
            allumettes.setNombreAlumettesSelectionnees(nb);
            allumettes.invalidate();

            message.append(this.toString());
            message.invalidate();
        }
    }

    protected class MessageDeResolution extends MessageDeProgression{
        int nb;
        int nbRestant;
        Joueur prochain;

        public String toString(){
            String resolution = "Il reste " + nbRestant + " allumettes(s) ! \n";

            if(prochain == null){
                resolution += "Fin de partie !\n";
            }
            else{
                resolution += "C'est à " + prochain + " de jouer ! \n";
            }
            return resolution;
        }

        void miseAJour(){
            allumettes.enleverAllumettes(nb);
            allumettes.setNombreAlumettesSelectionnees(0);
            allumettes.invalidate();

            message.append(this.toString());
            message.invalidate();
        }
    }

    protected class Tache extends AsyncTask<Void, MessageDeProgression, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                MessageDeCoup messageCoup = new MessageDeCoup();
                MessageDeResolution messageResolution = new MessageDeResolution();

                messageResolution.nb = 0;
                messageResolution.prochain = règles.aQuiDeJouer();
                messageResolution.nbRestant = règles.obtenirNbAllumettesRestantes();

                publishProgress(messageResolution);

                while (règles.obtenirGagnant() == null){
                    messageCoup.j = règles.aQuiDeJouer();
                    messageCoup.nb = règles.jouerUnTour();

                    publishProgress(messageCoup);
                    messageCoup.j.temporiser();

                    messageResolution.nb = messageCoup.nb;
                    messageResolution.prochain = règles.aQuiDeJouer();
                    messageResolution.nbRestant = règles.obtenirNbAllumettesRestantes();

                    publishProgress(messageResolution);
                    messageCoup.j.temporiser();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return règles.obtenirGagnant().toString() + " a gagné ! \n";
        }

        @Override
        protected void onProgressUpdate(MessageDeProgression... values) {
            super.onProgressUpdate(values);
            values[0].miseAJour();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            encours = false;
            message.append(s);
            allumettes.invalidate();
        }
    }
}
