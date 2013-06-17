package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;

abstract class CoreGameObjects {
	// declare final variables in order to determine what bullet shall be fired 
	static final int PLAYER_BULLET_STD 			= 0;
	static final int PLAYER_SPECIAL_BULLET_ONE	= 1;
	static final int PLAYER_SPECIAL_BULLET_TWO	= 2;
	static final int ENEMY_BULLET_STD			= 10;
	
	// declare close ranged weapons (melee and casts)
	static final int PLAYER_MELEE_AOE			= 20;
	
	// finals for defining melee enemy type
	static final int ENEMY_TRAP					= 40;
	static final int ENEMY_PATROL				= 41;
	static final int ENEMY_RANDOM_WALKER		= 42;
	static final int ENEMY_FIGURE_RUN			= 43;
	static final int ENEMY_FLEEING				= 44;
	static final int ENEMY_FIRE					= 45;
	static final int ENEMY_FIGURE_FLYING		= 46;
	
	// finals for defining ranged enemy type
	static final int ENEMY_FIRE_SHOOTING		= 80;
	
	// Getter and Setter methods for Objects used by Logic and GameDrawer classes
	abstract int 	getHP();	
	abstract double getPosX();
	abstract double getPosY();
	abstract double getRad();
	abstract double getVX();
	abstract double getVY();
	
	abstract void setPos(double inX, double inY);
	abstract void setSpeed(double inVX, double inVY);
	abstract void setRad(double inR);
	abstract void setHP(int inHP);
	
	// Draw and Attack methods for all inheriting classes
	abstract void draw(Graphics2D g, int xOffset, int yOffset, double step);
	abstract double getWidth();
	abstract double getHeight();
	abstract void takeDamage(int type, int strength);
}
