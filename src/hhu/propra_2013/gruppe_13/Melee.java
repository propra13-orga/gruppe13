package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Melee extends Attack {

	private static final long serialVersionUID = -5015189883215185491L;

	private int 	player;
	
	private int 	hp, type, strength;
	private double 	x, y, vx, vy, rad;
	private double	width, height;
	
	private int 	counter_one;
	private int 	radMax;
	private int 	fadeOut;
	
	Figure 						figure;
	ArrayList<CoreGameObjects>	room;
	
	private boolean destroyed;
	
	/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	Melee (double inX, double inY, double inVX, double inVY, int inType, Figure inFigure, ArrayList<CoreGameObjects> InRoom, int player) {
		x	= inX;
		y	= inY;
		
		this.type 	= inType;
		this.figure = inFigure;
		this.room	= InRoom;
		this.player = player;
		
		switch (type) {
		case PLAYER_MELEE_AOE:
			strength = 5;
			
			width 	= figure.getWidth();
			height	= figure.getHeight();
			rad		= 5*Math.max(width, height);
			
			radMax 	= 5;
			fadeOut = 14;
			attack();
			break;
		
		case PLAYER_MELEE_SHORT:
			strength = 3;
			
			width 	= figure.getWidth();
			height	= figure.getHeight();
			rad		= 3*Math.max(width, height);
			
			radMax 	= 3;
			fadeOut = 10;
			attack();
			break;
		}
}
	
	/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	int getHP() {
		return hp;
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
		return rad;
	}

	@Override
	double getVX() {
		return vx;
	}

	@Override
	double getVY() {
		return vy;
	}
	
	@Override
	double getWidth() {
		return width;
	}

	@Override
	double getHeight() {
		return height;
	}
	
	// check whether the bullet has finished disintegrating and can be deleted from the program
	@Override
	boolean getFinished() {
		return destroyed;
	}
	
	@Override
	int getPlayer() {
		return player;
	}
	
	/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setPos(double inX, double inY) {
		x = inX;
		y = inY;
	}

	@Override
	void setSpeed(double inVX, double inVY) {
		vx = inVX;
		vy = inVY;
	}

	@Override
	void setRad(double inR) {
		rad = inR;
	}

	@Override
	void setHP(int inHP) {
		hp = inHP;
	}

	void setCounterOne (int counter_one) {
		this.counter_one = counter_one;
	}
	
	void setDestroyed (boolean destroyed) {
		this.destroyed = destroyed;
	}
	
	@Override
	void setTime() {
		// Keep empty, we don't need a timer here
	}
	/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		switch (type) {
		case PLAYER_MELEE_AOE:
			g.setColor(new Color(1.0f, 51/255.f, 201/255.f));
			if (counter_one < radMax) {
				g.fillOval(xOffset+(int)Math.round((x-(width+counter_one*(rad-width)/radMax)/2.)*step),  yOffset+(int)Math.round((y-(height+counter_one*(rad-height)/radMax)/2.)*step), (int)Math.round(step*(width+counter_one*(rad-width)/radMax)), (int)Math.round(step*(height+counter_one*(rad-height)/radMax)));
				counter_one++;
			} else if (counter_one < fadeOut){
				g.setColor(new Color(1.0f, 51/255.f, 201/255.f, 1.f/(radMax-fadeOut)*counter_one+1.f*fadeOut/(fadeOut-radMax)));
				g.fillOval(xOffset+(int)Math.round((x-rad/2.)*step),  yOffset+(int)Math.round((y-rad/2.)*step), (int)Math.round(step*rad), (int)Math.round(step*rad));
				counter_one++;
			}

			if (counter_one == 15)	destroyed = true;
			break;
		}
	}

	@Override
	void attack() {
		
		CoreGameObjects damageable;
		double objPosX;
		double objPosY;
		
		switch (type) {
		case PLAYER_MELEE_AOE:
//			CoreGameObjects damageable;
//			double objPosX;
//			double objPosY;
			
			
			for (int i=0; i<room.size(); i++) {
				damageable = room.get(i);
				
				if (damageable instanceof MISCWall || damageable instanceof Enemy) {
					
					objPosX		= damageable.getPosX();
					objPosY		= damageable.getPosY();
					
					if ((x-objPosX)*(x-objPosX)+(y-objPosY)*(y-objPosY) < rad*rad/4) {
						damageable.takeDamage(type, strength);
					}
				}
			}
			break;
			
		case PLAYER_MELEE_SHORT:
//			CoreGameObjects damageable;
//			double objPosX;
//			double objPosY;
			
			
			for (int i=0; i<room.size(); i++) {
				damageable = room.get(i);
				
				if (damageable instanceof Enemy) {
					
					objPosX		= damageable.getPosX();
					objPosY		= damageable.getPosY();
					
					if ((x-objPosX)*(x-objPosX)+(y-objPosY)*(y-objPosY) < rad*rad/4) {
						damageable.takeDamage(type, strength);
					}
				}
			}
		}
	}
	
	@Override
	Attack copy() {
		// return a new bullet object with the same attributes as the old one
		Melee melee = new Melee(this.x, this.y, this.vx, this.vy, this.type, this.figure, this.room, this.player);
		melee.setCounterOne(this.counter_one);
		melee.setDestroyed(this.destroyed);
		return melee;
	}

	@Override
	void takeDamage(int type, int strength) {
		// Keep empty as an attack does not take but deals damage
	}

	@Override
	void propagate(ArrayList<CoreGameObjects> room, boolean server) {
		// Most likely kept empty, since Melee attacks are stationary and only the animation moves
	}
}
