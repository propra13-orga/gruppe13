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
	
	// variable for setting the running direction of the figure
	private int 		direction	= NONE;

	// set square root of 2 and define a boolean variable for the game loop
	private static final double SQRT_2 = 1.41421356237309504880168872420969807856967187537694807317667973799; // http://en.wikipedia.org/wiki/Square_root_of_2
	private boolean 	running;
	private boolean 	bulletEnable;
	private long 		bulletCoolDown;

	// Boolean variables for movement and collision detection, location counter for the room
	private boolean 	down, up, right, left; // für die Bewegungsrichtungen
	private boolean 	north, east, south, west, northwest, northeast, southwest, southeast; // zum schießen in die Himmelsrichtungen

	// variables for collision detection
	private boolean 	freeRight, freeUp, freeDown, freeLeft;
	private double 		distDown, distUp, distRight, distLeft;

	// variables for navigating in the level
	private int 		locationX, locationY;
	private CoreRoom 	currentRoom;

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
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void setDirection (int input) {
		direction 	= input;
		figure.setDirection(input);
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
	NetClientLogic(Figure inFigure, CoreO_Game inGame, NetClientIn incoming, NetClientOut outgoing) {
		running = true;
		
		this.incoming = incoming;
		this.outgoing = outgoing;		
		
		figure = inFigure;
		
		freeRight 	= true;
		freeLeft 	= true;
		freeUp 		= true;
		freeDown 	= true;

		bulletEnable = true;
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

		ArrayList<CoreGameObjects> collidable = level.getRoom(locationX, locationY).getContent();
		CoreGameObjects collided;

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

				CoreGameObjects initBullet = new AttackBullet(figure.getBulletType(), figX, figY, figVX, figVY, signVX, signVY, figure.getPlayer());

				currentRoom.getContent().add(initBullet);
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
			
			incoming.getList(currentRoom.getContent());

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

			outgoing.sendList(currentRoom.getContent());
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
