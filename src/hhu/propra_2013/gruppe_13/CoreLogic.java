package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;

class CoreLogic implements Runnable {

	// set square root of 2 and define a boolean variable for the game loop
	private static final double SQRT_2 = 1.41421356237309504880168872420969807856967187537694807317667973799; // http://en.wikipedia.org/wiki/Square_root_of_2
	private boolean 	gameRunning;
	private boolean 	bulletEnable;
	private long 		bulletCoolDown;

	// Boolean variables for movement and collision detection, location counter for the room
	private boolean 	down, up, right, left, upLeft, upRight, downLeft, downRight; // für die Bewegungsrichtungen
	private boolean 	north, east, south, west, northwest, northeast, southwest, southeast; // zum schießen in die Himmelsrichtungen

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

	private boolean 	punch, use, aoe, showMap, esc; // für Aktionen map zeigt Map an

	// List of all Objects within the game
	private CoreLevel 	level;
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	void setGameRunning(boolean boolIn) {
		gameRunning = boolIn;
	}

	void setDown(boolean in) {
		down = in;
	}

	void setUp(boolean in) {
		up = in;
	}

	void setRight(boolean in) {
		right = in;
	}

	void setLeft(boolean in) {
		left = in;
	}

	void setUpRight(boolean in) {
		upRight = in;
	}

	void setUpLeft(boolean in) {
		upLeft = in;
	}

	void setDownRight(boolean in) {
		downRight = in;
	}

	void setDownLeft(boolean in) {
		downLeft = in;
	}

	void setPunch(boolean in) {
		punch = in;
	}

	void setUse(boolean in) {
		use = in;
	}

	void setBomb(boolean in) {
		aoe = in;
	}
	
	void setShowMap(boolean in){
		showMap = in;
		if(map.getDraw() == true) showMap = false;
		map.setDraw(showMap);
	}

	void setEsc(boolean in){
		esc = in;
	}
	
	void setFireUp(boolean in) {
		north = in;
	}

	void setFireRight(boolean in) {
		east = in;
	}

	void setFireDown(boolean in) {
		south = in;
	}

	void setFireLeft(boolean in) {
		west = in;
	}

	void setFireUpLeft(boolean in) {
		northwest = in;
	}

	void setFireUpRight(boolean in) {
		northeast = in;
	}

	void setFireDownLeft(boolean in) {
		southwest = in;
	}

	void setFireDownRight(boolean in) {
		southeast = in;
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
	CoreLogic(Figure inFigure, CoreO_Game inGame, int mode) {
		gameRunning = true;

		figure = inFigure;
		game = inGame;

		stage = 1;
		boss = "test";
		
		// create Level and Map
		level = new CoreLevel(mode);
		level.buildLevel(stage, boss);

		map	= new Map();
		map.setRoom(level.getConstruction());
		
		// find out where in the level we are, switching rooms will be relative to this value
		locationX = level.getStartX();
		locationY = level.getStartY();
		
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
	private void setRoom(int newLocationX, int newLocationY) {
		locationX = newLocationX;
		locationY = newLocationY;

		currentRoom = level.getRoom(locationX, locationY);
		map.setVisited(locationX, locationY);

		currentRoom.getContent().add(figure);
		currentRoom.getContent().add(map);

		game.setRoom(currentRoom);
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
				if (((Enemy) collided).leftForDead() == false && ((((Enemy)collided).getType() != Enemy.ENEMY_FIRE) || ((Enemy)collided).getType() != Enemy.ENEMY_FIRE)) {
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

				// before switching the room we make a copy of our figure for resurrection
				saveFigure = figure;

				switch (destination) { // only advance trough door if player is moving in the direction of the door diagonal movement should work too
				
				case 0:
					if (upLeft || up || upRight) {
						this.switchRoom(destination);
					}
					break;

				case 1:
					if (right || upRight || downRight) {
						this.switchRoom(destination);
					}
					break;

				case 2:
					if (down || downRight || downLeft) {
						this.switchRoom(destination);
					}
					break;

				case 3:
					if (left || downLeft || upLeft) {
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
		// First set the direction of the figure,as we are gonna fuck around with the input variables just now
		this.setDirection();

		// convert diagonal movement into two horizontal components, diagonal
		// movement is slowed,
		// as it will take place into two directions
		if (upLeft) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			left = true;
			up = true;
		}

		if (upRight) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			right = true;
			up = true;
		}

		if (downLeft) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			left = true;
			down = true;
		}

		if (downRight) {
			figVX /= SQRT_2;
			figVY /= SQRT_2;
			right = true;
			down = true;
		}

		// horizontal and vertical movement handlers, these also try to find
		// out, whether there is anything
		// within a diagonal direction inhibiting movement.
		if (right) {
			if (freeRight) {
				if (figX + figVX >= 21.5)
					figX = 21.5;
				else
					figX += figVX;
			} else
				figX += distRight;
		}

		if (left) {
			if (freeLeft) {
				if (figX - figVX <= 0.5)
					figX = 0.5;
				else
					figX -= figVX;
			} else
				figX -= distLeft;
		}

		if (up) {
			if (freeUp) {
				if (figY - figVY <= 0.5)
					figY = 0.5;
				else
					figY -= figVY;
			} else
				figY -= distUp;
		}

		if (down) {
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
	private void setDirection() {
		/* set the viewing direction of the figure, at the moment this is subject of the walking direction, 
		 * it is questionable whether we wish to anchor this to the shooting direction. */
		if (up)					figure.setDirection(Figure.UP);
		else if (down)			figure.setDirection(Figure.DOWN);
		else if (left)			figure.setDirection(Figure.LEFT);
		else if (right)			figure.setDirection(Figure.RIGHT);
		else if (upLeft)		figure.setDirection(Figure.UPLEFT);
		else if (upRight)		figure.setDirection(Figure.UPRIGHT);
		else if (downLeft)		figure.setDirection(Figure.DOWNLEFT);
		else if (downRight)		figure.setDirection(Figure.DOWNRIGHT);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Propagate all Bullets and create new Attacks
	private void attacks() {
		// Iterate over all Bullets and propagate them
		ArrayList<CoreGameObjects> collidable = currentRoom.getContent();
		Attack attack;
		MISCWall wall;
		boolean deleted;

		for (int i = 0; i < collidable.size(); i++) {

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

		// If the player has enough resources, create a new area of effect attack
		if (aoe && figure.getChocolate() > 0) {
			figure.setChocolate(figure.getChocolate()-1);
			CoreGameObjects melee = new Melee(figX, figY, 0, 0, Attack.PLAYER_MELEE_AOE, figure, collidable, figure.getPlayer());
			currentRoom.getContent().add(melee);
		}
		
		// regardless of whether an attack has been fired we need to reset this variable
		aoe = false;

		// Create new Bullets if the player wishes to do so, and the cooldown
		// for shooting has expired
		if (north || east || south || west || northeast || northwest || southeast || southwest) {
			
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

				if (north) {
					signVX = 0;
					signVY = -1;
				}

				else if (east) {
					signVX = 1;
					signVY = 0;
				}

				else if (south) {
					signVX = 0;
					signVY = 1;
				}

				else if (west) {
					signVX = -1;
					signVY = 0;
				}

				else if (northeast) {
					signVX = 1;
					signVY = -1;
				}

				else if (northwest) {
					signVX = -1;
					signVY = -1;
				}

				else if (southeast) {
					signVX = 1;
					signVY = 1;
				}

				else if (southwest) {
					signVX = -1;
					signVY = 1;
				}

				CoreGameObjects initBullet = new Bullet(figure.getBulletType(), figX, figY, figVX, figVY, signVX, signVY, figure.getPlayer());

				currentRoom.getContent().add(initBullet);
				bulletEnable = false;

			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void checkFigure() {
		if (figure.getHP() <= 0 && !figure.checkRes()) {
			game.end(false);
			System.out.println("You died!");
		}
		if (figure.getHP() <= 0 && figure.checkRes()) {
			figure = saveFigure;
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
			time = System.currentTimeMillis();

			// get current figure positions and velocities
			figX = figure.getPosX();
			figY = figure.getPosY();
			figVX = figure.getVX();
			figVY = figure.getVY();

			// do the actual logic in this game
			if(!showMap){
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
