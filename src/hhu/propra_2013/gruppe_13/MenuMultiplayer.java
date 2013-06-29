package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


class MenuMultiplayer {
	
	// set game difficulty 
	private static int mode;
	
	// booleans to set whether threads for checking user input are supposed to remain active
	private static boolean serverRunning;
	private static boolean clientRunning;
	
	// fields for entering IP address and port number 
	private static MenuIPField clientIPField;
	private static JFormattedTextField portField;
	
	// variables for initiating a connection
	private static int 		port;
	private static String 	ip;
	
	// variables for checking port, entered IP address and whether a connection to the server can be established
	private static boolean portCheck;
	private static boolean ipCheck;
	private static boolean connectionCheck;
	
	// buttons for beginning the actual game, should a connection be possible
	private static JButton clientBegin;
	private static JButton serverBegin;
	
	// set by host: radio buttons for mode and the number of players
	private static JFormattedTextField players;
	private static int 			playerNo;
	private static JRadioButton modes;

	// Text field for telling the user that the specified TCP/IP connection is available
	private static JTextField clientDestReachable;
	private static JTextField portServerCheck;
	
	// Build two new mini panels to show whether an IP address/TCP port has been accepted
	private static JPanel ipIndicator;
	private static JPanel portIndicator;
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	static JPanel showMulti(JFrame gameWindow) {
		GridBagConstraints cButtons = new GridBagConstraints();
		
		// Create a new Background with a specified color (Black at the moment)
		final JPanel multi = new JPanel(new GridBagLayout());
		
		multi.setSize(gameWindow.getContentPane().getSize());
		multi.setBackground(new Color(0, 0, 0));
		multi.setLayout(new GridBagLayout());
		
		// build three buttons for choosing whether to be a host, client or go back to the menu
		JButton backToMenu 	 = new JButton("Menu");
		final JButton host	 = new JButton("Host");
		final JButton client = new JButton("Client");
		
		// different "Begin" buttons for client and server
		clientBegin = new JButton("Begin");
		serverBegin = new JButton("Begin");
		
		// initialize IP input field and port input field
		clientIPField 	= new MenuIPField();
		clientIPField.setPreferredSize(new Dimension(155, 20));
		clientIPField.setBorder(null);
		clientIPField.setVisible(false);
		
		portField		= new JFormattedTextField();
		portField.setDocument(new PlainDocument() {
			
			// Override the insertString method in order to set a proper port
			private static final long serialVersionUID = 1901522425888189836L;

			@Override 
			public void insertString (int offs, String str, AttributeSet attributeSet) throws BadLocationException {
				// check whether input is a digit and the maximum length of the string hasn't been reached yet, or if the input is greater than 65535
				int length = super.getLength();
				
				if (!Character.isDigit(str.charAt(0)) || length >= 5 || Integer.parseInt(super.getText(0, length)+str) > 65535 || (length == 0 && str.charAt(0) == '0'))
					str = "";

				// call super method, which does the actual insertion
				super.insertString(offs, str, attributeSet);
			}
		});
		
		portField.setVisible(false);
		portField.setPreferredSize(new Dimension(60, 20));
		portField.setHorizontalAlignment(JTextField.CENTER);
		portField.setBorder(null);
		
		// Build three radio buttons to set game difficulty, group them and set medium to standard 
		final JRadioButton difficultyEasy 	= new JRadioButton("I'm a pussy!");
		final JRadioButton difficultyMed	= new JRadioButton("I want to win!");
		final JRadioButton difficultyHard	= new JRadioButton("Prepare to die!");
		difficultyMed.setSelected(true);
		
		ButtonGroup difficulty = new ButtonGroup();
		difficulty.add(difficultyEasy);
		difficulty.add(difficultyMed);
		difficulty.add(difficultyHard);
		
		// set button visibility, only active if server is selected
		difficultyEasy.setVisible(false);
		difficultyMed.setVisible(false);
		difficultyHard.setVisible(false);
		
		difficultyEasy.setForeground(Color.WHITE);
		difficultyEasy.setBackground(Color.BLACK);
		difficultyEasy.setFocusable(false);
		
		difficultyMed.setForeground(Color.WHITE);
		difficultyMed.setBackground(Color.BLACK);
		difficultyMed.setFocusable(false);
		
		difficultyHard.setForeground(Color.WHITE);
		difficultyHard.setBackground(Color.BLACK);
		difficultyHard.setFocusable(false);
		
		// Set up text fields to tell the user what to do with IP and ports
		final JTextField ipClient = new JTextField("Server IP:");
		ipClient.setPreferredSize(new Dimension (150, 20));
		ipClient.setForeground(Color.WHITE);
		ipClient.setBackground(Color.BLACK);
		ipClient.setBorder(null);
		ipClient.setHorizontalAlignment(JTextField.CENTER);
		ipClient.setEditable(false);
		ipClient.setHighlighter(null);
		ipClient.setFocusable(false);
		
		final JTextField portExplain = new JTextField("Port from 1024 to 65535:");
		portExplain.setPreferredSize(new Dimension (200, 20));
		portExplain.setForeground(Color.WHITE);
		portExplain.setBackground(Color.BLACK);
		portExplain.setBorder(null);
		portExplain.setHorizontalAlignment(JTextField.CENTER);
		portExplain.setEditable(false);
		portExplain.setHighlighter(null);
		portExplain.setFocusable(false);
		
		portServerCheck = new JTextField();
		portServerCheck.setPreferredSize(new Dimension (200, 20));
		portServerCheck.setForeground(Color.WHITE);
		portServerCheck.setBackground(Color.BLACK);
		portServerCheck.setBorder(null);
		portServerCheck.setHorizontalAlignment(JTextField.CENTER);
		portServerCheck.setEditable(false);
		portServerCheck.setHighlighter(null);
		portServerCheck.setFocusable(false);
		portServerCheck.setVisible(false);

		// initiate the two panels which show whether a port and IP address are acceptable
		ipIndicator 	= new JPanel();
		portIndicator 	= new JPanel();
		
		ipIndicator.setVisible(false);
		portIndicator.setVisible(false);
		
		// Text Area for getting all public IP addresses of the server, let the text run from right to left
		final JTextArea ipAddresses	= new JTextArea(0, 11);
		ipAddresses.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		ipAddresses.setForeground(Color.WHITE);
		ipAddresses.setBackground(Color.BLACK);
		ipAddresses.setEditable(false);
		ipAddresses.setHighlighter(null);
		ipAddresses.setFocusable(false);
		
		// set up a text field for checking the connection to the server
		clientDestReachable = new JTextField();
		clientDestReachable.setPreferredSize(new Dimension(150, 20));
		clientDestReachable.setForeground(Color.WHITE);
		clientDestReachable.setBackground(Color.BLACK);
		clientDestReachable.setBorder(null);
		clientDestReachable.setText(" ");
		clientDestReachable.setHorizontalAlignment(JTextField.CENTER);
		clientDestReachable.setEditable(false);
		clientDestReachable.setHighlighter(null);
		clientDestReachable.setFocusable(false);
		
		// initialize two text areas, one "Players:" and one to enter the number of participating players
		players = new JFormattedTextField();
		players.setDocument(new PlainDocument() {
			
			// override the insert string method in order to get a proper number of players
			private static final long serialVersionUID = -5563121635373210379L;

			@Override 
			public void insertString (int offs, String str, AttributeSet attributeSet) throws BadLocationException {
				// check whether input is a digit and the maximum length of the string hasn't been reached yet, or if the input is greater than 65535
				int length = super.getLength();
				
				if (!Character.isDigit(str.charAt(0)) || length >= 2 || (length == 0 && str.charAt(0) == '0'))
					str = "";

				// call super method, which does the actual insertion
				super.insertString(offs, str, attributeSet);
			}
		});
		
		players.setVisible(false);
		players.setPreferredSize(new Dimension(60, 20));
		players.setHorizontalAlignment(JTextField.CENTER);
		players.setBorder(null);
		
		final JTextField playerTitle = new JTextField("Players:");
		playerTitle.setPreferredSize(new Dimension (200, 20));
		playerTitle.setForeground(Color.WHITE);
		playerTitle.setBackground(Color.BLACK);
		playerTitle.setBorder(null);
		playerTitle.setHorizontalAlignment(JTextField.CENTER);
		playerTitle.setEditable(false);
		playerTitle.setHighlighter(null);
		playerTitle.setFocusable(false);
		playerTitle.setVisible(false);
		
		
		// set dimensions, visibilities and whether buttons are enabled
		portExplain.setVisible(false);
		ipAddresses.setVisible(false);
		
		ipClient.setVisible(false);
		clientDestReachable.setVisible(false);
		
		clientBegin.setVisible(false);
		clientBegin.setEnabled(false);
		
		serverBegin.setVisible(false);
		serverBegin.setEnabled(false);
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		difficultyEasy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mode = 1;
			}
		});
		
		difficultyMed.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = 2;
			}
		});
		
		difficultyHard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mode = 3;
			}
		});
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// action listener for the menu button
		backToMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initMenu();
			}
		});
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// action listener for the host button
		host.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// reset boolean variables for the connection
				ipCheck 		= false;
				portCheck 		= false;
				connectionCheck = false;
				
				// set all client related stuff to non-visible
				clientIPField.setVisible(false);
				
				clientBegin.setVisible(false);
				clientBegin.setEnabled(false);
				
				clientDestReachable.setVisible(false);
				ipClient.setVisible(false);
				ipIndicator.setVisible(false);
				portIndicator.setVisible(false);
				
				// terminate a potential client checking thread and start a server checking thread
				clientRunning = false;
				serverRunning = true;
				
				checkServer inputCheck = new MenuMultiplayer().new checkServer();
				Thread thread = new Thread(inputCheck);
				thread.start();
				
				// clear the IP text area
				ipAddresses.setText("Your IP Adresses");
				
				// get all network interfaces on the machine
				Enumeration<NetworkInterface> interfaces = null;
				
				try {
					interfaces = NetworkInterface.getNetworkInterfaces();
				} catch (SocketException e) {
					// if network devices could not be contacted, output will remain empty
				}
				
				if (interfaces != null) {
					while (interfaces.hasMoreElements()){
						// read from the next interface
					    NetworkInterface current = interfaces.nextElement();
					    
					    try {
							if (!current.isUp() || current.isLoopback() || current.isVirtual()) 
								continue;
						} catch (SocketException e) {
							/*  will probably only happen if a network device shuts down while the function is active...
							 *  very unlikely, the user will definitely have other problems. */
						}
					    
					    // get all addresses of that interface
					    Enumeration<InetAddress> addresses = current.getInetAddresses();
					    while (addresses.hasMoreElements()){
					        InetAddress current_addr = addresses.nextElement();
	
					        // check whether we are dealing with IPv4, I am not really up to working with IPv6 as well
					        if (current_addr instanceof Inet4Address) {
					        	// add the IP address to the text area
					        	ipAddresses.append(System.getProperty("line.separator"));
					        	ipAddresses.append(current_addr.getHostAddress());
					        }
					    }
					}
					
					// set various fields visible
					ipAddresses.setVisible(true);
					
					portField.setVisible(true);
					portExplain.setVisible(true);
					portServerCheck.setVisible(true);
					
					serverBegin.setVisible(true);
					host.setSelected(true);
					
					players.setVisible(true);
					playerTitle.setVisible(true);
					
					difficultyEasy.setVisible(true);
					difficultyMed.setVisible(true);
					difficultyHard.setVisible(true);
				}
			}
		});

		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		client.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// reset boolean variables for the connection
				ipCheck 		= false;
				portCheck 		= false;
				connectionCheck = false;
				
				// delete all server related stuff
				ipAddresses.setVisible(false);
				serverBegin.setVisible(false);
				serverBegin.setEnabled(false);
				portServerCheck.setVisible(false);
				
				players.setVisible(false);
				playerTitle.setVisible(false);
				
				difficultyEasy.setVisible(false);
				difficultyMed.setVisible(false);
				difficultyHard.setVisible(false);
				
				// make certain items visible to the user
				clientIPField.setVisible(true);
				portField.setVisible(true);
				clientBegin.setVisible(true);
				clientDestReachable.setVisible(true);
				ipClient.setVisible(true);
				ipIndicator.setVisible(true);
				portIndicator.setVisible(true);
				portExplain.setVisible(true);

				// terminate a potential server checking thread and start a client checking thread
				clientRunning = true;
				serverRunning = false;
				
				checkClient inputCheck = new MenuMultiplayer().new checkClient();
				Thread thread = new Thread(inputCheck);
				thread.start();
				
				client.setSelected(true);
			}
		});
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		clientBegin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		serverBegin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		cButtons.weightx = 1;
		cButtons.weighty = 1;
		
		// set a Panel at the top and left of all buttons, this way the constraints will hopefully work out better
		JPanel dummy = new JPanel();
		dummy.setVisible(true);
		dummy.setBackground(multi.getBackground());
		
		cButtons.gridx = 0;
		cButtons.gridy = 0;
		multi.add(dummy, cButtons);
		
		// add the buttons for "Host", "Client" and "Back to Menu"
		cButtons.gridx = 1;
		cButtons.gridy = 1;
		multi.add(host, cButtons);
		
		cButtons.gridx = 1;
		cButtons.gridy = 2;
		cButtons.gridheight = 2;
		multi.add(client, cButtons);
		
		cButtons.gridx = 1;
		cButtons.gridy = 4;
		cButtons.gridheight = 1;
		multi.add(backToMenu, cButtons);
		
		/* Client related stuff ------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// add a field for entering server IP address, including title and graphic indicator
		cButtons.gridx = 2;
		cButtons.gridy = 1;
		cButtons.insets = new Insets(20, 0, 0, 0);
		cButtons.anchor = GridBagConstraints.PAGE_START;
		multi.add(ipClient, cButtons);
		
		cButtons.gridx = 2;
		cButtons.gridy = 1;
		cButtons.anchor = GridBagConstraints.CENTER;
		multi.add(clientIPField, cButtons);
		
		cButtons.gridx = 2;
		cButtons.gridy = 1;
		cButtons.anchor = GridBagConstraints.LINE_END;
		multi.add(ipIndicator, cButtons);
		
		// add a field for entering server port, including title and graphic indicator
		cButtons.gridx = 2;
		cButtons.gridy = 2;
		cButtons.anchor = GridBagConstraints.CENTER;
		multi.add(portExplain, cButtons);
		
		cButtons.gridx = 2;
		cButtons.gridy = 2;
		cButtons.insets = new Insets(0, 0, 17, 0);
		cButtons.anchor = GridBagConstraints.LAST_LINE_END;
		multi.add(portIndicator, cButtons);
		
		cButtons.gridx = 2;
		cButtons.gridy = 2;
		cButtons.insets = new Insets(0, 0, 10, 0);
		cButtons.anchor = GridBagConstraints.PAGE_END;
		multi.add(portField, cButtons);
		
		// indicate whether server is reachable and add a button to enter waiting area
		cButtons.gridx = 2;
		cButtons.gridy = 3;
		cButtons.insets = new Insets(10, 0, 0, 0);
		cButtons.anchor = GridBagConstraints.PAGE_START;
		multi.add(clientDestReachable, cButtons);
		
		cButtons.gridx = 2;
		cButtons.gridy = 4;
		cButtons.insets = new Insets(0, 0, 0, 0);
		cButtons.anchor = GridBagConstraints.CENTER;
		multi.add(clientBegin, cButtons);
		
		/* Server related stuff ------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// reset a couple of variables 
		cButtons.gridheight = 1;
		cButtons.gridwidth  = 1;
		cButtons.weightx = 1;
		cButtons.weighty = 1;
		
		// add an IP address area, contains all external IP addresses of the server
		cButtons.gridx = 2;
		cButtons.gridy = 1;
		multi.add(ipAddresses, cButtons);
		
		// add a check for the current port number
		cButtons.gridx = 2;
		cButtons.gridy = 3;
		cButtons.anchor = GridBagConstraints.PAGE_START;
		multi.add(portServerCheck, cButtons);
		
		// button to enter the waiting area should this be possible
		cButtons.gridx = 2;
		cButtons.gridy = 4;
		cButtons.anchor = GridBagConstraints.CENTER;
		cButtons.gridwidth = 2;
		multi.add(serverBegin, cButtons);
		
		// add a field for the amount of players
		cButtons.gridx = 3;
		cButtons.gridy = 1;
		cButtons.gridwidth = 1;
		multi.add(playerTitle, cButtons);
		
		cButtons.insets = new Insets(40, 0, 0, 0);
		multi.add(players, cButtons);
		
		// add the radio buttons for difficulty selection
		cButtons.gridx = 3;
		cButtons.gridy = 2;
		cButtons.gridheight = 2;
		
		cButtons.insets = new Insets(0, 0, 80, 0);
		multi.add(difficultyEasy, cButtons);
		
		cButtons.insets = new Insets(0, 0, 0, 0);
		multi.add(difficultyMed, cButtons);
		
		cButtons.insets = new Insets(80, 0, 0, 0);
		multi.add(difficultyHard, cButtons);
		
		/* Add a last dummy ----------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		// add a last dummy at the right of the frame
		dummy = new JPanel();
		dummy.setVisible(true);
		dummy.setBackground(multi.getBackground());
		
		cButtons.gridx = 4;
		cButtons.gridy = 5;
		cButtons.anchor = GridBagConstraints.CENTER;
		cButtons.insets = new Insets(0, 0, 0, 0);
		cButtons.gridwidth = 1;
		cButtons.gridheight = 1;
		multi.add(dummy, cButtons);
		
		return multi;
	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	private class checkServer implements Runnable {

		@Override
		public void run() {
			
			// run as long as the user is in server selection mode
			while (serverRunning) {
				
				// get the number of desired players
				if (players.getText().length() != 0)
					mode = Integer.parseInt(players.getText().trim());
				else
					mode = 0;
				
				// get the entered port and check whether it is in the viable range of 1024 to 65535
				if (portField.getText().length() != 0)
					port = Integer.parseInt(portField.getText().trim());
				else 
					port = 0;
				
				if (port >= 1024 && port <= 65535) 
					portCheck = true;
				else 
					portCheck = false;
				
				// should the port be OK give out an according message
				if (portCheck)
					portServerCheck.setText("port accepted");
				else
					portServerCheck.setText(" ");
				
				// should the port be OK and more than zero players selected, enable the button to begin a game
				if (portCheck && mode != 0)
					serverBegin.setEnabled(true);
				else
					serverBegin.setEnabled(false);
				
				// set the thread asleep, we don't need to waste resources
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// Ignore interruption
				}
			}
		}
	}
	
	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	private class checkClient implements Runnable {

		@Override
		public void run() {
			
			// runs as long as user is in client selection mode
			while (clientRunning) {
				
				// get the entered port and check whether it is in the viable range of 1024 to 65535
				if (portField.getText().length() != 0)
					port = Integer.parseInt(portField.getText().trim());
				else 
					port = 0;
				
				if (port >= 1024 && port <= 65535) {
					portCheck = true;
					portIndicator.setBackground(Color.GREEN);
				}
				else {
					portCheck = false;
					portIndicator.setBackground(Color.RED);
				}
				
				// get the current IP address and check whether it is valid
				ip = clientIPField.getIP();
				
				if (ip != null) {
					ipCheck = true;
					ipIndicator.setBackground(Color.GREEN);
				}
				else {
					ipCheck = false;
					ipIndicator.setBackground(Color.RED);
				}
				
				// last, if we have a valid IP and port check whether a connection can be established to the given server
				if (ipCheck && portCheck) {
					Socket socket = new Socket();

					// build a new TCP/IP address
					InetSocketAddress address = new InetSocketAddress(ip, port);
					
					// try to open a connection, finally try to close it
					try {
						socket.connect(address, 1000);
						connectionCheck = true;
					} catch (IOException e) {
						connectionCheck = false;
					} finally {
						try {
							socket.close();
						} catch (IOException e) {
						}
					}
				}
				else 
					connectionCheck = false;
				
				// if everything checks out, enable the begin button, so players can start the game
				if (connectionCheck) {
					clientBegin.setEnabled(true);
					clientDestReachable.setText("destination reachable");
				}
				else {
					clientBegin.setEnabled(false);
					clientDestReachable.setText(" ");
				}
				
				// put the thread asleep, we don't wish to waste resources
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// Ignore interruption
				}
			}
		}
	}
}
