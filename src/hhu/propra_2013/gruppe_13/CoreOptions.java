package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CoreOptions{
	
	// Initializer method for the menu
	static JPanel showOptions(JFrame gameWindow) {
		// Create a new Background with a specified color (Black at the moment)
		JPanel menu = new JPanel();
		menu.setSize(gameWindow.getContentPane().getSize());
		menu.setBackground(new Color(0, 0, 0));

		
		//Create Buttons to start the game or end the program
		// TODO: implement cool new shit for the game
		JButton difficultyEasy 	= new JButton("I'm a pussy!");
		JButton difficultyMed	= new JButton("I want to win!");
		JButton difficultyHard	= new JButton("Prepare to die!");
		JButton backToMenu		= new JButton("Menu");
		JCheckBoxMenuItem fullscreen = new JCheckBoxMenuItem("Fullscreen");
		
		
		
		// implement action listeners to start and end the game
		difficultyEasy.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				ProPra.setMode(1);
			}
		});
		
		
		difficultyMed.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				ProPra.setMode(2);
			}
		});
		
		difficultyHard.addActionListener(new ActionListener() {
			
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				ProPra.setMode(3);
			}
		});
		
		fullscreen.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				ProPra.setFullscreen(true);
			}
		});
		
		backToMenu.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initMenu();
			}
		});
		
		// implement the layout manager
		menu.setLayout(new GridBagLayout());
		
		GridBagConstraints cButtons = new GridBagConstraints();
		cButtons.gridheight = 3;
		cButtons.gridwidth	= 3;
		cButtons.gridx = 1;
		cButtons.gridy = 0;
		menu.add(difficultyEasy, cButtons);
		
		cButtons.insets = new Insets(200, 0, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 1;
		menu.add(difficultyMed, cButtons);
		
		cButtons.insets = new Insets(300 , 0 , 0 ,0 );
		cButtons.gridx = 1;
		cButtons.gridy = 2;
		menu.add(difficultyHard, cButtons);
		
		cButtons.insets = new Insets(400 , 0 , 0 ,0 );
		cButtons.gridx = 1;
		cButtons.gridy = 3;
		menu.add(fullscreen, cButtons);
		
		cButtons.insets = new Insets(500 , 0 , 0 ,0 );
		cButtons.gridx = 1;
		cButtons.gridy = 3;
		menu.add(backToMenu, cButtons);
		
		return menu;
	}
}
