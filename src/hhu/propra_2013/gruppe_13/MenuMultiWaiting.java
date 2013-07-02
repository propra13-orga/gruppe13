package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class MenuMultiWaiting {

	// boolean to know whether this is a host or client
	private static boolean server;
	
	// the panel which will be the new content pane
	private static JPanel 	waitingArea;
	
	// lists for colors, stati and usernames of all clients
	private static ArrayList<Boolean> 	clientStati;
	private static ArrayList<Color>		clientColors;
	private static ArrayList<String>	usernames;
	
	// initiate two panels, one containing all users and another as a status bar
	private static JPanel 	users;
	private static JPanel 	statusBox;
	
	// method for showing a waiting room in case the user is the host
	static void showWaitingServer (JFrame gameWindow) {
		// mark this as server
		server = true;
		
		// first of all we build the JPanel needed to show the waiting area
		waitingArea = new JPanel(new GridBagLayout());

		showWaiting(gameWindow);
		
	}
	
	// method for showing the waiting room in case the user is a client
	static void showWaitingClient (JFrame gameWindow) {
		// first of all we build the JPanel needed to show the waiting area
		waitingArea = new JPanel(new GridBagLayout());
		
		showWaiting(gameWindow);
		
	}
	
	// the basic builder of the panel
	private static void showWaiting(JFrame gameWindow) {
//		waitingArea.setSize(gameWindow.getContentPane().getSize());
//		waitingArea.setBackground(Color.BLACK);
		
		// build two new buttons for the waiting area
		JButton begin 	= new JButton("Begin");
		JButton menu	= new JButton("Menu");
		
		// if we are the host, change the text of the first Button
		if (server) 
			begin.setText("Start Game");
		
		// build two new panels, one for listing all users and one for listing all relevant personal information
		JPanel uPanel = new MenuMultiWaiting().new UserPanel().getPanel();
		JPanel iFrame = new MenuMultiWaiting().new InfoFrame().getPanel();
		//NetChatPanel chat = new NetChatPanel();
		
		// this Panel will underly dynamic gridbag constraints
		GridBagConstraints layout = new GridBagConstraints();
		
		// add the user panel
		layout.gridheight 	= 3;
		layout.gridwidth	= 2;
		
		layout.gridx = 0;
		layout.gridy = 0;
		layout.insets = new Insets(30, 30, 30, 30);
		waitingArea.add(uPanel, layout);
		
		// add the information panel
		layout.gridheight	= 2;
		layout.gridx = 2;
		waitingArea.add(iFrame, layout);
		
		layout.gridheight	= 1;
		layout.gridwidth	= 1;
		layout.insets = new Insets(0, 0, 0, 0);
		
		// add the two buttons
		layout.gridy = 2;
		waitingArea.add(begin, layout);
		
		layout.gridx = 3;
		waitingArea.add(menu, layout);
		
		// last of all add the chat area
		/*
		 * layout.gridwith = 4;
		 * layout.gridx = 0;
		 * layout.gridy = 3;
		 * waitingArea.add(chat, layout);*/
		
		gameWindow.setContentPane(waitingArea);
		gameWindow.setVisible(true);
	}
	
	// initiate the actual game
	private static void initGame() {
		
	}
	
	// inner class for the panel containing all usernames, colors and stati
	private class UserPanel {
		JPanel userPanel;
		
		UserPanel() {
			userPanel = new JPanel();
		}
		
		JPanel getPanel() {
			return userPanel;
		}
		
	}
	
	// inner class for setting an infoframe containg IP addresses, ports and some such
	private class InfoFrame {
		JPanel infoPanel;
		
		InfoFrame() {
			infoPanel = new JPanel();
		}
		
		JPanel getPanel() {
			return infoPanel;
		}
	}
	
	// inner class to build a new thread which will constantly check all variables input by the server
	private class checkAll implements Runnable {

		@Override
		public void run() {

		}
	}
}
