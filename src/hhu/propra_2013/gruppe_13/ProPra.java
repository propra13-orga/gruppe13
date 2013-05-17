package hhu.propra_2013.gruppe_13;

import javax.swing.JFrame;

public class ProPra {
	// the entire game will run here
	static JFrame gameWindow;
	
	// initializer method
	static void initGame() {
		System.out.println("Initiate game");
		O_Game game = new O_Game(gameWindow);
		game.start();
	}
	
	// method for a lost game
	static void blueScreen(){
		gameWindow.setContentPane(Maria.showBlueScreen(gameWindow));
	}
	
	// method for a won game
	static void win(){
		gameWindow.setContentPane(Wegi.showWinScreen(gameWindow));
	}
	
	// method for initiating/reinitiating the menu
	static void initMenu() {
		gameWindow.setContentPane(Menu.showMenu(gameWindow));
		gameWindow.setVisible(true);
	}
	/*-----------------------------------------------------------------------------------------------*/
	public static void main(String[] args) {
		// Initiate a new window to run the game in, default parameters are 800x600 and title "Propra 2013"
		gameWindow = new JFrame();
		gameWindow.setSize(800, 600);
		gameWindow.setTitle("Propra 2013");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initMenu();
	}
}