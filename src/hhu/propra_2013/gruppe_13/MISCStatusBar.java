package hhu.propra_2013.gruppe_13;

import java.awt.Font;
import java.awt.Graphics2D;

public class MISCStatusBar {
	private Font font;

	private Figure 	figure;
	
	//variables  for the stats
	private int		hp, maxHP, money, armor, volt;
	
	//coordinates for initializing the draw
	private double	hpx, hpy, mhpx, mhpy, ax, ay, voltX, voltY;
	
	public MISCStatusBar(Figure inFigure) {
		figure 	= inFigure;
	}
	
	//here we get our working variables
	private void work(double step){
		
		//getting my variables for counting the draws
		hp 		= figure.getHP();
		maxHP	= figure.getMaxHP();
		money	= figure.getGeld();
		armor	= figure.getArmor();
		volt	= figure.getVolt();
		//find the basic position of the panels
		hpx = step * 7;
		hpy = step * 16;
		
		mhpx = hpx;
		mhpy = hpy + step/2;
		
		ax = step*15;
		ay = step*15 - step/2;
		
		voltX = step * 15;
		voltY = step * 16;
		font = new Font("Arial", Font.PLAIN, (int)step);
	}
	
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		
		this.work(step);
	//drawing the hp++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			for(int i = 0 ; i < hp ; i++){
				if(i < 12){
					if(i%2 == 1) g.fillRect(xOffset+(int)(hpx+((i-1)*step)/2),yOffset+(int)(hpy-step/2 - step),(int)(step-step/13),(int)step/2);
					if(i%2 == 0) g.fillRect(xOffset+(int)(hpx+(i*step)/2),yOffset+(int)(hpy - step),(int)(step-step/13),(int)step/2);
				}
				if(i >= 12 && i < 24){
					if(i%2 == 1) g.fillRect(xOffset+(int)(hpx+((i-16)*step)/2 - step/2),yOffset+(int)(hpy-step/2+step/7),(int)(step-step/13),(int)step/2);
					if(i%2 == 0) g.fillRect(xOffset+(int)(hpx+((i-15)*step)/2 - step/2),yOffset+(int)(hpy+step/7),(int)(step-step/13),(int)step/2);
				}
			}

		
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//drawing our free slots for hp
			for(int i=1 ; i<=figure.getMaxHP() ; i++){
				if(i <= 6) 			g.fillRect(xOffset+(int)(mhpx+(i-1)*step),yOffset+(int)(mhpy-step),(int)(step-step/13),(int)step/10);
				if(i > 6 && i < 12)	g.fillRect(xOffset+(int)(mhpx+(i-9)*step),yOffset+(int)(mhpy + step/7),(int)(step-step/13),(int)step/10);
			}

		
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//drawing our armor
		for(int i = 0 ; i < figure.getArmor() ; i++){
			if(i%2 == 1) g.fillRect(xOffset+(int)(ax+i*step),yOffset+(int)(ay-step/2),(int)(step-step/13),(int)step/2);
			if(i%2 == 0) g.fillRect(xOffset+(int)(ax+(i*step)/2),yOffset+(int)ay,(int)(step-step/13),(int)step/2);
		}
		
		
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//writing our money to some place
		g.setFont(font);
		g.drawString(figure.getGeld()+"#", xOffset+(int)(step*17), yOffset+(int)step*16);
		
		
		//draw our "mana"
		for(int i = 0 ; i < volt ; i++){
			g.drawRect(xOffset+(int)(voltX + (i * step/2)) , yOffset+(int)voltY , (int)step/3 , (int)(step-step/5));
		}
	}

}
