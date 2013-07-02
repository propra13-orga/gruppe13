package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Dimension;
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
	private static boolean host;
	private static boolean running;
	
	// the panel which will be the new content pane
	private static JPanel 		waitingArea;
	private static JFrame		gameFrame;
	private static Dimension 	frameDimension;
	
	// lists for colors, stati and usernames of all clients
	private static ArrayList<Boolean> 	clientStati;
	private static ArrayList<Color>		clientColors;
	private static ArrayList<String>	usernames;
	
	// initiate two panels, one containing all users and another as a status bar
	private static JPanel 	uPanel;
	private static JPanel 	iFrame;
	
	// Client and server which are needed to show all connections and start the game
	private static NetClient client;
	private static NetServer server;
	
	// objects for the info box
	private static int 					port;
	private static String 				ip;
	private static ArrayList<String>	publicIPs;
	
	// button to begin the game, we might need to control whether it is supposed to be active
	private static JButton begin;
	
	// method for showing a waiting room in case the user is the host
	static void showWaitingServer (JFrame gameWindow, int playerNo, int mode, int inPort, NetClient inClient, NetServer inServer, ArrayList<String> listOfIPs) {
		// mark this as server
		host = true;

		// copy connection information
		port = inPort;
		publicIPs = listOfIPs;
		
		// copy object references to the running client and server
		client = inClient;
		server = inServer;
		
		showWaiting(gameWindow);
	}
	
	// method for showing the waiting room in case the user is a client
	static void showWaitingClient (JFrame gameWindow, NetClient inClient, String inIP, int inPort) {
		// copy object reference to the client, needed for communication with graphic output
		client = inClient;
		
		// connection information for info box
		ip = inIP;
		port = inPort;
		
		showWaiting(gameWindow);
	}
	
	// the basic builder of the panel
	private static void showWaiting(JFrame gameWindow) {
		// set the current frame which contains the entire program
		gameFrame = gameWindow;
		frameDimension = gameFrame.getContentPane().getSize();
		
		// get the needed lists from the provided client class
		clientColors 	= client.getColors();
		clientStati		= client.getStati();
		usernames		= client.getUsers();
		
		// first of all we build the JPanel needed to show the waiting area
		waitingArea = new JPanel(new GridBagLayout());
		waitingArea.setBackground(Color.black);
		waitingArea.setSize(gameFrame.getContentPane().getSize());
		
		// build two new buttons for the waiting area
		begin 			= new JButton("Begin");
		JButton menu	= new JButton("Menu");
		
		// if we are the host, change the text of the first Button
		if (host) 
			begin.setText("Start Game");
		
		// build two new panels, one for listing all users and one for listing all relevant personal information
		uPanel = new MenuMultiWaiting().new UserPanel().getPanel();
		iFrame = new MenuMultiWaiting().new InfoFrame().getPanel();
		//NetChatPanel chat = new NetChatPanel(gameFrame);
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// enable the Buttons to do something
		begin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		menu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				running = false;
				ProPra.initMenu();
			}
		});
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
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
		
		// build a thread to check all inputs and outputs to and from the server
		CheckAll check = new MenuMultiWaiting().new CheckAll();
		Thread thread = new Thread(check);
		thread.start();
		
		// set the actual content pane and show the panel
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
			userPanel.setBackground(Color.white);
			userPanel.setVisible(true);
			userPanel.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/2.-60), (int)(frameDimension.getHeight()*3/4.-60)));
		}
		
		JPanel getPanel() {
			return userPanel;
		}
		
	}
	
	// inner class for setting an info frame containing IP addresses, ports and some such
	private class InfoFrame {
		JPanel infoPanel;
		
		InfoFrame() {
			infoPanel = new JPanel();
			infoPanel.setBackground(Color.GRAY);
			infoPanel.setVisible(true);
			infoPanel.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/2.-60), (int)(frameDimension.getHeight()/2.-60)));
		}
		
		JPanel getPanel() {
			return infoPanel;
		}
	}
	
	// inner class to build a new thread which will constantly check all variables input by the server
	private class CheckAll implements Runnable {

		@Override
		public void run() {
			
			// booleans for a running thread and whether the start button can be activated
			running = true;
			boolean beginGame;
			
			while (running) {
				
				// check whether this is a host computer
				if (host && clientStati != null) {
					// lets just assume, that all clients are ready
					beginGame = true;
					
					for (int i=1; i<clientStati.size(); i++) {
						if (!clientStati.get(i))
							beginGame = false;
					}
					
					begin.setEnabled(beginGame);
				}
				
//				System.out.println(clientStati+ " "+client);

				
				
				
				// set the thread asleep to give computing time to other threads
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
