package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class NetServerWait extends NetIO {
	
	// boolean for activity 
	private boolean 			waiting;
	
	// array list for the status of all client and all colors
	private ArrayList<Boolean>	clientCheck;
	private ArrayList<Color> 	colors;
	private ArrayList<String>	usernames;
	
	// array list with all clients, needed for output
	private ArrayList<NetServerClientCheck> 	clients;
	
	// counter for all connections
	private int counter;
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	NetServerWait () {
		waiting = true;
		counter = 0;
		
		this.colors 		= new ArrayList<Color>();
		this.clientCheck 	= new ArrayList<Boolean>();
		this.usernames		= new ArrayList<String>();
		this.clients		= new ArrayList<NetServerClientCheck>();
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		waiting = running;
		
		for (int i=0; i<clients.size(); i++) {
			clients.get(i).setRunning(running);
		}
	}
	
	void setColor (Color color, int client) {
		if (colors.size() <= client)
			colors.add(client, color);
		else
			colors.set(client, color);
	}
	
	void setReady (boolean ready, int client) {
		if (clientCheck.size() <= client)
			clientCheck.add(client, ready);
		else
			clientCheck.set(client, ready);
	}
	
	void setUser (String user, int client) {
		if (usernames.size() <= client)
			usernames.add(client, user);
		else
			usernames.set(client, user);
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	void add(Socket socket,  ObjectOutputStream output, ObjectInputStream input, int connNo) {
		// add a false variable to the array list for reference if all connections are ready to start the game
		clientCheck.add(false);
		colors.add(Color.BLACK);
		usernames.add("user "+counter);

		// build a new client and start it as a thread
		NetServerClientCheck client = new NetServerClientCheck(counter, socket, output, input, this, connNo);
		Thread thread = new Thread(client);
		thread.start();

		// add the object to an array list and increase the client counter
		clients.add(client);
		counter++;
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		
		while (waiting) {
			// iterate over all clients, send them the colors, statuses (and usernames) of all players 
			for (int i=0; i<clients.size(); i++) {
				clients.get(i).sendObjects(colors);
				clients.get(i).sendObjects(clientCheck);
				clients.get(i).sendObjects(usernames);
			}

			// let the thread sleep to give computation time to other processes
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}
}
