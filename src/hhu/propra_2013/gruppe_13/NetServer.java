package hhu.propra_2013.gruppe_13;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class NetServer extends NetIO {
	
	// Sockets needed for the connection
	ServerSocket socket		= null;
	ArrayList<NetIO> list	= null;
	
	// boolean needed to know how long the server should remain active, port and number of connections
	private boolean 	serverActive;
	private int			port;
	private int 		connNo;
	
	// Waiting room for the server
	private NetServerWait waiting;

	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServer (int inPort, int connNo) {
		port 			= inPort;
		serverActive 	= true;
		this.connNo		= connNo;
		
		// try to open a new server socket, needed to initiate communication with any client
		try {
			socket = new ServerSocket(port);
		} catch (IOException e){
			ProPra.errorOutput(SERVER_SOCKET_ERROR, e);
		}
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning (boolean activity) {
		serverActive = activity;
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		int count = 0;
		
		// Connections and array list of all connections
		Socket connection			= null;
		
		// initialize new wait room for the server and initialize it's own thread
		waiting = new NetServerWait();
		Thread thread = new Thread(waiting);
		thread.start();
		
		// only run as long as desired, break if the maximum number of connections has been achieved 
		while (serverActive) {
			// wait until a new connection has been initialized by a client
			if (count < connNo) {
				try {
					connection = socket.accept();
				} catch (IOException e) {
					ProPra.errorOutput(CONNECTION_SOCKET_ERROR, e);
				}
				
				// check whether this is a serious connection
				BufferedReader in = null;
				
				try {
					in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String test = in.readLine();
					
					if (test.contentEquals("the real deal")) {
						// add the connection to the waiting room
						System.out.println("blub im server");
						waiting.add(connection);
						count++;
					}
					System.out.println("test im server");
				} catch (IOException e) {
				} finally {
//					try {
////						in.close();
//					} catch (IOException e) {
//					}
				}
			}
			
			// check whether the maximum amount of connections has been achieved, terminate the server if this is the case
			if (count == connNo) {
				waiting.setInitGame(true);
				break;
			}
		}
		
		// close socket
		try {
			socket.close();
		} catch (IOException e) {
			// not really sure whether this is worth handling
		}
	}
}
