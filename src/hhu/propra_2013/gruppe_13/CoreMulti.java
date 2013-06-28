package hhu.propra_2013.gruppe_13;

import java.awt.Color;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CoreMulti {
	private static GridBagConstraints cButtons;

	static JPanel showMulti(JFrame gameWindow) {
		
		// Create a new Background with a specified color (Black at the moment)
		JPanel multi = new JPanel();
		multi.setSize(gameWindow.getContentPane().getSize());
		multi.setBackground(new Color(0, 0, 0));
		
		JButton backToMenu 	= new JButton("Menu");
		JButton host		= new JButton("Host");
		JButton client		= new JButton("Client");
		
		backToMenu.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				ProPra.initMenu();
			}
		});
		
		host.addActionListener(new ActionListener() {
			
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				Enumeration e = null;
				try {
					e = NetworkInterface.getNetworkInterfaces();
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            while(e.hasMoreElements())
	            {
	                NetworkInterface n=(NetworkInterface) e.nextElement();
	                Enumeration ee = n.getInetAddresses();
	                while(ee.hasMoreElements())
	                {
	                    InetAddress i= (InetAddress) ee.nextElement();
	                    if (i instanceof Inet4Address)
	                    System.out.println(i.getHostAddress());
	                }
	            }
			}
		});

		client.addActionListener(new ActionListener() {
	
			@Override	// initiate the game
			public void actionPerformed(ActionEvent arg0) {
				//TODO:need client
			}
		});
		
		multi.setLayout(new GridBagLayout());
		
		cButtons = new GridBagConstraints();
		cButtons.gridheight = 3;
		cButtons.gridwidth	= 3;
		
		cButtons.gridx = 1;
		cButtons.gridy = 0;
		multi.add(host, cButtons);
		
		cButtons.insets = new Insets(100, 0, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 1;
		multi.add(client, cButtons);
		
		cButtons.insets = new Insets(200, 0, 0, 0);
		cButtons.gridx = 1;
		cButtons.gridy = 2;
		multi.add(backToMenu, cButtons);
		
		return multi;
	}
}
