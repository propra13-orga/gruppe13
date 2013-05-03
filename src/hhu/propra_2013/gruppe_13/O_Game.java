package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;


class O_Game {
	
	Logic logic;
	GameDrawer graphics;
	ArrayList<ArrayList<GameObjects>> rooms;
	
	// Initialize method for the actual game
	void init() {
		// Initiate object variables
		logic = new Logic();
		graphics = new GameDrawer();
		rooms = new ArrayList<ArrayList<GameObjects>>();
		
		// iterate over all objects and rooms within the level, all objects run within [0...800)x[0...600)
		for (int i=0; i<3; i++) {
			ArrayList<GameObjects> temp = new ArrayList<GameObjects>();
			temp.add(0, new Figure(0, 0, 1));
			
			rooms.add(i, temp);
		}
		
		// Initialize Logic and Graphics
		logic.init(rooms);
		graphics.init(rooms);
		
		
	}
}