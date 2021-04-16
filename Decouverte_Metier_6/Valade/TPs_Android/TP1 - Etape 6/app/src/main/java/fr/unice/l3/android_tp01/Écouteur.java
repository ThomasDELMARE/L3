package fr.unice.l3.android_tp01;

import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Écouteur implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, Emitter.Listener {
    private Chat chat;
    private Socket socket;
    private Préférences préférences;

    public Écouteur(Chat chat, Préférences préférences) {
        setChat(chat);
        setPréférences(préférences);
        try {
            // Serveur perso
            // socket = IO.socket("http://192.168.1.22:10101");

            // Serveur Toto
            socket = IO.socket("http://2.15.255.168:10101");

                    // Serveur du prof
            // socket = IO.socket("http://78.243.124.47:10102");

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

    @Override
    public void onClick(View v) {
        String msg = chat.obtenirTextTapé();
        if (socket != null) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("userName", préférences.obtenirSurnom());
                obj.put("message", msg);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("chatevent", obj);
        }
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.e(ChatActivity.LOG, "switch " + isChecked);
        if (isChecked) {
            connexion();
        } else {
            deconnexion();
        }
    }

    public void setPréférences(Préférences préférences) {
        this.préférences = préférences;
    }

    public Préférences getPréférences() {
        return préférences;
    }

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
        }
    }

    private void créerConnexion(){
        try{
            socket = IO.socket("http://" + getPréférences().obtenirServeur() + ":" + getPréférences().obtenirPort());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void deconnexion() {
        if (socket != null) socket.disconnect();
    }

    public void connexion() {
        if (socket != null){
            créerConnexion();
            socket.connect();
        }
    }

    public void demandeListesConnectés(){
        if(socket != null){
            socket.emit("queryconnected");
        }
    }

    public void changerConnextion(boolean connexion) {
        deconnexion();
        créerConnexion();
        if(connexion){
            connexion();
        }
    }
}
