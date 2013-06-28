package hhu.propra_2013.gruppe_13;

import java.awt.KeyboardFocusManager;
import javax.swing.JFrame;

class CoreO_Game {
	// Frame, graphic, logic and a figure for the actual game
	JFrame 			gameWindow;
	CoreLogic 		logic;
	CoreGameDrawer 	graphics;
	Figure 			figure;
	MISCStatusBar	statusBar;
	
	// Build two lists, the graphics component will also receive the figure, which has a special function in the logic class
	CoreLevel		level;
	
	// Initialize method for the actual game
	CoreO_Game(JFrame inFrame , int mode) {
		// Initiate object variables
		gameWindow 		= inFrame;
		figure 			= new Figure(10.5, 6.5, 1, 1, 0);
		
		// Initialize Logic and Graphics
		logic 		= new CoreLogic(figure, this, mode);
		level		= logic.getLevel();
		statusBar	= new MISCStatusBar(figure, mode);
		graphics 	= new CoreGameDrawer(level, gameWindow, statusBar);
	
		// set contentPane to JPanel returned by GameDrawer, set GameIO as keyboard manager
		gameWindow.setContentPane(graphics.init(logic));
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new CoreGame_IO(logic));
	}
	
	// Setter method so the graphic will know which room to paint
	void setRoom(CoreRoom inRoom) {
		graphics.setRoom(inRoom);
	}
	
	void end(boolean win ) {
		logic.setGameRunning(false);
		graphics.setGameRunning(false);
		
		if (win == true)	ProPra.win();
		else				ProPra.blueScreen();		
	}
	
	void start() {
		// Build two new threads, one for logic and one for graphics
		Thread logicThread = new Thread(logic);
		Thread graphicThread = new Thread(graphics);

		logicThread.start();
		graphicThread.start();
		System.out.println("game is running");
	}
}