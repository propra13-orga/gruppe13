package hhu.propra_2013.gruppe_13;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class Game_IO implements KeyEventDispatcher {
	// Logic used by the class, set within the constructor
	Logic logic;
	Game_IO(Logic inLogic) {
		logic =inLogic;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			System.err.println("pressed");

			switch (e.getKeyCode()) {
			case 87:									//87='w'
				logic.setMoveXn(true);
				break;
			case 83:									//83='s'
			 	logic.setMoveX(true);  
			 	break;
			case 68: 									//68='d'
				logic.setMoveY(true);  
				break;
			case 65: 									//65='a'
				logic.setMoveYn(true);  
				break;
			case 17:									//17='ctrl'
				logic.setHit(true);						//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
				break;
			case 69:									//96='e'
				logic.setUse(true);						//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
				break;
			case 32:									//32='space'
				logic.setBomb(true);					//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
				break;
			case 79:									//79='o'
				logic.setBomb(true);					//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
				break;
			case 101:									//101='numpad 5'
				logic.setBomb(true);					//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
				break;
			}
		} 
		
		else if (e.getID() == KeyEvent.KEY_RELEASED) {
			System.err.println("released");

			switch (e.getKeyCode()) {
			case 87:									//87='w'
				logic.setMoveXn(false);
				break;
			case 83:									//83='s'
			 	logic.setMoveX(false);  
			 	break;
			case 68: 									//68='d'
				logic.setMoveY(false);  
				break;
			case 65: 									//65='a'
				logic.setMoveYn(false);  
				break;

			}
		}
		return false;
	}
	
}
