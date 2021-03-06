package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class NetServerIn extends NetIO {

	private ObjectInputStream	receiveObjects;
	
	private boolean 			running;
	
	private int[]				location;
	private Figure				figure;
	private Map 				map;
	
	private ArrayList<Attack> 	attacks;
	private Lock				lock;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServerIn (ObjectInputStream incoming) {
		running 	= true;
		
		location 	= new int[2];
		figure		= null;
		map 		= null;
		attacks		= new ArrayList<Attack>();
		
		receiveObjects = incoming;
		
		this.lock = new ReentrantLock();
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	int getLocation(int i) {
		// x-variable stored in 0, y stored in 1
		return location[i];
	}
	
	Figure getFigure() {
		return figure;
	}
	
	Map getMap() {
		return map;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning (boolean inRunning) {
		running = inRunning;
	}
	
	void setLocation (int locX, int locY) {
		location[0] = locX;
		location[1] = locY;
	}
	
	void setMap(Map map) {
		this.map = map;
	}

	void setFigure (Figure figure) {
		this.figure = figure;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void updateRoom(ArrayList<CoreGameObjects> content, int player) {
		// boolean for checking whether an attack has already been transferred
		boolean isPresent = false;
		
		// go over all content and delete the old figure and old attacks by that client
		CoreGameObjects object;
		for (int i=0; i<content.size(); i++) {
			object = content.get(i);
			
			if (object instanceof Figure && ((Figure)object).getPlayer() == player) 
				content.remove(i);
		}
		
		// add a copy of the figure and all attacks into the current room used by the logic
		content.add(figure.copy());
			
		// first check whether the array is even there
		if (attacks == null)
		return;
		
//		System.out.println("Size of Attack list: "+attacks.size());
//		System.out.println("Size of Collidable list: "+content.size());
		
		lock.lock();
		check:
		for (int i=0; i<attacks.size(); i++) {
			for (int j=0; j<content.size(); j++) {
				if (attacks.get(i).getID() == content.get(j).getID()) {
					isPresent = true;
					break check;
				}
			}
			
			if (!isPresent)
				content.add(attacks.get(i));
		}
		lock.unlock();
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void removeAttack(Attack attack) {
		lock.lock();
		attacks.remove(attack);
		lock.unlock();
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		Object incoming = null;
		
		while (running) {
			try {
				// read an object from the input stream
				incoming = receiveObjects.readObject();
				
				// check what class the object is and act accordingly
				if 		(incoming instanceof Figure) {
					lock.lock();
					figure	= (Figure)incoming;
					lock.unlock();

				}
				
				else if (incoming instanceof Attack){
					lock.lock();
					if  (!attacks.contains(((Attack)incoming))) {
						attacks.add(((Attack)incoming));
						((Attack)incoming).setTime();
						System.out.println(incoming);
					}
					lock.unlock();

				}
//				System.out.println("from client: "+incoming);
					
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Object could not be read. ");
				e.printStackTrace();
			} finally {
			}
		}
	}
}
