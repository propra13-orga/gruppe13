package hhu.propra_2013.gruppe_13;

/*
 * Wir brauchen:
 * Position
 * Hitpoints
 * Richtung
 * Radius
 */

public class Figure extends GameGraphics {
	
	int x,y,r,hp;
	
	
	
	int getHP(){
		return hp;
	}
	
	double getPosX(){
		return x;
	}
	
	double getPosY(){
		return y;
	}
	
	double getRad(){
		return r;
	}
	
	void setPosX(){
		x=1;
	}
	
	void setPosY(){
		y=1;
	}
	
	void setRad(){
		r=1;
	}
	
	void setHP(){
		hp=1;
	}
}
