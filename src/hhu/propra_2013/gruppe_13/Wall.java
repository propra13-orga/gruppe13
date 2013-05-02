package hhu.propra_2013.gruppe_13;

class Wall extends GameGraphics {

	int hp = 0;
	double x = 0;
	double y = 0;
	double r = 0;
	
	
	int getHP() {
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

	void setPosX(double inx) {

		x = inx;
		return;
	}

	void setPosY(double iny) {

		y = iny;
		return;
	}


	void setRad(double inr) {

		r = inr;
		return;
	}


	void setHP(int inhp) {

		hp = inhp;
		return;
	}

}
