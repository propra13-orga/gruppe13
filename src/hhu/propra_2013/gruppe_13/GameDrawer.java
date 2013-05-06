package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class GameDrawer implements Runnable {
	
	// List of all Objects within the game, JPanel and the number of locations
	private ArrayList<ArrayList<GameObjects>> rooms;
	private JPanel game;
	private final JFrame gameWindow;
	private int location;
	//private int height;
	
	private final Image background = Toolkit.getDefaultToolkit().getImage("Layout.jpg");
	
	GameDrawer(ArrayList<ArrayList<GameObjects>> objectsInit, JFrame inFrame) {
		rooms = objectsInit;
		gameWindow = inFrame;
		location = 0;
	}
	
	// Initiate current objects variables, returns constructed JPanel
	JPanel init(Logic inLogic) {	
		// Build a new panel, override paint method
		game = new JPanel() {
			// Serial-ID in order to appease Eclipse
			private static final long serialVersionUID = 1L;
			private int xOffset;
			private int yOffset;
			private int height;
			
			// Actual paint method, is great for painting stuff... and cookies
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g; //strategy.getDrawGraphics();
				super.paintComponent(g2d);
				
				this.setBackground(Color.BLACK);
				
				height 	= gameWindow.getContentPane().getHeight();
				xOffset = (int)(0.5*(gameWindow.getContentPane().getWidth()-height*4/3));
				yOffset = (int)(height/15);
				g.drawImage(background, xOffset, 0, height*4/3, height, this);
				g.drawRect(xOffset+height/15, yOffset, height*4/3-(2*height/15), height*9/12-yOffset);
				
				// Iterate over all objects and call draw method
				ArrayList<GameObjects> list = rooms.get(location);
				for(GameObjects toDraw : list) {
					toDraw.draw(g2d, xOffset, yOffset, height);
				}
			}
		};

		// add KeyListener with appropriate logic object to the panel
		/*height = gameWindow.getContentPane().getHeight();
		game.setSize(height*4/3, height);*/
		game.setSize(gameWindow.getContentPane().getSize());
		return game;
	}
	
	// remove a drawable object, thus not every enemy and wall needs to be called if it has been destroyed
	void removeDrawableObject (GameObjects toRemove) {
		rooms.get(location).remove(toRemove);
	}
	
	// Tell the draw methods which location to draw
	void setRoom(int inlocation) {
		location = inlocation;
	}
	
	@Override // Override Thread method run, this will implement the game loop
	public void run() {
		long 	time;
		long 	temp;
		
		// game loop, TODO: repaint on screen synchronization (not sure if this is possible with our library)
		while (true) {
			// get current system time, this will determine fps
			time = System.currentTimeMillis();
			
			// Repaint the game and wait
			game.repaint();
			
			//game.setSize(gameWindow.getContentPane().getSize());
			
			try {
				// tries to set the draw method at 62.5fps
				if((temp = System.currentTimeMillis()-time) < 16)	
					Thread.sleep(16-temp);
				else
					game.repaint();
				
			} catch (InterruptedException e) {
				System.err.println("Graphics Thread interrupted, continuing execution. ");
			}
			
			//System.err.println(((double)(System.currentTimeMillis()-time)));

		}
	}
}
