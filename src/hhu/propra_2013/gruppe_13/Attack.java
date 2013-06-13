package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;

abstract class Attack extends CoreGameObjects {
	// declare final variables in order to determine what bullet shall be fired 
	static final int PLAYER_BULLET_STD 			= 0;
	static final int PLAYER_SPECIAL_BULLET_ONE	= 1;
	static final int PLAYER_SPECIAL_BULLET_TWO	= 2;
	static final int ENEMY_BULLET_STD			= 10;
	
	// Getter and Setter methods for Objects used by Logic and GameDrawer classes
	abstract int 	getHP();	
	abstract double getPosX();
	abstract double getPosY();
	abstract double getRad();
	abstract double getVX();
	abstract double getVY();
	
	abstract void setPos(double inX, double inY);
	abstract void setSpeed(double inVX, double inVY);
	abstract void setRad(double inR);
	abstract void setHP(int inHP);
	
	// Draw and Attack methods for all inheriting classes
	abstract void draw(Graphics2D g, int xOffset, int yOffset, double step);
	abstract void attack();
	abstract double getWidth();
	abstract double getHeight();
}
