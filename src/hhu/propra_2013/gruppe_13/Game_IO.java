package hhu.propra_2013.gruppe_13;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Game_IO implements KeyListener{
	
	Logic logic;
	
	public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 case 87:
			 logic.setMoveXn(true);
			 break;
		 case 83:
		 	 logic.setMoveX(true);  
		 	 break;
		 case 68: 
			 logic.setMoveY(true);  
			 break;
		 case 65: 
			 logic.setMoveYn(true);  
			 break;
	    } 
		 
	 public void keyReleased(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 case 87: 
			 Logic.setMoveXn(false);  
			 break;
		 case 83: 
			 Logic.setMoveX(false);
			 break;
		 case 68: 
			 Logic.setMoveY(false);  
			 break;
		 case 65: 
			 Logic.setMoveYn(false);  
			 break;
		 }
	    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
