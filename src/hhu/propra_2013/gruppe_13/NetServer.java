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
		waiting.setRunning(activity);
		// TODO: terminate the thread blocking in accept
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
			// wait until a new connection has been initialized by a client, only accept if the maximum amount of connections hasn't been achieved yet
			if (count < connNo) {
				
				// try to build a new socket from the welcome socket
				try {
					//TODO: resolve block when instantiating a new server
					connection = socket.accept();
				} catch (IOException e) {
					ProPra.errorOutput(CONNECTION_SOCKET_ERROR, e);
				}
				
				// build two new I/O-Streams and an object to read
				ObjectOutputStream 	output = null;
				ObjectInputStream	input = null;
				
				try {
					// open the output stream, flush the header
					output 	= new ObjectOutputStream(new BufferedOutputStream(connection.getOutputStream()));
					output.flush();
					
					// open an input stream
					input	= new ObjectInputStream(new BufferedInputStream(connection.getInputStream()));
					
					// read an object from the stream and convert to String
					String test 	= "";
					
					Object inObject = input.readObject();
					if (inObject instanceof String)
						test = (String)inObject;
					
					// check whether the client wishes to initiate a new setting
					if (test.contentEquals("the real deal")) {
						waiting.add(connection, output, input, connNo);
					}
					
				} catch (IOException | ClassNotFoundException e) {
				}
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
