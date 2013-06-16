package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ItemImproveWeapon extends Item{


	private double 	x, y;
	private double	r;
	private double 	v_x, v_y;
	private double 	height, width;
	private int 	hp;
	
	public ItemImproveWeapon(double initX, double initY, int initWidth, int initHeight, int inHP) {
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
		
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
		
	}

	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.orange);
		g.fillRect(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
		
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
		return hp;
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
		r = inR;
		
	}

	@Override
	void setHP(int inHP) {
		// TODO Auto-generated method stub
		hp = inHP;
	}

	
	@Override
	void modFigure(ArrayList<CoreGameObjects> room, Figure figure) {
		room.remove(this);
	}

	@Override
	void takeDamage(int type, int strength) {
		// Keep empty since items should probably not be able to take damage
	}

	

}
