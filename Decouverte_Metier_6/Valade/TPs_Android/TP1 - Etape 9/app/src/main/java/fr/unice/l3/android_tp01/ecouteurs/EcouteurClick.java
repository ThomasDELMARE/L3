package fr.unice.l3.android_tp01.ecouteurs;

import android.graphics.Color;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import fr.unice.l3.android_tp01.Chat;
import fr.unice.l3.android_tp01.Préférences;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EcouteurClick implements View.OnClickListener, Emitter.Listener {

    private Chat chat;
    private Socket socket;
    private Préférences préférences;

    public EcouteurClick(Chat chat, Préférences préférences) {
        setChat(chat);
        setPréférences(préférences);
    }

    // Méthode qui gère le click de l'utilisateur.
    @Override
    public void onClick(View v) {
        String msg = chat.obtenirTextTapé();

        connexion();

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

    public void setPréférences(Préférences préférences) {
        this.préférences = préférences;
    }

    public Préférences getPréférences() {
        return préférences;
    }

    @Override
    public void call(Object... args) {}

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

    // Méthode permettant de renvoyer la liste des utilisateurs connectés.
    public void demandeListesConnectés(){
        if(socket != null){
            socket.emit("queryconnected");
        }
    }

    // Méthode permettant de redéfinir les paramètres de connexion.
    public void changerConnexion(boolean connexion) {
        deconnexion();
        créerConnexion();
        if(connexion){
            connexion();
        }
    }
}
