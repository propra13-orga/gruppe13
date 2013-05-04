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
	
	// Boolean variables for movement and collision detection, location counter for the room
	private boolean 	moveX, moveXn, moveY, moveYn;
	private boolean 	freeX, freeY, freeYn, freeXn;
	private int 		location;
	private GameObjects figure;
	
	// Setter methods to determine whether a movement shall be initiated 
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
	void init(ArrayList<ArrayList<GameObjects>> objectsInit, Figure inFigure) {
		rooms 	= objectsInit;
		figure 	= inFigure;
		
		moveX 	= false;
		moveXn	= false;
		moveY	= false;
		moveYn	= false;
		
		freeX	= true;
		freeXn	= true;
		freeY	= true;
		freeYn	= true;
	}
	
	private void checkDistance() {
		
	}
	
	private void moveEnemy() {
		// TODO Auto-generated method stub, wird erstmal leer bleiben, da wir noch keine KI haben
	}
	
	private void checkCollision() {
		// TODO Auto-generated method stub
	}
	
	private void moveFigure() {
		if(moveX && !moveXn && freeX){
			figure.incX();
		}
		
		if(moveXn && !moveX && freeXn){
			
		}
		
		if(moveY && !moveYn && freeY){
			
		}
		
		if(moveYn && !moveY && freeYn){
		
		}
	}
	
	@Override //Override run method from interface, this will have the game loop
	public void run() {
		// game loop
		while (true) {
			this.checkDistance();
			this.checkCollision();
			this.moveFigure();
			this.moveEnemy();
		}
	}
}
