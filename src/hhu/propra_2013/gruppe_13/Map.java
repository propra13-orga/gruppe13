package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class creates and shows our map.
 * @author : gp13
 */

class Map extends CoreGameObjects {
	
	/**
	 * Variables:
	 * map array is the array that carries the information about the rooms and their status (we add 10 for every visit, so we know where we were)
	 * min and max values are looked up for knowing the drawing range of the map
	 * cur values are set for knowing the currently visited room
	 * now is set by logic / player to know when it is time to show the map
	 */

	private long 	id;
	private static final long serialVersionUID = 1L;
	
	private int 	x,y, hp;
	private int 	map [][] = new int[10][10];
	private int 	minX, maxX, minY, maxY;
	private int 	curX, curY;
	private boolean now; //gets set by logic for drawing or not drawing the map
	

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	long getID() {
		return id;
	}
	
	@Override
	int getHP() {
		return hp;
	}

	@Override
	double getPosX() {
		return 0;
	}

	@Override
	double getPosY() {
		return 0;
	}

	@Override
	double getRad() {
		return 0;
	}

	@Override
	double getVX() {
		return 0;
	}

	@Override
	double getVY() {
		return 0;
	}
	
	@Override
	double getWidth() {
		return 0;
	}

	@Override
	double getHeight() {
		return 0;
	}
	
	boolean getDraw(){
		return now;
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setPos(double inX, double inY) {
	}

	@Override
	void setSpeed(double inVX, double inVY) {
	}

	@Override
	void setRad(double inR) {
	}

	@Override
	void setHP(int inHP) {
		hp = inHP;
	}
	/**
	 * setVisited is the input method to handle the input from the logic
	 * @param inLocationX X-Koordinate des Raums im Levelarray
	 * @param inLocationY Y-Koordinate des Raums im Levelarray
	 */
	void setVisited(int inLocationX, int inLocationY){
		map[inLocationX][inLocationY] = map[inLocationX][inLocationY]+10;
		curX = inLocationX;
		curY = inLocationY;
		
	}
	/**
	 * the level builder makes the basic map, here we start to work our map out
	 * @param inConstruction is the construction array from the level builder
	 */
	void setRoom(int[][] inConstruction){
		map = inConstruction;
		this.castMap();
	}
	
	void setDraw(boolean in){
		now = in;
	}
	/**
	 * the method for finding the minmax settings
	 */
	void castMap(){
		for(y = 0;y <= 9;y++){
			for(x = 0;x <= 9;x++){
				if(map[x][y] != 0){
					//find the max/min Position of objects in my array
					if(minX > x) minX = x;
					if(maxX < x) maxX = x;
					if(minY > y) minY = y;
					if(maxY < y) maxY = y;
				}
			}
		}
	}

	/*------------------------------------------------------------------------------------------------------------------------*/
	
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		if(now){	
			g.setColor(new Color(0,0,0,100));
			g.fillRect(xOffset -(int)step, yOffset - (int)step, (int)(step*24), (int)(step*15));
			g.setColor(Color.BLACK);
//			Color c = new Color(0,0,0);
			for(y = minY ;y <= 9;y++){
				for(x = minX ;x <= 9;x++){
					if(map[x][y]-10 > 0){
						if(x == curX && y == curY){
							g.setColor(Color.RED);
							g.draw3DRect(xOffset+ (int)(step*(2*x)), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step), now);
						}
						if(map[x][y]%10 == 2){
							g.setColor(Color.BLACK);
							g.fillRect(xOffset+ (int)(step*(2*x)), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
							g.setColor(Color.WHITE);
							g.drawRect(xOffset+ (int)(step*(2*x)), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
						}
						if(map[x][y]%10 == 3){
							g.setColor(Color.DARK_GRAY);
							g.fillRect(xOffset+ (int)step*(2*x), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
							g.setColor(Color.WHITE);
							g.drawRect(xOffset+ (int)step*(2*x), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
						}
						if(map[x][y]%10 == 4){
							g.setColor(Color.LIGHT_GRAY);
							g.fillRect(xOffset+ (int)step*(2*x), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
							g.setColor(Color.WHITE);
							g.drawRect(xOffset+ (int)step*(2*x), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
						}
						if(map[x][y]%10 == 1){
							g.setColor(Color.yellow);
							g.fillRect(xOffset+ (int)step*(2*x), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
							g.setColor(Color.WHITE);
							g.drawRect(xOffset+ (int)step*(2*x), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step));
						}
						if(x == curX && y == curY){
							g.setColor(Color.RED);
							g.draw3DRect(xOffset+ (int)(step*(2*x)), yOffset+(int)(step)*(2*y), (int)(2*step), (int)(2*step), now);
						}
					}
				}
			}
		}
	}

	@Override
	void takeDamage(int type, int strength) {
	} 
	/**
	 * here the consructor, it only initializes the array
	 */
	Map(long id){
		
		this.id = id;
		hp = 1;
		
		for (x = 0;x < 10; x++){
			for (y = 0; y < 10; y++){
				map[x][y] = 0; //nullen des map array
			}
		}
	}
	
}
