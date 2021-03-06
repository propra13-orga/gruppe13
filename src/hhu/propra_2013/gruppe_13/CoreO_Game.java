package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import javax.swing.JFrame;


/**
 * Zentrale Spiel Klasse
 * Hier werden Logikund GameDrawer, sowie die Figur und die Statusbar initialisiert
 * @author Gruppe13
 *
 */


class CoreO_Game {
	// Frame, graphic, logic and a figure for the actual game
	private JFrame 			gameWindow;
	private CoreLogic 		logic;
	private CoreGameDrawer 	graphics;
	private Figure 			figure;
	private MiscStatusBar	statusBar;
	
	// Build two lists, the graphics component will also receive the figure, which has a special function in the logic class
	CoreLevel		level;
	
	/**
	 * Konstruktor
	 * Generiert Figur, Logik (die dann ein Level generiert), Statusbar und Grafik
	 * Erzeugt außerdem KeyListener und die IO
	 * @param inFrame	Das Fenster in dem das Spiel läuft
	 * @param mode		Der Schwierigkeitsgrad, wird vom Menü an die Spielelemente weitergereicht
	 */
	
	// Initialize method for the actual game
	CoreO_Game (JFrame inFrame , int mode) {
		// Initiate object variables
		gameWindow 	= inFrame;
		figure 		= new Figure(10.5, 6.5, 1, 1, 0, Color.BLUE, CoreGameObjects.allIds++);
		
		// Initialize Logic and Graphics
		logic 		= new CoreLogic(figure, this, mode);
		level		= logic.getLevel();
		statusBar	= new MiscStatusBar(figure);
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
	
	/**
	 * Beendet das Spiel, wird von der Logik aufgerufen falls man durch die letzte Bosstür geht oder stirbt
	 * @param win	Info ob der Spieler gewonnen oder verloren hat
	 */
	
	void end(boolean win) {
		logic.setGameRunning(false);
		graphics.setGameRunning(false);
		
		if (win == true)	ProPra.win();
		else				ProPra.blueScreen();		
	}
	
	/**
	 * Erzeugt Threads für Logik und Grafik und startet diese
	 */
	
	void start() {
		// Build two new threads, one for logic and one for graphics
		Thread logicThread = new Thread(logic);
		Thread graphicThread = new Thread(graphics);

		logicThread.start();
		graphicThread.start();
		System.out.println("game is running");
	}
}