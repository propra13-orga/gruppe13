package hhu.propra_2013.gruppe_13;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class GameDrawer implements Runnable {
	
	// List of all Objects within the game
	private ArrayList<ArrayList<GameObjects>> rooms;
	private JPanel game;
	private int room;
	
	// Initiate current objects variables
	void init(ArrayList<ArrayList<GameObjects>> objectsInit) {
		rooms = objectsInit;
		
		// Build a new panel, override paint method
		game = new JPanel() {
			private static final long serialVersionUID = 1L;
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					
					// Iterate over all objects and call draw method
					ArrayList<GameObjects> list = rooms.get(room);
					for(GameObjects  toDraw : list) {
						toDraw.draw(g);
					}
					
				}
		};
	}
	
	@Override // Override Thread method run, this will implement the game loop
	public void run() {
		// game loop, TODO: repaint on screen synchronization
		while (true) {
			
		}
	}
}
