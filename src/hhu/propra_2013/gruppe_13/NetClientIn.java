package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

class NetClientIn extends NetIO {

	private ObjectInputStream			receiveObjects;
	private boolean 					running;
	private ArrayList<CoreGameObjects> 	allObjects;
	private Lock 						lock;
	private int							clientNo;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetClientIn (ObjectInputStream inputStream, Lock lock, int clientNo) {
		running 	= true;
		allObjects 	= new ArrayList<CoreGameObjects>();
		receiveObjects = inputStream;
		this.lock 	= lock;
		this.clientNo = clientNo;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void resetList() {
		lock.lock();
		allObjects.clear();
		lock.unlock();
	}
	
	void getList(ArrayList<CoreGameObjects> currentRoom, Figure figure) {
		CoreGameObjects toCopy;
		
		// Check whether there is anything to work with in the first place
		if (currentRoom != null)
			currentRoom.clear();
		else
			return;
		
		if (allObjects == null)
			return;
		
		for (int i=0; i<allObjects.size(); i++) {
			
			toCopy = allObjects.get(i);
			
			if 		(toCopy instanceof Figure && ((Figure)toCopy).getPlayer() == clientNo)  {
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
		boolean alreadyThere;
		
		// Game loop
		while (running) {
			// reset boolean value
			alreadyThere = false;
			
			// read objects from the stream
			try {
//				System.out.println("reading in client");
				incoming = receiveObjects.readObject();
//				System.out.println(incoming);
				
				// if they are of the desired type, add them to the current arraylist
				if (incoming instanceof CoreGameObjects){
					lock.lock();
					
					loop:
					for (int i=0; i<allObjects.size(); i++) {
						if (((CoreGameObjects)incoming).getID() == allObjects.get(i).getID()) {
							alreadyThere = true;
							break loop;
						}
					}
					
					if (!alreadyThere) {						
						allObjects.add((CoreGameObjects)incoming);
						
//						if (incoming instanceof Figure) {
//							System.out.println(" (Client In) Coordinates of the figure: "+((Figure)incoming).getPosX()+" "+((Figure)incoming).getPosY());
//						}
//						System.out.println(((CoreGameObjects)incoming).getID());
					}
					lock.unlock();
				}
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Error in NetClientIn");
				e.printStackTrace();
			}
		}
	}
}
