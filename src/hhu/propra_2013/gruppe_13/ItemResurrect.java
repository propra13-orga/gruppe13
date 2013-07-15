package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * Klasse für das Item mit dem der Spieler respawnen kann
 * @author Gruppe13
 *
 */


class ItemResurrect extends Item{

	private long 	id;
	private static final long serialVersionUID = 1L;
	
	private double 	x, y;
	private double	r;
	private double 	height, width;
	private int 	prize;
	private final 	String jesus;
//	private Figure 	figure;
	
	private int 	hp;
	
	/**
	 * Konstruktor
	 * @param initX			X-Position
	 * @param initY			Y-Position
	 * @param initWidth		Breite
	 * @param initHeight	Höhe
	 * @param id			Eindeutige ID (Multiplayer)
	 */
	
	
	ItemResurrect(double initX, double initY, int initWidth, int initHeight, long id) {
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
		prize 	= 5;
//		figure 	= inFigure;
		jesus = "jesus.jpeg";
		
		hp = 1;
		
		this.id = id;
	}
	
	@Override
	long getID() {
		return id;
	}
	
	int getPrize(){
		return prize;
	}

	/**
	 * Fügt dem Spieler das Item hinzu
	 * Genutzt wird das Item über checkFigure in der Logik
	 * @param room 		Um das Item vom boden zu entfernen falls es aufgehoben wurde
	 * @param figure	Um der Figur das Item zu übergeben
	 */
	
	
	
	@Override
	void modFigure(ArrayList<CoreGameObjects> room, Figure figure) {
		int money = figure.getGeld();
		if (money >= 5){
			money = money - 5;
			figure.setGeld(money);
			figure.pickUpItem(this);
			
			hp = 0;
			
			room.remove(this);
		}		
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
		Image imageOfJesus = Toolkit.getDefaultToolkit().getImage(jesus);
		g.drawImage(imageOfJesus, xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height), null);
//		g.setColor(Color.gray);
//		g.fillOval(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
		Font font = new Font("Arial", Font.PLAIN, (int)step/2);
		g.setFont(font);
//		if(figure.getGeld() >= prize){
			g.setColor(Color.yellow);
//		}
//		if(figure.getGeld() < prize){
//			g.setColor(Color.red);
//		}
		g.drawString(prize + "#", xOffset+(int)Math.round((x+width/2.)*step), yOffset+(int)((y-height/2.)*step) );
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
		// Keep empty since items shouldn't take damage
	}

}
