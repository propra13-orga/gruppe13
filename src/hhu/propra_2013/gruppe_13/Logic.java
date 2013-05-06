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
	private boolean 	down, up, right, left;								//für die Bewegungsrichtungen
	private boolean		punch, use, bomb;									//für Aktionen
	private boolean		north, east, south, west, northwest, northeast, southwest, southeast;		//zum schießen in die Himmelsrichtungen
	private boolean 	freeright, freeup, freedown, freeleft;
	private int 		location;
	private GameObjects figure;
	private O_Game		game;
	
	// List of all Objects within the game
	private ArrayList<ArrayList<GameObjects>> 	rooms;
	private ArrayList<GameObjects> 				currentRoom;

	// Setter methods to determine whether a movement shall be initiated 
	void setDown(boolean in){
		down = in;
	}
	void setUp(boolean in){
		up = in;
	}
	void setRight(boolean in){
		right = in;
	}
	void setLeft(boolean in){
		left = in;
	}
	void setPunch(boolean in) {
		punch = in;		
	}
	void setUse(boolean in) {
		use = in;		
	}
	void setBomb(boolean in) {
		bomb = in;		
	}
	void setNorth(boolean in) {
		north = in;		
	}
	void setEast(boolean in) {
		east = in;		
	}
	void setSouth(boolean in) {
		south = in;		
	}
	public void setWest(boolean in) {
		west = in;		
	}
	public void setNorthwest(boolean in) {
		northwest = in;		
	}
	public void setNortheast(boolean in) {
		northeast = in;		
	}
	public void setSouthwest(boolean in) {
		southwest = in;		
	}
	public void setSoutheast(boolean in) {
		southeast = in;		
	}

	// Initiate the current objects variables
	Logic(ArrayList<ArrayList<GameObjects>> objectsInit, Figure inFigure, O_Game inGame) {
		rooms 		= objectsInit;
		figure 		= inFigure;
		game		= inGame;

		up 			= false;
		down		= false;
		right		= false;
		left		= false;
		
		punch		= false;
		use			= false;
		bomb		= false;
		
		north		= false;
		east		= false;
		south		= false;
		west		= false;
		
		northwest	= false;
		northeast	= false;
		southwest	= false;
		southeast	= false;
		
		freeright	= true;
		freeleft	= true;
		freeup		= true;
		freedown	= true;
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
		if(right && !left && freeright){
			figure.incX();
		}
		
		if(left && !right && freeleft){
			figure.decX();
		}
		
		if(up && !down && freeup){
			figure.decY();
		}
		
		if(down && !up && freedown){
			figure.incY();
		}
	}
	
	@Override //Override run method from interface, this will have the game loop
	public void run() {
		currentRoom = rooms.get(0);
		long time;
		long temp;
		
		// game loop
		while (true) {
			time = System.currentTimeMillis();

			this.checkDistance();
			this.checkCollision();
			this.moveFigure();
			this.moveEnemy();
			
			try {
				if((temp=System.currentTimeMillis()-time)<20)
				Thread.sleep(16-temp);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
