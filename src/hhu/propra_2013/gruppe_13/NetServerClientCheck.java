package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class NetServerClientCheck extends NetIO {
	
	// variable to determine how long we are going to stay in the waiting room
	private boolean 			running;
	private int 				clientNo;
	
	// Communication with the server and the client
	private NetServerWait		server;
	private ObjectInputStream	incoming;
	private ObjectOutputStream	outgoing;
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	NetServerClientCheck (int clientNo, Socket socket, ObjectOutputStream output, ObjectInputStream input, NetServerWait server) {
		this.clientNo 	= clientNo;
		this.running 	= true;
		this.server 	= server;

		// open two new streams for communication with the client
		this.outgoing = output;
		this.incoming = input;
		
		// tell the client what number he has, that way the waiting room can be built 
		try {
			outgoing.writeObject(clientNo);
			System.out.println("Wrote client number: "+clientNo);
		} catch (IOException e) {
			System.err.println("Could not output client ID to client");
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void sendObjects(Object toSend) {
		try {
			outgoing.writeObject(toSend);
			outgoing.flush();
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_SERVER_WRITE, e);
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		Object inObject;
		
		while (running) {
			
			// reset incoming object
			inObject = null;
			
			// try to read an object from the stream, handle according to type
			try {
				inObject = incoming.readObject();
				
				if (inObject == null)
					continue;
				
				// check whether the client wishes to begin the game
				if (inObject instanceof String) {
					server.setUser((String)inObject, clientNo);
				}
				
				if (inObject instanceof Boolean) {
					server.setReady(true, clientNo);
					this.setRunning(false);
				}
				
				// set the clients game figure color
				if (inObject instanceof Color) {
					server.setColor((Color)inObject, clientNo);
				}
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Object could not be read. ");
				System.err.println(e.getMessage());
			}
		}
	}
}