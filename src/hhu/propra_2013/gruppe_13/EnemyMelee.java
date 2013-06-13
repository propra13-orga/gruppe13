package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.math.*;

public class EnemyMelee extends Enemy{

	
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
	private int		strength;
	
EnemyMelee(double inPosX, double inPosY,int inWidth, int inHeight, Figure inFigure, int inType){
	x = inPosX;
	y = inPosY;
	width = inWidth;
	height = inHeight;
	figure = inFigure;
	type = inType;
	strength = 1;
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

	@Override
	void takeDamage(int type) {
		// TODO Auto-generated method stub
		
	}
	
	void artificialIntelligence(Figure figure){
		switch(type){
		
			case 0: //this will be the trap type
				//here is nothing, because this does nothing
			break;
		/*-------------------------------------------------------------------------------------*/
			case 1: //this will be the patrol type
				//
			break;
		/*-------------------------------------------------------------------------------------*/
			case 2: //this will be the random walk type
				//
			break;
		/*-------------------------------------------------------------------------------------*/
			case 3: //this one will run towards the figure
				double figX = figure.getPosX();
				double figY = figure.getPosY();
				vx = 1*(figX-x)/Math.sqrt(figX*figX-2*figX*x+x*x+figY*figY+2*figY*y+y*y); //berechnung der vx-Richtung als normierte x-Komponente des r-Vektors
				vy = 1*(figY-y)/Math.sqrt(figX*figX-2*figX*x+x*x+figY*figY+2*figY*y+y*y);
				this.setPos(x+vx, y+vy);
			break;
		/*-------------------------------------------------------------------------------------*/
			case 4: //this runs away from the figure
				//
			break;
		/*-------------------------------------------------------------------------------------*/
		}
	}

}
