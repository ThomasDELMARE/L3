package fr.unice.l3.android_tp01;

import android.app.Activity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

public class ChatActivity extends Activity implements Chat {

    public static final String LOG = "TP-ANDROID-CHAT";

    EditText message;
    TextView chat;
    ScrollView scroll;
    Button envoyer;
    Préférences prefs;

    Écouteur écouteur;
    Switch connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        message = findViewById(R.id.message);
        chat = findViewById(R.id.chat);
        scroll = findViewById(R.id.scroll);
        envoyer = findViewById(R.id.envoyer);

        connexion = findViewById(R.id.connexion);
        prefs = new Préférences();
        écouteur = new Écouteur(this, prefs);
        envoyer.setOnClickListener(écouteur);
        connexion.setOnCheckedChangeListener(écouteur);
    }

    @Override
    public String obtenirTextTapé() {
        String texte = message.getText().toString();
        message.setText("");
        return texte;
    }

    @Override
    public void ajouterMessage(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chat.append(msg);
                // 2 Pour faire « descendre » scroll, il faut utiliser une ligne de code ressemblent à
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void ajouterMessage(final String msg, final int couleur){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SpannableString formaté = new SpannableString(msg);
                formaté.setSpan(new ForegroundColorSpan(couleur), 0, msg.length(), 0);

                chat.append(formaté);
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        écouteur.deconnexion();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (connexion.isChecked()) écouteur.connexion();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.listesconnectes) {
            écouteur.demandeListesConnectés();
        }
        return true;
    }

}
