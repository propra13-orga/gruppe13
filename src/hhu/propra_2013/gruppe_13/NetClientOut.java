package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class NetClientOut extends NetIO {

	// Output to Internet and list of objects to send
	private ObjectOutputStream 			outgoing;
	private ArrayList<CoreGameObjects> 	sendList;
	
	// to check the threads liveliness
	private boolean 					running;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetClientOut(ObjectOutputStream outputStream) {
		running = true;
		sendList = new ArrayList<CoreGameObjects>();
		outgoing = outputStream;
		
//		try {
//			outgoing = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//		} catch (IOException e) {
//			ProPra.errorOutput(CONNECTION_CLIENT_OOS, e);
//		}
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void sendList(ArrayList<CoreGameObjects> toSend) {
		CoreGameObjects toCopy;
		
		sendList.clear();
		
		for (int i=0; i<toSend.size(); i++) {
			toCopy = toSend.get(i);
			
			if 		(toCopy instanceof Figure)
				sendList.add(((Figure)toCopy).copy());
			
			else if (toCopy instanceof Attack) 
				sendList.add(((Attack)toCopy).copy());
		}
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		long time;
		long temp;
		
		CoreGameObjects toSend;
		
		while (running) {
			time = System.currentTimeMillis();
			
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
			
			// set the thread asleep, we don't need it too often
			try {
				if ((temp = System.currentTimeMillis() - time) < 8)
					Thread.sleep(8 - temp);
			} catch (InterruptedException e) {
				// don't care if the thread is interrupted
			}
		}
	}
}
