package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;


//NOCH NICHT VOLLSTÄNDIG DOKUMENTIERT!!!!
/**
 * Enthält die Spiellogik mit
 * -Gameloop
 * -Kollision
 * -verwaltet Gameobjects (Figur, Gegner, Items...)
 * TODO more Documentation
 * @author Gruppe13
 *
 */



class CoreLogic implements Runnable {
	// set these for the walking direction of the figure
	static final int NONE				= 0;
	static final int UP					= 1;
	static final int DOWN				= 2;
	static final int LEFT				= 3;
	static final int RIGHT				= 4;
	static final int UPLEFT				= 5;
	static final int UPRIGHT			= 6;
	static final int DOWNLEFT			= 7;
	static final int DOWNRIGHT			= 8;
	
	static final int FIRENONE			= 10;
	static final int FIREUP				= 11;
	static final int FIREDOWN			= 12;
	static final int FIRELEFT			= 13;
	static final int FIRERIGHT			= 14;
	static final int FIREUPLEFT			= 15;
	static final int FIREUPRIGHT		= 16;
	static final int FIREDOWNLEFT		= 17;
	static final int FIREDOWNRIGHT		= 18;
	
	// varibale for setting the running direction of the figure
	private int 		direction		= NONE;
	private int  		fireDirection	= FIRENONE;

	// set square root of 2 and define a boolean variable for the game loop
	private static final double SQRT_2 = 1.41421356237309504880168872420969807856967187537694807317667973799; // http://en.wikipedia.org/wiki/Square_root_of_2
	private boolean 	gameRunning;
	private boolean 	bulletEnable;
	private long 		bulletCoolDown;

	// Boolean variables for movement and collision detection, location counter for the room
	private boolean 	down, up, right, left; // für die Bewegungsrichtungen

	// variables for collision detection
	private boolean 	freeRight, freeUp, freeDown, freeLeft;
	private double 		distDown, distUp, distRight, distLeft;

	// variables for navigating in the level
	private int 		locationX, locationY;
	private CoreRoom 	currentRoom;

	// information about the level
	private int 		stage;
	private String 		boss;

	// information about the room
	private boolean 	finished; //to open doors once there are no enemys in the room, can be done by the collision
	
	private Figure 		figure, saveFigure; // TODO check why it was GameObjects
	private Map			map;
	private CoreO_Game 	game;

	// figure values
	private double 		figX, figY;
	private double 		figVX, figVY;

	private boolean 	aoe, esc, showMap; // für Aktionen map zeigt Map an

	// List of all Objects within the game
	private CoreLevel 	level;
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	void setGameRunning(boolean boolIn) {
		gameRunning = boolIn;
	}

	void setDirection (int input) {
		direction 	= input;
		figure.setDirection(input);
	}
	
	void setFireDirection (int input) {
		fireDirection 	= input;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	void setBomb(boolean in) {
		aoe = in;
	}
		
	void setShowMap(boolean in){
		showMap = in;
		if(map.getDraw() == true) showMap = false;
		map.setDraw(showMap);
	}

	void setEsc(boolean in){
		if (!esc) {
			esc = in;
		}
		else 
			esc = false;
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
	
	/**
	 * Konstruktor
	 * Variablen werden initialisiert, ein Level wird erzeugt sowie die Position des Startraums im Level wird gelesen
	 * Die Bewegung im Level (also das wechseln von Räumen) wird dann relativ zu diesem Startwert durchgeführt
	 * Die Position des Startraums ist in jedem Level gleich und wird nur bei Neustart des Spiels neu gewürfelt
	 * @param inFigure Erhält die Figur um Modifikationen (Schaden, Items) an ihr durchzuführen
	 * @param inGame Um Zugriff auf alles im Spiel zu erhalten
	 * @param mode Der Schwierigkeitsgrad, wird an den Levelbuilder weitergegeben
	 */
	
	
	// Initiate the current objects variables
	CoreLogic(Figure inFigure, CoreO_Game inGame, int mode) {
		esc = false;
		gameRunning = true;

		figure = inFigure;
		game = inGame;

		stage = 1;
		boss = "test";
		
		// create Level
		level = new CoreLevel(mode);
		level.buildLevel(stage, boss);

		// find out where in the level we are, switching rooms will be relative to this value
		locationX = level.getStartX();
		locationY = level.getStartY();
		
		// create the Map
		map	= new Map();
		map.setRoom(level.getConstruction());
		map.setVisited(locationX, locationY);
		
		currentRoom = level.getRoom(locationX, locationY);
		currentRoom.getContent().add(figure);
		currentRoom.getContent().add(map);
		
		freeRight 	= true;
		freeLeft 	= true;
		freeUp 		= true;
		freeDown 	= true;

		bulletEnable = true;
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	
	/**
	 * Wechselt den Raum zu dem in den Parametern angegebenen.
	 * Wird beim durchqueren von Türen aufgerufen, die neuen Koordinaten können aus der Destination Variable der Tür sowie den alten Koordinaten berechnet werden
	 * Außerdem wird der neu "entdeckte" Raum der Karte hinzugefügt
	 * 
	 * @param newLocationX X-Koordinate des Zielraums im Level
	 * @param newLocationY Y-Koordinate des Zielraums im Level
	 */
	
	
	private void setRoom(int newLocationX, int newLocationY) {

		locationX = newLocationX;
		locationY = newLocationY;

		currentRoom = level.getRoom(locationX, locationY);
		map.setVisited(locationX, locationY);

		currentRoom.getContent().add(figure);
		currentRoom.getContent().add(map);
		map.setVisited(locationX, locationY);
		game.setRoom(currentRoom);
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Do the Artificial Intelligence for all Enemies
	
	//TODO Dokumentieren
	
	private void enemyAI() {
		Enemy enemy;
		ArrayList<CoreGameObjects> collidable = currentRoom.getContent();

		// iterate over all objects and do the AI of all Enemies
		for (int i = 0; i < collidable.size(); i++) {
			if (collidable.get(i) instanceof Enemy) {
				enemy = (Enemy) collidable.get(i);
				enemy.artificialIntelligence(figure, collidable, false);

				// check whether the enemy is dead yet
				if (enemy.stopDrawing()) {
					currentRoom.getContent().remove(enemy);
				}
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	
	//TODO Dokumentieren
	
	private void checkCollision() {
		// reset collision values
		freeRight = true;
		freeLeft = true;
		freeUp = true;
		freeDown = true;
		
		//reset enemy check, falls 
		finished = true; 


		// reset distance values
		distUp = 50;
		distDown = 50;
		distLeft = 50;
		distRight = 50;

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

			collOne 	= 10;
			collTwo 	= 10;
			collThree 	= 10;
			collFour 	= 10;

			// First check whether the objects are close enough to encounter one
			// another within the next couple of moves, use squares, saves a
			// couple of sqrt calls
			if ((((objX - figX) * (objX - figX) + (objY - figY) * (objY - figY)) < ((figR + objR) * (figR + objR))) 
					&& !(collided == figure) && !(collided instanceof Attack)) {
				tmpX = figX - objX;
				tmpY = figY - objY;

				/*
				 * Reference point for all objects is the top left corner, as
				 * drawing exclusively starts here First of all we check,
				 * whether the object in question is on a collidable course in x
				 * or y direction, once that is done, the remaining distance
				 * between the two objects will be computed and saved in a
				 * corresponding double value.
				 * 
				 * Start with collisions in y-direction
				 */
				if (Math.abs(tmpX) < (figWidth + objWidth) / 2.) {
					// check whether the object is to the left or right of the
					// figure and whether the figure could reach it within one
					// step

					if (((tmpY) > 0) && (figVY > (collOne = tmpY- (figHeight + objHeight) / 2.))) {
						// set remaining distance and checking variable
						distUp = Math.min(distUp, collOne);
						freeUp = false;
					}
					else if ((tmpY < 0) && (figVY > (collTwo = -tmpY - (figHeight + objHeight) / 2.))) {
						distDown = Math.min(distDown, collTwo);
						freeDown = false;
					}
				}

				// this will cover collision detection in x-direction, analogous to above
				if (Math.abs(tmpY) < (figHeight + objHeight) / 2.) {
					if ((tmpX > 0) && (figVX > (collThree = tmpX - (figWidth + objWidth) / 2.))) {
						distLeft = Math.min(distLeft, collThree);
						freeLeft = false;
					} else if ((tmpX < 0) && (figVX > (collFour = -tmpX - (figWidth + objWidth) / 2.))) {
						distRight = Math.min(distRight, collFour);
						freeRight = false;
					}
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


			if (finished) {			// check if there is no enemy found in the room

				// before switching the room we make a copy of our figure for resurrection
				saveFigure = figure.copy();
				figDir = figure.getDirection();
				
				switch (destination) { // only advance trough door if player is moving in the direction of the door diagonal movement should work too
				
				case 0:
					if (figDir == UPLEFT || figDir == UP || figDir == UPRIGHT) {
						this.switchRoom(destination);
					}
					break;

				case 1:
					if (figDir == RIGHT || figDir == UPRIGHT || figDir == DOWNRIGHT) {
						this.switchRoom(destination);
						}
					break;

				case 2:
					if (figDir == DOWN || figDir == DOWNRIGHT || figDir == DOWNLEFT) {
						this.switchRoom(destination);
					}
					break;

				case 3:
					if (figDir == LEFT || figDir == DOWNLEFT || figDir == UPLEFT) {
						this.switchRoom(destination);
					}
					break;
				case 4:
					this.switchRoom(destination);

				}
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	/* This is the actual movement method, which checks for all directions
	 * whether the figure needs to be moved. Additionally the method checks
	 * whether the figure has reached a boundary and will prevent it from moving
	 * out of the gaming area. */
	private void moveFigure() {
		up 		= false;
		down 	= false;
		right 	= false;
		left 	= false;

		// convert diagonal movement into two horizontal components, diagonal
		// movement is slowed,
		// as it will take place into two directions
		if (direction == UPLEFT) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			left = true;
			up = true;
		}

		if (direction == UPRIGHT) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			right = true;
			up = true;
		}

		if (direction == DOWNLEFT) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			left = true;
			down = true;
		}

		if (direction == DOWNRIGHT) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			right = true;
			down = true;
		}

		// horizontal and vertical movement handlers, these also try to find
		// out, whether there is anything
		// within a diagonal direction inhibiting movement.
		if (direction == RIGHT || right) {
			if (freeRight) {
				if (figX + figVX >= 21.5)
					figX = 21.5;
				else
					figX += figVX;
			} else
				figX += distRight;
		}

		if (direction == LEFT || left) {
			if (freeLeft) {
				if (figX - figVX <= 0.5)
					figX = 0.5;
				else
					figX -= figVX;
			} else
				figX -= distLeft;
		}

		if (direction == UP || up) {
			if (freeUp) {
				if (figY - figVY <= 0.5)
					figY = 0.5;
				else
					figY -= figVY;
			} else
				figY -= distUp;
		}

		if (direction == DOWN || down) {
			if (freeDown) {
				if (figY + figVY >= 12.5)
					figY = 12.5;
				else
					figY += figVY;
			} else
				figY += distDown;
		}

		// finally set the position of the figure
		figure.setPos(figX, figY);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Propagate all Bullets and create new Attacks
	private void attacks() {
		// Iterate over all Bullets and propagate them
		ArrayList<CoreGameObjects> collidable = currentRoom.getContent();
		Attack attack;
		MiscWall wall;
		boolean deleted;

		for (int i = 0; i < collidable.size(); i++) {

			deleted = false;
			// handle attack propagation and check whether the attack is finished
			if (collidable.get(i) instanceof Attack) {
				attack = (Attack) collidable.get(i);
				attack.propagate(collidable, false);

				// Check whether we can destroy the bullet
				if (attack.getFinished()) {
					currentRoom.getContent().remove(attack);
					deleted = true;
				}
			}
	
			if (!deleted && collidable.get(i) instanceof MiscWall) {
				wall = (MiscWall) collidable.get(i);
				if (wall.getHP() == 0)
					currentRoom.getContent().remove(wall);
			}
		}

		// If the player has enough resources, create a new area of effect attack
		if (aoe && figure.getChocolate() > 0) {
			figure.setChocolate(figure.getChocolate()-1);
			CoreGameObjects melee = new AttackMelee(figX, figY, 0, 0, Attack.PLAYER_MELEE_AOE, figure, collidable, figure.getPlayer());
			currentRoom.getContent().add(melee);
		}
		
		// regardless of whether an attack has been fired we need to reset this variable
		aoe = false;

		// Create new Bullets if the player wishes to do so, and the cooldown
		// for shooting has expired
		if (fireDirection != FIRENONE) {
			
			if (!bulletEnable) {
				if (System.currentTimeMillis() - bulletCoolDown > figure.getBulletCoolDownTime())
					bulletEnable = true;
			}
			
			if (bulletEnable) {
				// Save current system time in order to check the cooldown
				bulletCoolDown = System.currentTimeMillis();

				// Check which direction to shoot, this depends on the input
				int signVX = 0;
				int signVY = 0;

				
				if ( fireDirection == FIREUP ) {
					signVX = 0;
					signVY = -1;
				}

				else if (fireDirection == FIRERIGHT) {
					signVX = 1;
					signVY = 0;
				}

				else if (fireDirection == FIREDOWN) {
					signVX = 0;
					signVY = 1;
				}

				else if (fireDirection == FIRELEFT) {
					signVX = -1;
					signVY = 0;
				}

				else if (fireDirection == FIREUPRIGHT) {
					signVX = 1;
					signVY = -1;
				}

				else if (fireDirection == FIREUPLEFT) {
					signVX = -1;
					signVY = -1;
				}

				else if (fireDirection == FIREDOWNRIGHT) {
					signVX = 1;
					signVY = 1;
				}

				else if (fireDirection == FIREDOWNLEFT) {
					signVX = -1;
					signVY = 1;
				}

				CoreGameObjects initBullet = new AttackBullet(figure.getBulletType(), figX, figY, figVX, figVY, signVX, signVY, figure.getPlayer());

				currentRoom.getContent().add(initBullet);
				bulletEnable = false;

			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void checkFigure() {
		if (figure.getHP() <= 0 && !figure.checkRes()) {
			game.end(false);
		}
		if (figure.getHP() <= 0 && figure.checkRes()) {
			figure = saveFigure.copy();
			figure.setHP(3);
			figX = 11.5;
			figY = 6.5;
			figure.setItem1(null);
			figure.setItem2(null);
			figure.setItem3(null);
			this.setRoom(level.getStartX(), level.getStartY());
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void switchRoom(int destination) {
		
		map.setVisited(locationX, locationY);
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
		while (gameRunning) {
			time  = System.currentTimeMillis();

			// get current figure positions and velocities
			figX  = figure.getPosX();
			figY  = figure.getPosY();
			figVX = figure.getVX();
			figVY = figure.getVY();

			// do the actual logic in this game
			if(!showMap && !esc){
				this.checkCollision();
				this.moveFigure();
				this.attacks();
				this.enemyAI();
				this.checkFigure();
			}

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
