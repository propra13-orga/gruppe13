package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;


class NetClient extends NetIO {

	// variable to set whether execution should be continued, socket for communication with the server
	private boolean running;
	private Socket 	socket;
	
	// Streams for communication with the server
	private ObjectInputStream 	incoming;
	private ObjectOutputStream	outgoing;
	
	// Array lists for all clients colors and status
	private ArrayList<Color> 	colors;
	private ArrayList<Boolean>	stati;
	private ArrayList<String> 	users;
	
	// Integer to know what client we are in order
	private int clientNo;
	private int connNo;
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	NetClient(int port, String ip) {

		colors		= null;
		stati		= null;
		
		try {
			socket = new Socket(ip, port);
			
			// open a new output stream
			outgoing = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			outgoing.flush();
			
			// write a message to the server telling it to take a new client
			outgoing.writeObject(new String("the real deal"));
			outgoing.flush();
			
			// build a new OIS to the server
			incoming = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			ProPra.errorOutput(CLIENT_SOCKET_ERROR, e);
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@Override
	void setRunning(boolean running) {
		this.running = running;
	}
	
	void setColor (Color color) {
		// output variable to server
		try {
			outgoing.reset();
			outgoing.writeObject(color);
			outgoing.flush();
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	void setBegin (boolean begin) {
		// output variable to server
		try {
			outgoing.reset();
			outgoing.writeObject(begin);
			outgoing.flush();
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	void setUsername (String username) {
		// output username to server
		try {
			outgoing.reset();
			outgoing.writeObject(username);
			outgoing.flush();
		} catch (IOException e) {
			ProPra.errorOutput(CONNECTION_CLIENT_WRITE, e);
		}
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	ArrayList<Color> getColors() {
		return colors;
	}
	
	ArrayList<Boolean> getStati() {
		return stati;
	}
	
	ArrayList<String> getUsers() {
		return users;
	}
	
	int getClientNumber() {
		return clientNo;
	}
	
	int getConnNo() {
		return connNo;
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	private void initGame() {
//		// Initiate object variables
//				gameWindow 		= inFrame;
//				figure 			= new Figure(10.5, 6.5, 1, 1, 0, Color.BLUE);
//				
//				// Initialize Logic and Graphics
//				logic 		= new CoreLogic(figure, this, mode);
//				level		= logic.getLevel();
//				statusBar	= new MiscStatusBar(figure, mode);
//				graphics 	= new CoreGameDrawer(level, gameWindow, statusBar);
//			
//				// set contentPane to JPanel returned by GameDrawer, set GameIO as keyboard manager
//				gameWindow.setContentPane(graphics.init(logic));
//				KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//		        manager.addKeyEventDispatcher(new CoreGame_IO(logic));
		
		// build two handlers, one for incoming and one for outgoing objects
		NetClientIn clientIn 	= new NetClientIn(incoming);
		NetClientOut clientOut	= new NetClientOut(outgoing);
		
		// build a new Figure with the color specified by the user
		Figure figure = new Figure(10.5, 6.5, 1, 1, 0, colors.get(clientNo));
		
		// tell the server about the figure before anything else happens
		boolean isOut = false;
		
		while (!isOut) {
			try {
				outgoing.writeObject(figure);
				isOut = true;
			} catch (IOException e) {
				System.out.println("Problem sending figure to server");
				e.printStackTrace();
			}
		}
		
		// get the current frame, initialize a new status bar and a new drawer
		JFrame gameWindow = ProPra.getGameWindow();
		MiscStatusBar statusBar = new MiscStatusBar(figure);
		NetClientGameDrawer drawer = new NetClientGameDrawer(gameWindow, statusBar);
				
		// initialize logic with all threadable objects, the idea is to let the logic terminate everthing
		NetClientLogic logic = new NetClientLogic(figure, clientIn, clientOut, drawer);
		
		// set the windows content pane as the game drawer, build a new GameIO for keyboard input/output
		gameWindow.setContentPane(drawer.init());
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new NetClientGameIO(logic));
		
		// build new threads and start everything the client needs
		new Thread(clientIn).start();
		new Thread(clientOut).start();
		new Thread(logic).start();
		new Thread(drawer).start();
		
		System.out.println("initiated all client threads");
	}
	
	/*---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Object 		 inObject;
		ArrayList<?> list;
		
		running 	= true;
		clientNo 	= -1;
		
		while (running) {
			// reset the object, we don't wish to work with the same object again
			inObject = null;
			
			try {
				inObject = incoming.readObject();
				
				// check whether the object is an array list or a level, the latter would mean that the game has begun
				if (inObject instanceof ArrayList) {
					list = (ArrayList<?>)inObject;

					// sets the colors of all users
					if		(list.size() > 0 && list.get(0) instanceof Color) {
						colors 	= (ArrayList<Color>)list;
					}
					
					// sets the status of all users
					else if (list.size() > 0 && list.get(0) instanceof Boolean)
						stati	= (ArrayList<Boolean>)list;
					
					// sets the names of all users
					else if (list.size() > 0 && list.get(0) instanceof String)
						users	= (ArrayList<String>)list;
				}
				
				// if the string should match, begin the game
				else if (inObject instanceof String) {
					System.out.println((String)inObject);
					if (((String)inObject).contentEquals("begin")) {
						System.out.println("beginning in client");
						
						this.initGame();
						this.running = false;
					}
				}
				
				// variable to set which user we are
				else if (inObject instanceof Integer) {
					if (clientNo < 0)
						clientNo = (Integer)inObject;
					else
						connNo = (Integer)inObject;
				}
				
			} catch (ClassNotFoundException | IOException e) {
				System.err.println("Object could not be read. ");
				System.err.println(e.getMessage());
			}
		}
		System.out.println("terminating client");
	}
}