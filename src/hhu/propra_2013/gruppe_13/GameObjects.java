package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;

abstract class GameObjects {
	// Getter and Setter methods for Objects used by Logic and GameDrawer classes
	abstract int getHP();	
	abstract double getPosX();
	abstract double getPosY();
	abstract double getRad();
	abstract double getVX();
	abstract double getVY();
	
	abstract void setPos(double inX, double inY);
	abstract void setSpeed(double inVX, double inVY);
	abstract void setRad(double inR);
	abstract void setHP(int inHP);
	
	// methods to move the object
	abstract void incX();
	abstract void decX();
	abstract void incY();
	abstract void decY();
	
	// Draw and Attack methods for all inheriting classes
	abstract void draw(Graphics2D g);
	abstract void attack();
}
