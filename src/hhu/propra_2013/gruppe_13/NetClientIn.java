package hhu.propra_2013.gruppe_13;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

class NetClientIn extends NetIO {

	private ObjectInputStream			receiveObjects;
	
	private boolean 					running;
	
	private ArrayList<CoreGameObjects> 	allObjects;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetClientIn (Socket socket, NetClientLogic logic) {
		running 	= true;
		allObjects 	= null;
		
		try {
			receiveObjects = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_OIS, e);
		}
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void resetList() {
		allObjects.clear();
	}
	
	void getList(ArrayList<CoreGameObjects> currentRoom) {
		CoreGameObjects toCopy;
		currentRoom.clear();
		
		for (int i=0; i<allObjects.size(); i++) {
			
			toCopy = allObjects.get(i);
			
			if 		(toCopy instanceof Figure) 
				currentRoom.add(((Figure)toCopy).copy());
			
			else if	(toCopy instanceof Attack) 
				currentRoom.add(((Attack)toCopy).copy());
			
			else
				currentRoom.add(toCopy);
		}
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		Object incoming;
		
		// Game loop
		while (running) {
			// read objects from the stream
			try {
				incoming = receiveObjects.readObject();
				
				// if they are of the desired type, add them to the current arraylist
				if (incoming instanceof CoreGameObjects){
					if (!allObjects.contains(incoming))
						allObjects.add((CoreGameObjects)incoming);
				}
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Object could not be read. ");
				System.err.println(e.getMessage());
			}
		}
	}
}
