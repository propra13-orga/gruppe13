package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

class NetClient extends NetIO {

	// variable to set whether execution should be continued, socket for communication with the server
	private boolean running;
	private Socket 	socket;
	
	// variables for the connection
	private int 	port;
	private String	ip;
	
	// Streams for communication with the server
	private ObjectInputStream 	incoming;
	private ObjectOutputStream	outgoing;
	
	// Array lists for all clients colors and status
	private ArrayList<Color> 	colors;
	private ArrayList<Boolean>	stati;
	private ArrayList<String> 	users;
	
	// Integer to know what client we are in order
	private int clientNo;
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	NetClient(int port, String ip) {
		this.port 	= port;
		this.ip		= ip;
		
		colors		= null;
		stati		= null;
		 
		// tell the server that this is a serious connection, not just a ping
		BufferedWriter out = null;
		Socket greatSocket = null;
		try {
			System.out.println("verify client");
			greatSocket = new Socket(ip, port);

			out = new BufferedWriter(new OutputStreamWriter(greatSocket.getOutputStream()));
			out.write("the real deal");
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				greatSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			outgoing.writeObject(color);
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	void setBegin (boolean begin) {
		// output variable to server
		try {
			outgoing.writeObject(begin);
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	void setUsername (String username) {
		// output username to server
		try {
			outgoing.writeObject(username);
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
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	//TODO: synchronize menu and array lists from this class
	void init () {
		try {
			socket = new Socket(ip, port);
			outgoing = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			outgoing.flush();
			System.out.println("opened an outgoing OOS in client init()");

			incoming = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			System.out.println("opened an incoming OIS in client init()");
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
			// reset the object, we don't wish to work with the same object again
			inObject = null;
			
			try {
				inObject = incoming.readObject();
				
				// check whether the object is an array list or a level, the latter would mean that the game has begun
				if (inObject instanceof ArrayList) {
					list = (ArrayList<?>)inObject;
					
					// sets the colors of all users
					if		(list.size() > 0 && list.get(0) instanceof Color)
						colors 	= (ArrayList<Color>)list;
					
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
				else if (inObject instanceof Integer)
					clientNo = (Integer)inObject;
				
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Object could not be read. ");
				System.err.println(e.getMessage());
			}
		}
	}
}
