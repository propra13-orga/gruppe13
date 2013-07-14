package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Klasse für die "Munition" des AoE Angriffs
 * @author Gruppe13
 *
 */


class ItemChocolateBar extends Item{

	private long 	id;
	private static final long serialVersionUID = 1L;
	
	private double 	x, y;
	private double	r;
	private double 	height, width;
	
	private int 	hp;
	
	/**
	 * Konstruktor
	 * @param initX			X-Position im Raum
	 * @param initY			Y-Position im Raum
	 * @param initWidth		Breite des Items
	 * @param initHeight	Höhe des Items
	 * @param inHP			'HP' des Items
	 * @param id			Eindeutige ID (Multiplayer)
	 */
	
	
	ItemChocolateBar(double initX, double initY, int initWidth, int initHeight, int inHP, long id) {
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
		x = inX;
		y = inY;
		
	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.WHITE);
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
		return 0;
	}

	@Override
	double getVY() {
		return 0;
	}

	@Override
	void setSpeed(double inVX, double inVY) {
		
	}

	@Override
	void setRad(double inR) {
		r = inR;
		
	}

	@Override
	void setHP(int inHP) {
		hp = inHP;
	}

	
	/**
	 * Änderung der Figur nach aufsammeln des Items
	 * Der Figur wird ein Rüstungspunkt hinzugefügt
	 * @param	room	Liste der Objekte im Raum, um das Item nach dem Aufheben aus dem Raum zu entfernen
	 * @param	figure	Die Figur, um ihr den neuen Rüstungspunkt übergeben zu können
	 */
	
	@Override
	void modFigure(ArrayList<CoreGameObjects> room, Figure figure) {
		int chocolate;
		chocolate = figure.getChocolate();				
		chocolate++;
		figure.setChocolate(chocolate);
		hp = 0;
		room.remove(this);
	}

	@Override
	void takeDamage(int type, int strength) {
		// Keep empty since items should probably not be able to take damage
	}

	

}

