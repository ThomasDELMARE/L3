package fr.unice.l3.android_tp01;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Ecouteur implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, Emitter.Listener {

    Chat chat;
    Préférences preferences;
    Socket socket;

    public Ecouteur(Chat inputChat, Préférences prefs) {
        setChat(inputChat);
        setPréférences(prefs);

        // Je n'ai pas réussi à faire marcher la partie socket car le serveur ne répondait pas (serveur probablement down)
        try{
            // socket = IO.socket("http://134.59.2.27:10101");
            socket = IO.socket("http://78.243.124.47:10102");
            socket.on("chatevent", this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    // Overrided methods

    @Override
    public void onClick(View v) {
        String texteTape = chat.obtenirTextTapé();

        if(socket != null){
            JSONObject message = new JSONObject();

            try{
                message.put("userName", preferences.obtenirSurnom());
                message.put("message", texteTape);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            socket.emit("chatevent", message);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        Log.e(ChatActivity.LOG, "switch" + isChecked);

        if(isChecked){
            Log.i("Socket connexion", "Connexion à la socket...");
            connexion();
        }
        else{
            Log.i("Socket deconnexion", "Déconnexion à la socket...");
            deconnexion();
        }
    }

    // Méthode qui s'occupera de récupérer le message envoyé
    @Override
    public void call(Object... args) {

        JSONObject messageReçu = (JSONObject) args[0];

        try{
            final String username = messageReçu.getString("userName");
            final String message = (String) messageReçu.get("message");
            chat.ajouterMessage(username + "a envoyé le message" + message + "\n");
        }
        catch(JSONException e){
            e.printStackTrace();
            Log.e("Socket message failed", "Message non transcrit");
            return;
        }

    }

    public void setPréférences(Préférences préférences) {
        this.preferences = préférences;
    }


    // Ecouteur methods
    public void connexion(){
        if(socket !=  null){
            socket.connect();
        }
    }

    public void deconnexion(){
        if(socket !=  null){
            socket.disconnect();
        }
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
