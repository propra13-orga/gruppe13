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
	
	private boolean moveX, moveXn, moveY, moveYn ,freeX, freeY, freeYn, freeXn;
	private int location;
	void setMoveX(boolean in){
		moveX = in;
	}
	void setMoveXn(boolean in){
		moveXn = in;
	}
	void setMoveY(boolean in){
		moveY = in;
	}
	void setMoveYn(boolean in){
		moveYn = in;
	}
	
	
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

			this.checkDistance();
			this.checkCollision(moveX,moveXn,moveY,moveYn);
			this.moveFigure(moveX,moveXn,moveY,moveYn);
			this.moveEnemy(moveX,moveXn,moveY,moveYn);
		}
	}
	private void checkDistance() {
				
	}
	private void moveEnemy(boolean moveX2, boolean moveXn2, boolean moveY2,
			boolean moveYn2) {
		// TODO Auto-generated method stub
		
	}
	private void checkCollision(boolean moveX2, boolean moveXn2,
			boolean moveY2, boolean moveYn2) {
		// TODO Auto-generated method stub
		
	}
	private void moveFigure(boolean moveX2, boolean moveXn2, boolean moveY2,
			boolean moveYn2) {
		if(moveX2 && !moveXn2){
			if(freeX){
				
			}
		}
		if(moveXn2 && !moveX2){
			if(freeXn){
				
			}
		}
		if(moveY2 && !moveYn2){
			if(freeY){
				
			}
		}
		if(moveYn2 && !moveY2){
			if(freeYn){
				
			}
		}
		
	}

}
