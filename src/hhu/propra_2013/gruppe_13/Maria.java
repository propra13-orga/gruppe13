package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maria{
	
	private static JFrame gameWindow;
	
	// Initializer method for the bluescreen
	protected static void showBlueScreen(JFrame initWindow) {
		// set the current working screen
		gameWindow = initWindow;
		
		
		// Create a new Background with a specified color (Black at the moment)
		Container content = gameWindow.getContentPane();
		content.setBackground(Color.CYAN);
		JPanel blueScreen = new JPanel();
		
		blueScreen.addKeyListener(new KeyListener() {
		

			@Override
			public void keyPressed(KeyEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}
		});
		blueScreen.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				Menu.showMenu(gameWindow);
				
			}
			
		});
		
		gameWindow.setVisible(true);
	}
}
