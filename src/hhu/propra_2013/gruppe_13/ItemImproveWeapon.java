package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Klasse für das DMG-UP Item 
 * @author Gruppe13
 *
 */


class ItemImproveWeapon extends Item{

	private long 	id;
	private static final long serialVersionUID = 1L;
	
	private double 	x, y;
	private double	r;
	private double 	height, width;
	
	private int		hp;
	
	/**
	 * Konstruktor
	 * @param initX			X-Position
	 * @param initY			Y-Position
	 * @param initWidth		Breite
	 * @param initHeight	Höhe
	 * @param inHP			hp
	 * @param id			Eindeutige ID (Multiplayer)
	 */
	
	
	ItemImproveWeapon(double initX, double initY, int initWidth, int initHeight, int inHP, long id) {
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
		
		hp = 1;
		
		this.id = id;
	}
	
	@Override
	long getID() {
		return id;
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
	void setPos(double inX, double inY) {
		x = inX;
		y = inY;
		
	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.orange);
		g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
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
	 * Ändert beim aufnehmen den BulletType des Spielers (Größe der Kugel, Schaden) sowie die Schussfrequenz
	 * @param room 			Raum, um das Item nach dem Aufsammeln vom Boden zu entfernen
	 * @param figure		Der Spieler, um seine Kugeln modifizieren zu können
	 * @see AttackBullet	Für mehr infos über den BulletType	
	 */
	
	@Override
	void modFigure(ArrayList<CoreGameObjects> room, Figure figure) {
		figure.setBulletType(AttackBullet.PLAYER_SPECIAL_BULLET_ONE);
		figure.setBulletCoolDownTime(figure.getBulletCoolDownTime()/2);
		hp = 0;
		room.remove(this);
	}

	@Override
	void takeDamage(int type, int strength) {
		// Keep empty since items should probably not be able to take damage
	}
}
