package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;

class NetClientLogic extends NetIO implements Runnable {
	// set these for the walking direction of the figure
	static final int NONE			= 0;
	static final int UP				= 1;
	static final int DOWN			= 2;
	static final int LEFT			= 3;
	static final int RIGHT			= 4;
	static final int UPLEFT			= 5;
	static final int UPRIGHT		= 6;
	static final int DOWNLEFT		= 7;
	static final int DOWNRIGHT		= 8;
	
	static final int FIRENONE		= 10;
	static final int FIREUP			= 11;
	static final int FIREDOWN		= 12;
	static final int FIRELEFT		= 13;
	static final int FIRERIGHT		= 14;
	static final int FIREUPLEFT		= 15;
	static final int FIREUPRIGHT	= 16;
	static final int FIREDOWNLEFT	= 17;
	static final int FIREDOWNRIGHT	= 18;
	
	// variable for setting the running direction of the figure
	private int 		direction		= NONE;
	private int  		fireDirection	= FIRENONE;

	// set square root of 2 and define a boolean variable for the game loop
	private static final double SQRT_2 = 1.41421356237309504880168872420969807856967187537694807317667973799; // http://en.wikipedia.org/wiki/Square_root_of_2
	private boolean 	running;
	private boolean 	bulletEnable;
	private long 		bulletCoolDown;

	// Boolean variables for movement and collision detection, location counter for the room
	private boolean 	down, up, right, left; // für die Bewegungsrichtungen

	// variables for collision detection
	private boolean 	freeRight, freeUp, freeDown, freeLeft;
	private double 		distDown, distUp, distRight, distLeft;

	// variables for navigating in the level
	private ArrayList<CoreGameObjects> currentRoom;

	// information about the room
	private Figure 		figure;
	private Map			map;

	// figure values
	private double 		figX, figY;
	private double 		figVX, figVY;

	private boolean 	aoe, showMap; // für Aktionen map zeigt Map an

	// List of all Objects within the game
	private CoreLevel 	level;
	
	// Connections of the client
	private NetClientIn		incoming;
	private NetClientOut 	outgoing;
	
	// the clients drawing class
	private NetClientGameDrawer drawer;
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void setDirection (int input) {
		direction 	= input;
		figure.setDirection(input);
	}
	
	void setFireDirection (int input) {
		fireDirection 	= input;
	}

	void setBomb(boolean in) {
		aoe = in;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	void setShowMap(boolean in){
		showMap = in;
		if(map.getDraw() == true) showMap = false;
		map.setDraw(showMap);
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	Figure getFigure () {
		return figure;
	}
	
	ArrayList<CoreGameObjects> getRoom(){
		return currentRoom;
	}

	CoreLevel getLevel() {
		return level;
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Initiate the current objects variables
	NetClientLogic(Figure inFigure, NetClientIn incoming, NetClientOut outgoing, NetClientGameDrawer drawer) {
		running = true;
		
		this.incoming 	= incoming;
		this.outgoing 	= outgoing;	
		this.drawer		= drawer;
		
		figure = inFigure;
		
		freeRight 	= true;
		freeLeft 	= true;
		freeUp 		= true;
		freeDown 	= true;

		bulletEnable = true;
		currentRoom = new ArrayList<CoreGameObjects>();
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	private void checkCollision() {
		// reset collision values
		freeRight = true;
		freeLeft = true;
		freeUp = true;
		freeDown = true;
		
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

		CoreGameObjects collided;

		// iterate over all objects within the room, excepting the figure, of course
		for (int i = 0; i < currentRoom.size(); i++) {
			collided 	= currentRoom.get(i);
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

		// convert diagonal movement into two horizontal components, diagonal movement is slowed, as it will take place into two directions
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
		// If the player has enough resources, create a new area of effect attack
		if (aoe && figure.getChocolate() > 0) {
			figure.setChocolate(figure.getChocolate()-1);
			CoreGameObjects melee = new AttackMelee(figX, figY, 0, 0, Attack.PLAYER_MELEE_AOE, figure, currentRoom, figure.getPlayer());
			currentRoom.add(melee);
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

				currentRoom.add(initBullet);
				bulletEnable = false;
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	public void run() {
		long time;
		long temp;

		// game loop
		while (running) {
			time = System.currentTimeMillis();
			
			// get the current room from the server
			incoming.getList(currentRoom, figure);
			drawer.setRoom(currentRoom);
			
//			System.out.println(currentRoom);
			// check whether we actually have a list from the server yet, else we try to get one again
			if (currentRoom == null) 
				continue;
			
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
			}

			// send all modifications to the room and reset incoming list of the connection
			outgoing.sendList(currentRoom);
			incoming.resetList();
			
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
