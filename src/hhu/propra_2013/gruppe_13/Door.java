package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;

class Door extends GameObjects {
	double 		x,y,r;
	boolean 	open, enabled;
	int 		destination;
	
	Door (double initX, double initY, double initRadius, boolean inOpen, boolean inEnabled, int inDestination){
		x 			= initX;
		y 			= initY;
		r			= initRadius;
		open		= inOpen;
		destination	= inDestination; //Destination stores where the door leads to. 1=goes up 2=goes right 3= goes down 4= goes left
		enabled		= inEnabled;
	}

	void setEnabled(boolean in){
		enabled = in;
	}
	
	
	boolean isEnabled(){
		return enabled;
	}
	
	
	
	@Override
	int getHP() {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub

	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		// TODO Auto-generated method stub

	}

	@Override
	void attack() {
		// TODO Auto-generated method stub

	}

	@Override
	void incX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void decX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void incY() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void decY() {
		// TODO Auto-generated method stub
		
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

}
