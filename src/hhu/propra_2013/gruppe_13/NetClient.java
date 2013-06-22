package hhu.propra_2013.gruppe_13;

import java.io.IOException;
import java.net.Socket;

class NetClient extends NetIO {

	private boolean running;
	private Socket 	socket;
	
	private int 	port;
	private String	ip;
	
	NetClient(int port, String ip) {
		this.port 	= port;
		this.ip		= ip;	
	}
	
	int init() {
		
		try {
			socket = new Socket(ip, port);
		} catch (IOException e) {
			ProPra.errorOutput(CLIENT_SOCKET_ERROR, e);
		}
		
		return EXIT_SUCCESS;
	}
	
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
