package hhu.propra_2013.gruppe_13;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

class GameDrawer implements Runnable {
	
	// List of all Objects within the game, Jpanel and the number of rooms
	private ArrayList<ArrayList<GameObjects>> rooms;
	private JPanel game;
	private int room;
	
	// Initiate current objects variables, returns constructed JPanel
	JPanel init(ArrayList<ArrayList<GameObjects>> objectsInit, Logic inLogic) {
		
		rooms = objectsInit;
		
		// Build a new panel, override paint method
		game = new JPanel() {
			// Serial-ID in order to appease Eclipse
			private static final long serialVersionUID = 1L;

			// Actual paint method, is great for painting stuff... and cookies
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				
				// Iterate over all objects and call draw method
				ArrayList<GameObjects> list = rooms.get(room);
				for(GameObjects  toDraw : list) {
					toDraw.draw(g2d);
				}
				
			}
		};
		
		// add KeyListener with appropriate logic object to the panel
		game.addKeyListener(new Game_IO(inLogic));
		return game;
	}
	
	@Override // Override Thread method run, this will implement the game loop
	public void run() {
		// game loop, TODO: repaint on screen synchronization
		while (true) {
		}
	}
}
