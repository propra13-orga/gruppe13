package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;


class Figure extends CoreGameObjects {
	/*-----------------------------------------------------------------------------------------------*/
	// set these for the viewing direction of the figure
	static final int UP				= 1;
	static final int DOWN			= 2;
	static final int LEFT			= 3;
	static final int RIGHT			= 4;
	static final int UPLEFT			= 5;
	static final int UPRIGHT		= 6;
	static final int DOWNLEFT		= 7;
	static final int DOWNRIGHT		= 8;
	
	/* hp: Hitpoints, money is, well, money and chocolate is a very fun stuff to many people eat way too much of. 
	 * Here it is our mana though. */
	private int 	hp;
	private int 	money;
	private int 	chocolate;
	
	// Position and velocity data, and the width and height of the figure 
	private double 	x, y, r;
	private double 	v_x, v_y;
	private double 	width, height;
	
	// maximum amount of health slots and a cooldown for enemy contact. The latter is established via the System time. 
	private int 	maxHP;
	private long	cooldown;
	
	// armor variable and items, if armor is present it is reduced instead of health
	private int 	armor;
	private Item 	item1, item2, item3;
	
	// special attack and attack type is needed in order to draw the current attack
	private boolean	specialAttack;
	private int 	attackType;
	
	// this variables is needed to set the direction in which the figure is looking
	private int 	direction;
	
	// class constructor
	Figure(double initX, double initY, double initHeight, double initWidth) {
		x 		= initX;
		y 		= initY;
		
		width  	= initWidth;
		height 	= initHeight;
		
//		width	= 0.5;
//		height	= 0.5;
		
		v_x 	= 0.3;
		v_y 	= 0.3;
		r   	= Math.max(width, height) + Math.pow(Math.ceil(Math.abs(v_x)), 2)*Math.pow(Math.ceil(Math.abs(v_y)), 2);
		
		hp  	= 15;
		maxHP	= 8;
		chocolate	= 10;
		
		item1 	= null;
		item2 	= null;
		item3 	= null;
		
		cooldown = System.currentTimeMillis();
		armor	 = 5;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
	// Getter and Setter methods for above variables
	int getArmor(){
		return armor;
	}
	
	int getGeld(){
		return money;
	}
	
	
	int getMaxHP(){
		return maxHP;
	}
	
	
	@Override
	int getHP(){
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
	
	boolean checkRes() {
		if(item1 instanceof ItemResurrect){
			return true;
		}
		else if(item2 instanceof ItemResurrect){
			return true;
		}
		else if(item3 instanceof ItemResurrect){
			return true;
		}

		else 
			return false;
	}
	
	int getChocolate() {
		return chocolate;
	}
	
	/*-----------------------------------------------------------------------------------------------*/
	void setArmor(int inArmor){
		armor = inArmor;
	}
	
	void setPos(double inX, double inY) {
		x 	= inX;
		y 	= inY;
	}
	
	void setSpeed(double inVX, double inVY) {
		v_x = inVX;
		v_y = inVY;
		r   	= Math.max(width, height) + Math.pow(Math.ceil(Math.abs(v_x)), 2)*Math.pow(Math.ceil(Math.abs(v_y)), 2);
	}
	
	void setRad(double inR) {
		r 	= inR;
	}
	
	void setHP(int inHP) {
		hp 	= inHP;
	}
	
	void setMaxHP(int inMaxHP){
		maxHP	= inMaxHP;
	}
	
	void setGeld(int inGeld){
		money 	= inGeld;
	}
	
	void setChocolate (int inChocolate) {
		chocolate 	= inChocolate;
	}
	
	void setAttackType (int type) {
		attackType 	= type;
	}
	
	void setDirection (int input) {
		direction 	= input;
	}

	void setItem1 (Item inItem){
		item1 = inItem;
	}
	void setItem2 (Item inItem){
		item2 = inItem;
	}
	void setItem3 (Item inItem){
		item3 = inItem;
	}

	/*-----------------------------------------------------------------------------------------------*/
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		/* Durch die Variable step ist eine Umrechnung auf den Screen möglich, ansonsten würde das Spiel immer unterschiedlich angezeigt werden, 
		 * allerdings ist zu beachten, dass durch den Gebrauch von Math.round() eine Verzeichnung um einen Pixel unten oder rechts nicht Auftritt, 
		 * wie es bei einem cast auf int passieren kann.  */
		g.setColor(Color.BLUE);
		g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
		// TODO: bilder anstelle des quadrats malen

	}
	
	// this method can be called from the collision method, then later on be used
	// TODO: Make that shit better
	void pickUpItem(Item inItem) {
		if		(item1 == null)	item1 = inItem;
		else if	(item2 == null)	item2 = inItem;
		else if	(item3 == null)	item3 = inItem;
	}

	// check whether there is any armor to destroy, else reduce hp
	void takeDamage(int type, int inStrength) {
		if(System.currentTimeMillis()-cooldown > 1000){
			if (armor > 0)
				armor -= inStrength;
			else
				hp -= inStrength;
			cooldown = System.currentTimeMillis();
		}
	}
}
