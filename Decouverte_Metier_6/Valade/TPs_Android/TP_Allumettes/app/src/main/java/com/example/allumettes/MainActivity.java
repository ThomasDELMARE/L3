package com.example.allumettes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.controls.Control;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.allumettes.contrôle.Controleur;
import com.example.allumettes.moteur.FabriqueDeJoueur;
import com.example.allumettes.moteur.JeuxDesAllumettes;
import com.example.allumettes.moteur.Joueur;
import com.example.allumettes.view.Allumettes;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Allumettes allumettes;
    JeuxDesAllumettes jeuAllumettes;
    Joueur joueur1;
    Joueur joueur2;
    Controleur controleur;
    ScrollView scrollView;
    Button boutonDemarrer;
    TextView logs;
    FabriqueDeJoueur.TYPE_DE_JOUEUR choix;
    Random RANDOM = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choix = FabriqueDeJoueur.TYPE_DE_JOUEUR.values()[RANDOM.nextInt(FabriqueDeJoueur.TYPE_DE_JOUEUR.values().length)];
        joueur1 = FabriqueDeJoueur.obtenirUnJoueur(choix);
        joueur1.changerNom("Alexandre");

        choix = FabriqueDeJoueur.TYPE_DE_JOUEUR.values()[RANDOM.nextInt(FabriqueDeJoueur.TYPE_DE_JOUEUR.values().length)];
        joueur2 = FabriqueDeJoueur.obtenirUnJoueur(choix);
        joueur2.changerNom("Julien");

        jeuAllumettes = new JeuxDesAllumettes();
        jeuAllumettes.ajouterJoueur(joueur1);
        jeuAllumettes.ajouterJoueur(joueur2);

        scrollView = findViewById(R.id.scrollLogs);
        logs = findViewById(R.id.textLogs);
        allumettes = findViewById(R.id.allumettes);
        boutonDemarrer = findViewById(R.id.startButton);

        controleur = new Controleur(jeuAllumettes, allumettes, logs, scrollView);
        boutonDemarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controleur.démarrerPartie();
            }
        });
    }
}