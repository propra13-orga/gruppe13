package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Menüklasse für das 'Spielmenü'
 * Hier kann der Spieler noch den Schwierigkeitsgrad festlegen bevor er das Spiel startet
 * @author Gruppe13
 *
 */


public class MenuStartup {
	
//	private static Icon easyImage;
//	private static Icon medImage;
//	private static Icon hardImage;
	
	// Initializer method for the menu
		static JPanel showStartup(JFrame gameWindow) {
//			easyImage = (Icon) Toolkit.getDefaultToolkit().getImage("easy.png");
//			medImage = (Icon) Toolkit.getDefaultToolkit().getImage("med.png");
//			hardImage = (Icon) Toolkit.getDefaultToolkit().getImage("hard.jpg");
			// Create a new Background with a specified color (Black at the moment)
			JPanel startup = new JPanel();
			startup.setSize(gameWindow.getContentPane().getSize());
			startup.setBackground(new Color(0, 0, 0));

			final JLabel diffPic = new JLabel();
			
			JRadioButton difficultyEasy 	= new JRadioButton("I'm a pussy!");
			JRadioButton difficultyMed		= new JRadioButton("I want to win!");
			JRadioButton difficultyHard		= new JRadioButton("Prepare to die!");
			difficultyMed.setSelected(true);
			ButtonGroup difficulty			= new ButtonGroup();
			
			difficulty.add(difficultyEasy);
			difficulty.add(difficultyMed);
			difficulty.add(difficultyHard);
			
			difficultyEasy.setBackground(startup.getBackground());
			difficultyEasy.setForeground(Color.white);
			
			difficultyMed.setBackground(startup.getBackground());
			difficultyMed.setForeground(Color.white);
			
			difficultyHard.setBackground(startup.getBackground());
			difficultyHard.setForeground(Color.white);
			//Create Buttons to start the game or end the program
			// TODO: implement cool new shit for the game
			JButton startGame	= new JButton("May the coding begin...");
			JButton back		= new JButton("Back to main menu");
			
			
			// implement action listeners to start set the difficulty
			difficultyEasy.addActionListener(new ActionListener() {
				
				@Override	// initiate the game
				public void actionPerformed(ActionEvent arg0) {
					ProPra.setMode(1);
//					diffPic.setIcon(easyImage);
				}
			});
			
			
			difficultyMed.addActionListener(new ActionListener() {
				
				@Override	// terminate the program
				public void actionPerformed(ActionEvent e) {
					ProPra.setMode(2);
//					diffPic.setIcon(medImage);
				}
			});
			
			difficultyHard.addActionListener(new ActionListener() {
				
				@Override	// initiate the options
				public void actionPerformed(ActionEvent arg0) {
					ProPra.setMode(3);
//					diffPic.setIcon(hardImage);
				}
			});
			
			// implement action listeners to start and end the game
			startGame.addActionListener(new ActionListener() {
				
				@Override	// initiate the game
				public void actionPerformed(ActionEvent arg0) {
					ProPra.initGame();
				}
			});
			
			
			
			back.addActionListener(new ActionListener() {
				
				@Override	// initiate the options
				public void actionPerformed(ActionEvent arg0) {
					ProPra.initMenu();
				}
			});
			
			startup.setLayout(new GridBagLayout());
			
			GridBagConstraints cButtons = new GridBagConstraints();
			cButtons.insets = new Insets(50, 0, 0, 0);
			cButtons.gridheight = 1;
			cButtons.gridwidth	= 1;
			cButtons.gridx = 0;
			cButtons.gridy = 3;
			startup.add(difficultyEasy, cButtons);
			
//			cButtons.insets = new Insets(0, 100, 0, 0);
			cButtons.gridx = 0;
			cButtons.gridy = 4;
			startup.add(difficultyMed, cButtons);
			
//			cButtons.insets = new Insets(0, 0, 0, 0);
			cButtons.gridx = 0;
			cButtons.gridy = 5;
			startup.add(difficultyHard, cButtons);
			
			cButtons.insets = new Insets(0, 200, 0, 0);
			cButtons.gridx = 2;
			cButtons.gridy = 0;
			startup.add(startGame, cButtons);
			
//			cButtons.insets = new Insets(0 , 0 , 0 ,0 );
			cButtons.gridx = 2;
			cButtons.gridy = 11;
			startup.add(back, cButtons);
			
			cButtons.gridx = 1;
			cButtons.gridy = 3;
			cButtons.gridwidth = 3;
			cButtons.gridheight= 3;
			startup.add(diffPic, cButtons);
			return startup;
		}
	}

