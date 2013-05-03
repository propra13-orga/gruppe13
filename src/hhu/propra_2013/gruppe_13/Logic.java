package hhu.propra_2013.gruppe_13;


/*
 * Ablauf: 	Logik bekommt die Bewegungsbefehle von der IO
 * 			Logik kennt die (Liste) der Objekte
 * 			(Gegner werden berechnet)
 * 			Kollisionsabfrage
 * 			Logik gibt den Objekten ihre neuen Positionen
 */


class Logic implements Runnable {
	
	@Override
	public void run() {
		
	}
	void setMoveX(boolean walkX){
		moveX = walkX;
	}
	void setMoveXn(boolean walkXn){
		moveXn = walkXn;
	}
	void setMoveY(boolean walkY){
		moveY = walkY;
	}
	void setMoveYn(boolean walkYn){
		moveYn = walkYn;
	}
}
