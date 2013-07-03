package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


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
	
	// Variables containing the own preferences
	private static Boolean 	ownStatus;
	private static Color 	ownColor;
	private static String 	ownUsername;
	
	// initiate two panels, one containing all users and another as a status bar
	private static UserPanel uPanel;
	private static InfoFrame iFrame;
	
	// Client and server which are needed to show all connections and start the game
	private static NetClient client = null;
	private static NetServer server = null;
	
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
		
		// set standard button size to the size of the begin button
		menu.setPreferredSize(begin.getPreferredSize());
		
		// build two new panels, one for listing all users and one for listing all relevant personal information
		uPanel = new MenuMultiWaiting().new UserPanel();
		iFrame = new MenuMultiWaiting().new InfoFrame();
		//NetChatPanel chat = new NetChatPanel(gameFrame);
		
		// build a thread to check all inputs and outputs to and from the server
		CheckAll check = new MenuMultiWaiting().new CheckAll();
		Thread thread = new Thread(check);
		thread.start();
		
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
				// terminate all threads
				running = false;
				
				server.setRunning(false);
				client.setRunning(false);
				ProPra.initMenu();
			}
		});
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// this Panel will underly dynamic gridbag constraints
		GridBagConstraints layout = new GridBagConstraints();
		
		// add the user panel
		layout.gridheight 	= 3;
		layout.gridwidth	= 2;
		
//		layout.weightx = 1;
//		layout.weighty = 1;
//
//		layout.anchor = GridBagConstraints.PAGE_START;
		
		layout.gridx = 0;
		layout.gridy = 0;
		layout.anchor = GridBagConstraints.CENTER;
		layout.insets = new Insets(30, 30, 30, 30);
		
		waitingArea.add(uPanel, layout);
		
		// add the information panel
		layout.gridheight	= 2;
		layout.gridx = 2;
		waitingArea.add(iFrame, layout);
		
		layout.gridheight	= 1;
		layout.gridwidth	= 1;
		
		// add the two buttons
		layout.gridy = 2;
		waitingArea.add(begin, layout);
		
		layout.gridx = 3;
		layout.anchor = GridBagConstraints.LINE_END;
		waitingArea.add(menu, layout);
		
		// last of all add the chat area
		/*
		 * layout.gridwith = 4;
		 * layout.gridx = 0;
		 * layout.gridy = 3;
		 * waitingArea.add(chat, layout);*/
		
		uPanel.initPanel();

		System.out.println("UPanel: "+uPanel.getSize());
		// set the actual content pane and show the panel
		gameWindow.setContentPane(waitingArea);
		gameWindow.setVisible(true);
	}
	
	// initiate the actual game
	private static void initGame() {
		
	}
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	// inner class for the panel containing all usernames, colors and stati
	private class UserPanel extends JPanel{

		// serial version ID and layout manager
		private static final long 	serialVersionUID = -4025842983277361277L;
		private GridBagConstraints	layout;
		private GridBagConstraints 	frameLayout;
		
		// build all fields needed for the panel
		private ArrayList<JTextField> 	namefield;
		private ArrayList<JTextField> 	statusfield;
		private ArrayList<FigureColor> 	color;
		
		// number of the client 
		private int clientNo;
		
		UserPanel() {
			this.setLayout(new GridBagLayout());
			this.setBackground(Color.white);
			this.setVisible(true);
			this.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/2.-60), (int)(frameDimension.getHeight()*3/4.-60)));
			
			layout = new GridBagConstraints();
			frameLayout = new GridBagConstraints();
			
			namefield 	= new ArrayList<JTextField>();
			statusfield = new ArrayList<JTextField>();
			color		= new ArrayList<FigureColor>();
		}
		
		ArrayList<JTextField> getNameField() {
			return namefield;
		}
		
		ArrayList<JTextField> getStatusField() {
			return statusfield;
		}
		
		ArrayList<FigureColor> getColor() {
			return color;
		}
		
		void initPanel() {
			// build two new panels which contain the users and a default empty space
			JPanel users = new JPanel();
			JPanel rest = new JPanel();
			
			// wait until the client statuses have been initialized
			while (clientStati == null) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
			}

			// get the number of the current client
			clientNo = client.getClientNumber();
			
			// set the size of both panel, we will add all components to the one and have the other as a filler
			users.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()), clientStati.size()*20));
			System.out.println(clientStati.size());
			rest.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()), (int)(this.getPreferredSize().getHeight()-users.getPreferredSize().getHeight())));
			
			users.setBorder(null);
			rest.setBorder(null);
			
			// build the layout manager accordingly, layout will be determined by the weight given to each object 
			frameLayout.fill = GridBagConstraints.BOTH;
			frameLayout.weightx = 1;
			frameLayout.weighty = clientStati.size()*20./(this.getPreferredSize().getHeight());
			System.out.println("weigt y: "+frameLayout.weighty);
			
			frameLayout.gridx = 0;
			frameLayout.gridy = 0;
			this.add(users, frameLayout);
			
			// add the rest panel to the menu, this is actually just a filler
			frameLayout.gridy = 1;
			frameLayout.weighty = 1 - frameLayout.weighty;
			this.add(rest, frameLayout);
			users.setLocation(this.getX(), this.getY());
			rest.setLocation(this.getX(), this.getY()+users.getHeight());
			
			// set background colors and visibilities
			users.setBackground(Color.RED);
			rest.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));

			users.setVisible(true);
			rest.setVisible(true);
			
			
			// reinitialize the layout manager to work with all subcomponents
			layout.fill 	= GridBagConstraints.BOTH;
			layout.anchor 	= GridBagConstraints.FIRST_LINE_START;
			
			// iterate over all clients, build their own little private space
			for (int i=0; i<clientStati.size(); i++) {
				// start at the beginning of the next line, with regular weights
				layout.weighty = 1;
				layout.weightx = 1;
				
				layout.gridx = 0;
				layout.gridy = i;
				
				// build a new field for the usernames and accepts only 15 Characters
				namefield.add(i, new JTextField());
				namefield.get(i).setBorder(null);
				namefield.get(i).setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()/2.-80), 20));
				namefield.get(i).setDocument(new PlainDocument() {
					
					private static final long serialVersionUID = 8592038311609206190L;

					@Override
					public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
						int length = super.getLength();
						
						if (length >= 15)
							str = "";
						
						super.insertString(offs, str, a);
					}
				});
				
				// add the users name, set Background according to order and add to the panel
				if (i%2 == 0) {
					namefield.get(i).setBackground(Color.DARK_GRAY);
					namefield.get(i).setForeground(Color.WHITE);
				}
				else {
					namefield.get(i).setBackground(Color.LIGHT_GRAY);
					namefield.get(i).setForeground(Color.BLACK);
				}
				namefield.get(i).setText(usernames.get(i));

				// set visibility and add the field to the panel
				namefield.get(i).setVisible(true);
				users.add(namefield.get(i), layout);
				
				// set whether the user has chosen to begin yet
				layout.gridx = 1;
				if (clientStati.get(i))
					statusfield.add(i, new JTextField("Begin"));
				else
					statusfield.add(i, new JTextField("Waiting"));
				
				statusfield.get(i).setBorder(null);
				statusfield.get(i).setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()/2.-10), 20));
				statusfield.get(i).setVisible(true);
				users.add(statusfield.get(i), layout);
				
				// add the color chooser so that a new color can be set
				layout.gridx = 2;
				layout.weightx = 0;
				color.add(i, new FigureColor(clientColors.get(i)));
				color.get(i).init();
				users.add(color.get(i), layout);

				// set all fields that do not belong to the user as non-editable
				if (i != clientNo) {
					namefield.get(i).setEditable(false);
					namefield.get(i).setFocusable(false);
					namefield.get(i).setHighlighter(null);
					
					color.get(i).setClickEnabled(false);
					color.get(i).setFocusable(false);
					color.get(i).setEnabled(false);
				}
				
				// all status fields should only be editable via the "Begin" button
				statusfield.get(i).setEditable(false);
				statusfield.get(i).setFocusable(false);
				statusfield.get(i).setHighlighter(null);
			}
		}
		
		
		/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		private class FigureColor extends JPanel {
			
			private static final long serialVersionUID = 7991390633821598074L;
			private boolean enabled;
			private ArrayList<Color> colors;
			
			// standard constructor
			FigureColor (Color color) {
				this.setBackground(color);
				this.setVisible(true);
				this.setBorder(null);
				
				this.enabled = true;
				this.colors = new ArrayList<Color>();
				
				this.setPreferredSize(new Dimension(20, 20));
			}
			
			// enable the panel to be clicked
			void setClickEnabled(boolean enabled) {
				this.enabled = enabled;
			}
			
			// initialize the panel with a mouse listener and different colors
			void init() {
				// add a couple of colors to the list
				colors.add(Color.white);
				colors.add(Color.lightGray);
				colors.add(Color.gray);
				colors.add(Color.darkGray);
				colors.add(Color.black);
				colors.add(Color.red);
				colors.add(Color.pink);
				colors.add(Color.orange);
				colors.add(Color.yellow);
				colors.add(Color.green);
				colors.add(Color.magenta);
				colors.add(Color.cyan);
				colors.add(Color.blue);

				
				// add a mouse listener to cycle trough the colors
				this.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent arg0) {
					}
					
					@Override
					public void mousePressed(MouseEvent arg0) {
						
						// check whether we are actually entitled to change the color
						if (!enabled)
							return;
						
						// find out how many colors there are
						int i = colors.indexOf(getBackground());
						
						// cycle to the next color
						if (i != colors.size()-1)
							setBackground(colors.get(++i));
						else
							setBackground(colors.get(0));
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {
					}
					
					@Override
					public void mouseEntered(MouseEvent arg0) {
					}
					
					@Override
					public void mouseClicked(MouseEvent arg0) {
					}
				});
			}
		}
	}
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	// inner class for setting an info frame containing IP addresses, ports and some such
	private class InfoFrame extends JPanel{

		private static final long serialVersionUID = -8936535415694190344L;

		InfoFrame() {
			this.setBackground(Color.GRAY);
			this.setVisible(true);
			this.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/2.-60), (int)(frameDimension.getHeight()/2.-60)));
		}
	}
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	// inner class to build a new thread which will constantly check all variables input by the server
	private class CheckAll implements Runnable {

		@Override
		public void run() {
			
			// booleans for a running thread and whether the start button can be activated
			running = true;
			boolean beginGame;
			
			// temporary variables for storing changes in variables
			boolean tempStatus;
			Color	tempColor;
			String	tempUsername;
			
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
				
				// find out what number client we are
				int clientNo = client.getClientNumber();
				
				// check whether the users variables have changed
				if (uPanel.getNameField().size() > clientNo) {
					ownUsername = uPanel.getNameField().get(clientNo).getText();
					ownColor	= uPanel.getColor().get(clientNo).getBackground();
					ownStatus	= !(uPanel.getStatusField().get(clientNo).getText().contentEquals("Waiting"));
					
					// if the variables have changed, reset the servers values
					if (!ownUsername.contentEquals(usernames.get(clientNo))) { 
						client.setUsername(ownUsername);
						usernames.set(clientNo, ownUsername);
					}
					
					if (ownColor != clientColors.get(clientNo))
						client.setColor(ownColor);
					
					if (ownStatus != clientStati.get(clientNo))
						client.setBegin(ownStatus);
				}
				
//				uPanel.resetPanel();
				uPanel.repaint();				

				// get the needed lists from the provided client class
				clientColors 	= client.getColors();
				clientStati		= client.getStati();
				usernames		= client.getUsers();
				
				// set the thread asleep to give computing time to other threads
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
