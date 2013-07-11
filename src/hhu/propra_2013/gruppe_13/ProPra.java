package hhu.propra_2013.gruppe_13;

import javax.swing.JFrame;

public class ProPra {
	// the entire game will run here
	static JFrame 	gameWindow;
	static int 		mode = 2;
	static boolean 	windowMode;
	
	// initializer method
	static void initGame() {
		System.out.println("Initiate game");
		CoreO_Game game = new CoreO_Game(gameWindow, mode);
		game.start();
	}
	
	static void initStartup() {
		gameWindow.setContentPane(MenuStartup.showStartup(gameWindow));
	}
	
	static void setMode(int inMode){
		mode = inMode;
	}
	
	// method for a lost game
	static void blueScreen(){
		gameWindow.setContentPane(ScreenMaria.showBlueScreen(gameWindow));
	}
	
	// method for a won game
	static void win(){
		gameWindow.setContentPane(ScreenWegi.showWinScreen(gameWindow));
	}
	
	// method for initiating/reinitiating the menu
	static void initMenu() {
		gameWindow.setContentPane(MenuMain.showMenu(gameWindow));
		gameWindow.setVisible(true);
	}
	
	//method for showing the options
	static void initOptions() {
		gameWindow.setContentPane(MenuOptions.showOptions(gameWindow));
		gameWindow.setVisible(true);
	}
	
	static void initMulti() {
		gameWindow.setContentPane(MenuMultiplayer.showMulti(gameWindow));
		gameWindow.setVisible(true);
	}
	
	static void errorOutput (int error, Exception e) {
		
	}

	static void setFullscreen(boolean toggle) {
		gameWindow.setUndecorated(toggle);
	}
	
	static JFrame getGameWindow() {
		return gameWindow;
	}
	
	/*-----------------------------------------------------------------------------------------------*/
	public static void main(String[] args) {
		// Initiate a new window to run the game in, default parameters are 800x600 and title "Propra 2013"
		gameWindow = new JFrame();
		gameWindow.setSize(800, 600);
		gameWindow.setTitle("Propra 2013");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setUndecorated(windowMode);
		
		initMenu();
	}
}