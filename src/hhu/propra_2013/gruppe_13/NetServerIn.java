package hhu.propra_2013.gruppe_13;

import java.net.Socket;
import java.util.ArrayList;

class NetServerIn extends NetIO {
	private NetServerLogic 	logic;
	private Socket 			socket;
	
	private boolean 		running;
	
	private int[]			location;
	private Figure			figure;
	
	private ArrayList<Attack> attacks;
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	NetServerIn (Socket inSocket) {
		socket 		= inSocket;
		running 	= true;
		
		location 	= new int[2];
		figure		= null;
		attacks		= null;
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	int getLocation(int i) {
		// x-variable stored in 0, y stored in 1
		return location[i];
	}
	
	Figure getFigure () {
		return figure;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning (boolean inRunning) {
		running = inRunning;
	}
	
	void setLogic (NetServerLogic inLogic) {
		logic = inLogic;
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	void updateRoom(ArrayList<CoreGameObjects> content, int player) {
		// go over all content and delete the old figure and old attacks by that client
		CoreGameObjects object;
		for (int i=0; i<content.size(); i++) {
			object = content.get(i);
			
			if (object instanceof Figure && ((Figure)object).getPlayer() == player) 
				content.remove(i);
			
			if (object instanceof Attack && ((Attack)object).getPlayer() == player)
				content.remove(i);
		}
		
		// add a copy of the figure and all attacks into the current room used by the logic
		content.add(figure.copy());
		
		for (int i=0; i<attacks.size(); i++) {
			content.add(attacks.get(i).copy());
		}
	}
	
	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		while (running) {
			
		}
	}
}
