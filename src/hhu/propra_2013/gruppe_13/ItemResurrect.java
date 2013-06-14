package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ItemResurrect extends Item{

	private double 	x, y;
	private double	r;
	private double 	v_x, v_y;
	private double 	height, width;
	private int 	hp;
	private int 	prize;
	
	public ItemResurrect(double initX, double initY, int initWidth, int initHeight) {
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
		prize 	= 5;
		
	}
	
	int getPrize(){
		return prize;
	}

	@Override
	void modFigure(ArrayList<CoreGameObjects> room, Figure figure) {
		int money = figure.getGeld();
		if (money >= 5){
			money = money - 5;
			figure.setGeld(money);
			figure.pickUpItem(this);
			room.remove(this);
		}		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.gray);
		g.fillOval(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
		
	}

	@Override
	void attack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	double getHeight() {
		// TODO Auto-generated method stub
		return height;
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
		// Keep empty since items shouldn't take damage
	}

}
