package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class NetClient extends NetIO {

	// variable to set whether execution should be continued, socket for communication with the server
	private boolean running;
	private Socket 	socket;
	
	// Streams for communication with the server
	private ObjectInputStream 	incoming;
	private ObjectOutputStream	outgoing;
	
	// Array lists for all clients colors and status
	private ArrayList<Color> 	colors;
	private ArrayList<Boolean>	stati;
	private ArrayList<String> 	users;
	
	// Integer to know what client we are in order
	private int clientNo;
	private int connNo;
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	NetClient(int port, String ip) {

		colors		= null;
		stati		= null;
		
		try {
			socket = new Socket(ip, port);
			
			// open a new output stream
			outgoing = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			outgoing.flush();
			
			// write a message to the server telling it to take a new client
			outgoing.writeObject(new String("the real deal"));
			outgoing.flush();
			
			// build a new OIS to the server
			incoming = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			ProPra.errorOutput(CLIENT_SOCKET_ERROR, e);
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void setColor (Color color) {
		// output variable to server
		try {
			outgoing.reset();
			outgoing.writeObject(color);
			outgoing.flush();
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	void setBegin (boolean begin) {
		// output variable to server
		try {
			outgoing.reset();
			outgoing.writeObject(begin);
			outgoing.flush();
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	void setUsername (String username) {
		// output username to server
		try {
			outgoing.reset();
			outgoing.writeObject(username);
			outgoing.flush();
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	ArrayList<Color> getColors() {
		return colors;
	}
	
	ArrayList<Boolean> getStati() {
		return stati;
	}
	
	ArrayList<String> getUsers() {
		return users;
	}
	
	int getClientNumber() {
		return clientNo;
	}
	
	int getConnNo() {
		return connNo;
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Object 		 inObject;
		ArrayList<?> list;
		
		running 	= true;
		clientNo 	= -1;
		
		while (running) {
			// reset the object, we don't wish to work with the same object again
			inObject = null;
			
			try {
				inObject = incoming.readObject();
				
				// check whether the object is an array list or a level, the latter would mean that the game has begun
				if (inObject instanceof ArrayList) {
					list = (ArrayList<?>)inObject;

					// sets the colors of all users
					if		(list.size() > 0 && list.get(0) instanceof Color) {
						colors 	= (ArrayList<Color>)list;
					}
					
					// sets the status of all users
					else if (list.size() > 0 && list.get(0) instanceof Boolean)
						stati	= (ArrayList<Boolean>)list;
					
					// sets the names of all users
					else if (list.size() > 0 && list.get(0) instanceof String)
						users	= (ArrayList<String>)list;
				}
				
				// if we have a level we need to start the game
				else if (inObject instanceof CoreLevel) {
					// TODO: start the game
				}
				
				// variable to set which user we are
				else if (inObject instanceof Integer) {
					if (clientNo < 0)
						clientNo = (Integer)inObject;
					else
						connNo = (Integer)inObject;
				}
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Object could not be read. ");
				System.err.println(e.getMessage());
			}
		}
	}
}