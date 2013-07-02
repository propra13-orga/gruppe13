package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;

class NetServerLogic extends NetIO {

	// set these for the running direction of the figure
	static final int 	NONE		= 0;
	static final int 	UP			= 1;
	static final int 	DOWN		= 2;
	static final int 	LEFT		= 3;
	static final int 	RIGHT		= 4;
	static final int 	UPLEFT		= 5;
	static final int 	UPRIGHT		= 6;
	static final int 	DOWNLEFT	= 7;
	static final int 	DOWNRIGHT	= 8;

	// variable for the game loop
	private boolean 	running;

	// variables for navigating in the level
	private int 		locationX, locationY;
	private CoreRoom 	currentRoom;

	// information about the level
	private int 		stage;
//	private String 		boss;

	// information about the room
	private boolean 	finished; //to open doors once there are no enemies in the room, can be done by the collision	

	// figure values
	private Figure		figure;
	private double 		figX, figY;
	private Map			map;

	// List of all Objects within the game
	private CoreLevel 				level;
	private ArrayList<NetServerIn>	incoming;
	private ArrayList<NetServerOut> outgoing;
	
	// list for checking if all players have finished the level
	private ArrayList<Integer>		levelComplete;
	
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
	NetServerLogic(CoreLevel level, ArrayList<NetServerIn> incoming, ArrayList<NetServerOut> outgoing) {
		running 	= true;
		stage 		= 1;
		
		levelComplete = new ArrayList<Integer>();
		
		// create Level
		this.level 	= level;
		
		// find out where in the level we are, switching rooms will be relative to this value
		locationX	= level.getStartX();
		locationY 	= level.getStartY();
		currentRoom = level.getRoom(locationX, locationY);
		
		// Get ArrayLists for all connections
		this.incoming 	= incoming;
		this.outgoing 	= outgoing;
		
		// set the current room for all incoming connections
		for (int i=0; i<incoming.size(); i++) {
			incoming.get(i).setLocation(locationX, locationY);
			levelComplete.add(PLAYING);
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void setRoom(int newLocationX, int newLocationY, int client) {
		// set new location
		locationX = newLocationX;
		locationY = newLocationY;

		// get the new room and set it in the servers incoming object and the map
		currentRoom = level.getRoom(locationX, locationY);
		incoming.get(client).setLocation(locationX, locationY);
		map.setVisited(locationX, locationY);

		// add the figure and the map to the game content
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
				enemy.artificialIntelligence(figure, collidable, true);

				// check whether the enemy is dead yet
				if (enemy.stopDrawing()) {
					currentRoom.getContent().remove(enemy);
				}
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void checkCollision(int client) {		
		//reset enemy check, will be set to false if there are still enemies within the room
		finished = true; 

		// method variables for the figure, object in question and the object that was collided with
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
			collided 	= collidable.get(i);
			objX	 	= collided.getPosX();
			objY	 	= collided.getPosY();
			objR	 	= collided.getRad();

			objWidth 	= collided.getWidth();
			objHeight 	= collided.getHeight();

			collOne 	= 1;
			collTwo 	= 1;
			collThree 	= 1;
			collFour 	= 1;

			// First check whether the objects are close enough to encounter one another within the next couple of moves, use squares, saves a couple of sqrt calls
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
					doCollision(collidable, collided, client);
				}
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void doCollision (ArrayList<CoreGameObjects> collidable, CoreGameObjects collided, int client) {
		// variables for handling door-collision, names are the same as in the door class
		int destination;
		int figDir;
		
		if (collided instanceof EnemyMelee) {
			// Should the figure and an enemy collide, the figure will automatically take damage
			((EnemyMelee) collided).attack(figure);
		}

		if (collided instanceof Item) {
			((Item) collided).modFigure(collidable, (Figure) figure);
		}
		
		if (collided instanceof MiscNPC){
			((MiscNPC) collided).talk();
		}
		
		if (collided instanceof MiscDoor) { //Doors MUST be checked last because of the new Method of Room-finishing
			destination = ((MiscDoor) collided).getDestination();

			figDir = figure.getDirection();
			
			if (finished) {			// check if there is no enemy found in the room
				switch (destination) { // only advance trough door if player is moving in the direction of the door diagonal movement should work too
				
				case 0:
					if (figDir == UPLEFT || figDir == UP || figDir == UPRIGHT) {
						this.switchRoom(destination, client);
					}
					break;

				case 1:
					if (figDir == RIGHT || figDir == UPRIGHT || figDir == DOWNRIGHT) {
						this.switchRoom(destination, client);
					}
					break;

				case 2:
					if (figDir == DOWN || figDir == DOWNRIGHT || figDir == DOWNLEFT) {
						this.switchRoom(destination, client);
					}
					break;

				case 3:
					if (figDir == LEFT || figDir == DOWNLEFT || figDir == UPLEFT) {
						this.switchRoom(destination, client);
					}
					break;

				case 4:
					this.switchRoom(destination, client);
					break;
				}
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Propagate all Bullets and create new Attacks
	private void attacks(int client) {
		// Iterate over all Bullets and propagate them
		ArrayList<CoreGameObjects> collidable = currentRoom.getContent();
		Attack attack;
		MiscWall wall;
		boolean deleted;

		for (int i=0; i < collidable.size(); i++) {
			deleted = false;
			// handle attack propagation and check whether the attack is finished
			if (collidable.get(i) instanceof Attack) {
				attack = (Attack) collidable.get(i);
				attack.propagate(collidable, true);

				// Check whether we can destroy the bullet
				if (attack.getFinished()) {
					currentRoom.getContent().remove(attack);
					incoming.get(client).removeAttack(attack);

					deleted = true;
				}
			}
	
			// handle all walls, remove them if they are "dead"
			if (!deleted && collidable.get(i) instanceof MiscWall) {
				wall = (MiscWall) collidable.get(i);
				if (wall.getHP() == 0)
					currentRoom.getContent().remove(wall);
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void switchRoom(int destination, int client) {

		// wechselt den Raum, falls die Figur an einer Stelle steht an der im aktuellen Raum eine Tür ist
		switch (destination) { // prüft in welchem Raum die Figur ist (bisher 0-2 für die 3 Räume)

		case (0):// Door leads up
			locationY--;
			figY = 12.49;
			
			this.setRoom(locationX, locationY, client);
			break;

		case (1): // Door leads to the right
			locationX++;
			figX = 0.51; // linker Spielfeldrand
			
			this.setRoom(locationX, locationY, client); // neuen Raum and Grafik und Logik geben
			break;

		case (2): // Door leads down
			locationY++;
			figY = 0.51;
			
			this.setRoom(locationX, locationY, client);
			break;

		case (3): // Door leads left
			locationX--;
			figX = 21.49;// rechter Spielfeldrand
			
			this.setRoom(locationX, locationY, client);
			break;

		case (4):
			levelComplete.set(client, FINISHED);
			figure.setActivity(FINISHED);
			break;
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void checkFigure(int client) {
		if (figure.getHP() <= 0) {
			levelComplete.set(client, DEAD);
			figure.setActivity(DEAD);
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void switchLevel() {
		stage++;
		
		if (stage < 4) {
			// TODO maybe change the boss sometimes
			String boss = "test";
			
			// find out where in the starting room to put each figure
			int amount 	= incoming.size();
			amount++;
			
			double inX = 22./(amount);
			double inY = 6.5;
			
			// build a new level
			level.buildLevel(stage, boss); //generate new level
			locationX = level.getStartX(); //go to the start room
			locationY = level.getStartY();

			// tell all clients, that we have a new room, set the figures accordingly
			for (int i=0; i<incoming.size(); i++) {
				this.setRoom(locationX, locationY, i);
				incoming.get(i).getFigure().setPos((inX*(i+1)+0.5), inY);
			}
		} else {
			// TODO: terminate the game
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	// Override run method from interface, this will have the game loop
	public void run() {
//		long time;
//		long temp;
		boolean newLevel;
		
		// set the maps of all clients and add to internal array list
		for (int i=0; i<incoming.size(); i++) {
			incoming.get(i).setMap(new Map());
			incoming.get(i).getMap().setRoom(level.getConstruction());
		}
		
		// game loop
		while (running) {
			newLevel = true;
//			time = System.currentTimeMillis();

			// iterate over all connections
			for (int i=0; i<incoming.size(); i++) {
				
				// get the figure and map and the current room
				figure 		= incoming.get(i).getFigure();
				map 		= incoming.get(i).getMap();
				currentRoom = level.getRoom(incoming.get(i).getLocation(0), incoming.get(i).getLocation(1));
				
				// update the room according to all changes made by the client
				incoming.get(i).updateRoom(currentRoom.getContent(), figure.getPlayer());
				
				// get current figure positions and velocities
				figX = figure.getPosX();
				figY = figure.getPosY();

				// do the actual logic in this game
				this.checkCollision(i);
				this.attacks(i);
				this.enemyAI();
				this.checkFigure(i);
				
				// set the objects to send back to the client
				outgoing.get(i).setRoom(currentRoom.getContent());
			}

			for (int i=0; i<levelComplete.size(); i++) {
				if (levelComplete.get(i) == PLAYING) {
					newLevel = false;
					break;
				}
			}
			
			if (newLevel) {
				switchLevel();
			}
//			// set the thread asleep, we don't need it too often
//			try {
//				if ((temp = System.currentTimeMillis() - time) < 8)
//					Thread.sleep(8 - temp);
//			} catch (InterruptedException e) {
//				// don't care if the thread is interrupted
//			}
		}
	}
}