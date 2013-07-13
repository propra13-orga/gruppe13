package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class NetServerOut extends NetIO {
	// OOS for output to Internet
	private ObjectOutputStream 	sendObjects;
	
	// variables for objects to send
	private ArrayList<CoreGameObjects> 	gameObjects;
	
	// variable for checking the threads liveliness
	private boolean running;

	private Lock lock;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServerOut (ObjectOutputStream outgoing) {
		this.running 	= true;
		this.sendObjects = outgoing;
		this.lock = new ReentrantLock();
		this.gameObjects = new ArrayList<CoreGameObjects>();
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning (boolean inRunning) {
		running = inRunning;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void setRoom (ArrayList<CoreGameObjects> sendList) {
		lock.lock();
		gameObjects = sendList;
		lock.unlock();
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		CoreGameObjects toSend;
		long threadTimer;
		long timerTemp;

		while (running) {
			threadTimer = System.currentTimeMillis();
			
			// Try to set the thread asleep, so that other components also have a chance of using system time
			try {
				if ((timerTemp = System.currentTimeMillis()-threadTimer) < 8) 
					Thread.sleep(8-timerTemp);
			} catch (InterruptedException e) {
				// Do nothing as we don't care if the thread is interrupted
			}
			
			/* Iterate over all objects and send them. Since the servers logic should always overwrite anything at the clients 
			 * the entire room will be sent. */
			lock.lock();
			for (int i=0; i<gameObjects.size(); i++) {
				toSend = gameObjects.get(i);
				
				try {
					sendObjects.reset();
					sendObjects.writeObject(toSend);
					sendObjects.flush();
				} catch (IOException e) {
					System.out.println(toSend);
					e.printStackTrace();
					System.out.println();
				}
			}
			lock.unlock();
		}
	}
}