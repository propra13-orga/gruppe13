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
	
	// variables for the connection
	private int 	port;
	private String	ip;
	
	// Variables to send to the server
	private boolean begin;
	private Color 	color;
	
	// Streams for communication with the server
	private ObjectInputStream 	incoming;
	private ObjectOutputStream	outgoing;
	
	// Array lists for all clients colors and status
	private ArrayList<Color> 	colors;
	private ArrayList<Boolean>	stati;
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	NetClient(int port, String ip) {
		this.port 	= port;
		this.ip		= ip;
		
		colors		= null;
		stati		= null;
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void setColor (Color color) {
		// save the variable and tell the server
		this.color = color;
		
		try {
			outgoing.writeObject(color);
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	void setBegin (boolean begin) {
		// save the variable and tell the server
		this.begin = begin;
		
		try {
			outgoing.writeObject(begin);
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	//TODO: synchronize menu and array lists from this class
	void init () {
		try {
			socket = new Socket(ip, port);
			incoming = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			outgoing = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			ProPra.errorOutput(CLIENT_SOCKET_ERROR, e);
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Object 		 inObject;
		ArrayList<?> list;
		
		while (running) {
			inObject = null;
			
			try {
				inObject = incoming.readObject();
				
				if (inObject instanceof ArrayList) {
					list = (ArrayList<?>)inObject;
					
					if		(list.size() > 0 && list.get(0) instanceof Color)
						colors 	= (ArrayList<Color>)list;
					
					else if (list.size() > 0 && list.get(0) instanceof Boolean) 
						stati	= (ArrayList<Boolean>)list;
				}
				
				else if (inObject instanceof CoreLevel) {
					// TODO: start the game
				}
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Object could not be read. ");
				System.err.println(e.getMessage());
			}
		}
	}
}
