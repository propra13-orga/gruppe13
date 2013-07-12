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
	void sendList(ArrayList<CoreGameObjects> toSend) {
		CoreGameObjects toCopy;
		
		lock.lock();
		sendList.clear();
		
		for (int i=0; i<toSend.size(); i++) {
			toCopy = toSend.get(i);
			
			if 		(toCopy instanceof Figure)
				sendList.add(((Figure)toCopy).copy());
			
			else if (toCopy instanceof Attack) 
				sendList.add(((Attack)toCopy).copy());
		}
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
