package hhu.propra_2013.gruppe_13;

class Wall extends GameGraphics {

	int 	hp;
	double 	x,y,r;

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

	void setPosX(double inX) {

		x = inX;
		return;
	}

	void setPosY(double inY) {

		y = inY;
		return;
	}


	void setRad(double inR) {

		r = inR;
		return;
	}


	void setHP(int inHP) {

		hp = inHP;
		return;
	}


}
