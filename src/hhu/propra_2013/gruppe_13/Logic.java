package hhu.propra_2013.gruppe_13;

/*
 * Ablauf: 	Logik bekommt die Bewegungsbefehle von der IO
 * 			Logik kennt die (Liste) der Objekte
 * 			(Gegner werden berechnet)
 * 			Kollisionsabfrage
 * 			Logik gibt den Objekten ihre neuen Positionen
 */

class Logic {

	GameGraphics graphics;
	
	// use constructor to set gameGraphics object
	Logic(GameGraphics inGraphics) {
		graphics = inGraphics;
	}
	
}
