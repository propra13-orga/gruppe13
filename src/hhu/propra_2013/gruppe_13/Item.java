package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Item extends CoreGameObjects{
	
	//method for modifying the properties of other objects around, calls other methods
	abstract void modFigure(ArrayList<CoreGameObjects> collidable, Figure figure);
	
	//getter and setter methods for the properties of the items
	abstract double getPosX();
	abstract double getPosY();
	abstract double getRad();
	abstract void setPos(double inX, double inY);
	
	//draw method for the item and stuff
	abstract void draw(Graphics2D g, int xOffset, int yOffset, double step);
	abstract void attack();
	abstract double getWidth();
	abstract double getHeight();
}
