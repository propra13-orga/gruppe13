package hhu.propra_2013.gruppe_13;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

class NetServerOut extends NetIO {
	// Socket and OOS for output to Internet
	private Socket 				socket;
	private ObjectOutputStream 	sendObjects;
	
	// variables for objects to send
	private ArrayList<CoreGameObjects> 	gameObjects;
	
	// variables for checking the threads liveliness and storing sleep time
	boolean running;
	long 	threadTimer;
	long 	timerTemp;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServerOut (Socket inSocket) {
		socket 		= inSocket;
		running 	= true;
		
		// open the output stream to send objects over
		try {
			sendObjects = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_SERVER_OOS, e);
		}
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning (boolean inRunning) {
		running = inRunning;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void setRoom (ArrayList<CoreGameObjects> sendList) {
		gameObjects = sendList;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		CoreGameObjects toSend;

		while (running) {
			threadTimer = System.currentTimeMillis();
			
			/* Iterate over all objects and send them. Since the servers logic should always overwrite anything at the clients 
			 * the entire room will be sent. */ 
			for (int i=0; i<gameObjects.size(); i++) {
				toSend = gameObjects.get(i);
				
				try {
					sendObjects.writeObject(toSend);
					sendObjects.flush();
				} catch (IOException e) {
					ProPra.errorOutput(CONNECTION_SERVER_WRITE, e);
				}
			}

			// Try to set the thread asleep, so that other components also have a chance of using system time
			try {
				if ((timerTemp = System.currentTimeMillis()-threadTimer) < 8) 
					Thread.sleep(8-timerTemp);
			} catch (InterruptedException e) {
				// Do nothing as we don't care if the thread is interrupted
			}
		}
	}
}