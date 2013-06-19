package hhu.propra_2013.gruppe_13;

import java.net.*;

class NetServerIO implements Runnable {
	private CoreLogic 	logic;
	private Socket 		socket;
	
	boolean running;
	long threadTimer;
	long timerTemp;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServerIO (CoreLogic inLogic, Socket inSocket) {
		logic 	= inLogic;
		socket 	= inSocket;
		
		running 	= true;
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	void setRunning (boolean inRunning) {
		running = inRunning;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		while (running) {
			threadTimer = System.currentTimeMillis();
			
			
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
