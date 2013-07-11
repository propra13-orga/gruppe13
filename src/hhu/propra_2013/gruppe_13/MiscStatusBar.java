package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MiscStatusBar {
	private Font font;

	private Figure 	figure;
	
	//variables  for the stats
	private int		hp, maxHP, money, armor, chocolate;
	
	//coordinates for initializing the draw
	private double	hpx, hpy, mhpx, mhpy, ax, ay, chocolateX, chocolateY;
	
	public MiscStatusBar(Figure inFigure) {
		figure 	= inFigure;
//		mode = inMode;
	}
	
	//here we get our working variables
	private void work(double step){
		
		//getting my variables for counting the draws
		hp 		= figure.getHP();
		maxHP	= figure.getMaxHP();
		money	= figure.getGeld();
		armor	= figure.getArmor();
		chocolate	= figure.getChocolate();
		
		//find the basic position of the panels
		hpx = step * 7;
		hpy = step * 16;
		
		mhpx = hpx;
		mhpy = hpy + step/2;
		
		ax = step*15;
		ay = step*15 - step/2;
		
		chocolateX = step * 15;
		chocolateY = step * 16;
		font = new Font("Arial", Font.PLAIN, (int)step);
	}
	
	void talkTo(String inString){
		
	}
	
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
//		for(int i = 1; i <= mode; i++){
//			g.setColor(Color.yellow);
//			g.drawOval(xOffset+(int)(step*16+step/10*i), yOffset+(int)(step), (int)(step/10), (int)(step/10));
//		}
		
		g.setColor(Color.BLUE);
		this.work(step);
		
		//drawing the hp++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		int j = 1;
			for(int i = 0 ; i < hp ; i++){
				j = i;
				if(i < 12){
					if(i%2 == 1) g.fillRect(xOffset+(int)(hpx+((i-1)*step)/2),yOffset+(int)(hpy-step/2 - step),(int)(step-step/13),(int)step/2);
					if(i%2 == 0) g.fillRect(xOffset+(int)(hpx+(i*step)/2),yOffset+(int)(hpy - step),(int)(step-step/13),(int)step/2);
				}
				if(i >= 12 && i < 24){
					if(i%2 == 1) g.fillRect(xOffset+(int)(hpx+((i-16)*step)/2 - step/2),yOffset+(int)(hpy-step/2+step/7),(int)(step-step/13),(int)step/2);
					if(i%2 == 0) g.fillRect(xOffset+(int)(hpx+((i-15)*step)/2 - step/2),yOffset+(int)(hpy+step/7),(int)(step-step/13),(int)step/2);
				}
			}
			j++;													//raise the counter by one
			if(j%2 == 1)j++;										//now check if its on top of one cup or a new slot
			for(int i = j ; i < figure.getArmor() + j ; i++){		//draw the cans
				g.setColor(Color.DARK_GRAY);
				if(i < 12){
					if(i%2 == 1) g.fillRect(xOffset+(int)(hpx+((i-1)*step)/2),yOffset+(int)(hpy-step/2 - step),(int)(step-step/13),(int)step/2);
					if(i%2 == 0) g.fillRect(xOffset+(int)(hpx+(i*step)/2),yOffset+(int)(hpy - step),(int)(step-step/13),(int)step/2);
				}
				if(i >= 12 && i < 24){
					if(i%2 == 1) g.fillRect(xOffset+(int)(hpx+((i-16)*step)/2 - step/2),yOffset+(int)(hpy-step/2+step/7),(int)(step-step/13),(int)step/2);
					if(i%2 == 0) g.fillRect(xOffset+(int)(hpx+((i-15)*step)/2 - step/2),yOffset+(int)(hpy+step/7),(int)(step-step/13),(int)step/2);
				}
				g.setColor(Color.black);
			}

		

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//drawing our free slots for hp

			for(int k = 1 ; k <= figure.getMaxHP() ; k++){
				if(k <= 6) 			g.fillRect(xOffset+(int)(mhpx+(k-1)*step),yOffset+(int)(mhpy-step),(int)(step-step/13),(int)step/10);
				if(k > 6 && j < 12)	g.fillRect(xOffset+(int)(mhpx+(k-9)*step),yOffset+(int)(mhpy + step/7),(int)(step-step/13),(int)step/10);
			}
			
		
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//		//drawing our armor
//		for(int i = 0 ; i < figure.getArmor() ; i++){
//			if(i%2 == 1) g.fillRect(xOffset+(int)(ax+i*step),yOffset+(int)(ay-step/2),(int)(step-step/13),(int)step/2);
//			if(i%2 == 0) g.fillRect(xOffset+(int)(ax+(i*step)/2),yOffset+(int)ay,(int)(step-step/13),(int)step/2);
//		}
		
		
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//writing our money to some place
		g.setFont(font);
		g.drawString(figure.getGeld()+"#", xOffset+(int)(step*17), yOffset+(int)step*16);
		
		
		//draw our "mana"
		for(int i = 0 ; i < chocolate ; i++){
			g.setColor(Color.magenta);
			g.drawRect(xOffset+(int)(chocolateX + (i * step/2)) , yOffset+(int)chocolateY , (int)step/3 , (int)(step-step/5));
			g.setColor(Color.BLACK);

		}
	}

}
