package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MenuProfil {

	// Initializer method for the menu
	static JPanel showOptions(JFrame gameWindow) {
		// Create a new Background with a specified color (Black at the moment)
		JPanel menu = new JPanel();
		menu.setSize(gameWindow.getContentPane().getSize());
		menu.setBackground(new Color(0, 0, 0));

		
		//Create Buttons to start the game or end the program
		// TODO: implement cool new shit for the game
		
//		JRadioButton difficultyEasy 	= new JRadioButton("I'm a pussy!");
//		JRadioButton difficultyMed		= new JRadioButton("I want to win!");
//		JRadioButton difficultyHard		= new JRadioButton("Prepare to die!");
		JButton backToMenu			 = new JButton("Menu");
		JCheckBoxMenuItem fullscreen = new JCheckBoxMenuItem("Fullscreen");
		JButton editKeys			 = new JButton("Key Settings");
//		ButtonGroup difficulty			= new ButtonGroup();
		
//		difficulty.add(difficultyEasy);
//		difficulty.add(difficultyMed);
//		difficulty.add(difficultyHard);
		
		
		// implement action listeners to start and end the game
//		difficultyEasy.addActionListener(new ActionListener() {
//			
//			@Override	// initiate the game
//			public void actionPerformed(ActionEvent arg0) {
//				ProPra.setMode(1);
//			}
//		});
//		
//		
//		difficultyMed.addActionListener(new ActionListener() {
//			
//			@Override	// terminate the program
//			public void actionPerformed(ActionEvent e) {
//				ProPra.setMode(2);
//			}
//		});
//		
//		difficultyHard.addActionListener(new ActionListener() {
//			
//			@Override	// initiate the options
//			public void actionPerformed(ActionEvent arg0) {
//				ProPra.setMode(3);
//			}
//		});
		
		fullscreen.addActionListener(new ActionListener() {
			
			@Override	//make decorations disappear for fullscreen
			public void actionPerformed(ActionEvent arg0) {
				ProPra.setFullscreen(true);
			}
		});
		
		editKeys.addActionListener(new ActionListener() {
			
			@Override	//open the key editor
			public void actionPerformed(ActionEvent arg0) {
				
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
//		cButtons.insets = new Insets(300, 0, 0, 500);
//		cButtons.gridheight = 5;
//		cButtons.gridwidth	= 5;
//		cButtons.gridx = 0;
//		cButtons.gridy = 3;
//		menu.add(difficultyEasy, cButtons);
//		
//		cButtons.insets = new Insets(400, 0, 0, 500);
//		cButtons.gridx = 0;
//		cButtons.gridy = 4;
//		menu.add(difficultyMed, cButtons);
//		
//		cButtons.insets = new Insets(500, 0, 0, 500);
//		cButtons.gridx = 0;
//		cButtons.gridy = 5;
//		menu.add(difficultyHard, cButtons);
		
		cButtons.insets = new Insets(0, 0, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 3;
		menu.add(fullscreen, cButtons);
		
		cButtons.gridx = 4;
		cButtons.gridy = 4;
		menu.add(backToMenu, cButtons);
		
		cButtons.gridx = 2;
		cButtons.gridy = 3;
		menu.add(editKeys , cButtons);
		
		return menu;
	}
}
