package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

class NetClientIn extends NetIO {

	private ObjectInputStream			receiveObjects;
	private boolean 					running;
	private ArrayList<CoreGameObjects> 	allObjects;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetClientIn (ObjectInputStream inputStream) {
		running 	= true;
		allObjects 	= new ArrayList<CoreGameObjects>();
		receiveObjects = inputStream;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void resetList() {
		allObjects.clear();
	}
	
	void getList(ArrayList<CoreGameObjects> currentRoom, Figure figure) {
		CoreGameObjects toCopy;
		
		// Check whether there is anything to work with in the first place
		if (currentRoom != null)
			currentRoom.clear();
		
		if (allObjects == null)
			return;
		
		for (int i=0; i<allObjects.size(); i++) {
			
			toCopy = allObjects.get(i);
			
			if 		(toCopy instanceof Figure)  {
				currentRoom.add(((Figure)toCopy).copy());
				figure = ((Figure)toCopy).copy();
			}
			
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
				System.out.println("reading in client");
				incoming = receiveObjects.readObject();
				System.out.println(incoming);
				
				// if they are of the desired type, add them to the current arraylist
				if (incoming instanceof CoreGameObjects){
					if (!allObjects.contains(incoming))
						allObjects.add((CoreGameObjects)incoming);
				}
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Client: Object could not be read. ");
				e.printStackTrace();
			}
		}
	}
}
