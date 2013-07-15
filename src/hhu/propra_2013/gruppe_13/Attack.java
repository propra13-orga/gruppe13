package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstrakte Klasse für die Angriffe
 * @see AttackBullet
 * @see AttackMelee
 * @author Gruppe13
 * 
 */


abstract class Attack extends CoreGameObjects implements Serializable {

	private static final long serialVersionUID = 1L;

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
	
	// Attack specific propagation and finished status
	abstract boolean getFinished();
	abstract void propagate(ArrayList<CoreGameObjects> room, boolean server);
	abstract int getPlayer();
	abstract Attack copy();

	abstract void setTime();
}
