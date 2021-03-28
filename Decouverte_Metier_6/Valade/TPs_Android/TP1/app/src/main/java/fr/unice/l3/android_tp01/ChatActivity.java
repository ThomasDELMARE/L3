package fr.unice.l3.android_tp01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

public class ChatActivity extends Activity implements Chat{

    Button envoyez;
    TextView chat;
    ScrollView scroll;
    EditText message;
    Switch connexion;
    Préférences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // On import le surnom
        preferences = new Préférences();

        // Récupération des objets
        envoyez = findViewById(R.id.envoyez);
        chat = findViewById(R.id.chat);
        scroll = findViewById(R.id.scroll);
        message = findViewById(R.id.message);
        connexion = findViewById(R.id.connexion);

        // Ecouteur du click
        Ecouteur ecouteurClick = new Ecouteur(this);
        envoyez.setOnClickListener(ecouteurClick);

        // Ecouteur du switch
        Ecouteur ecouteurSwitch = new Ecouteur(connexion);
        connexion.setOnCheckedChangeListener(ecouteurSwitch);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Ecouteur ecouteurSwitch = new Ecouteur(connexion);
        ecouteurSwitch.connexion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Ecouteur ecouteurSwitch = new Ecouteur(connexion);
        ecouteurSwitch.deconnexion();
    }

    @Override
    public String obtenirTextTapé() {
        return preferences.obtenirSurnom() + ">" + message.getText().toString() + "\n";
    }

    @Override
    public void ajouterMessage(String msg) {
        // Permet de reset le texte
        chat.setText(R.string.blank);
        chat.append(msg);
        scroll.fullScroll(View.FOCUS_DOWN);
    }
}