package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Klasse der Türen zum Raum bzw Levelwechsel
 * @author Gruppe13
 *
 */

class MiscDoor extends CoreGameObjects {
	
	private long 	id;
	private static final long serialVersionUID = 1L;
	
	private double	x,y,r;
	private double 	height, width;
	private int 	destination;
	private int 	hp;
	
	/**
	 * Konstruktor, 
	 * @param initX			X-Position
	 * @param initY			Y-Position
	 * @param initWidth		Breite
	 * @param initHeight	Höhe
	 * @param initRadius	Radius (für Kollisionen)
	 * @param inDestination	'Richtung' der Tür (führt nach oben, unten, links, rechts)
	 * @param id			Eindeutige ID (Mulitplayer)
	 */
	
	MiscDoor (double initX, double initY, double initWidth, double initHeight, double initRadius, int inDestination, long id){
		x 			= initX;
		y 			= initY;
		r			= 2*Math.max(initWidth, initHeight);
		
		width 		= initWidth;
		height		= initHeight;
		
		destination	= inDestination; //Destination stores where the door leads to. 0=goes up 1=goes right 2= goes down 3= goes left 4= Goes to the next Level

		this.id = id;
		this.hp = 1;
	}
	
	@Override
	long getID() {
		return id;
	}
	
	int getDestination(){
		return destination;
	}

	
	@Override
	int getHP() {
		return hp;
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
		// TODO Auto-generated method stub

	}

	@Override
	void setSpeed(double inVX, double inVY) {
		// TODO Auto-generated method stub

	}

	@Override
	void setRad(double inR) {
		// TODO Auto-generated method stub
		r = inR;
	}

	@Override
	void setHP(int inHP) {
		hp = inHP;
	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
			g.setColor(Color.RED);
			if (destination == 4){g.setColor(Color.ORANGE);}//Türen die in den nächsten Level führen haben eine andere Farbe
			g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
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
	void takeDamage(int type, int strength) {
		// TODO: Check whether we want this for secret rooms
	}

}
