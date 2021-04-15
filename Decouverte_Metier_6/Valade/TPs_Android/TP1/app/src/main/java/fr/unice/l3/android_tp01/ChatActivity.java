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

    public static final String LOG = "TP-ANDROID-CHAT";

    Button envoyez;
    TextView chat;
    ScrollView scroll;
    EditText message;
    Switch connexion;
    Préférences preferences;

    Ecouteur ecouteur;

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
        ecouteur = new Ecouteur(this, preferences);
        envoyez.setOnClickListener(ecouteur);
        connexion.setOnCheckedChangeListener(ecouteur);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ecouteur.connexion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // On vérifie que la conexion n'est pas déjà faite
        if(connexion.isChecked()){
            ecouteur.connexion();
        }
    }

    @Override
    public String obtenirTextTapé() {
        message.setText("");
        return message.getText().toString() + "\n";
    }

    @Override
    public void ajouterMessage(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chat.append(msg);
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
}