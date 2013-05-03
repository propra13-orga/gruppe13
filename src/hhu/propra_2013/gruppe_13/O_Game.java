package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;
import javax.swing.JFrame;


class O_Game {
	
	// Frame, graphic,logic and graphic objects for the actual game
	JFrame gameWindow;
	Logic logic;
	GameDrawer graphics;
	ArrayList<ArrayList<GameObjects>> rooms;
	
	// Initialize method for the actual game
	void init(JFrame inFrame) {
		// Initiate object variables
		gameWindow = inFrame;
		logic = new Logic();
		graphics = new GameDrawer();
		rooms = new ArrayList<ArrayList<GameObjects>>();
		
		// iterate over all objects and rooms within the level, all objects run within [0...800)x[0...600)
		for (int i=0; i<3; i++) {
			ArrayList<GameObjects> temp = new ArrayList<GameObjects>();
			temp.add(0, new Figure(0, 0, 1));
			
			rooms.add(i, temp);
		}
		
		// Initialize Logic and Graphics, set contentPane to JPanel returned by GameDrawer
		logic.init(rooms);
		gameWindow.setContentPane(graphics.init(rooms, logic));
	}
}