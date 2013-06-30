package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuMain{
	
	// Initializer method for the menu
	static JPanel showMenu(JFrame gameWindow) {
		// Create a new Background with a specified color (Black at the moment)
		JPanel menu = new JPanel();
		menu.setSize(gameWindow.getContentPane().getSize());
		menu.setBackground(new Color(0, 0, 0));

		
		//Create Buttons to start the game or end the program
		// TODO: implement cool new shit for the game
		JButton endProgram 	= new JButton("I'm out of here!");
		JButton startGame	= new JButton("Single Player");
		JButton options		= new JButton("Options");
		JButton multi		= new JButton("Multiplayer");
		JButton edit		= new JButton("Editor");
		
		
		// implement action listeners to start and end the game
		startGame.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initStartup();
			}
		});
		
		
		endProgram.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		options.addActionListener(new ActionListener() {
			
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initOptions();
			}
		});
		
		multi.addActionListener(new ActionListener() {
			
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initMulti();
			}
		});
		
		edit.addActionListener(new ActionListener() {
			
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				//TODO:have a room builder
			}
		});
		
		JPanel dummy = new JPanel();
		dummy.setBackground(menu.getBackground());
		dummy.setVisible(true);
		
		// implement the layout manager
		menu.setLayout(new GridBagLayout());
		
		GridBagConstraints cButtons = new GridBagConstraints();
		cButtons.weightx = 1;
		cButtons.weighty = 1;
		
		cButtons.gridx = 1;
		cButtons.gridy = 0;
		cButtons.insets = new Insets(0, 0, 100, 0);
		menu.add(dummy, cButtons);
		
		dummy = new JPanel();
		dummy.setBackground(menu.getBackground());
		dummy.setVisible(true);
		
		cButtons.gridy = 5;
		cButtons.insets = new Insets(100, 0, 0, 0);
		menu.add(dummy, cButtons);
		
		cButtons.gridy = 1;
		cButtons.insets = new Insets(0, 0, 0, 0);
		menu.add(startGame, cButtons);
		
		cButtons.gridy = 4;
		menu.add(endProgram, cButtons);
		
		cButtons.gridy = 2;
		menu.add(multi, cButtons);
		
		cButtons.gridy = 3;
		menu.add(options, cButtons);
		
		return menu;
	}
}
