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
	private O_Game		game;
	
	// List of all Objects within the game
	private ArrayList<ArrayList<GameObjects>> 	rooms;
	private ArrayList<GameObjects> 				currentRoom;

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
	
	// Initiate the current objects variables
	Logic(ArrayList<ArrayList<GameObjects>> objectsInit, Figure inFigure, O_Game inGame) {
		rooms 	= objectsInit;
		figure 	= inFigure;
		game	= inGame;
		
		moveX 	= false;
		moveXn	= false;
		moveY	= false;
		moveYn	= false;
		
		freeX	= true;
		freeXn	= true;
		freeY	= true;
		freeYn	= true;
	}
	
	private void setRoom(int newLocation) {
		game.setRoom(newLocation);
	}
	
	private void checkDistance() {
		
	}
	
	private void moveEnemy() {
		// TODO Auto-generated method stub, wird erstmal leer bleiben, da wir noch keine KI haben
	}
	
	private void checkCollision() {
		double figR 	= figure.getRad();
		double posX		= figure.getPosX();
		double posY		= figure.getPosY();
		
		double objX;
		double objY;
		double objR;
		
		// TODO Auto-generated method stub, hier muss noch genau festgelegt werden, wie wir Kollisionen feststellen wollen
		// iterate over all objects within the room
		for(GameObjects collidable: currentRoom) {
			objX = collidable.getPosX();
			objY = collidable.getPosY();
			objR = collidable.getRad();
			
			if (Math.sqrt((objX-posX)*(objX-posX)+(objY-posY)*(objY-posY)) < figR+objR) {
				// TODO: figure out an ingenious collision detection algorithm
			}
		}
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
		currentRoom = rooms.get(0);
		// game loop
		while (true) {
			System.err.println(moveX+" "+moveXn+" "+moveY+" "+moveYn);
			this.checkDistance();
			this.checkCollision();
			this.moveFigure();
			this.moveEnemy();
		}
	}
}
