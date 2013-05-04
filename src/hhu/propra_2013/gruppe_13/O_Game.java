package hhu.propra_2013.gruppe_13;

import java.util.ArrayList;

import javax.swing.JFrame;

class O_Game {
	// Frame, graphic, logic and graphic objects for the actual game
	JFrame 		gameWindow;
	Logic 		logic;
	GameDrawer 	graphics;
	ArrayList<ArrayList<GameObjects>> rooms;
	Figure 		figure;
	
	// Initialize method for the actual game
	void init(JFrame inFrame) {
		// Initiate object variables
		gameWindow 	= inFrame;
		logic 		= new Logic();
		graphics 	= new GameDrawer();
		rooms 		= new ArrayList<ArrayList<GameObjects>>();
		figure 		= new Figure(0, 0, 1, gameWindow);
		
		// iterate over all objects and rooms within the level, all objects run within [0...800)x[0...600)
		// TODO: make that shit better!!
		for (int i=0; i<3; i++) {
			ArrayList<GameObjects> temp = new ArrayList<GameObjects>();
			
			// TODO: Build cool shit for reading levels
			rooms.add(i, temp);
		}

		// Initialize Logic and Graphics, set contentPane to JPanel returned by GameDrawer
		logic.init(rooms, figure);
		
		for (ArrayList<GameObjects> array: rooms) {
			array.add(figure);
		}
		
		gameWindow.setContentPane(graphics.init(rooms, logic, gameWindow));
	}
	
	void start() {
		// Build two new threads, one for logic and one for graphics
		Thread logicThread = new Thread(logic);
		Thread graphicThread = new Thread(graphics);

		logicThread.start();
		graphicThread.start();
	}
}