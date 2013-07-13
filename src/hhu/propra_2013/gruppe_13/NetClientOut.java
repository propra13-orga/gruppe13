package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class NetClientOut extends NetIO {

	// Output to Internet and list of objects to send
	private ObjectOutputStream 			outgoing;
	private ArrayList<CoreGameObjects> 	sendList;
	
	// to check the threads liveliness
	private boolean 					running;
	
	// build a new lock for synchronization
	private Lock lock;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetClientOut(ObjectOutputStream outputStream) {
		running = true;
		sendList = new ArrayList<CoreGameObjects>();
		outgoing = outputStream;
		
		this.lock = new ReentrantLock();
		
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void sendList(boolean newAttack, Attack attack, Figure figure) {
		
		// lock and clear the list, this way the list won't be corrupted while we are working with it
		lock.lock();
		sendList.clear();
		
		// send the current Figure and the new Attack if there is one
		sendList.add(figure);
		
//		System.out.println("Coordinates of the figure: "+figure.getPosX()+" "+figure.getPosY());
		
		if (newAttack) {
			sendList.add(attack);
//			System.out.println("Added an attack");
		}
		
		// unlock the list so it can be sent
		lock.unlock();
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		long time;
		long temp;
		
		CoreGameObjects toSend;
		
		while (running) {
			time = System.currentTimeMillis();
			
			lock.lock();
			for (int i=0; i<sendList.size(); i++) {
				toSend = sendList.get(i);
				
				try {
					outgoing.reset();
					outgoing.writeObject(toSend);
					outgoing.flush();
				} catch (IOException e) {
					System.err.println("Error in NetClientOut");
					e.printStackTrace();
				}
			}
			lock.unlock();
			
			// set the thread asleep, we don't need it too often
			try {
				if ((temp = System.currentTimeMillis() - time) < 16)
					Thread.sleep(16 - temp);
			} catch (InterruptedException e) {
				// don't care if the thread is interrupted
			}
		}
	}
}
