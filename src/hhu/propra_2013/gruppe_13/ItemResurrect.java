package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

class ItemResurrect extends Item{

	private static final long serialVersionUID = -5798199784641517967L;
	
	private double 	x, y;
	private double	r;
	private double 	height, width;
	private int 	prize;
//	private Figure 	figure;
	
	ItemResurrect(double initX, double initY, int initWidth, int initHeight) {
		x	= initX;
		y	= initY;
		r = Math.max(width, height);
		height	= initWidth;
		width	= initHeight;
		prize 	= 5;
//		figure 	= inFigure;
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
		Font font = new Font("Arial", Font.PLAIN, (int)step/2);
		g.setFont(font);
//		if(figure.getGeld() >= prize){
			g.setColor(Color.yellow);
//		}
//		if(figure.getGeld() < prize){
//			g.setColor(Color.red);
//		}
		g.drawString(prize + "#", xOffset+(int)Math.round(x*step), yOffset+(int)(y*step) );
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
