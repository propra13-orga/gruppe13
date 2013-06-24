package hhu.propra_2013.gruppe_13;

import java.net.Socket;
import java.util.ArrayList;

class NetServerWait extends NetIO {
	
	// check whether the thing is still supposed to be active
	private boolean waiting;
	
	
	NetServerWait (ArrayList<Socket> connections) {
		waiting = true;
	}

	@Override
	void setRunning(boolean running) {
		waiting = running;
	}
	
	@Override
	public void run() {
		while (waiting) {
			
		}
	}
}
