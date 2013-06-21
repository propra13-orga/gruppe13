package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;

class NetServerLogic extends NetIO implements Runnable {

	// variable for the game loop
	private boolean 	running;

	// variables for navigating in the level
	private int 		locationX, locationY;
	private CoreRoom 	currentRoom;

	// information about the level
	private int 		stage;
	private String 		boss;

	// information about the room
	private boolean 	finished; //to open doors once there are no enemys in the room, can be done by the collision
	
	private Figure 		figure;
	private Map			map;
	private CoreO_Game 	game;

	// figure values
	private double 		figX, figY;

	// List of all Objects within the game
	private CoreLevel 	level;
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	void setRunning(boolean boolIn) {
		running = boolIn;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	Figure getFigure () {
		return figure;
	}
	
	CoreRoom getRoom(){
		return currentRoom;
	}

	CoreLevel getLevel() {
		return level;
	}

	int getCurrentX() {
		return locationX;
	}

	int getCurrentY() {
		return locationY;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Initiate the current objects variables
	NetServerLogic(CoreO_Game inGame, int mode) {
		running = true;
		game = inGame;

		stage = 1;
		boss = "test";
		
		//create map
		map	= new Map();
		
		// create Level
		level = new CoreLevel(figure, mode, map);
		level.buildLevel(stage, boss);
		
		// find out where in the level we are, switching rooms will be relative to this value
		locationX = level.getStartX();
		locationY = level.getStartY();
		currentRoom = level.getRoom(locationX, locationY);
		currentRoom.getContent().add(figure);
		currentRoom.getContent().add(map);
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void setRoom(int newLocationX, int newLocationY) {
		locationX = newLocationX;
		locationY = newLocationY;

		currentRoom = level.getRoom(locationX, locationY);
		map.setVisited(locationX, locationY);

		currentRoom.getContent().add(figure);
		currentRoom.getContent().add(map);
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Do the Artificial Intelligence for all Enemies
	private void enemyAI() {
		Enemy enemy;
		ArrayList<CoreGameObjects> collidable = currentRoom.getContent();

		// iterate over all objects and do the AI of all Enemies
		for (int i = 0; i < collidable.size(); i++) {
			if (collidable.get(i) instanceof Enemy) {
				enemy = (Enemy) collidable.get(i);
				enemy.artificialIntelligence(figure, collidable);

				// check whether the enemy is dead yet
				if (enemy.stopDrawing()) {
					currentRoom.getContent().remove(enemy);
				}
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void checkCollision() {		
		//reset enemy check, falls 
		finished = true; 

		// method variables for
		double figR 		= figure.getRad();
		double figWidth 	= figure.getWidth();
		double figHeight 	= figure.getHeight();
		double tmpX, tmpY;

		double objX, objY;
		double objR;
		double objWidth, objHeight;

		double collOne, collTwo, collThree, collFour;

		ArrayList<CoreGameObjects> collidable = level.getRoom(locationX, locationY).getContent();
		CoreGameObjects collided;

		// check whether there are still enemies in the room
		for (int i = 0; i < collidable.size(); i++) {
			collided = collidable.get(i);
			if (collided instanceof Enemy){
				if (((Enemy) collided).leftForDead() == false && ((((Enemy)collided).getType() != Enemy.ENEMY_FIRE) || ((Enemy)collided).getType() != Enemy.ENEMY_FIRE_SHOOTING)) {
				finished = false;
				}				
			} 
		}
		
		// iterate over all objects within the room, excepting the figure, of course
		for (int i = 0; i < collidable.size(); i++) {
			collided = collidable.get(i);
			objX	 = collided.getPosX();
			objY	 = collided.getPosY();
			objR	 = collided.getRad();

			objWidth = collided.getWidth();
			objHeight = collided.getHeight();

			collOne = 1;
			collTwo = 1;
			collThree = 1;
			collFour = 1;

			// First check whether the objects are close enough to encounter one
			// another within the next couple of moves, use squares, saves a
			// couple of sqrt calls
			if ((((objX - figX) * (objX - figX) + (objY - figY) * (objY - figY)) < ((figR + objR) * (figR + objR))) 
					&& !(collided instanceof Attack)) {
				tmpX = figX - objX;
				tmpY = figY - objY;

				// Check whether there is a direct collision with any other object
				if (Math.abs(tmpX) < (figWidth + objWidth) / 2.) {
					collOne = tmpY- (figHeight + objHeight) / 2.;
					collTwo = -tmpY - (figHeight + objHeight)/2.;

				}

				// this will cover collision detection in x-direction, analogous to above
				if (Math.abs(tmpY) < (figHeight + objHeight) / 2.) {
					collThree = tmpX - (figWidth + objWidth) / 2.;
					collFour = -tmpX - (figWidth + objWidth) / 2.;
				}

				// Check collisions with objects, act accordingly
				if (collOne == 0 || collTwo == 0 || collThree == 0 || collFour == 0) {
					doCollision(collidable, collided);
				}
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void doCollision (ArrayList<CoreGameObjects> collidable, CoreGameObjects collided) {
		// variables for handling door-collision, names are the same as in the door class
		int destination;
		
		if (collided instanceof EnemyMelee) {
			// Should the figure and an enemy collide, the figure will automatically take damage
			((EnemyMelee) collided).attack(figure);
		}

		if (collided instanceof Item) {
			((Item) collided).modFigure(collidable, (Figure) figure);
		}
		
		if (collided instanceof MISCNPC){
			((MISCNPC) collided).talk();
		}
		
		if (collided instanceof MISCDoor) { //Doors MUST be checked last because of the new Method of Room-finishing
			destination = ((MISCDoor) collided).getDestination();

			if (finished) {			// check if there is no enemy found in the room
				switch (destination) { // only advance trough door if player is moving in the direction of the door diagonal movement should work too
				
				case 0:
					if (figure.getUpLeft() || figure.getUp() || figure.getUpRight()) {
						this.switchRoom(destination);
					}
					break;

				case 1:
					if (figure.getRight() || figure.getUpRight() || figure.getDownRight()) {
						this.switchRoom(destination);
					}
					break;

				case 2:
					if (figure.getDown() || figure.getDownRight() || figure.getDownLeft()) {
						this.switchRoom(destination);
					}
					break;

				case 3:
					if (figure.getLeft() || figure.getDownLeft() || figure.getUpLeft()) {
						this.switchRoom(destination);
					}
					break;

				case 4:
					this.switchRoom(destination);
					break;
				}
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Propagate all Bullets and create new Attacks
	private void attacks() {
		// Iterate over all Bullets and propagate them
		ArrayList<CoreGameObjects> collidable = currentRoom.getContent();
		Attack attack;
		MISCWall wall;
		boolean deleted;

		for (int i=0; i < collidable.size(); i++) {
			deleted = false;
			// handle attack propagation and check whether the attack is finished
			if (collidable.get(i) instanceof Attack) {
				attack = (Attack) collidable.get(i);
				attack.propagate(collidable);

				// Check whether we can destroy the bullet
				if (attack.getFinished()) {
					currentRoom.getContent().remove(attack);
					deleted = true;
				}
			}
	
			if (!deleted && collidable.get(i) instanceof MISCWall) {
				wall = (MISCWall) collidable.get(i);
				if (wall.getHP() == 0)
					currentRoom.getContent().remove(wall);
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void switchRoom(int destination) {

		// wechselt den Raum, falls die Figur an einer Stelle steht an der im aktuellen Raum eine Tür ist
		switch (destination) { // prüft in welchem Raum die Figur ist (bisher 0-2 für die 3 Räume)

		case (0):// Door leads up
			locationY--;
			figY = 12.49;
			this.setRoom(locationX, locationY);
			break;

		case (1): // Door leads to the right
			locationX++;
			figX = 0.51; // linker Spielfeldrand
			this.setRoom(locationX, locationY); // neuen Raum and Grafik und Logik geben
			break;

		case (2): // Door leads down
			locationY++;
			figY = 0.51;
			this.setRoom(locationX, locationY);
			break;

		case (3): // Door leads left
			locationX--;
			figX = 21.49;// rechter Spielfeldrand
			this.setRoom(locationX, locationY);
			break;

		case (4):
			stage++;
			if (stage < 4){
			boss = "test";// TODO maybe change the boss sometimes
			level.buildLevel(stage, boss); //generate new level
			locationX = level.getStartX(); //go to the start room
			locationY = level.getStartY();
			figX = 11.5; //put figure in the middle of the start room
			figY = 6.5;
			this.setRoom(locationX, locationY);
			}
			else{
				game.end(true);
			}

			break;
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	// Override run method from interface, this will have the game loop
	public void run() {
		long time;
		long temp;

		// game loop
		while (running) {
			time = System.currentTimeMillis();

			// get current figure positions and velocities
			figX = figure.getPosX();
			figY = figure.getPosY();

			// do the actual logic in this game
			this.checkCollision();
			this.attacks();
			this.enemyAI();

			// set the thread asleep, we don't need it too often
			try {
				if ((temp = System.currentTimeMillis() - time) < 16)
					Thread.sleep(16 - temp);
			} catch (InterruptedException e) {
				// don't care if the thread is interrupted
			}
		}
	}
}