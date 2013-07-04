package hhu.propra_2013.gruppe_13;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;


class CoreGame_IO implements KeyEventDispatcher {
	private int		move = 0;
	private int		fire = 0;

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	
	private boolean fireup = false;
	private boolean firedown = false;
	private boolean fireleft = false;
	private boolean fireright = false;

	private boolean esc = false;
	// Logic used by the class, set within the constructor
	CoreLogic logic;
	CoreGame_IO (CoreLogic inLogic) {
		logic 	= inLogic;
	}

	@Override //reacts to any key event no matter where the focus is. Thus it doesn't matter in what order objects are drawn to the screen
	public boolean dispatchKeyEvent(KeyEvent e) {
		
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			move = 0;
			fire = 0;
			
			switch (e.getKeyCode()) {
			case 87:									//Wenn Taste für Up gedrueckt wird 87='w'		
				up = true;
				break;
			case 83:									//Wenn Taste für Down gedrueckt wird 83='s'
				down = true;				
				break;
			case 68: 									//Wenn Taste für Right gedrueckt wird 68='d'
				right = true;
				break;
			case 65: 									//Wenn Taste für Right gedrueckt wird 65='a'
				left = true;
				break;
			case 38:									//38='Pfeil nach oben'
				fireup = true;
				break;
			case 40: 									//68='Pfeil nach unten'
				firedown = true;				
				break;
			case 39:									//83='Pfeil nach rechts'
				fireright = true;
				break;
			case 37: 									//65='Pfeil nach links'
				fireleft = true;
				break;		
//			case 17:									//17='ctrl'
//				logic.setPunch(true);					//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
//				break;
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

			case 77:
				logic.setShowMap(true);

				break;
			case 27:									//77='esc'
				logic.setEsc(true);						//wird nicht zurück auf false gestetzt kümmert sich die Logic drum.
				break;	
				
			}						
			
		} 
		
		else if (e.getID() == KeyEvent.KEY_RELEASED) {

			move = 0;
			fire = 0;

			
			switch (e.getKeyCode()) {
			case 87:									//Wenn Taste für Up geloest wird 87='w'
				up = false;
				break;
				
			case 83:									//Wenn Taste für Down geloest wird 83='s'
			 	down = false;
				break;
			 	
			case 68: 									//Wenn Taste für Right geloest wird 68='d'
				right = false;
				break;

			case 65: 									//Wenn Taste für Left geloest wird 65='a'
				left = false;
				break;
				
			case 38:									//38='Pfeil nach oben'
				fireup = false;
				break;
			
			case 40: 									//68='Pfeil nach unten'
			 	firedown = false;
			 	break;
			 	
				
			case 39:									//83='Pfeil nach rechts'
				fireright = false;
				break;
		
			case 37: 									//65='Pfeil nach links'
				fireleft = false;
				break;
			}
					
		}
		
		move = 0;
		fire = 0;
		
		if (up) move++;								//Zaehle Anzahl gleichzeitig gedrueckter Bewegungstasten
		if (down) move++;
		if (left) move++;
		if (right) move++;
		
		if (move == 0) 	logic.setDirection(CoreLogic.NONE);
		
		if (move == 1) {
			if (up) 		logic.setDirection(CoreLogic.UP);
			else if (down)	logic.setDirection(CoreLogic.DOWN);
			else if (right)	logic.setDirection(CoreLogic.RIGHT);
			else if (left)	logic.setDirection(CoreLogic.LEFT);
		}
		
		else if (move == 2) {
			if (up) {
				if (down)		logic.setDirection(CoreLogic.NONE);
				else if (right)	logic.setDirection(CoreLogic.UPRIGHT);
				else			logic.setDirection(CoreLogic.UPLEFT);
			}
			else if (down) {
				if (right)		logic.setDirection(CoreLogic.DOWNRIGHT);
				else			logic.setDirection(CoreLogic.DOWNLEFT);
			}
			else
				logic.setDirection(CoreLogic.NONE);
		}
		
		else if (move == 3) {
			if (up) {
				if (down) {
					if (right) 	logic.setDirection(CoreLogic.RIGHT);
					else		logic.setDirection(CoreLogic.LEFT);
				}
				else
					logic.setDirection(CoreLogic.UP);
			}
			
			else if (down) 
				logic.setDirection(CoreLogic.DOWN);
		}
		
		else
			logic.setDirection(CoreLogic.NONE);

		
		if (fireup) fire++;								//Zaehle Anzahl gleichzeitig gedrueckter Bewegungstasten
		if (firedown) fire++;
		if (fireleft) fire++;
		if (fireright) fire++;
		
		if (fire == 0) 	logic.setFireDirection(CoreLogic.FIRENONE);
		
		if (fire == 1) {
			if (fireup) 		logic.setFireDirection(CoreLogic.FIREUP);
			else if (firedown)	logic.setFireDirection(CoreLogic.FIREDOWN);
			else if (fireright)	logic.setFireDirection(CoreLogic.FIRERIGHT);
			else if (fireleft)	logic.setFireDirection(CoreLogic.FIRELEFT);
		}
		
		else if (fire == 2) {
			if (fireup) {
				if (firedown)		logic.setFireDirection(CoreLogic.FIRENONE);
				else if (fireright)	logic.setFireDirection(CoreLogic.FIREUPRIGHT);
				else			logic.setFireDirection(CoreLogic.FIREUPLEFT);
			}
			else if (firedown) {
				if (fireright)		logic.setFireDirection(CoreLogic.FIREDOWNRIGHT);
				else			logic.setFireDirection(CoreLogic.FIREDOWNLEFT);
			}
			else
				logic.setFireDirection(CoreLogic.FIRENONE);
		}
		
		else if (fire == 3) {
			if (fireup) {
				if (firedown) {
					if (fireright) 	logic.setFireDirection(CoreLogic.FIRERIGHT);
					else		logic.setFireDirection(CoreLogic.FIRELEFT);
				}
				else
					logic.setFireDirection(CoreLogic.FIREUP);
			}
			
			else if (firedown) 
				logic.setFireDirection(CoreLogic.FIREDOWN);
		}
		
		else
			logic.setFireDirection(CoreLogic.FIRENONE);

		return false;
	}
	
}
