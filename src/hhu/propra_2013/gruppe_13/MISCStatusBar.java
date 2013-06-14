package hhu.propra_2013.gruppe_13;

import java.awt.Graphics2D;

public class MISCStatusBar {

	private Figure 	figure;
	
	//variables  for the stats
	private int		hp, maxHP, money, armor;
	
	//coordinates for initializing the draw
	private double	hpx, hpy, mhpx, mhpy;
	
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

		//find the basic position of the panels
		hpx = step*20;
		hpy = step*16;
		
		mhpx = step*20;
		mhpy = step*16+step/10;
	}
	
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		
		this.work(step);
		
		for(int i=1 ; i<=hp ; i++){
			g.fillRect((int)(hpx+i*step),(int)hpy,(int)step,(int)step/2);
		}
		for(int i=1 ; i<=figure.getMaxHP() ; i++){
			g.fillRect((int)(mhpx+i*step),(int)mhpy,(int)step,(int)step/10);
		}
		for(int i=1 ; i<=figure.getArmor() ; i++){
			//g.fillRect()
		}
	}

}
