package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class ItemResurrect extends Item{

	private double 	x, y;
	private double	r;
	private double 	v_x, v_y;
	private double 	height, width;
	private int 	hp;
	
	public ItemResurrect(double initX, double initY, int initWidth, int initHeight, int inHP) {
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
		
	}

	@Override
	void modFigure(ArrayList<CoreGameObjects> collidable, Figure figure) {
		
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
	void takeDamage(int type) {
		// TODO Auto-generated method stub
		
	}

}
