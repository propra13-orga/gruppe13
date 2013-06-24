package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics2D;

class Map extends CoreGameObjects {
	int x,y;
	int map [][] = new int[10][10];
	int minX, maxX, minY, maxY;
	boolean now; //gets set by logic for drawing or not drawing the map
	

	/*------------------------------------------------------------------------------------------------------------------------*/
	@Override
	int getHP() {
		return 0;
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
	}
	
	void setVisited(int inLocationX, int inLocationY){
		map[inLocationX][inLocationY] = map[inLocationX][inLocationY]+10;
	}
	
	void setRoom(int[][] inConstruction){
		map = inConstruction;
		this.castMap();
	}
	
	void setDraw(boolean in){
		now = in;
	}
	
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
			for(y = minY;y <= maxY;y++){
				for(x = minX;x <= maxX;x++){
					if(map[x][y]-10 > 0){
						if(map[x][y]%10 == 2){
							g.setColor(Color.BLACK);
							g.fillRect(xOffset+ (int)step*(16/9)*(x-minX), yOffset+(int)(step)*(y - minY), (int)(step*16/9), (int)(step));
							g.setColor(Color.WHITE);
							g.drawRect(xOffset+ (int)step*(16/9)*(x-minX), yOffset+(int)(step)*(y - minY), (int)(step*16/9), (int)(step));
						}
						else if(map[x][y]%10 == 3){
							g.setColor(Color.DARK_GRAY);
							g.fillRect(xOffset+ (int)step*(16/9)*(x-minX), yOffset+(int)(step)*(y - minY), (int)(step*16/9), (int)(step));
							g.setColor(Color.WHITE);
							g.drawRect(xOffset+ (int)step*(16/9)*(x-minX), yOffset+(int)(step)*(y - minY), (int)(step*16/9), (int)(step));
						}
						else if(map[x][y]%10 == 4){
							g.setColor(Color.LIGHT_GRAY);
							g.fillRect(xOffset+ (int)step*(16/9)*(x-minX), yOffset+(int)(step)*(y - minY), (int)(step*16/9), (int)(step));
							g.setColor(Color.WHITE);
							g.drawRect(xOffset+ (int)step*(16/9)*(x-minX), yOffset+(int)(step)*(y - minY), (int)(step*16/9), (int)(step));
						}
						System.out.println("i'm a rect");
					}
				}
			}
		}
	}

	@Override
	void takeDamage(int type, int strength) {
	} 
	
	Map(){
		for (x = 0;x < 10; x++){
			for (y = 0; y < 10; y++){
				map[x][y] = 0; //nullen des map array
			}
		}
	}
	
}
