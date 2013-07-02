package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

class MenuMultiWaiting {

	// the panel which will be the new content pane
	private static JPanel waitingArea;
	
	// method for showing a waiting room in case the user is the host
	static void showWaitingServer (JFrame gameWindow) {
		// first of all we build the JPanel needed to show the waiting area
		waitingArea = new JPanel(new GridBagLayout());
		
		showWaiting(gameWindow);
		
		gameWindow.setContentPane(waitingArea);
		gameWindow.setVisible(true);
	}
	
	// method for showing the waiting room in case the user is a client
	static void showWaitingClient (JFrame gameWindow) {
		// first of all we build the JPanel needed to show the waiting area
		waitingArea = new JPanel(new GridBagLayout());
		
		showWaiting(gameWindow);
		
		gameWindow.setContentPane(waitingArea);
		gameWindow.setVisible(true);
	}
	
	// the basic builder of the panel
	static void showWaiting(JFrame gameWindow) {
		waitingArea.setSize(gameWindow.getContentPane().getSize());
		waitingArea.setBackground(Color.BLUE);
	}
	
	// inner class for the panel containing all usernames, colors and stati
	private class userPanel {
		
	}
	
	// inner class for setting an infoframe containg IP addresses, ports and some such
	private class infoFrame {
		
	}
	
	// inner class to build a new thread which will constantly check all variables input by the server
	private class checkAll implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
}
