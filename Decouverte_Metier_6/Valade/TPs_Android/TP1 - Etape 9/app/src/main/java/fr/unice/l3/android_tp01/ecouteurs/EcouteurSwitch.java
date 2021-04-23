package fr.unice.l3.android_tp01.ecouteurs;

import android.graphics.Color;
import android.util.Log;
import android.widget.CompoundButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import fr.unice.l3.android_tp01.Chat;
import fr.unice.l3.android_tp01.ChatActivity;
import fr.unice.l3.android_tp01.Préférences;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EcouteurSwitch implements CompoundButton.OnCheckedChangeListener, Emitter.Listener{

    private Chat chat;
    private Socket socket;
    private Préférences préférences;

    public EcouteurSwitch(Chat chat, Préférences préférences) {
        setChat(chat);
        setPréférences(préférences);
    }

    // Méthode permettant de créer la connexion
    private void créerConnexion(){
        try{
            socket = IO.socket("http://" + getPréférences().obtenirServeur() + ":" + getPréférences().obtenirPort());

            socket.on("chatevent", this);
            socket.on("connected list", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONArray connected = data.getJSONArray("connected");
                        String liste = "*** connecté-es ****\n";
                        for (int i = 0; i < connected.length(); i++){
                            liste += connected.get(i).toString()+'\n';
                        }
                        liste +="********\n";
                        chat.ajouterMessage(liste, Color.RED);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // Méthode permettant de se déconnecter du serveur
    public void deconnexion() {
        if (socket != null) socket.disconnect();
    }

    // Méthode permettant de se connecter au serveur
    public void connexion() {
        if (socket == null){
            créerConnexion();
        }
        socket.connect();
    }

    // Méthode permettant de définir ce que le programme va renvoyer lors de l'envoi de données.
    @Override
    public void call(Object... args) {
        JSONObject data = (JSONObject) args[0];
        try {
            final String username = data.getString("userName");
            final String message = data.getString("message");
            Log.e(ChatActivity.LOG, message);
            chat.ajouterMessage(username + " > " + message + "\n");
        } catch (JSONException e) {
            Log.e(ChatActivity.LOG, "JSONException");
            return;
        }};

    // Ecouteur placé au niveau du switch de connexion
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.e(ChatActivity.LOG, "switch " + isChecked);
        if (isChecked) {
            connexion();
        } else {
            deconnexion();
        }
        chat.activerInterface(isChecked);
    }


    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

    public void setPréférences(Préférences préférences) {
        this.préférences = préférences;
    }

    public Préférences getPréférences() {
        return préférences;
    }

}
