package hhu.propra_2013.gruppe_13;

import java.awt.KeyboardFocusManager;
import java.util.ArrayList;

import javax.swing.JFrame;

class O_Game {
	// Frame, graphic, logic and a figure for the actual game
	JFrame 		gameWindow;
	Logic 		logic;
	GameDrawer 	graphics;
	Figure 		figure;
	
	// Build two lists, the graphics component will also receive the figure, which has a special function in the logic class
	ArrayList<ArrayList<GameObjects>> graphicsRooms;
	ArrayList<ArrayList<GameObjects>> logicsRooms;
	
	// Initialize method for the actual game
	O_Game(JFrame inFrame) {
		// Initiate object variables
		gameWindow 		= inFrame;
		graphicsRooms 	= new ArrayList<ArrayList<GameObjects>>();
		figure 			= new Figure(0, 0, 1, gameWindow);
		
		// iterate over all objects and rooms within the level, all objects run within [0...800)x[0...600)
		// TODO: make that shit better!!, implement the current level
		for (int i=0; i<3; i++) {
			ArrayList<GameObjects> temp = new ArrayList<GameObjects>();
			
			// TODO: Build cool shit for reading levels
			logicsRooms.add(i, temp);
			graphicsRooms.add(i, temp);
		}
		
		for (ArrayList<GameObjects> array: graphicsRooms) {
			array.add(figure);
		}

		// Initialize Logic and Graphics
		graphics 	= new GameDrawer(graphicsRooms, gameWindow);
		logic 		= new Logic(logicsRooms, figure, this);
		
		// set contentPane to JPanel returned by GameDrawer, set GameIO as keyboard manager
		gameWindow.setContentPane(graphics.init(logic));
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new Game_IO(logic));
	}
	
	// Setter method so the graphic will know which room to paint
	void setRoom(int inRoom) {
		graphics.setRoom(inRoom);
	}
	
	void start() {
		// Build two new threads, one for logic and one for graphics
		Thread logicThread = new Thread(logic);
		Thread graphicThread = new Thread(graphics);

		logicThread.start();
		graphicThread.start();
	}
}