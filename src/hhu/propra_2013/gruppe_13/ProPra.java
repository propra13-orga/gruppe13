package hhu.propra_2013.gruppe_13;

import javax.swing.JFrame;

	/**
	 * Hauptklasse
	 * Von hier aus werden die Menüs und das OGame initialisiert
	 * @author Gruppe13
	 *
	 */


public class ProPra {
	// the entire game will run here
	static JFrame 	gameWindow;
	static int 		mode = 2;
	static boolean 	windowMode;
	
	/**
	 * Startet das eigentliche Spiel
	 * erzeugt ein O_Game Objekt das dann das weitere Spiel regelt
	 * @see CoreO_Game	
	 */
	
	// initializer method
	static void initGame() {
		System.out.println("Initiate game");
		CoreO_Game game = new CoreO_Game(gameWindow, mode);
		game.start();
	}
	
	/**
	 * Startet das Spielmenü um vor dem Spielstart noch Einstellungen vorzunehmen
	 * @see MenuStartup
	 */
	
	static void initStartup() {
		gameWindow.setContentPane(MenuStartup.showStartup(gameWindow));
	}
	
	/**
	 * Methode zum festsetzen des Schwierigkeitsgrads
	 * @param inMode Der Schwierigkeitsgrad
	 */
	
	static void setMode(int inMode){
		mode = inMode;
	}
	/**
	 * Zeigt den Loss-Screen an
	 * @see ScreenMaria
	 */
	
	static void blueScreen(){
		gameWindow.setContentPane(ScreenMaria.showBlueScreen(gameWindow));
	}
	
	/**
	 * Zeigt den Winscreen an
	 * @see ScreenWegi
	 */
	
	static void win(){
		gameWindow.setContentPane(ScreenWegi.showWinScreen(gameWindow));
	}
	
	/**
	 * Ruft das Hauptmenü auf
	 * @see MenuMain
	 */
	
	// method for initiating/reinitiating the menu
	static void initMenu() {
		gameWindow.setContentPane(MenuMain.showMenu(gameWindow));
		gameWindow.setVisible(true);
	}
	
	/**
	 * Ruft das Optionsmenü auf
	 * @see MenuOptions
	 */
	
	//method for showing the options
	static void initOptions() {
		gameWindow.setContentPane(MenuOptions.showOptions(gameWindow));
		gameWindow.setVisible(true);
	}
	
	/**
	 * Ruft das Multiplayer Menü auf
	 * @see MenuMultiplayer
	 */
	
	static void initMulti() {
		gameWindow.setContentPane(MenuMultiplayer.showMulti(gameWindow));
		gameWindow.setVisible(true);
	}
	
	/**
	 * Zeigt bei Programmstart ein Intro vor dem Hauptmenü an
	 * @see ScreenIntro
	 */
	
	//Method for showing an intro before the menu
	static void initIntro(){
		gameWindow.setContentPane(ScreenIntro.initIntro(gameWindow));
		gameWindow.setVisible(true);

		ScreenIntro.showIntro();
	}
	
//	void initMapEditor(){
//		gameWindow.setContentPane(CoreMapEditor.showMapCreator(gameWindow));
//	}
	
	static void errorOutput (int error, Exception e) {
		System.err.println("Brougth error to ProPra: "+error);
		e.printStackTrace();		
	}

	static void setFullscreen(boolean toggle) {
		gameWindow.setUndecorated(toggle);
	}
	
	static JFrame getGameWindow() {
		return gameWindow;
	}
	
	/*-----------------------------------------------------------------------------------------------*/
	/**
	 * Main-Methode
	 * Generiert ein Fenster und ruft initIntro auf
	 * @param args	Default Parameter
	 */
	
	public static void main(String[] args) {
		// Initiate a new window to run the game in, default parameters are 800x600 and title "Propra 2013"
		gameWindow = new JFrame();
		gameWindow.setSize(800, 600);
		gameWindow.setTitle("Propra 2013");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setUndecorated(windowMode);
		
		initIntro();
		
		//initMenu();
	}
}