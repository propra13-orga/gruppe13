package hhu.propra_2013.gruppe_13;

class Wall extends GameObjects {
	/*-----------------------------------------------------------------------------------------------*/
	// Hitpoints, position and collision radius
	int hp;
	double x, y, r;
	double v_x, v_y;
	
	Wall(double initX, double initY, double initRadius) {
		x = initX;
		y = initY;
		v_x = 0;
		v_y = 0;
		r = initRadius;
		hp = 1;
	}
	
	// Getter and Setter methods for above variables
	int getHP(){
		return hp;
	}
	
	double getPosX() {
		return x;
	}
	
	double getPosY() {
		return y;
	}
	
	double getRad() {
		return r;
	}
	
	double getVX() {
		return v_x;
	}
	
	double getVY() {
		return v_y;
	}
	
	
	void setPos(double inX, double inY) {
		x = inX;
		y = inY;
	}
	
	void setSpeed(double inVX, double inVY) {
		v_x = inVX;
		v_y = inVY;
	}
	
	void setRad(double inR) {
		r = inR;
	}
	
	void setHP(int inHP) {
		hp = inHP;
	}
	
	
	/*-----------------------------------------------------------------------------------------------*/
	void draw() {
		
	}
	
	void attack() {
		
	}
}
