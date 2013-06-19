package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Enemy extends CoreGameObjects{
	
	// Getter and Setter methods for Objects used by Logic and GameDrawer classes
	abstract int	getType();
	abstract int 	getHP();	
	abstract double getPosX();
	abstract double getPosY();
	abstract double getRad();
	abstract double getVX();
	abstract double getVY();
	
	abstract void setType(int inType);
	abstract void setPos(double inX, double inY);
	abstract void setSpeed(double inVX, double inVY);
	abstract void setRad(double inR);
	abstract void setHP(int inHP);
		
	// Draw and Attack methods for all inheriting classes
	abstract void draw(Graphics2D g, int xOffset, int yOffset, double step);
	abstract void attack(Figure figure);
	abstract double getWidth();
	abstract double getHeight();
	 
	// Methods for manipulating enemy objects
	abstract void artificialIntelligence(Figure inFigure, ArrayList<CoreGameObjects> currentRoom);
	abstract boolean leftForDead();
	abstract boolean stopDrawing();
}
