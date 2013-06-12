package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

class MISCDoor extends CoreGameObjects {
	private double 		x,y,r;
	private double 		height, width;
	private boolean 	open;
	private int 		destination;
	
	MISCDoor (double initX, double initY, double initWidth, double initHeight, double initRadius, boolean inOpen, int inDestination){
		x 			= initX;
		y 			= initY;
		r			= Math.max(initWidth, initHeight);
		
		width 		= initWidth;
		height		= initHeight;
		
		open		= inOpen;
		destination	= inDestination; //Destination stores where the door leads to. 0=goes up 1=goes right 2= goes down 3= goes left 4= Goes to the next Level

	}
	
	int getDestination(){
		return destination;
	}

	boolean getOpen(){
		return open;
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
	double getWidth() {
		return width;
	}

	@Override
	double getHeight() {
		return height;
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
			g.setColor(Color.RED);
			if (destination == 5){g.setColor(Color.ORANGE);}//Türen die in den nächsten Level führen haben eine andere Farbe
			g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
	}

	@Override
	void attack() {
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
