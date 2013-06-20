package hhu.propra_2013.gruppe_13;

import java.net.Socket;

class NetServerIn extends NetIO {
	private CoreLogic 	logic;
	private Socket 		socket;
	
	boolean running;
	long threadTimer;
	long timerTemp;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServerIn (Socket inSocket) {
		socket 	= inSocket;
		
		running 	= true;
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning (boolean inRunning) {
		running = inRunning;
	}
	
	void setLogic (CoreLogic inLogic) {
		logic = inLogic;
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
