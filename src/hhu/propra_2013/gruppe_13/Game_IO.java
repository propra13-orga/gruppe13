package hhu.propra_2013.gruppe_13;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Game_IO implements KeyListener {
	
	// Logic used by the class, set within the constructor
	Logic logic;
	Game_IO(Logic inLogic) {
		logic =inLogic;
	}
	
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
			 logic.setMoveXn(false);  
		 }
		 if(e.getKeyCode() == 83){ 
			 logic.setMoveX(false);  
		 }
		 if(e.getKeyCode() == 68){ 
			 logic.setMoveY(false);  
		 }
		 if(e.getKeyCode() == 65){ 
			 logic.setMoveYn(false);  
		 }
	    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
