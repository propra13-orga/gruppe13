package hhu.propra_2013.gruppe_13;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class Game_IO implements KeyListener {
	// Logic used by the class, set within the constructor
	Logic logic;
	Game_IO(Logic inLogic) {
		logic =inLogic;
	}
	
	@Override // Move the figure as long as a key is pressed 
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
	} 
	
	@Override	// Stop movement if a key is released
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 87: 
			logic.setMoveXn(false);  
			break;
		case 83: 
			logic.setMoveX(false);
			break;
		case 68: 
			logic.setMoveY(false);  
			break;
		case 65: 
			logic.setMoveYn(false);  
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
