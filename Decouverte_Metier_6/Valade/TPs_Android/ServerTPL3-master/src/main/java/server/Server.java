package server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import server.IoObject;

public class Server {

	HashMap<String, String> allConnected = new HashMap<String, String> ();
	SocketIOServer server;
	
	boolean waiting = false; /// pour des démonstrations en cours

	public Server(Configuration config) {
		this(config, false);
	}
	
	public Server(Configuration config, boolean waiting) {	
		this.waiting = waiting;
		
		// creation du serveur
		server = new SocketIOServer(config);
		
		
		// pour ecouter les nouvelles connexions
		server.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient arg0) {
				System.out.println("connexion "+arg0.getRemoteAddress());
				// par defaut : adresse = pseudo
				allConnected.put(arg0.getRemoteAddress().toString(), arg0.getRemoteAddress().toString());
				arg0.sendEvent("chatevent", new IoObject("#server#", "welcome !"));
			}
		}); /* */

		// pour ecouter les deconnexions
		server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient arg0) {
				System.out.println("disconnexion "+arg0.getRemoteAddress());
				allConnected.remove(arg0.getRemoteAddress().toString());
			}
		});

		// pour ecouter les nouveaux messages du chat
		// et retransmettre à tout le monde
		server.addEventListener("chatevent", IoObject.class, new DataListener<IoObject>() {
			@Override
			public void onData(SocketIOClient client, IoObject data, AckRequest ackSender) throws Exception {
				allConnected.put(client.getRemoteAddress().toString(), data.getUserName());
				System.out.println(data.getUserName() + " > " + data.getMessage());
				server.getBroadcastOperations().sendEvent("chatevent", data);
			}
		});

		// pour ecouter 
		server.addEventListener("queryconnected", IOQueryConnected.class, new DataListener<IOQueryConnected>() {
			@Override
			public void onData(SocketIOClient client, IOQueryConnected data, AckRequest ackSender) throws Exception {

				System.out.println("queryconnected from " + client.getRemoteAddress());
				String[] addresses = new String[allConnected.size()];
				int i = 0;
				for (String cl : allConnected.values()) {
					addresses[i] = cl;
					i++;
				}
				
				if (Server.this.waiting) {
					TimeUnit.SECONDS.sleep(20);
				}
				
				client.sendEvent("connected list", new IOAnswerConnected(addresses));
			}
		});

		server.start();


		/*
		// c'est obsolete maintenant
		try {
			TimeUnit.SECONDS.sleep(Integer.MAX_VALUE); // il faut faire durer le
														// serveur... boucle
														// avec un test
			// ici c'est un raccourcit...
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		server.stop();
		*/
	}

	
	// deux paramètres : IP puis PORT
	public static void main(String[] args) {

		Configuration config = new Configuration();

		if ((args != null) && (args.length > 0))
			config.setHostname(args[0]);
		else {
			config.setHostname("192.168.1.22");
			// mettre ici l'adresse ip du serveur par defaut
		}

		if ((args != null) && (args.length > 1)) config.setPort(Integer.parseInt(args[1]));
		else config.setPort(10101);

		new Server(config);
	}

}
