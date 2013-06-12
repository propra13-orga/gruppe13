package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

class Bullet extends Attack {

	// declare final variables in order to determine what bullet shall be fired 
	static final int PLAYER_BULLET_STD 			= 0;
	static final int PLAYER_SPECIAL_BULLET_ONE	= 1;
	static final int PLAYER_SPECIAL_BULLET_TWO	= 2;
	static final int ENEMY_BULLET_STD			= 10;
	
	// position and velocity data
	private double 	posX;
	private double 	posY;
	private double 	v_x;
	private double 	v_y;
	
	// collision radius, hitpoints
	private double 	rad;
	private int 	hp;
	private int 	type;
	
	// width and height
	private double width;
	private double height;
	
	// Bullet manipulation sets
	private boolean destroyed;
	private boolean hit;
	
	// Bullet constructor
	Bullet(int inType, double initX, double initY, double figVX, double figVY, int signVX, int signVY) {
		posX	= initX;
		posY	= initY;
		
		type	= inType;
		
		// Bullet type and hitpoints, this way there is a possibility of building Bullets that can hit multiple enemies.  
		switch (type) {
		// Standard player Bullet
		case PLAYER_BULLET_STD:
			width 	= 0.2;
			height 	= 0.2;
			
			v_x	= signVX*0.5 + figVX;
			v_y = signVY*0.5 + figVY;
			
			hp = 1;
			break;
			
		case PLAYER_SPECIAL_BULLET_ONE:
			break;
			
		case PLAYER_SPECIAL_BULLET_TWO:
			break;
			
		case ENEMY_BULLET_STD: 
			break;
			
		}
		
		rad	= Math.max(width, height) + Math.sqrt(v_x*v_x+v_y*v_y);
	}
	
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

	// Drawing method
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		switch (type) {
		// draw a standard bullet
		case PLAYER_BULLET_STD: //TODO: Change animation when the bullet hits something. 
			g.setColor(Color.ORANGE);
			g.fillOval(xOffset+(int)Math.round((posX-width/2.)*step),  yOffset+(int)Math.round((posY-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
			break;
		}
	}

	@Override
	void attack() {
		v_x = 0;
		v_y = 0;
		
		hit = true;
		destroyed = true;
		//TODO: Change Animation to an exploding bullet or some such
	}

	boolean getFinished() {
		// TODO Auto-generated method stub
		return destroyed;
	}
}
