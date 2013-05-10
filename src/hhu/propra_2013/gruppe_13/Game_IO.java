package hhu.propra_2013.gruppe_13;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;


class Game_IO implements KeyEventDispatcher {
	// Logic used by the class, set within the constructor
	Logic logic;
	Game_IO(Logic inLogic) {
		logic =inLogic;
	}

	@Override //reacts to any key event no matter where the focus is. Thus it doesn't matter in what order objects are drawn to the screen
	public boolean dispatchKeyEvent(KeyEvent e) {

		boolean up = false;
		boolean down = false;
		boolean left = false;
		boolean right = false;
		
		if (e.getID() == KeyEvent.KEY_PRESSED) {

			switch (e.getKeyCode()) {
			case 87:									//87='w'
				up =true;
				//logic.setUp(true);
				break;
			case 83:									//83='s'
				down=true;
			 	//logic.setDown(true);  
			 	break;
			case 68: 									//68='d'
				right=true;
				//logic.setRight(true);  
				break;
			case 65: 									//65='a'
				left=true;
//				logic.setLeft(true);  
				break;
			case 17:									//17='ctrl'
				logic.setPunch(true);					//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
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
			if (up && !down){
				if (left && !right){
					logic.setUpLeft(true);
					right = false;
					up = false;
				}else if (right && !left){
					logic.setUpRight(true);
					left = false;
					up = false;
				}
			}else if (down && !up){
				if (left && !right){
					logic.setDownLeft(true);
					right = false;
					down = false;
				}else if (right && !left){
					logic.setDownRight(true);
					left = false;
					down = false;
				}
			}else if (!down && !up){
				if (left && !right){
					logic.setLeft(true);
				}else if (right && !left){
					logic.setRight(true);
				}
			}
		} 
		
		else if (e.getID() == KeyEvent.KEY_RELEASED) {
			
			switch (e.getKeyCode()) {
			case 87:									//87='w'
				up = false;
				//logic.setUp(false);
				break;
			case 83:									//83='s'
			 	down = false;
				//logic.setDown(false);  
			 	break;
			case 68: 									//68='d'
				right = false;
				//logic.setRight(false);  
				break;
			case 65: 									//65='a'
				left = false;
				//logic.setLeft(false);  
				break;

			}
			
			if(up == false)logic.setUp(false);
			if(down == false)logic.setDown(false);
			if(right == false)logic.setRight(false);
			if(left == false) logic.setLeft(false);
			if(up && right == false)logic.setUpRight(false);
			if(up && left == false)logic.setUpLeft(false);
			if(down && right == false)logic.setDownRight(false);
			if(down && left == false)logic.setDownLeft(false);
		}
		return false;
	}
	
}
