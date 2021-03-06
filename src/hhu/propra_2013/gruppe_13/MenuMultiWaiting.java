package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Menü in dem vor dem Start einer Multiplayersession 
 * Hier kann jeder Spieler seinen Namen und die Farbe seiner Spielfigur wählen
 * Wenn sich alle bereit gemeldet haben kann das Spiel gestartet werden
 * @author Gruppe13
 *
 */


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
	
	// add a chat panel for interlcient communication
	private static NetChatPanel chatPanel;
	
	// Client and server which are needed to show all connections and start the game
	private static NetClient client = null;
	private static NetServer server = null;
	
	// objects for the info box
	private static int 					port;
	private static String 				ip;
	private static ArrayList<String>	publicIPs;
	
	// button to begin the game, we might need to control whether it is supposed to be active
	private static JButton begin;
	
	// the amount of maximum connections, that way we can initialize enough space for all components, current clientNo
	private static int 	connections;
	private static int 	clientNo;
	
	// variables for game settings
	private static int mode;
	
	// method for showing a waiting room in case the user is the host
	static void showWaitingServer (JFrame gameWindow, int playerNo, int inMode, int inPort, NetClient inClient, NetServer inServer, ArrayList<String> listOfIPs) {
		// mark this as server
		host = true;

		// copy connection information
		port = inPort;
		publicIPs = listOfIPs;
		
		// copy object references to the running client and server
		client = inClient;
		server = inServer;
		
		mode = inMode;
		
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
		
		// build the chat panel
		chatPanel = new NetChatPanel(frameDimension, client, clientNo);
		
		// build a thread to check all inputs and outputs to and from the server
		CheckAll check = new MenuMultiWaiting().new CheckAll();
		Thread thread = new Thread(check);
		thread.start();
		
		uPanel.initPanel();
		iFrame.initPanel();
		chatPanel.initPanel();
		
		// tell the client we have a chatpanel
		
		client.setChatPanel(chatPanel);

		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// enable the Buttons to do something
		begin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// if we are the game host, initiate the game
				if (host) {
					initGame();
				}
				
				// if this is a client we just wish to tell the server we are ready
				else {
					begin.setEnabled(false);
					uPanel.getStatusField().get(clientNo).setText("Ready");
					
					iFrame.getFigureColor().setClickEnabled(false);
					iFrame.getFigureColor().setEnabled(false);
					iFrame.getFigureColor().setFocusable(false);
					
					iFrame.getUserInput().setEditable(false);
					iFrame.getUserInput().setHighlighter(null);
					iFrame.getUserInput().setFocusable(false);
				}
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
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// this Panel will underly dynamic gridbag constraints
		GridBagConstraints layout = new GridBagConstraints();
		
		// add the user panel
		layout.gridheight 	= 3;
		layout.gridwidth	= 2;
		
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
		layout.insets = new Insets(45, 30, 45, 30);
		waitingArea.add(begin, layout);
		
		layout.gridx = 3;
		layout.anchor = GridBagConstraints.LINE_END;
		waitingArea.add(menu, layout);
		
		// last of all add the chat area
		layout.gridwidth = 4;
		layout.gridx = 0;
		layout.gridy = 3;
		waitingArea.add(chatPanel, layout);
		

		// set the actual content pane and show the panel
		gameWindow.setContentPane(waitingArea);
		gameWindow.setVisible(true);
	}
	
	// initiate the actual game
	private static void initGame() {
		server.begin(mode);
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
		private ArrayList<JPanel> 	color;
		
		// build two subpanels
		private JPanel users;
		private JPanel rest;
		
		// number of the client 
		private int clientNo;
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		UserPanel() {
			this.setLayout(new GridBagLayout());
			this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
			this.setVisible(true);
			this.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/2.-60), (int)(frameDimension.getHeight()*3/4.-60)));
			
			layout 		= new GridBagConstraints();
			frameLayout = new GridBagConstraints();
			
			namefield 	= new ArrayList<JTextField>();
			statusfield = new ArrayList<JTextField>();
			color		= new ArrayList<JPanel>();
		}
		
		ArrayList<JTextField> getStatusField() {
			return statusfield;
		}
		
		boolean getReady() {
			boolean ready = true;
			
			if (namefield == null || statusfield == null || color == null)
				ready = false;
			else if (namefield.size() <= clientNo || statusfield.size() <= clientNo || color.size() <= clientNo)
				ready = false;
			
			return ready;
		}
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		void initPanel() {
			// build two new panels which contain the users and a default empty space
			users = new JPanel();
			rest = new JPanel();
			
			// wait until the client statuses have been initialized
			while (clientStati == null || connections == 0) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
			}

			// get the number of the current client
			clientNo = client.getClientNumber();
			
			// set the size of both panel, we will add all components to the one and have the other as a filler
			users.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()), connections*20));
			rest.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()), (int)(this.getPreferredSize().getHeight()-users.getPreferredSize().getHeight())));
			
			users.setBorder(BorderFactory.createEmptyBorder());
			rest.setBorder(BorderFactory.createEmptyBorder());
			
			// build the layout manager accordingly, layout will be determined by the weight given to each object 
			frameLayout.fill = GridBagConstraints.BOTH;
			frameLayout.weightx = 1;
			frameLayout.weighty = 1;//clientStati.size()*20./(this.getPreferredSize().getHeight());
			
			frameLayout.gridx = 0;
			frameLayout.gridy = 0;
			this.add(users, frameLayout);
			
			// add the rest panel to the menu, this is actually just a filler
			frameLayout.gridy = 1;
			frameLayout.weighty = 1;// - frameLayout.weighty;
			this.add(rest, frameLayout);
			
			// set background colors and visibilities
			users.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
			rest.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));

			users.setVisible(true);
			rest.setVisible(true);

			users.setLayout(new GridBagLayout());
			
			// reinitialize the layout manager to work with all subcomponents
			layout.fill 	= GridBagConstraints.BOTH;
			layout.anchor 	= GridBagConstraints.NORTH;
			
			// iterate over all clients, build their own little private space
			for (int i=0; i<connections; i++) {
				// start at the beginning of the next line, with regular weights
				layout.weighty = 1;
				layout.weightx = 1;
				
				layout.gridx = 0;
				layout.gridy = i;
				
				// build a new field for the usernames and accepts only 15 Characters
				namefield.add(i, new JTextField());
				namefield.get(i).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
				namefield.get(i).setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()/2.-10), 20));
				

				// build a new status field 
				statusfield.add(i, new JTextField(""));
				
				// set Background according to order and add to the panel
				if (i%2 == 0) {
					namefield.get(i).setBackground(Color.DARK_GRAY);
					namefield.get(i).setForeground(Color.WHITE);
					
					statusfield.get(i).setBackground(new Color(202,225,255));
					statusfield.get(i).setForeground(Color.BLACK);
				}
				else {
					namefield.get(i).setBackground(Color.LIGHT_GRAY);
					namefield.get(i).setForeground(Color.BLACK);
					
					statusfield.get(i).setBackground(new Color(	110,123,139));
					statusfield.get(i).setForeground(Color.WHITE);
				}

				if (i<clientStati.size())
					namefield.get(i).setText(usernames.get(i));
				else {
					namefield.get(i).setText("");
					namefield.get(i).setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
					namefield.get(i).setForeground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
				}
				
				// set visibility and add the field to the panel
				namefield.get(i).setVisible(true);
				users.add(namefield.get(i), layout);
				
				// set whether the user has chosen to begin yet
				layout.gridx = 1;
				if (i<clientStati.size()) {
					if (!host && clientStati.get(i))
						statusfield.get(i).setText("Ready");
					else if (!host)
						statusfield.get(i).setText("Waiting");
					else
						statusfield.get(i).setText("Host");
				} 
				else {
					statusfield.get(i).setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
					statusfield.get(i).setForeground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
				}
				
				statusfield.get(i).setHorizontalAlignment(JTextField.CENTER);
				statusfield.get(i).setBorder(BorderFactory.createEmptyBorder());
				statusfield.get(i).setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()/4.-10), 20));
				statusfield.get(i).setVisible(true);
				
				users.add(statusfield.get(i), layout);
				
				// add the color chooser so that a new color can be set
				layout.gridx = 2;
				layout.weightx = 0;
				
				
				color.add(i, new JPanel());
				color.get(i).setPreferredSize(new Dimension(20, 20));
				
				if (i<clientStati.size())
					color.get(i).setBackground(clientColors.get(i));
				else
					color.get(i).setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
					
				users.add(color.get(i), layout);

				// set all fields as non-editable, they should only be editable by the info field
				namefield.get(i).setEditable(false);
				namefield.get(i).setFocusable(false);
				namefield.get(i).setHighlighter(null);
				
				color.get(i).setFocusable(false);
				color.get(i).setEnabled(false);
					
				// all status fields should only be editable via the "Begin" button
				statusfield.get(i).setEditable(false);
				statusfield.get(i).setFocusable(false);
				statusfield.get(i).setHighlighter(null);
			}
		}
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		void resetPanel() {
			// leave if the arrays haven't been initialized yet
			if (clientStati == null || !(this.getReady())) 
				return;
			
			// iterate over all panels already active
			for (int i=0; i<clientStati.size(); i++) {
				
				// set the names and colors of all fields
				namefield.get(i).setText(usernames.get(i));
				color.get(i).setBackground(clientColors.get(i));
				
				// set the status of all clients except the host
				if (clientStati.get(i) && i != 0)
					statusfield.get(i).setText("Ready");
				else if (i != 0)
					statusfield.get(i).setText("Waiting");
				else
					statusfield.get(i).setText("Host");
				
				// check whether the fields are visible, toggle accordingly
				if (namefield.get(i).getBackground().getAlpha() == 0) {
					
					// set Background according to order
					if (i%2 == 0) {
						namefield.get(i).setBackground(Color.DARK_GRAY);
						namefield.get(i).setForeground(Color.WHITE);
						
						statusfield.get(i).setBackground(new Color(202,225,255));
						statusfield.get(i).setForeground(Color.BLACK);
					}
					else {
						namefield.get(i).setBackground(Color.LIGHT_GRAY);
						namefield.get(i).setForeground(Color.BLACK);
						
						statusfield.get(i).setBackground(new Color(	110,123,139));
						statusfield.get(i).setForeground(Color.WHITE);
					}	
				}
			}
		}
	}
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	// inner class for setting an info frame containing IP addresses, ports and some such
	private class InfoFrame extends JPanel{

		private static final long serialVersionUID = -8936535415694190344L;
		
		private JTextArea	ips;
		private JTextField 	username;
		private JTextField 	userInput;
		private JTextField 	userColor;
		private FigureColor	colorField;
		private JPanel		filler;
		
		private JTextArea	gameInfo;
		
		InfoFrame() {
			this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
			this.setVisible(true);
			this.setPreferredSize(new Dimension((int)(frameDimension.getWidth()/2.-60), (int)(frameDimension.getHeight()/2.-60)));
		}
		
		JTextField getUserInput() {
			return userInput;
		}
		
		FigureColor getFigureColor() {
			return colorField;
		}
		
		boolean getReady() {
			boolean ready = true;
			
			if (userInput == null || colorField == null) {
				ready = false;
			}
			
			return ready;
		}
		
		void initPanel() {
			// build a new layout manager
			this.setLayout(new GridBagLayout());
			GridBagConstraints layout = new GridBagConstraints();
			
			// build a text box for IPs and port numbers, two for the user name, one for user color and a JPanel as room filler
			ips 		= new JTextArea();
			username	= new JTextField();
			userInput 	= new JTextField();
			userColor	= new JTextField();
			colorField 	= new FigureColor(clientColors.get(clientNo));
			filler		= new JPanel();
			gameInfo	= new JTextArea();
			
			// initiate the color field and JPanel
			colorField.init();
			filler.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
			
			// fill IP-field according to user status 
			if (host) {
				ips.setText("Your IPs and port:");
				
				for (int i=0; i<publicIPs.size(); i++) {
					ips.append(System.getProperty("line.separator"));
					ips.append(publicIPs.get(i)+": "+port);
				}
				
				gameInfo.setText("Coders: max. "+connections);
				gameInfo.append(System.getProperty("line.separator"));
				gameInfo.append("Your choosen difficulty: ");
				gameInfo.append(System.getProperty("line.separator"));
				
				String modeMessage = "";
				
				switch (mode) {
				case 1: 
					modeMessage = "We are pussies!";
					break;
				case 2:
					modeMessage = "We want to win!";
					break;
				case 3:
					modeMessage = "Prepare to die!";
					break;
				}
				
				gameInfo.append(modeMessage);
			}
			else {
				ips.setText("Connected to:");
				ips.append(System.getProperty("line.separator"));
				ips.append(ip+": "+port);
			}
			
			// set the text for the color chooser 
			userColor.setText("User color:");
			
			// set editability of all fields
			ips.setEditable(false);
			ips.setHighlighter(null);
			ips.setFocusable(false);
			
			username.setEditable(false);
			username.setFocusable(false);
			username.setHighlighter(null);
			
			userColor.setEditable(false);
			userColor.setFocusable(false);
			userColor.setHighlighter(null);
			
			filler.setEnabled(false);
			filler.setFocusable(false);
			
			gameInfo.setEditable(false);
			gameInfo.setFocusable(false);
			gameInfo.setHighlighter(null);
			
			// limit usernames to 15 characters 
			userInput.setDocument(new PlainDocument() {
				
				private static final long serialVersionUID = 8592038311609206190L;

				@Override
				public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
					int length = super.getLength();
					
					if (length >= 15)
						str = "";
					
					super.insertString(offs, str, a);
				}
			});
			
			// set the text "Username" for the username text field and enable the user to change its name
			username.setText("Username:");
			userInput.setText(usernames.get(clientNo));
			
			// set everything visible
			ips.setVisible(true);
			username.setVisible(true);
			userInput.setVisible(true);
			userColor.setVisible(true);
			colorField.setVisible(true);
			filler.setVisible(true);
			
			if (host) 
				gameInfo.setVisible(true);
			else
				gameInfo.setVisible(false);
			
			// set the size of all pieces
			ips.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()), ips.getLineCount()*20));
			username.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()*2/3.), 20));
			userInput.setPreferredSize(new Dimension((int)(username.getPreferredSize().getWidth()), 20));
			userColor.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()-20), 20));
			gameInfo.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()), (int)(gameInfo.getLineCount()*20)));
			filler.setPreferredSize(new Dimension((int)(this.getPreferredSize().getWidth()), (int)(ips.getLineCount()*20+gameInfo.getLineCount()*20+40)));
			
			// set borders for padding
			ips.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			username.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			userInput.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			userColor.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			gameInfo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			
			// alter a couple of other properties
			userInput.setHorizontalAlignment(JTextField.TRAILING);
			
			// build layout of all pieces
			layout.fill 	= GridBagConstraints.BOTH;
			
			layout.weightx = 1/2.;
			layout.weighty = 0;
			layout.gridheight = 1;
			
			layout.gridx = 0;
			layout.gridy = 0;
			layout.gridwidth = 2;
			this.add(ips, layout);
			
			layout.gridy = 1;
			layout.gridwidth = 1;
			this.add(username, layout);
			
			layout.gridx = 1;
			layout.weightx = 1-layout.weightx;
			this.add(userInput, layout);
			
			layout.gridy = 2;
			layout.gridx = 0;
			layout.gridwidth = 1;
			layout.weightx = 1-layout.weightx;
			this.add(userColor, layout);
			
			layout.gridx = 1;
			layout.weightx = 0;
			this.add(colorField, layout);
			
			layout.gridheight = 1;
			layout.gridwidth = 2;
			layout.weightx = 1;
			
			layout.gridx = 0;
			layout.gridy = 3;
			this.add(gameInfo, layout);
			
			layout.gridy = 4;
			layout.weighty = 1;
			this.add(filler, layout);
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
				this.setBorder(BorderFactory.createEmptyBorder());
				
				this.enabled = true;
				this.colors = new ArrayList<Color>();
				
				this.setPreferredSize(new Dimension(20, 20));
			}
			
			/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
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
	// inner class to build a new thread which will constantly check all variables input by the server
	private class CheckAll implements Runnable {

		@Override
		public void run() {
			
			// booleans for a running thread and whether the start button can be activated
			running = true;
			boolean beginGame;
			
			// find out the current client number and how many connections there are
			clientNo = 0;
			
			while (connections == 0) {
				connections = client.getConnNo();
				clientNo 	= client.getClientNumber();
			}
			
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
				
				// check whether the users variables have changed
				if (uPanel.getReady() && iFrame.getReady()) {
					
					ownUsername = iFrame.getUserInput().getText();
					ownColor	= iFrame.getFigureColor().getBackground();
					ownStatus	= (uPanel.getStatusField().get(clientNo).getText().contentEquals("Ready"));
					
					// if the variables have changed, reset the servers values
					if (!ownUsername.contentEquals(usernames.get(clientNo))) { 
						client.setUsername(ownUsername);
						chatPanel.setUsername(ownUsername);
						usernames.set(clientNo, ownUsername);
					}
					
					if (ownColor != clientColors.get(clientNo)) {
						client.setColor(ownColor);
						clientColors.set(clientNo, ownColor);
					}
					
					if (ownStatus != clientStati.get(clientNo)) {
						client.setBegin(ownStatus);
						clientStati.set(clientNo, ownStatus);
					}
				}
				
				// get the needed lists from the provided client class
				clientColors 	= client.getColors();
				clientStati		= client.getStati();
				usernames		= client.getUsers();
				
				// reset all values within the panel
				uPanel.resetPanel();
				uPanel.repaint();
				
				// set the thread asleep to give computing time to other threads
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
