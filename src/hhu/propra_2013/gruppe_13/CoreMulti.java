package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CoreMulti {

	static JPanel showMulti(JFrame gameWindow) {
		// Create a new Background with a specified color (Black at the moment)
		JPanel multi = new JPanel();
		multi.setSize(gameWindow.getContentPane().getSize());
		multi.setBackground(new Color(0, 0, 0));
		
		JButton backToMenu			 = new JButton("Menu");
		JButton host			 = new JButton("Host");
		JButton client			 = new JButton("Client");
		
		backToMenu.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initMenu();
			}
		});
		
		host.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				//TODO:make it run from here
			}
		});

		client.addActionListener(new ActionListener() {
	
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				//TODO:need client
			}
		});
		
		multi.setLayout(new GridBagLayout());
		
		GridBagConstraints cButtons = new GridBagConstraints();
		
		cButtons.gridx = 4;
		cButtons.gridy = 4;
		multi.add(backToMenu, cButtons);
		
		cButtons.gridx = 0;
		cButtons.gridy = 0;
		multi.add(host, cButtons);
		
		cButtons.gridx = 0;
		cButtons.gridy = 1;
		multi.add(client, cButtons);
		
		return multi;
	}
}
