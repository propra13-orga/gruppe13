package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class EnemyRanged extends Enemy {
	
	// type, maximum hp and hp and a stationary variable for initial waiting as the player enters the room
	private int 	type, maxHp, hp;
	private int 	stationary;
	
	// position and velocity data, width, height and collision radius
	private double 	x, y, vx, vy, v_weight;
	private double 	width, height, rad;
	
	// strength of the enemy and booleans for checking vital signs
	private int 	strength;
	private boolean	dead, dying, stopDrawing;
	
	// the current stage of the game and a long for the time stamp
	private int 	stage;
	private long 	regenerate;
	
	EnemyRanged (double inx, double iny,int inWidth, int inHeight, int inType, int inStage) {
		x = inx;
		y = iny;
		
		width 	= inWidth;
		height 	= inHeight;
		
		type	= inType;
		
		stage	= inStage;
		
		switch (type) {
		case ENEMY_FIRE_SHOOTING:
			regenerate	= System.currentTimeMillis();
			strength 	= 1;
			hp			= 5;
			maxHp		= hp;
			break;
		}
		
		rad = Math.max(width, height) + Math.pow(Math.ceil(Math.abs(v_weight)),2);
	}

	@Override
	int getType() {
		return type;
	}

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

	@Override
	boolean leftForDead() {
		return dead;
	}

	@Override
	boolean stopDrawing() {
		return stopDrawing;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setType(int inType) {
		type	= inType;
	}

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

	/*-----------------------------------------------------------------------------------------------------------------------*/
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		switch (type) {
		case ENEMY_FIRE_SHOOTING:
			// the fire will diminish according to the amount of hp left, if it is dead, only an empty fireplace will remain.  
			double fireWidth 	= width/maxHp*hp;
			double fireHeight	= height/maxHp*hp;
			
			g.setColor(Color.GRAY);
			g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
			
			g.setColor(Color.PINK);
			g.fillOval(xOffset+(int)Math.round((x-fireWidth/2.)*step),  yOffset+(int)Math.round((y-fireHeight/2.)*step), (int)Math.round(step*fireWidth), (int)Math.round(step*fireHeight));
			
			break;
		}
	}

	@Override
	void attack(Figure figure) {
		switch (type) {
		case ENEMY_FIRE_SHOOTING:
			if (hp > 0) 
				figure.takeDamage(type, strength);
			break;
		
		default:
			figure.takeDamage(type, strength);
			break;
		}
	}

	@Override
	void artificialIntelligence(Figure inFigure, ArrayList<CoreGameObjects> currentRoom) {
	}

	@Override
	void takeDamage(int type, int strength) {
		regenerate = System.currentTimeMillis();
		
		switch (type) {
		
		default:
			hp -= strength;
			break;
		}
		
		if (hp <= 0) 
			dying = true;
	}
}
