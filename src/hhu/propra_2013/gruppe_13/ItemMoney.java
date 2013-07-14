package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Das Geld Item
 * @author Gruppe13
 *
 */


class ItemMoney extends Item {

	private long 	id;
	private static final long serialVersionUID = 1L;
	
	private double 	height, width;
	private double 	x, y;
	private double	r;
	
	private int 	hp;
	
	ItemMoney(double initX, double initY, int initHeight, int initWidth, long id){
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
		
		this.id = id;
		
		hp = 1;
	}
	
	@Override
	long getID() {
		return id;
	}

	@Override

	/**
	 * Wird von der Logik aufgerufen wenn die Figur mit einem Item dieses Typs kollidiert
	 * In diesem Fall wird das gesammelte Geld der Figur um eins erhöht
	 * @param	room	Liste der Objekte im Raum, um das Geld nach dem aufnehmen vom Boden zu entfernen
	 * @param figure	Die Figur, um das gesammelte Geld entsprechend zu lagern 
	 */
	
	
	void modFigure(ArrayList<CoreGameObjects> room, Figure figure) {
		int geld;
		geld = figure.getGeld();			
		geld++;
		figure.setGeld(geld);
		hp = 0;
		room.remove(this);
	}

	@Override
	double getPosX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	double getPosY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	double getRad() {
		// TODO Auto-generated method stub
		return r;
	}

	@Override
	void setPos(double inX, double inY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.yellow);
		g.fillOval(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
	}

	@Override
	double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	int getHP() {
		return hp;
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
	void setSpeed(double inVX, double inVY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setRad(double inR) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setHP(int inHP) {
		hp = inHP;
	}

	@Override
	void takeDamage(int type, int strength) {
		// TODO Auto-generated method stub
		
	}

}
