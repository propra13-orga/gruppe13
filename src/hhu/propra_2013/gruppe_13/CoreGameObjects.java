package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;
import java.io.Serializable;


	/**
	 * Basis aller 'Objekte' im Spiel:
	 * Gegner, NPC, Items, Figure
	 * @param PLAYER_BULLET_STD first ranged attack
	 * @param PLAYER_SPECIAL_BULLET_ONE upgrade of above
	 * @param PLAYER_SPECIAL_BULLET_TWO upgrade of above
	 * @param ENEMY_BULLET_STD std enemy range attack
	 */

abstract class CoreGameObjects implements Serializable {

	// serial version ID, the interface Serializable asks for it
	private static final long serialVersionUID = 2537021854756774619L;
	

	// declare final variables in order to determine what bullet shall be fired 
	static final int PLAYER_BULLET_STD 			= 0;
	static final int PLAYER_SPECIAL_BULLET_ONE	= 1;
	static final int PLAYER_SPECIAL_BULLET_TWO	= 2;
	static final int ENEMY_BULLET_STD			= 10;
	
	// declare close ranged weapons (melee and casts)
	static final int PLAYER_MELEE_AOE			= 20;
	static final int PLAYER_MELEE_SHORT			= 21;
	
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
	/**
	 * Methode um die HP eines GameObject zu erfragen
	 * @return Gibt die HP des GameObject zurück
	 */
	abstract int 	getHP();	
	
	/**
	 * Methode um die X-Position eines Objekts im Raum zu erfragen
	 * @return Gibt die X-Position des Objekts im Raum zurück
	 */
	abstract double getPosX();
	
	/**
	 * Methode um die Y-Position eines Objekts im Raum zu erfragen
	 * @return Gibt die Y-Position des Objekts im Raum zurück
	 */
	abstract double getPosY();
	
	/**
	 * Methode um den Kollisionsradius des Objekts zu erfragen
	 * @return Gibt den Kollisionsradius des Objekts zurück
	 */
	abstract double getRad();
	
	/**
	 * Methode um die Geschwindigkeit des Objekts in X-Richtung zu erfragen
	 * @return Gibt die Geschwindigkeit des Objekts in X-Richtung zurück, kann negativ sein
	 */
	abstract double getVX();
	
	/**
	 * Methode um die Geschwindigkeit des Objekts in Y-Richtung zu erfragen
	 * @return Gibt die Geschwindigkeit des Objekts in Y-Richtung zurück, kann negativ sein
	 */
	abstract double getVY();
	
	/**
	 * Methode um die Position des Objekts im Raum festzulegen
	 * @param inX Legt die X Position fest
	 * @param inY Legt die Y Position fest
	 */
	abstract void setPos(double inX, double inY);
	
	/**
	 * Methode um die Geschwindigkeit des Objekts festzulegen
	 * @param inVX Legt die Geschwindigkeit in X-Richtung fest
	 * @param inVY Legt die Geschwindigkeit in Y-Richtung fest
	 */
	abstract void setSpeed(double inVX, double inVY);
	
	/**
	 * Methode um den Kollisionsradius des Objekts festzulegen
	 * @param inR Legt den Kollisionsradius fest
	 */
	abstract void setRad(double inR);
	
	/**
	 * Methode um die HP des Objekts festzulegen
	 * @param inHP Legt die HP fest
	 */
	abstract void setHP(int inHP);
	
	// Draw and Attack methods for all inheriting classes
	
	//TODO Dokumentier das mal jemand der weiß wie genau das Ding arbeitet
	abstract void draw(Graphics2D g, int xOffset, int yOffset, double step);
	
	/**
	 * Gibt die Breite eines Objekts zurück
	 * @return Die Breite des Objekts
	 */
	abstract double getWidth();
	
	/**
	 * Gibt die Höhe eines Objekts zurück
	 * @returnDie Höhe des Objekts
	 */
	abstract double getHeight();
	
	/**
	 * Informiert ein Objekt das es Schaden genommen hat, sodass das Objekt seine HP (und gegebenenfalls sonstige Stati) anpassen kann
	 * @param type Codiert den Typ des Schadens
	 * @param strength Gibt die Stärke des Schadens an
	 */
	abstract void takeDamage(int type, int strength);
//	abstract CoreGameObjects copy();
}

