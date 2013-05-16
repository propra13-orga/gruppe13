package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

class Wall extends GameObjects {
	/*-----------------------------------------------------------------------------------------------*/
	// Hitpoints, position and collision radius
	private int 	hp;
	private double 	x, y, r;
	private double 	height, width;
	private double 	v_x, v_y;
	
	Wall(double initX, double initY, double initWidth, double initHeight, int inHP) {
		x = initX;
		y = initY;
		v_x = 0;
		v_y = 0;
		
		width = initWidth;
		height = initHeight;
		
		r = Math.max(width, height);
		hp = inHP;
	}
	
	// Getter and Setter methods for above variables
	int getHP(){
		return hp;
	}
	
	double getPosX() {
		return x;
	}
	
	double getPosY() {
		return y;
	}
	
	double getRad() {
		return r;
	}
	
	@Override
	double getVX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double getVY() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	double getWidth() {
		return width;
	}

	@Override
	double getHeight() {
		return height;
	}
	
	void setPos(double inX, double inY) {
		x = inX;
		y = inY;
	}
	
	void setSpeed(double inVX, double inVY) {
		v_x = inVX;
		v_y = inVY;
	}
	
	void setRad(double inR) {
		r = inR;
	}
	
	void setHP(int inHP) {
		hp = inHP;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.GREEN);
		g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
	}
	
	void attack() {
		
	}

	/*-----------------------------------------------------------------------------------------------*/
	@Override
	void incX() {		
	}

	@Override
	void decX() {		
	}

	@Override
	void incY() {		
	}

	@Override
	void decY() {		
	}
}
