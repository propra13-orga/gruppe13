package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ScreenWegi {
	
	private static	Image win;
	private static 	JFrame gameWindow;
	
	// Initializer method for the victoryScreen
	static JPanel showWinScreen(JFrame inWindow) {
		// set the current working screen
		win = Toolkit.getDefaultToolkit().getImage("victory.png");
		gameWindow = inWindow;
		gameWindow.setBackground(new Color(0, 0, 0));
		
		// Create a new Background with a specified color (Black at the moment)
		JPanel victoryScreen = new JPanel() {
			
			private static final long serialVersionUID = 42;
			private int height;
			private int width;
			private int x0;
			private int y0;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponents(g);
								
				// height und width speichern die innere Fenstergröße, x0,y0 sind für die Bildposition
				height 	= gameWindow.getContentPane().getHeight();
				width	= gameWindow.getContentPane().getWidth();
				
				// Abhängig von der Höhe und Breite des Fensters muss das Spielfeld entsprechend angepasst werden
				x0 = (int)Math.round(0.5*(width-height*4/3.));
				
				if (x0 < 0) {							// Fenster ist höher als breit
					x0 = 0;
					y0 = (int)Math.round(0.5*(height-3/4.*width));
					
					// male den Hintergrund
					g.drawImage(win, x0, y0, width, (int)(width*3/4.), this);
				} else {								// Fenster ist breiter als hoch
					y0 = 0;

					// male den Hintergrund
					g.drawImage(win, x0, y0, (int)(height*4/3.), height, this);
				}
			}
		};
		
		// Fill the entire Frame
		victoryScreen.setSize(gameWindow.getContentPane().getSize());
		
		//Das Panel ruft ein Menu auf, sobald Maus oder Tastatur benutzt werden
		victoryScreen.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				ProPra.initMenu();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {				
			}
		});
		
		
		victoryScreen.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				ProPra.initMenu();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
		
		return victoryScreen;
	}
}
