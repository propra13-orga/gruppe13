package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ItemMoney extends Item {

	private double 	height, width;
	private double 	x, y;
	private double	r;
	
	public ItemMoney(double initX, double initY, int initHeight, int initWidth){
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
	}
	
	
	
	
	
	@Override
	void modFigure(ArrayList<CoreGameObjects> room, Figure figure) {
		int geld;
		geld = figure.getGeld();			
		geld++;
		figure.setGeld(geld);
		room.remove(this);
	}

	@Override
	double getPosX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double getPosY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	double getRad() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	void setPos(double inX, double inY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		// TODO Auto-generated method stub
		g.setColor(Color.yellow);
		g.fillOval(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
		
	}

	@Override
	void attack() {
		// TODO Auto-generated method stub
		
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

	@Override
	int getHP() {
		// TODO Auto-generated method stub
		return 0;
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

	@Override
	void setSpeed(double inVX, double inVY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setRad(double inR) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void setHP(int inHP) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void takeDamage(int type, int strength) {
		// TODO Auto-generated method stub
		
	}

}
