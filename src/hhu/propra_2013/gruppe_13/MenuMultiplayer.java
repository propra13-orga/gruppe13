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

import java.text.ParseException;

import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.MaskFormatter;

class MenuMultiplayer {
	// Layout Manager
	private static GridBagConstraints cButtons;
	
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

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	static JPanel showMulti(JFrame gameWindow) {
		// Create a new Background with a specified color (Black at the moment)
		final JPanel multi = new JPanel();
		
		multi.setSize(gameWindow.getContentPane().getSize());
		multi.setBackground(new Color(0, 0, 0));
		
		// build three buttons for choosing whether to be a host, client or go back to the menu
		JButton backToMenu 	= new JButton("Menu");
		JButton host		= new JButton("Host");
		JButton client		= new JButton("Client");
		
		// different "Begin" buttons for client and server
		clientBegin = new JButton("Begin");
		serverBegin = new JButton("Begin");
		
		// Build a new mask for the port field, this will limit input to a maximum of 5 numbers
		MaskFormatter mask = null;
		try {
			 mask = new MaskFormatter("#####");
		} catch (ParseException e1) {
		}
	
		// initialize IP input field and port input field
		clientIPField 	= new MenuIPField();
		portField		= new JFormattedTextField(mask);

		
		// Text Area for getting all public IP addresses of the server
		final JTextArea ipAddresses	= new JTextArea(0, 11);
		
		// set the text to run from right to left and don't let the user edit the text field
		ipAddresses.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		ipAddresses.setEditable(false);

		// set dimensions, visibilities and whether buttons are enabled
		clientIPField.setPreferredSize(new Dimension(115, 20));
		clientIPField.setVisible(false);
		
		portField.setVisible(false);
		portField.setPreferredSize(new Dimension(60, 20));
		
		ipAddresses.setVisible(false);
		
		clientBegin.setVisible(false);
		clientBegin.setEnabled(false);
		
		serverBegin.setVisible(false);
		serverBegin.setEnabled(false);
		
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
				portField.setVisible(false);
				clientBegin.setVisible(false);
				clientBegin.setEnabled(false);
				
				// terminate a potential client checking thread and start a server checking thread
				clientRunning = false;
				serverRunning = true;
				
				checkServer inputCheck = new MenuMultiplayer().new checkServer();
				Thread thread = new Thread(inputCheck);
				thread.start();
				
				// clear the IP text area
				ipAddresses.setText("");
				
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
					        	// add the ip address to the text area
					        	ipAddresses.append(current_addr.getHostAddress());
					        	ipAddresses.append(System.getProperty("line.separator"));
					        }
					    }
					}
					
					// set various fields visible, the user might wish to know what ip address it's pc has
					ipAddresses.setVisible(true);
					portField.setVisible(true);
					serverBegin.setVisible(true);
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
				portField.setVisible(false);
				serverBegin.setVisible(false);
				serverBegin.setEnabled(false);
				
				// make certain items visible to the user
				clientIPField.setVisible(true);
				portField.setVisible(true);
				clientBegin.setVisible(true);
				
				// terminate a potential server checking thread and start a client checking thread
				clientRunning = true;
				serverRunning = false;
				
				checkClient inputCheck = new MenuMultiplayer().new checkClient();
				Thread thread = new Thread(inputCheck);
				thread.start();
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
		multi.setLayout(new GridBagLayout());
		
		cButtons = new GridBagConstraints();
		cButtons.gridheight = 3;
		cButtons.gridwidth	= 2;
		
		cButtons.gridx = 0;
		cButtons.gridy = 0;
		multi.add(host, cButtons);
		
		cButtons.insets = new Insets(100, 0, 0, 0);
		cButtons.gridx = 0;
		cButtons.gridy = 1;
		multi.add(client, cButtons);
		
		cButtons.insets = new Insets(200, 0, 0, 0);
		cButtons.gridx = 0;
		cButtons.gridy = 2;
		multi.add(backToMenu, cButtons);
		
		cButtons.insets = new Insets(0, 200, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 0;
		multi.add(clientIPField, cButtons);
		multi.add(ipAddresses, cButtons);
		
		cButtons.insets = new Insets(100, 200, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 1;
		multi.add(portField, cButtons);
		
		cButtons.insets = new Insets(200, 200, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 2;
		multi.add(serverBegin, cButtons);
		multi.add(clientBegin, cButtons);
		
	
		return multi;
	}

	/*--------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	private class checkServer implements Runnable {

		@Override
		public void run() {
			// run as long as the user is in server selection mode
			while (serverRunning) {
				// get the entered port and check whether it is in the viable range of 1024 to 65535
				if (portField.getText().trim().length() != 0)
					port = Integer.parseInt(portField.getText().trim());
				
				if (port >= 1024 && port <= 65535) 
					portCheck = true;
				else
					portCheck = false;
				
				// should the port be OK, enable the button to begin a game
				if (portCheck)
					serverBegin.setEnabled(true);
				else
					serverBegin.setEnabled(false);
				
				// set the thread asleep, we don't need to waste resources
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
				if (portField.getText().trim().length() != 0)
					port = Integer.parseInt(portField.getText().trim());
				
				if (port >= 1024 && port <= 65535) 
					portCheck = true;
				else
					portCheck = false;
				
				// get the current IP address and check whether it is valid
				ip = clientIPField.getIP();
				
				if (ip != null)
					ipCheck = true;
				else 
					ipCheck = false;
				System.out.println("test");
				
				// last, if we have a valid IP and port check whether a connection can be established to the given server
				if (ipCheck && portCheck) {
					Socket socket = new Socket();
					System.out.println("ip and port ok");

					// build a new TCP/IP address
					InetSocketAddress address = new InetSocketAddress(ip, port);
					
					// try to open a connection, finally try to close it
					try {
						socket.connect(address, 1000);
						connectionCheck = true;
						System.out.println("connection ok");
					} catch (IOException e) {
						connectionCheck = false;
						System.out.println("connection fucked");
					} finally {
						try {
							socket.close();
						} catch (IOException e) {
						}
					}
				}
				
				// if everything checks out, enable the begin button, so players can start the game
				if (connectionCheck)
					clientBegin.setEnabled(true);
				else
					clientBegin.setEnabled(false);
				
				// put the thread asleep, we don't wish to waste resources
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
