package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class CoreOptions {

	/**
	 * @param args
	 */
	static JPanel showOptions(JFrame gameWindow) {
		
		
		JPanel options = new JPanel();
		options.setSize(gameWindow.getContentPane().getSize());
		options.setBackground(Color.GREEN);
		
		//implement buttons for settings(difficulty and fullscreen mode and stuff)
		ButtonGroup howHardIsIt		= new ButtonGroup();
		JRadioButton difficultyEasy 	= new JRadioButton("I'm too young to die!");
		JRadioButton difficultyNorm 	= new JRadioButton("This will work");
		JRadioButton difficultyHard 	= new JRadioButton("Nintendo-Hard");
		JCheckBox toggleFullscreen 	= new JCheckBox("Toggle Fullscreen");
		JButton backToMenu = new JButton("Back to Menu");
		
		difficultyNorm.setSelected(true);

		howHardIsIt.add(difficultyEasy);
		howHardIsIt.add(difficultyNorm);
		howHardIsIt.add(difficultyHard);
		
		options.setLayout(new GridBagLayout());
		
		GridBagConstraints cButtons = new GridBagConstraints();
		cButtons.gridheight = 10;
		cButtons.gridwidth	= 3;
		cButtons.gridx = 2;
		cButtons.gridy = 0;
		options.add(difficultyEasy, cButtons);
		
		cButtons.insets = new Insets(50, 0, 0, 0);
		cButtons.gridx = 2;
		cButtons.gridy = 1;
		options.add(difficultyNorm, cButtons);
		
		cButtons.insets = new Insets(100, 0, 0, 0);
		cButtons.gridx = 2;
		cButtons.gridy = 2;
		options.add(difficultyHard, cButtons);
		
		cButtons.insets = new Insets(150, 0, 0, 0);
		cButtons.gridx = 2;
		cButtons.gridy = 3;
		options.add(toggleFullscreen, cButtons);
		
		cButtons.insets = new Insets(50, 0, 0, 0);
		cButtons.gridx = 0;
		cButtons.gridy = 0;
		options.add(backToMenu, cButtons);
		
		if(toggleFullscreen.isSelected()){
			ProPra.setFullscreen(true);
		}
	
		difficultyEasy.addActionListener(new ActionListener() {
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				ProPra.setMode(1);
			}
		});
		
		difficultyNorm.addActionListener(new ActionListener() {
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				ProPra.setMode(2);
			}
		});
		
		difficultyHard.addActionListener(new ActionListener() {
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				ProPra.setMode(3);
			}
		});
		
		difficultyNorm.addActionListener(new ActionListener() {
			@Override	// initiate the options
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initMenu();
			}
		});
		
		return options;
	}
}
