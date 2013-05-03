package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu{
	
	private static JFrame gameWindow;
	
	// Initializer method for the menu
	protected static void showMenu(JFrame initWindow) {
		// set the current working menu
		gameWindow = initWindow;
		
		
		// Create a new Background with a specified color (Black at the moment)
		Container content = gameWindow.getContentPane();
		content.setBackground(Color.BLACK);
		
		//Create Buttons to start the game or end the program
		// TODO: implement cool new shit for the game
		JButton endProgram 	= new JButton("I'm out of here!");
		JButton startGame	= new JButton("May the coding begin...");
		
		
		// implement action listeners to start and end the game
		startGame.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initGame();
			}
		});
		
		
		endProgram.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// implement the layout manager
		content.setLayout(new GridBagLayout());
		
		GridBagConstraints cButtons = new GridBagConstraints();
		cButtons.gridheight = 2;
		cButtons.gridwidth	= 3;
		cButtons.gridx = 1;
		cButtons.gridy = 0;
		content.add(startGame, cButtons);
		
		cButtons.insets = new Insets(200, 0, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 1;
		content.add(endProgram, cButtons);
		
		gameWindow.setVisible(true);
	}
}
