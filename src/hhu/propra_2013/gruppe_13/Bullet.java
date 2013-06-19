package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

class Bullet extends Attack {
	
	// position and velocity data
	private double 	posX;
	private double 	posY;
	private double 	v_x;
	private double 	v_y;
	
	// collision radius, hitpoints
	private double 	rad;
	private int 	hp;
	private int 	type;
	private int		strength;
	
	// width and height
	private double 	width;
	private double 	height;
	
	// Bullet manipulation sets
	private boolean destroyed;
	private boolean hit;
	private int 	hitCounter;
	private boolean up, down, left, right;
	
	// Needed for collisions
	double[] 			dist = new double[4];
	CoreGameObjects[] 	coll = new CoreGameObjects[4];
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Bullet constructor
	Bullet(int inType, double initX, double initY, double objVX, double objVY, double signVX, double signVY) {
		// Save initial position and type data
		posX	= initX;
		posY	= initY;
		type	= inType;
		
		// save in which direction the bullet is flying, it is not anticipated, that we will have direction changing projectiles		
		if (signVX > 0) 		right	= true;
		else if (signVX < 0)	left 	= true;
		
		if (signVY > 0)			down	= true;
		else if (signVY < 0)	up		= true;
		
		// check whether the bullet is moving in one or two directions and adjust speed accordingly
		int counter = 0;
		if (right)	counter++;
		if (left)	counter++;
		if (up)		counter++;
		if (down)	counter++;
		
		if (counter == 2) {
			signVX /= Math.sqrt(2.);
			signVY /= Math.sqrt(2.);
		}
		
		// Bullet type and hitpoints, this way there is a possibility of building Bullets that can hit multiple enemies.  
		switch (type) {
		default:
			v_x	= signVX*0.5;
			v_y = signVY*0.5;
			break;
		}
		switch (type) {
			
		case PLAYER_SPECIAL_BULLET_ONE:
			width 		= 0.3;
			height 		= 0.3;
			
			hp 			= 20;
			hitCounter 	= 5;
			strength	= 2;
			break;
			
		case PLAYER_SPECIAL_BULLET_TWO:
			break;
		
		default:
			width 		= 0.2;
			height 		= 0.2;
			
			hp 			= 20;
			hitCounter 	= 5;
			strength	= 1;
			break;
		}
		
		rad	= Math.max(width, height) + Math.pow(Math.ceil(Math.abs(v_x)), 2)*Math.pow(Math.ceil(Math.abs(v_y)), 2);
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Various getter methods
	@Override
	int getHP() {
		return hp;
	}

	@Override
	double getPosX() {
		return posX;
	}

	@Override
	double getPosY() {
		return posY;
	}

	@Override
	double getRad() {
		return rad;
	}

	@Override
	double getVX() {
		return v_x;
	}

	@Override
	double getVY() {
		return v_y;
	}

	@Override
	double getWidth() {
		return width;
	}

	@Override
	double getHeight() {
		return height;
	}
	
	// check whether the bullet has finished disintegrating and can be deleted from the program
	boolean getFinished() {
		return destroyed;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Various setter methods
	@Override
	void setPos(double inX, double inY) {
		posX = inX;
		posY = inY;
	}

	@Override
	void setSpeed(double inVX, double inVY) {
		v_x = inVX;
		v_y = inVY;
	}

	@Override
	void setRad(double inR) {
		rad = inR;
	}

	@Override
	void setHP(int inHP) {
		hp = inHP;
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Drawing method
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		switch (type) {
		
		case Bullet.ENEMY_BULLET_STD:
			// determine whether the bullet is still traveling and active, or whether it has hit something
			if (!hit) {
				g.setColor(new Color(0, 255, 255));
				g.fillOval(xOffset+(int)Math.round((posX-width/2.)*step),  yOffset+(int)Math.round((posY-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
			} else {
				g.setColor(Color.BLACK);
				g.fillOval(xOffset+(int)Math.round((posX-width/2.)*step),  yOffset+(int)Math.round((posY-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
				
				// decrease the hit counter and destroy the object when the counter reaches zero. 
				hitCounter--;
				if (hitCounter == 0) destroyed = true;
			}
			break;
			
		// draw a standard bullet
		default: //TODO: Change animation when the bullet hits something, animate real bullets, not just red dots...
			// determine whether the bullet is still traveling and active, or whether it has hit something
			if (!hit) {
				g.setColor(Color.ORANGE);
				g.fillOval(xOffset+(int)Math.round((posX-width/2.)*step),  yOffset+(int)Math.round((posY-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
			} else {
				g.setColor(Color.RED);
				g.fillOval(xOffset+(int)Math.round((posX-width/2.)*step),  yOffset+(int)Math.round((posY-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
				
				// decrease the hit counter and destroy the object when the counter reaches zero. 
				hitCounter--;
				if (hitCounter == 0) destroyed = true;
			}
			break;
		}
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	void attack() {
		v_x = 0;
		v_y = 0;
		
		hit = true;
		//TODO: Change Animation to an exploding bullet or some such
	}

	/*-----------------------------------------------------------------------------------------------------------------------*/
	// Remember: double[] dist = {distUp, distDown, distRight, distLeft};
	private void checkCollision (CoreGameObjects collidable) {
		double objX = collidable.getPosX();
		double objY = collidable.getPosY();
		double objR = collidable.getRad();
		
		// check whether the object in question is close enough
		if ((posX-objX)*(posX-objX)+(posY-objY)*(posY-objY) < (objR+rad)*(objR+rad)) {
			double tmpX = posX-objX;
			double tmpY = posY-objY;
			
			// save the objects width and heigth
			double objWidth  = collidable.getWidth();
			double objHeight = collidable.getHeight();
			
			// check in which direction the bullet is moving and act accordingly should a collision occur
			if (up && tmpY > 0 && Math.abs(tmpX)<(width+objWidth)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if (tmpY-objHeight/2. < dist[0]) {
					dist[0] 	= tmpY-objHeight/2.;
					coll[0] 	= collidable;
				}
			}
			
			// analogous to above
			if (down && tmpY < 0 && Math.abs(tmpX)<(width+objWidth)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if (tmpY+objHeight/2. > dist[1]) {
					dist[1] 	= tmpY+objHeight/2.;
					coll[1] 	= collidable;
				}
			}
			
			// analogous to above
			if (right && tmpX < 0 && Math.abs(tmpY)<(height + objHeight)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if (tmpX+objWidth/2. > dist[2]) {
					dist[2] 	= tmpX+objWidth/2.;
					coll[2] 	= collidable;
				}
			}
			
			// analogous to above
			if (left && tmpX > 0 && Math.abs(tmpY)<(height + objHeight)/2.) {
				// Check whether there is a closer collision, save distance and object if there is
				if (tmpX-objWidth/2. < dist[3]) {
					dist[3] 	= tmpX-objWidth/2.;
					coll[3] 	= collidable;
				}
			}
		}
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// do the actual movement, should an object be encountered, kill it and destroy the bullet...
	private void move() {
		if (up) {
			if (dist[0]*dist[0] < v_y*v_y) {
				posY -= dist[0];
				coll[0].takeDamage(type, strength);
				attack();
			} else
				posY += v_y;
		}
		
		if (down) {
			if (dist[1]*dist[1] < v_y*v_y) {
				posY -= dist[1];
				coll[1].takeDamage(type, strength);
				attack();
			} else
				posY += v_y;
		}
		
		if (right) {
			if (dist[2]*dist[2] < v_x*v_x) {
				posX -= dist[2];
				coll[2].takeDamage(type, strength);
				attack();
			} else
				posX += v_x;
		}
		
		if (left) {
			if (dist[3]*dist[3] < v_x*v_x) {
				posX -= dist[3];
				coll[3].takeDamage(type, strength);
				attack();
			} else
				posX += v_x;
		}
		
		// Check that the boundaries are not broken
		if (posX <= 0) {
			posX = 0;
			attack();
		}
		
		else if (posX >= 22) {
			posX = 22;
			attack();
		}
		
		if (posY <= 0) {
			posY = 0;
			attack();
		}
		else if (posY >= 13) {
			posY = 13;
			attack();
		}

		// decrease bullet hp, destroy the bullet when hp reaches zero. This effectively determines the bullets range
		hp--;
		if (hp == 0) attack();
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	// check collisions with other objects and propagate the bullet accordingly
	void propagate(ArrayList<CoreGameObjects> room) {
		
		// objects for checking which object to kill, reset basic variables for this iteration
		CoreGameObjects collidable;
		
		coll[0] = null;
		coll[1] = null;
		coll[2] = null;
		coll[3] = null;
		
		dist[0] =  30;
		dist[1] = -30;
		dist[2] = -30;
		dist[3] =  30;
		
		// iterate over all objects within the room, ignore other bullets and itself of course
		for(int i=0; i<room.size(); i++) {
			collidable = room.get(i);
			
			switch (type) {
			case ENEMY_BULLET_STD:
				if (collidable instanceof Enemy || collidable instanceof MISCWall || collidable instanceof Figure) {
					checkCollision(collidable);
				}
				break;
				
			default:
				if (collidable instanceof Enemy || collidable instanceof MISCWall) {
					checkCollision(collidable);
				}
				break;
			}

		}
		move();
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	void takeDamage(int type, int strength) {
		// Keep empty, since a bullet cannot take damage, but only deal damage. 
	}
}
