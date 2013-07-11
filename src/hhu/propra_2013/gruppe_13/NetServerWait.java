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
	
	// array lists for saving all connections, this makes it easier to build a new game
	private ArrayList<ObjectOutputStream> 	outgoingStreams;
	private ArrayList<ObjectInputStream>	incomingStreams;
	
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
		
		this.outgoingStreams = new ArrayList<ObjectOutputStream>();
		this.incomingStreams = new ArrayList<ObjectInputStream>();
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
		
		// add both streams to their lists
		outgoingStreams.add(output);
		incomingStreams.add(input);

		// build a new client and start it as a thread
		NetServerClientCheck client = new NetServerClientCheck(counter, socket, output, input, this, connNo);
		Thread thread = new Thread(client);
		thread.start();

		// add the object to an array list and increase the client counter
		clients.add(client);
		counter++;
	}

	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	void begin(String toSend, int mode) {
		// we need to build two new array lists, as the logic needs all connections as such
		ArrayList<NetServerIn> inServers 	= new ArrayList<NetServerIn>();
		ArrayList<NetServerOut> outServers	= new ArrayList<NetServerOut>();
		
		// first of all terminate the objects thread, we don't need it any more
		waiting = false;
		
		// tell all clients to initiate a new game and terminate the associated threads
		for (int i=0; i<clients.size(); i++) {
			clients.get(i).sendObjects(toSend);
			clients.get(i).setRunning(false);
		}
		
		// Build a new level for the clients
		CoreLevel level = new CoreLevel(mode);
		level.buildLevel(1, "test");
		
		// iterate over all streams and build new server inputs and outputs, add locks
		for (int i=0; i<incomingStreams.size(); i++) {
			inServers.add(new NetServerIn(incomingStreams.get(i)));
			outServers.add(new NetServerOut(outgoingStreams.get(i)));
		}
		
		// build a new logic class on the server
		NetServerLogic logic = new NetServerLogic(level, inServers, outServers);
		
		// start all objects in their separate thread
		for (int i=0; i<inServers.size(); i++) {
			new Thread(inServers.get(i)).start();
			new Thread(outServers.get(i)).start();
		}
		
		// last but not least start the logic thread
		new Thread(logic).start();
		
		System.out.println("finished building server");
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
