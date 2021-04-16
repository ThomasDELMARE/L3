package fr.unice.l3.android_tp01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Reglages extends AppCompatActivity {

    EditText surnom;
    EditText serveur;
    EditText port;

    Button boutonRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reglages);

        surnom = this.findViewById(R.id.surnomText);
        serveur = this.findViewById(R.id.adresseIpText);
        port = this.findViewById(R.id.portText);

        surnom.setText(getIntent().getStringExtra(Préférences.surnom));
        serveur.setText(getIntent().getStringExtra(Préférences.serveur));
        port.setText(getIntent().getStringExtra(Préférences.port));

        boutonRetour = this.findViewById(R.id.retourButton);

        /*
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });
         */
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();

        Intent responseIntent = new Intent();

        responseIntent.putExtra(Préférences.SURNOM, surnom.getText().toString());
        responseIntent.putExtra(Préférences.SERVEUR, serveur.getText().toString());
        responseIntent.putExtra(Préférences.PORT, port.getText().toString());

        setResult(Activity.RESULT_OK, responseIntent);
    }

    public void retour(View v){
        onBackPressed();
    }
}