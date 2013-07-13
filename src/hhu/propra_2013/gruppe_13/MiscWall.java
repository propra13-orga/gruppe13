package hhu.propra_2013.gruppe_13;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

class MiscWall extends CoreGameObjects {
	
	private long 	id;
	private static final long serialVersionUID = 1L;
	/*-----------------------------------------------------------------------------------------------*/
	// Hitpoints, position and collision radius
	private int 	hp;
	private double 	x, y, r;
	private double 	height, width;
	private double 	v_x, v_y;
	private final 	String wall;
	
	MiscWall(double initX, double initY, double initWidth, double initHeight, int inHP, long id) {
		
		this.id = id;
		
		x = initX;
		y = initY;
		v_x = 0;
		v_y = 0;
		
		width = initWidth;
		height = initHeight;
		
		r = Math.max(width, height);
		hp = inHP;
		
		wall = "wall66x66.png";
	}
	
	// Getter and Setter methods for above variables
	@Override
	long getID() {
		return id;
	}
	
	int getHP(){
		return hp;
	}
	
	double getPosX() {
		return x;
	}
	
	double getPosY() {
		return y;
	}
	
	double getRad() {
		return r;
	}
	
	@Override
	double getVX() {
		return v_x;
	}

	@Override
	double getVY() {
		return v_y;
	}
	
	@Override
	double getWidth() {
		return width;
	}

	@Override
	double getHeight() {
		return height;
	}
	
	void setPos(double inX, double inY) {
		x = inX;
		y = inY;
	}
	
	void setSpeed(double inVX, double inVY) { 
		v_x = inVX;
		v_y = inVY;
	}
	
	void setRad(double inR) {
		r = inR;
	}
	
	void setHP(int inHP) {
		hp = inHP;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
	
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		Image imageOfWall = Toolkit.getDefaultToolkit().getImage(wall);
		
		//g.setColor(Color.green);
		g.drawImage(imageOfWall, xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height), null);
//		g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
	}
	
	void attack() {
		// Keep empty, Walls don't attack
	}

	@Override
	void takeDamage(int type, int strength) {
		switch (type) {
		case Attack.PLAYER_MELEE_AOE:
			hp = 0;
			break;
		}
	}

//	@Override
//	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
//		return false;
//	}
}
