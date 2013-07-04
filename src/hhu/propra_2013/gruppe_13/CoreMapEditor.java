package hhu.propra_2013.gruppe_13;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class CoreMapEditor {
	/**
	 * Map Builder, records klicks and writes an array that is written in a textfile!
	 * @author	gruppe13
	 * @param	char room[][]	Array that contains the room for writing it into the textfile
	 * @param	wall			wall variable
	 * @param	enemy(type)		
	 * @param	type			type of the item or enemy
	 * @param	door			
	 * @param	item(type)
	 */	
	static char obj;
	private int posX, posY;
	private static char room[][] = new char[23][14];
	private static JFrame game;
	
	
	JPanel showMapCreator(JFrame gameWindow){
		
		/*--------------------------------------------creating variables--------------------------------------------------------*/
		game = gameWindow;
		JPanel mapCreator = new JPanel();
		
		/*--------------------------------------------create the needed Buttons-------------------------------------------------*/
		
		ButtonGroup brush = new ButtonGroup();
		
		JRadioButton wall = new JRadioButton("Wall");
		JRadioButton enemy1 = new JRadioButton("Enemy1");
		JRadioButton enemy2 = new JRadioButton("Enemy2");
		JRadioButton enemy3 = new JRadioButton("Enemy3");
		JRadioButton item1 = new JRadioButton("Item1");
		JRadioButton item2 = new JRadioButton("Item2");
		JRadioButton item3 = new JRadioButton("Item3");
		JRadioButton delete = new JRadioButton("Del");
		
		brush.add(wall);
		brush.add(enemy1);
		brush.add(enemy2);
		brush.add(enemy3);
		brush.add(item1);
		brush.add(item2);
		brush.add(item3);
		brush.add(delete);
		
		wall.setSelected(true);
		
		JButton backToMenu 	= new JButton("Menu");
		JButton reset		= new JButton("Reset");
		JButton test		= new JButton("Test");
		JButton save		= new JButton("Save");
		
		wall.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj('W');
			}
		});
		
		enemy1.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj('E');
			}
		});
		
		enemy2.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj('F');
			}
		});
		
		enemy3.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj('B');
			}
		});
		
		item1.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj('I');
			}
		});
		
		item2.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj('?');
			}
		});
		
		item3.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj('?');
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.setObj(' ');
			}
		});
		
		backToMenu.addActionListener(new ActionListener() {
			
			@Override	//go back to menu
			//TODO:maybe ask if saved or warn at least
			public void actionPerformed(ActionEvent e) {
				ProPra.initMenu();
			}
		});
		
		test.addActionListener(new ActionListener() {
			
			@Override	// terminate the program
			public void actionPerformed(ActionEvent e) {
				CoreMapEditor.save();
				CoreMapEditor.run();
			}
		});

		save.addActionListener(new ActionListener() {
	
			@Override	// save array to file
			public void actionPerformed(ActionEvent e) {
				//TODO:für Lukas
				CoreMapEditor.save();
			}
		});

		reset.addActionListener(new ActionListener() {
	
			@Override	// reset the whole array
			public void actionPerformed(ActionEvent e) {
				room = null;
			}
		});
		
		/*-----------------------------------------build the buttons in the display--------------------------------------------*/
		
		mapCreator.setLayout(new GridBagLayout());
		GridBagConstraints cButtons = new GridBagConstraints();
		
		/*-----------------------------------------make mouse stuff-------------------------------------------------------------*/
		
		mapCreator.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				posX = arg0.getX();
				posY = arg0.getY();
				editArray(posX,posY);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		/*---------------------------------------------make layout-------------------------------------------------------------*/
		
		
		
		
		return mapCreator;
	}
	
	public void editArray(int x, int y){
		int i = game.getWidth()/x;
		int j = game.getHeight()/y;
		room[i][j] = getObj();
	}
	
	private char getObj(){
		return obj;
	}
	
	private static void setObj(char inObj){
		obj = inObj;
	}
	
	private static void save(){
		//TODO:actually save it
	}
	
	private static void run(){
		//TODO:call propra init game
	}
	
	private class MapPane extends JPanel{
		
		
		
	}
	
}
