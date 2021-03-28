package fr.unice.l3.android_tp01;


import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.json.JSONObject;

import fr.unice.l3.android_tp01.Préférences;

public class Ecouteur implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    Chat chat;
    Préférences preferences;
    Switch connexion;

    public Ecouteur(Chat inputChat) {
        chat = inputChat;
        preferences = new Préférences();
    }

    public Ecouteur(Switch connexionSwitch) {
        connexion = connexionSwitch;
        preferences = new Préférences();
    }


    @Override
    public void onClick(View v) {
        String textTape = chat.obtenirTextTapé();
        chat.ajouterMessage(textTape);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            connexion();
        }
        else{
            deconnexion();
        }
    }

    public void connexion(){
        String message = "{query:'" + preferences.obtenirSurnom() + "',message:'" + chat.obtenirTextTapé() + "'}";

    }

    public void deconnexion(){

    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
