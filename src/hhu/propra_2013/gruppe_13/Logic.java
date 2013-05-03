package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;


/*
 * Ablauf: 	Logik bekommt die Bewegungsbefehle von der IO
 * 			Logik kennt die (Liste) der Objekte
 * 			(Gegner werden berechnet)
 * 			Kollisionsabfrage
 * 			Logik gibt den Objekten ihre neuen Positionen
 */


class Logic implements Runnable {
	
	// List of all Objects within the game
	ArrayList<ArrayList<GameObjects>> rooms;
	
	// Initiate the current objects variables
	void init(ArrayList<ArrayList<GameObjects>> objectsInit) {
		rooms = objectsInit;
	}
	
	@Override //Override run method from interface, this will have the game loop
	public void run() {
		// game loop
		while (true) {
			
		}
	}
	void setMoveX(boolean walkX){
		moveX = walkX;
	}
	void setMoveXn(boolean walkXn){
		moveXn = walkXn;
	}
	void setMoveY(boolean walkY){
		moveY = walkY;
	}
	void setMoveYn(boolean walkYn){
		moveYn = walkYn;
	}
}
