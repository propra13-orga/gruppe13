package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

class Enemy extends GameObjects {
	/*-----------------------------------------------------------------------------------------------*/
	// Hitpoints, position and collision radius
	private int 	hp;
	private double 	x, y;
	private double	r;
	private double 	v_x, v_y;
	private double 	height, width;
	private JFrame 	window;
	
	Enemy(double initX, double initY, double initHeight, double initWidth, double initRadius, JFrame inFrame) {
		//zum kurzen anzeigen mal was ;)
		//initX = window.getWidth()/2;
		//initY = window.getHeight()/2;
		x = initX;
		y = initY;
		v_x = 0;
		v_y = 0;
		
		width = initWidth;
		height = initHeight;
		r = initRadius;
		hp = 1;
		window = inFrame;
	}
	
	// Getter and Setter methods for above variables
	@Override
	int getHP(){
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
		return r;
	}
	
	@Override
	double getWidth() {
		return width;
	}

	@Override
	double getHeight() {
		return height;
	}
	
	@Override
	void setPos(double inX, double inY) {
		x = inX;
		y = inY;
	}
	
	@Override
	void setSpeed(double inVX, double inVY) {
		v_x = inVX;
		v_y = inVY;
	}
	
	@Override
	void setRad(double inR) {
		r = inR;
	}
	
	@Override
	void setHP(int inHP) {
		hp = inHP;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double  step) {
		g.setColor(Color.red);
		g.fillOval(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
	}
	
	@Override
	void attack() {
		
	}

	/*-----------------------------------------------------------------------------------------------*/
	@Override
	void incX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void decX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void incY() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void decY() {
		// TODO Auto-generated method stub
		
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
	
}
