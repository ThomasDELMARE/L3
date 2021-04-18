package fr.unice.l3.android_tp01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Reglages extends Activity {

    EditText surnom;
    EditText serveur;
    EditText port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglages);

        surnom = this.findViewById(R.id.surnomText);
        serveur = this.findViewById(R.id.adresseIpText);
        port = this.findViewById(R.id.portText);

        surnom.setText(getIntent().getStringExtra(Préférences.SURNOM_KEY));
        serveur.setText(getIntent().getStringExtra(Préférences.SERVEUR_KEY));
        port.setText(getIntent().getStringExtra(Préférences.PORT_KEY));
    }

    public void retour(View v){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent responseIntent = new Intent();

        responseIntent.putExtra(Préférences.SURNOM_KEY, surnom.getText().toString());
        responseIntent.putExtra(Préférences.SERVEUR_KEY, serveur.getText().toString());
        responseIntent.putExtra(Préférences.PORT_KEY, port.getText().toString());

        setResult(Activity.RESULT_OK, responseIntent);

        super.onBackPressed();
    }

}