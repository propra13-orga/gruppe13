package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

class NetClientIn extends NetIO {

	private ObjectInputStream			receiveObjects;
	private boolean 					running;
	
	private ArrayList<CoreGameObjects> 	allObjects;
	private ArrayList<CoreGameObjects>	bufferListOne;
	private ArrayList<CoreGameObjects>	bufferListTwo;
	
	private Lock 						lock;
	private int							clientNo;
	
	private boolean 					bufferOne;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetClientIn (ObjectInputStream inputStream, Lock lock, int clientNo) {
		running 	= true;
		
		allObjects 	= new ArrayList<CoreGameObjects>();
		bufferListOne = new ArrayList<CoreGameObjects>();
		bufferListTwo = new ArrayList<CoreGameObjects>();
		
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
		// Check whether there is anything to work with in the first place
		if (currentRoom != null)
			currentRoom.clear();
		else
			return;
		
		// copy everything incoming into the two buffers
		boolean there;
		
		for (int i=0; i<allObjects.size(); i++) {
			
			//copy into the first buffer
			there = false;
			
			innerOne:
			for (int j=0; j<bufferListOne.size(); j++) {
				if (allObjects.get(i).getID() == bufferListOne.get(j).getID()) {
					there = true;
					break innerOne;
				}
			}
			
			if (!there) {
				bufferListOne.add(allObjects.get(i));
			}
			
			// copy into the second buffer
			there = false;
			
			innerTwo:
			for (int j=0; j<bufferListTwo.size(); j++) {
				if (allObjects.get(i).getID() == bufferListTwo.get(j).getID()) {
					there = true;
					break innerTwo;
				}
			}
			
			if (!there) {
				bufferListTwo.add(bufferListOne.get(i));
			}
		}
		
		// clear the incoming list
		allObjects.clear();

		// check from which buffer we wish to copy
		if (bufferOne) {
			for (int i=0; i<bufferListOne.size(); i++) {
				if (bufferListOne.get(i) instanceof Figure && ((Figure)bufferListOne.get(i)).getPlayer() == clientNo)  {
					currentRoom.add(bufferListOne.get(i));
					figure = ((Figure)bufferListOne.get(i)).copy();
				}
				
				else
					currentRoom.add(bufferListOne.get(i));
			}
			
			bufferListOne.clear();
		}
		
		else {
			for (int i=0; i<bufferListTwo.size(); i++) {
				if (bufferListTwo.get(i) instanceof Figure && ((Figure)bufferListTwo.get(i)).getPlayer() == clientNo)  {
					currentRoom.add(bufferListTwo.get(i));
					figure = ((Figure)bufferListTwo.get(i)).copy();
				}
				
				else
					currentRoom.add(bufferListTwo.get(i));
			}
			
			bufferListTwo.clear();
		}
		
		bufferOne = !bufferOne;
//		for (int i=0; i<allObjects.size(); i++) {
//			
//			toCopy = allObjects.get(i);
//			
//			if 		(toCopy instanceof Figure && ((Figure)toCopy).getPlayer() == clientNo)  {
//				currentRoom.add(((Figure)toCopy).copy());
//				figure = ((Figure)toCopy).copy();
//			}
//			
//			else if	(toCopy instanceof Attack) 
//				currentRoom.add(((Attack)toCopy).copy());
//			
//			else
//				currentRoom.add(toCopy);
//		}
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
				incoming = receiveObjects.readObject();
				
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
