package hhu.propra_2013.gruppe_13;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

class NetServerOut extends NetIO {
	private CoreLogic 	logic;
	private Socket 		socket;
	
	private ObjectOutputStream sendObjects;
	
	private ArrayList<CoreGameObjects> 	gameObjects;
	private CoreGameObjects				toSend;
	
	boolean running;
	long threadTimer;
	long timerTemp;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServerOut (Socket inSocket) {
		socket 	= inSocket;
		running 	= true;
		
		// open the output stream to send objects over
		try {
			sendObjects = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_OOS_ERROR, e);
		}
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning (boolean inRunning) {
		running = inRunning;
	}
	
	void setLogic (CoreLogic inLogic) {
		logic = inLogic;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		while (running) {
			threadTimer = System.currentTimeMillis();
			
			// get all current objects within the room
			gameObjects = logic.getRoom().getContent();
			
			// iterate over all game objects
			for (int i = 0; i<gameObjects.size(); i++) {
				toSend = gameObjects.get(i);
				
				// try to send the object if it is of any importance
				if (toSend instanceof MISCWall || toSend instanceof Figure || toSend instanceof Attack || toSend instanceof Enemy || toSend instanceof Item) {
					try {
						sendObjects.writeObject(toSend);
					} catch (IOException e) {
						System.err.println("Failed to send object. ");
					}
				}
			}

			// Try to set the thread asleep, so that other components also have a chance of using system time
			try {
				if ((timerTemp = System.currentTimeMillis()-threadTimer) < 16) 
					Thread.sleep(16-timerTemp);
			} catch (InterruptedException e) {
				// Do nothing as we don't care if the thread is interrupted
			}
		}
	}
}
