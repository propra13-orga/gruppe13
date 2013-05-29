package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

class Bullet extends GameObjects {
	
	// declare final variables in order to determine what bullet shall be fired 
	static final int PLAYER_BULLET_STD 	= 0;
	static final int PLAYER_BULLET_ONE	= 1;
	static final int PLAYER_BULLET_TWO	= 2;
	static final int ENEMY_BULLET_STD	= 10;
	
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
		// draw a standard bullet
		if (this.type == PLAYER_BULLET_STD) {
			g.setColor(Color.ORANGE);
			g.fillOval(xOffset+(int)Math.round((posX-width/2.)*step),  yOffset+(int)Math.round((posY-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
		}
	}

	@Override
	void attack() {

	}
}
