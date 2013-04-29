package hhu.propra_2013.gruppe_13;

import java.awt.*;
import javax.swing.*;

public class ProPra {

	protected static JFrame gameWindow;

	public static void main(String[] args) {
		/* TODO: Zum testen wärs nicht schlecht wählen zu können ob man das Menü oder das Spiel entwickeln möchte. 
		 * Somit ist eine bessere Kapselung während der Entwicklung möglich. */
		if (args.length < 1) {
			System.out.println("Bitte Parameter für Menü (1) oder Spiel (2) eingeben. System exit!");
			System.exit(0);
		} else if (Integer.parseInt(args[0]) == 1) {
			Menu.init();
		} else if (Integer.parseInt(args[0]) == 0) {
			O_Game.init();
		}
		
		// Initiate a new window to run the game in, default parameters are 800x600 ant title "Propra 2013"
		gameWindow = new JFrame();
		gameWindow.setSize(800, 600);
		gameWindow.setTitle("Propra 2013");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);
	}
	
	// setter method for the JPanel
	/*protected static void setPanel(JPanel inPanel) {
		gamePanel = inPanel;
	}*/

}