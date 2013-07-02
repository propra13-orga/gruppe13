package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;

class NetServerWait extends NetIO {
	
	// boolean for activity 
	private boolean 			waiting;
	private boolean 			initGame;
	
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
	}
	
	void setInitGame (boolean initGame) {
		this.initGame = initGame;
	}
	
	void setColor (Color color, int client) {
		colors.set(client, color);
	}
	
	void setReady (boolean ready, int client) {
		clientCheck.set(client, ready);
	}
	
	void setUser (String user, int client) {
		usernames.add(client, user);
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	void add(Socket socket) {
		// add a false variable to the array list for reference if all connections are ready to start the game
		clientCheck.add(false);
		colors.add(Color.BLACK);

		// build a new client and start it as a thread
		NetServerClientCheck client = new NetServerClientCheck(counter, socket, this);
		Thread thread = new Thread(client);
		thread.start();
		
		// add the object to an array list and increase the client counter
		clients.add(client);
		counter++;
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {		
		boolean startGame;
		while (waiting) {
			startGame = true;

			// iterate over all clients, send them the colors, statuses (and usernames) of all players 
			for (int i=0; i<clients.size(); i++) {
				clients.get(i).sendObjects(colors);
				clients.get(i).sendObjects(clientCheck);
				
				// check whether all clients have agreed to begin
				if (clientCheck.get(i) == false)
					startGame = false;
				
			}
			
			if (startGame && initGame) {
				//TODO: start the game
			}
		}
	}
}
