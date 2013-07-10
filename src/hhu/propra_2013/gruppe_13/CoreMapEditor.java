package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
	private char room[][] = new char[23][14];
	private static JFrame game;
	private static JPanel mapCreator;
	private static MapPane mapPane;
//	private static RadioPane radioPane;
	
	
	static void showMapCreator(JFrame gameWindow){
		
		System.out.println("reached the map creator");
		
		/*--------------------------------------------creating variables--------------------------------------------------------*/
		game = gameWindow;
		mapCreator = new JPanel();
		
		mapPane = new CoreMapEditor().new MapPane();
		mapPane.setEnabled(true);
		mapPane.setVisible(true);
		mapPane.setPreferredSize(new Dimension(560, 350));
		
//		radioPane = new RadioPane();
		
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
				char room[][] = new char[23][14];
			}
		});
		
		/*-----------------------------------------build the buttons in the display--------------------------------------------*/
		
		mapCreator.setLayout(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		layout.weightx		= 1;
		layout.weighty		= 1;
		layout.gridheight 	= 9;
		layout.gridwidth	= 3;
		layout.gridx		= 0;
		layout.gridy 		= 0;
		mapCreator.add(mapPane, layout);
		
		layout.weightx		= 0.4;
		layout.weighty		= 0.4;
		layout.gridheight 	= 1;
		layout.gridwidth	= 1;
		layout.gridx		= 4;
		layout.gridy 		= 0;
		mapCreator.add(wall, layout);
		
//		layout.insets = new Insets(0,0,0,0);
		layout.gridy		= 1;
		mapCreator.add(item1, layout);
		
		layout.gridy		= 2;
//		layout.insets = new Insets(0,0,0,0);
		mapCreator.add(item1, layout);
		
		layout.gridy		= 3;
//		layout.insets = new Insets(0,0,0,0);
		mapCreator.add(item2, layout);
		
		layout.gridy		= 4;
//		layout.insets = new Insets(0,0,0,0);
		mapCreator.add(item3, layout);
		
		layout.gridy		= 5;
//		layout.insets = new Insets(0,0,0,0);
		mapCreator.add(enemy1, layout);
		
		layout.gridy		= 6;
//		layout.insets = new Insets(0,0,0,0);
		mapCreator.add(enemy2, layout);
		
		layout.gridy		= 7;
//		layout.insets = new Insets(0,0,0,0);
		mapCreator.add(enemy3, layout);
		
		layout.gridy		= 8;
//		layout.insets = new Insets(0,0,0,0);
		mapCreator.add(delete, layout);
		
		layout.gridx		= 0;
		layout.gridy 		= 9;
		mapCreator.add(reset, layout);
		
//		layout.insets = new Insets(0,0,0,0);
		layout.gridx		= 1;
		layout.gridy 		= 9;
		mapCreator.add(save, layout);
		
//		layout.insets = new Insets(0,0,0,0);
		layout.gridx		= 2;
		layout.gridy 		= 9;
		mapCreator.add(test, layout);
		
//		layout.insets = new Insets(0,0,0,0);
		layout.gridx		= 4;
		layout.gridy 		= 9;
		mapCreator.add(backToMenu, layout);
		
		

//		/*---------------------------------------------make layout-------------------------------------------------------------*/
		
		
		mapCreator.setSize(game.getContentPane().getSize());
		game.setContentPane(mapCreator);
	}
	
//	public void editArray(int x, int y){
//		int i = game.getWidth()/x;
//		int j = game.getHeight()/y;
//		room[i][j] = getObj();
//	}
	
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
	
	//panel to show the map
	private class MapPane extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -5409709899594619477L;
		//blabla make array of elements of type mapelements
		private MapElement mapElement[][] = new MapElement[23][14];
		
		public MapPane(){
			
			System.out.println("reached the map pane");
			
//			this.setPreferredSize(new Dimension(560, 350));
			this.setEnabled(true);
			this.setVisible(true);
			this.setBackground(Color.darkGray);
			
			this.setLayout(new GridBagLayout());
			GridBagConstraints layout = new GridBagConstraints();
			
			
			for(int i = 0; i < 23 ; i++){
				for(int j = 0; j < 14; j++){
					mapElement[i][j] = new MapElement(i ,j);
					layout.gridx = i;
					layout.gridy = j;
					this.add(mapElement[i][j], layout);
					this.repaint();
				}
			}
		}
		
			//single map elements
			private class MapElement extends JPanel{
				
				
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1539840262942070244L;
				private int i, j;
				private char room[][] = new char[23][14];
				
				
				public void editArray(){
					room[i][j] = getObj();
					
					char curr;
					curr = getObj();
					
					Color bg;
					switch (curr) {
					case 'W':
						bg = Color.GREEN;
						break;
					case 'E':
						bg = Color.RED;
						break;
						
					case 'F':
						bg = Color.LIGHT_GRAY;
						break;
						
					case 'B':
						bg = Color.RED;
						break;
	
					case 'I':
						bg = Color.ORANGE;
						break;
						
//					case :
//						
//						break;
//						
//					case :
//						
//						break;
//						
//					case :
//						
//						break;
//						
//					case :
//						
//						break;
					default:
						bg = Color.WHITE;
					}
					this.setBackground(bg);
					this.repaint();
				}
				
				public MapElement(int i, int j) {
					
					System.out.println("reached the map element");
					
					this.setPreferredSize(new Dimension(22, 22));
					this.setEnabled(true);
					this.setVisible(true);
					this.setBackground(Color.WHITE);
					
					this.addMouseListener(new MouseListener(){
					
								@Override
								public void mouseClicked(MouseEvent arg0) {
									editArray();
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
									editArray();
									
								}
					
								@Override
								public void mouseReleased(MouseEvent arg0) {
									// TODO Auto-generated method stub
									
								}
								
							});
						}	
					}
			
	}
//	private class RadioPane extends JPanel{
//		
//	}
}