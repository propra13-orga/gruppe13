package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JFrame;

/*
 * Wir brauchen:
 * Position
 * Hitpoints
 * Richtung
 * Radius
 * TODO implement picturecycle for movement
 */

class Figure extends GameObjects {
	/*-----------------------------------------------------------------------------------------------*/
	// Hitpoints, position, collision radius and constructor
	private int hp;
	private double x, y, r;
	private double v_x, v_y;
	JFrame window;
	
	// class constructor
	Figure(double initX, double initY, JFrame inFrame) {
		x = initX;
		y = initY;
		v_x = 0.7;
		v_y = 0.7;
		r = 1+v_x*v_x+v_y*v_y;
		hp = 1;
		window = inFrame;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
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
	
	double getVX() {
		return v_x;
	}
	
	double getVY() {
		return v_y;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
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
	// increasing or decreasing x and y variables
	@Override
	void incX() {
		x += v_x;
	}
	
	@Override
	void decX() {
		x -= v_x;
	}
	
	@Override
	void incY() {
		y += v_y;
	}
	
	@Override
	void decY() {
		y -= v_y;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		/* Durch die Variable step ist eine Umrechnung auf den Screen möglich, ansonsten würde das Spiel immer unterschiedlich angezeigt werden, 
		 * allerdings ist zu beachten, dass durch den Gebrauch von Math.round() eine Verzeichnung um einen Pixel unten oder rechts nicht Auftritt, 
		 * wie es bei einem cast auf int passieren kann.  */
		g.setColor(Color.BLUE);
		g.fillRect(xOffset+(int)Math.round(x*step),  yOffset+(int)Math.round(y*step), (int)Math.round(step), (int)Math.round(step));
		// TODO: bilder anstelle des quadrats malen

	}
	
	@Override
	void attack() {
		
	}
}
