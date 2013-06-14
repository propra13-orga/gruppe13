package hhu.propra_2013.gruppe_13;

import java.awt.KeyboardFocusManager;
import java.util.ArrayList;

import javax.swing.JFrame;
import java.io.*;

class CoreO_Game {
	// Frame, graphic, logic and a figure for the actual game
	JFrame 			gameWindow;
	CoreLogic 		logic;
	CoreGameDrawer 	graphics;
	Figure 			figure;
	MISCStatusBar	statusBar;
		
	
	// Build two lists, the graphics component will also receive the figure, which has a special function in the logic class
	//ArrayList<ArrayList<CoreGameObjects>> rooms;
	CoreLevel	level;
	
	// Initialize method for the actual game
	CoreO_Game(JFrame inFrame) {
		// Initiate object variables
		gameWindow 		= inFrame;
		//rooms 			= new ArrayList<ArrayList<CoreGameObjects>>();
		figure 			= new Figure(0.5, 6.5, 1, 1);
		int element, line, column, dest; //for room generation, saves the current char (as int),the line its from, and the column its in
		

		
		// iterate over all objects and rooms within the level, all objects run within [0...800)x[0...600)
		// TODO: make that shit better!!, implement the current level




//		for (int i=0; i<9; i++) {
//			ArrayList<CoreGameObjects> temp = new ArrayList<CoreGameObjects>();
//			temp.add(figure);
//			
//			try {
//				InputStream roomStream = new FileInputStream("Level/Raum"+i+".txt");
//				Reader roomReader = new InputStreamReader (roomStream);
//				
//				element = 0;
//				column = 0; 
//				line = 0;	
//				dest = 0;
//				while ((element = roomReader.read()) != -1){ //Goes trough the whole raumX.txt, and spawns Objects at their Positions
//					
//					switch (element) { 	//ASCII: W=87 D=68 E=69
//					case 'W':			//In order of probability
//						temp.add(new MISCWall(column-1+0.5, line-1+0.5, 1, 1, 1)); 	//-1 because the top left corner seems to have
//						break;											//the coordinates 1:1
//						
//					case 'E':
//						temp.add(new EnemyMelee(column-1+0.5, line-1+0.5, 1, 1, figure, 1));
//						break;
//
//					case 'D': //looks where the door is, then sets destination accordingly
//						//I have no clue why this works
//						if (line == 0) 	{dest = 0;} //Door is on the upper edge of the field, door should lead up
//						if (line == 13)	{dest = 2;} //Door is on the bottom edge of the field, door should lead down
//						if (column==23)	{dest = 1;} //Door is on the right edge of the field, door should lead right
//						if (column==0) 	{dest = 3;} //Door is on the left edge of the field, door should lead left
//					
//						temp.add(new MISCDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, true, dest)); //creating door with correct destination
//						break;	
//						
//					case 'T': 
//						temp.add(new MISCTarget(column-1+0.5, line-1+0.5,1,1,1));
//						
//					case 'I':
//						temp.add(new ItemCupACoffee(column-1+0.5, line-1+0.5, 1, 1, 1));
//
//					}
//					column++; //sets column up for the next cycle of the switch-case
//						
//					if (column==25){ //since we use 0-24 chars per line, the 25th should trigger the next line
//						column = 0;
//						line++;
//					}
//				}
//				
//				roomReader.close();
//				roomStream.close();
//				
//			} catch (IOException e) {
//				System.out.println("File not found, system exiting.");
//				System.exit(1);
//			}
//			
//			rooms.add(i, temp);
//		}

		
		
		// Initialize Logic and Graphics
		logic 		= new CoreLogic(figure, this);
		level		= logic.getLevel();
		statusBar	= new MISCStatusBar(figure);
		graphics 	= new CoreGameDrawer(level, gameWindow, statusBar);

		
		
		// set contentPane to JPanel returned by GameDrawer, set GameIO as keyboard manager
		gameWindow.setContentPane(graphics.init(logic));
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new CoreGame_IO(logic));
	}
	
	// Setter method so the graphic will know which room to paint
	void setRoom(CoreRoom inRoom) {
		graphics.setRoom(inRoom);
	}
	
	
	void end(boolean win ) {
		logic.setGameRunning(false);
		graphics.setGameRunning(false);
		
		if (win == true)	ProPra.win();
		else				ProPra.blueScreen();		
	}
	
	void start() {
		// Build two new threads, one for logic and one for graphics
		Thread logicThread = new Thread(logic);
		Thread graphicThread = new Thread(graphics);

		logicThread.start();
		graphicThread.start();
		System.out.println("game is running");
	}
}