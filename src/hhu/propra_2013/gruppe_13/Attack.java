package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

import com.sun.org.apache.bcel.internal.generic.IXOR;

class Attack extends GameObjects {
	// position and velocity data
	private double posX;
	private double posY;
	private double v_x;
	private double v_y;
	
	// collision radius, hitpoints
	private double rad;
	private int 	hp;
	
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
		g.setColor(Color.ORANGE);
	}

	@Override
	void attack() {

	}
}
