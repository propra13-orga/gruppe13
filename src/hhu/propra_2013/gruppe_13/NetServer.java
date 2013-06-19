package hhu.propra_2013.gruppe_13;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class NetServer implements Runnable {
	// Exit codes, needed by the program to know whether to continue execution
	static final int EXIT_SUCCESS				= 0;
	static final int EXIT_FAILURE				= 1;
	
	// Needed for error codes
	static final int SERVER_SOCKET_ERROR 		= 0;
	static final int CONNECTION_SOCKET_ERROR 	= 1;
	
	// Sockets needed for the connection
	ServerSocket socket			= null;
	Socket connection			= null;
	ArrayList<NetServerIO> list	= null;
	
	// needed to know how long the server should remain active, and port
	private boolean 	serverActive;
	private int			port;

	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServer (int inPort) {
		port 			= inPort;
		serverActive 	= true;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void setActive (boolean activity) {
		serverActive = activity;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	int init () {
		// Open a new Socket with the specified port number
		try {
			socket = new ServerSocket(port);
		} catch (IOException e){
			ProPra.errorOutput(SERVER_SOCKET_ERROR, e);
			return EXIT_FAILURE;
		}
		
		// In case of success
		return EXIT_SUCCESS;
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		// only run as long as desired
		while (serverActive == true) {
			try {
				connection = socket.accept();
			} catch (IOException e) {
				ProPra.errorOutput(CONNECTION_SOCKET_ERROR, e);
			}
			
			/* create new serverIO for this connection and run in a seperate thread, this enables multiple connections 
			 * as one connection otherwise blocks the server */
			NetServerIO serverIO = new NetServerIO(connection);
			list.add(serverIO);
			Thread thread = new Thread(serverIO);
			thread.start();
		}
		
		// terminate all threads
		for (int i=0; i<list.size(); i++) {
			list.get(i).setRunning(false);
		}
		
		// wait a little so all threads have a chance to actually terminate
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// doesn't really matter if the thread is interrupted  
		}
		
		// close socket
		try {
			socket.close();
		} catch (IOException e) {
			// not really worth handling, since we are going to close the program now anyways
		}
	}
}
