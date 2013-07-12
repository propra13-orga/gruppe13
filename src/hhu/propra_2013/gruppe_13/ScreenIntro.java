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

class ScreenIntro {
	
	private static Image intro;
	private static 	JFrame gameWindow;
	
	// Initializer method for the bluescreen
	static JPanel showIntro(JFrame inWindow) {
		// set the current working screen
		
		gameWindow = inWindow;
		gameWindow.setBackground(new Color(0, 0, 0));
		
		// Create a new Background with a specified color (Black at the moment)
		JPanel introPanel = new JPanel() {
			
			private static final long serialVersionUID = 42;
			private int height;
			private int width;
			private int x0;
			private int y0;
			private int temp;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponents(g);
								
				// height und width speichern die innere Fenstergröße, x0,y0 sind für die Bildposition
				height 	= gameWindow.getContentPane().getHeight();
				width	= gameWindow.getContentPane().getWidth();
				
				// Abhängig von der Höhe und Breite des Fensters muss das Spielfeld entsprechend angepasst werden
				x0 = (int)Math.round(0.5*(width-height*4/3.));
				
				for ( int i = 1; i <= 160; i++){
					intro = Toolkit.getDefaultToolkit().getImage("intro/intro"+i+".png");
					
					
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

					System.out.println("intro/intro"+i+".png");
					if (x0 < 0) {							// Fenster ist höher als breit
						x0 = 0;
						y0 = (int)Math.round(0.5*(height-3/4.*width));

						// male den Hintergrund
						g.drawImage(intro, x0, y0, width, (int)(width*3/4.), this);
						
					} else {								// Fenster ist breiter als hoch
						y0 = 0;
						
						// male den Hintergrund
						g.drawImage(intro, x0, y0, (int)(height*4/3.), height, this);
						
					}
					
				
				}//Ende for Schleife
				ProPra.initMenu();
			}
		};
		
		// Fill the entire Frame
		introPanel.setSize(gameWindow.getContentPane().getSize());
		
		//Das Panel ruft ein Menu auf, sobald Maus oder Tastatur benutzt werden
		introPanel.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				ProPra.initMenu();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				ProPra.initMenu();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {				
			}
		});
		
		
		introPanel.addMouseListener(new MouseListener(){

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
		
		return introPanel;
	}
}
