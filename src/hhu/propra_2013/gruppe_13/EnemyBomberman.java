package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Weiterer Gegnertyp - ungenutzt
 * @author Gruppe
 *
 */

class EnemyBomberman extends Enemy{
	
	private static final long serialVersionUID = 1L;
	private long 	id;
	// type, maximum hp and hp and a stationary variable for initial waiting as the player enters the room
	private int 	type, maxHp, hp;
	private int 	stationary;
	
	// position and velocity data, width, height and collision radius
	private double 	x, y, vx, vy, v_weight;
	private double 	width, height, rad;
	
	// strength of the enemy and booleans for checking vital signs
	private int 	strength;
	private boolean	dead, dying, stopDrawing;
	
	// the current stage of the game and a long for the time stamp
	private int 	stage;
	private long 	regenerate;
	
	// timers for the server
	private long 	timer;
	private long	timeout;
	
	// Standard constructor, build enemies according to input 
	EnemyBomberman (double inx, double iny,double inWidth, double inHeight, int inType, int inStage, long id){
		x 			= inx;
		y 			= iny;
		
		width 		= inWidth;
		height 		= inHeight;
		
		type 		= inType;
		stage 		= inStage;
		
		// initialize the program according to the type of the desired enemy
		switch (type) {
		case ENEMY_FIRE:
			regenerate	= System.currentTimeMillis();
			strength 	= 1;
			hp			= 5;
			maxHp		= hp;
			break;
			
		case ENEMY_FIGURE_RUN:
			strength 	= 1;
			hp			= 5;
			maxHp		= hp;
			v_weight	= 0.1;
			break;
			
		case ENEMY_FIGURE_FLYING:
			strength	= 1;
			hp			= 3;
			maxHp		= hp;
			v_weight	= 0.07;
			break;
		}
		
		rad = Math.max(width, height) + Math.pow(Math.ceil(Math.abs(v_weight)),2);
		timeout = 16;
		timer = System.currentTimeMillis();
		
		this.id = id;
	}
		
	
	
/*getter and setter methods for the melee enemy--------------------------------------------------------------------------------------------------------*/
	@Override
	long getID() {
		return id;
	}
	
	@Override
	int getType() {
		return type;
	}

	@Override
	int getHP() {
		return hp;
	}

	@Override
	double getPosX() {
		return x;
	}

	@Override
	double getPosY() {
		return y;
	}

	@Override
	double getRad() {
		return rad;
	}

	@Override
	double getVX() {
		return vx;
	}

	@Override
	double getVY() {
		return vy;
	}
	
	@Override
	double getWidth() {
		return width;
	}

	@Override
	double getHeight() {
		return height;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setType(int inType) {
		type = inType;
	}

	@Override
	void setPos(double inX, double inY) {
		x = inX;
		y = inY;		
	}

	@Override
	void setSpeed(double inVX, double inVY) {
		vx = inVX;
		vy = inVY;
	}

	@Override
	void setRad(double inR) {
		rad = inR;
	}

	@Override
	void setHP(int inHP) {
		hp = inHP;		
	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		
		switch (type) {
		case ENEMY_FIGURE_RUN:
			if (!dying) {
				g.setColor(Color.RED);
				g.fillOval(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
			} 
			else {
				// TODO: do cool shit whilst the thing is dying
				dead = true;
				stopDrawing = true;
			}
			break;
			
			
		case ENEMY_FIRE:
			// the fire will diminish according to the amount of hp left, if it is dead, only an empty fireplace will remain.  
			double fireWidth 	= width/maxHp*hp;
			double fireHeight	= height/maxHp*hp;
			
			g.setColor(Color.GRAY);
			g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
			
			g.setColor(Color.PINK);
			g.fillOval(xOffset+(int)Math.round((x-fireWidth/2.)*step),  yOffset+(int)Math.round((y-fireHeight/2.)*step), (int)Math.round(step*fireWidth), (int)Math.round(step*fireHeight));
			
			break;
		}
	}

	@Override
	void attack(Figure figure) {
		switch (type) {
		case ENEMY_FIRE:
			if (hp > 0) 
				figure.takeDamage(type, strength);
			break;
		
		default:
			figure.takeDamage(type, strength);
			break;
		}
	}

	@Override
	void takeDamage(int attackType, int inStrength) {
		// Save when the last attack from the player occurred
		regenerate 	= System.currentTimeMillis();
		switch (attackType){
		default:
			hp -= inStrength;
			break;
		}
		
		// check whether the fucking thing is dead yet, initiate dying sequence if that is the case
		if (hp <= 0) {
			dying = true;
		}
		System.out.println("enemy hp: "+hp);
	}
	
	boolean stopDrawing () {
		return stopDrawing;
	}
	
	boolean leftForDead () {
		return dead;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	void artificialIntelligence(Figure figure, ArrayList<CoreGameObjects> currentRoom, boolean server){
		
		if (server && (System.currentTimeMillis()-timer)<timeout)
			return;
		else if (server)
			timer = System.currentTimeMillis();
		
		switch(type){
		
			//this will be the fire type
			case ENEMY_FIRE: 
				// See whether the last attack by the player is longer ago than 2 seconds, if so regenerate the fire
				if (System.currentTimeMillis() - regenerate > 2000) {
					if (hp > 0 && hp < maxHp) {
						hp++;
						regenerate = System.currentTimeMillis();
					}
				}
				
				dead = dying;
				break;
		/*-------------------------------------------------------------------------------------*/
			case ENEMY_PATROL: //this will be the patrol type
				//
				break;
		/*-------------------------------------------------------------------------------------*/
			case ENEMY_RANDOM_WALKER: //this will be the random walk type
				//
				break;
		/*-------------------------------------------------------------------------------------*/

			case ENEMY_FIGURE_RUN: //this one will run towards the figure
				propagateToFigure(currentRoom, figure);
				break;

		/*-------------------------------------------------------------------------------------*/
			case ENEMY_FLEEING: //this runs away from the figure
				//
				break;
		/*-------------------------------------------------------------------------------------*/
			case ENEMY_FIGURE_FLYING:
				propagateToFigure(currentRoom, figure);
				break;
		}
		stationary++;
	}

	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	double[] collision(CoreGameObjects collidable, double distUp, double distDown, double distRight, double distLeft) {
		double objX, objY, objR;
		double tmpX, tmpY;
		double objWidth, objHeight;
		
		objX = collidable.getPosX();
		objY = collidable.getPosY();
		objR = collidable.getRad();
		
		// Check collision radius, save distance between objects
		if ((x-objX)*(x-objX)+(y-objY)*(y-objY) < (objR+rad)*(objR+rad)) {
			tmpX = x-objX;
			tmpY = y-objY;
			
			objHeight 	= collidable.getHeight();
			objWidth 	= collidable.getWidth();
			
			// check in which direction the bullet is moving and act accordingly should a collision occur
			if (vy < 0 && tmpY > 0 && Math.abs(tmpX)<(width+objWidth)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if ((tmpY-(height+objHeight)/2.) < distUp) {
					distUp = tmpY-(height+objHeight)/2.;
				}
			}
			
			// analogous to above
			if (vy > 0 && tmpY < 0 && Math.abs(tmpX)<(width+objWidth)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if ((tmpY+(height+objHeight)/2.) > distDown) {
					distDown = tmpY+(height+objHeight)/2.;
				}
			}
			
			// analogous to above
			if (vx > 0 && tmpX < 0 && Math.abs(tmpY)<(height + objHeight)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if ((tmpX+(width+objWidth)/2.) > distRight) {
					distRight = tmpX+(width+objWidth)/2.;
				}
			}
			
			// analogous to above
			if (vx < 0 && tmpX > 0 && Math.abs(tmpY)<(height + objHeight)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if ((tmpX-(width+objWidth)/2.) < distLeft) {
					distLeft = tmpX-(width+objWidth)/2.;
				}
			}
		}
		
		double[] toReturn = {distUp, distDown, distLeft, distRight};
		return toReturn;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	void move (double distUp, double distDown, double distRight, double distLeft) {
		/* check whether there is a collision and move the enemy along the wall at normal speed, 
		 * this is to ensure that the enemy does not creep along walls while the player can pick him off from a distance. */
		if (distUp == 0 || distDown == 0) {
			vx = Math.signum(vx) * v_weight;
		}
		
		if (distRight == 0 || distLeft == 0) {
			vy = Math.signum(vy) * v_weight;
		}
		
		// do the actual movement, should an object be encountered, kill it and destroy the bullet...
		if (vy < 0) {
			if (distUp*distUp < vy*vy) {
				y -= distUp;
			} else
				y += vy;
		}
		
		if (vy > 0) {
			if (distDown*distDown < vy*vy) {
				y -= distDown;
			} else
				y += vy;
		}
		
		if (vx > 0) {
			if (distRight*distRight < vx*vx) {
				x -= distRight;
			} else
				x += vx;
		}
		
		if (vx < 0) {
			if (distLeft*distLeft < vx*vx) {
				x -= distLeft;
			} else
				x += vx;
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	void propagateToFigure (ArrayList<CoreGameObjects> room, Figure figure) {
		
		// check whether the enemy is still alive move toward figure if this is the case
		if (!dying) {
			double figX = figure.getPosX();
			double figY = figure.getPosY();
			
			// parameterized velocity vector towards the figure
			vx = v_weight*(figX-x)/Math.sqrt(figX*figX-2*figX*x+x*x+figY*figY-2*figY*y+y*y);
			vy = v_weight*(figY-y)/Math.sqrt(figX*figX-2*figX*x+x*x+figY*figY-2*figY*y+y*y);
			
			// while the 
			if (stationary < 50) {
				vx = 0;
				vy = 0;
			}
		}
		
		// Basic variables for checking each object within the room
		CoreGameObjects collidable;
		
		double distUp		=  30;
		double distDown		= -30;
		double distLeft 	=  30;
		double distRight 	= -30;
		
		// iterate over all elements within the room and check whether a collision occurs
		for (int i=0; i<room.size(); i++) {
			collidable = room.get(i);
			
			switch (type) {
			case ENEMY_FIGURE_RUN:
				// Check whether an object can be collided with, get data if necessary
				if (collidable instanceof Figure || collidable instanceof MiscWall) {
					double[] check = collision(collidable, distUp, distDown, distRight, distLeft);
					
					distUp 		= Math.min(distUp, check[0]);
					distDown 	= Math.max(distDown, check[1]);
					distLeft 	= Math.min(distLeft, check[2]);
					distRight 	= Math.max(distRight, check[3]);
				}
				break;
				
			case ENEMY_FIGURE_FLYING:
				// Check whether an object can be collided with, get data if necessary
				if (collidable instanceof Figure) {
					double[] check = collision(collidable, distUp, distDown, distRight, distLeft);
					
					distUp 		= Math.min(distUp, check[0]);
					distDown 	= Math.max(distDown, check[1]);
					distLeft 	= Math.min(distLeft, check[2]);
					distRight 	= Math.max(distRight, check[3]);
				}
				break;
			}

		}
		
		move(distUp, distDown, distRight, distLeft);
	}
}
