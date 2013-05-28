package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class CupACoffee extends Item {

	private double 	x, y;
	private double	r;
	private double 	v_x, v_y;
	private double 	height, width;
	
	public CupACoffee(double d, double e, int i, int j, int k) {
		x	= 5;
		y	= 5;
		r	= 5;
		height	= j;
		width	= k;
		
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
	void modFigure(ArrayList<ArrayList<GameObjects>> inRooms, Figure figure) {
		int hp;
		hp = figure.getHP();
			if(figure.getMaxHP() > hp){				
				hp++;
				figure.setHP(hp);
			}
	}

	

}
