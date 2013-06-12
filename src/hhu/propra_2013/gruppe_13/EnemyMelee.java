package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyMelee extends CoreGegner{

	
	private double 	rad;
	private int 	type;
	private int 	hp;
	private double 	x;
	private double 	y;
	private double 	vx;
	private double 	vy;
	private Figure 	figure;
	private int 	width;
	private int 	height;
	
EnemyMelee(double inPosX, double inPosY,int inWidth, int inHeight, Figure inFigure, int inType){
	x = inPosX;
	y = inPosY;
	width = inWidth;
	height = inHeight;
	figure = inFigure;
	type = inType;
}
	
	
	
	
/*getter and setter methods for the melee enemy--------------------------------------------------------------------------------------------------------*/
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/

	@Override
	void setType(int inType) {
		type = inType;
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

/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
	

	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.RED);
		g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
	}

	@Override
	void attack() {
		
		
	}
	
	void artificialIntelligence(Figure figure){
		switch(type){
		
		case 0: //this will be the trap type
			
			
			
		break;
		/*-------------------------------------------------------------------------------------*/
		case 1: //this will be the patrol type
			
		break;
		/*-------------------------------------------------------------------------------------*/
		case 2: //this will be the random walk type
			
		break;
		/*-------------------------------------------------------------------------------------*/
		case 3: //this one will run towards the figure
			
		break;
		/*-------------------------------------------------------------------------------------*/
		case 4: //this runs away from us 
			
		break;
		/*-------------------------------------------------------------------------------------*/
		}
	}

}
