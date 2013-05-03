package hhu.propra_2013.gruppe_13;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Game_IO {
	
	Logic logic;
	
	public void keyPressed(KeyEvent e) {
		 switch (e.getKeyCode()) {
		 case 87:
			 logic.setMoveXn(true);
			 break;
		 case 83:
		 }
		 if(e.getKeyCode() == 87){ 
			 logic.setMoveXn(true);  
		 }
		 if(e.getKeyCode() == 83){ 
			 logic.setMoveX(true);  
		 }
		 if(e.getKeyCode() == 68){ 
			 logic.setMoveY(true);  
		 }
		 if(e.getKeyCode() == 65){ 
			 logic.setMoveYn(true);  
		 }
	    } 
	 public void keyReleased(KeyEvent e) {
		 if(e.getKeyCode() == 87){ 
			 Logic.setMoveXn(false);  
		 }
		 if(e.getKeyCode() == 83){ 
			 Logic.setMoveX(false);  
		 }
		 if(e.getKeyCode() == 68){ 
			 Logic.setMoveY(false);  
		 }
		 if(e.getKeyCode() == 65){ 
			 Logic.setMoveYn(false);  
		 }
	    }
	
}
