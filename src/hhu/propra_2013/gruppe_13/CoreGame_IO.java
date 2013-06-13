package hhu.propra_2013.gruppe_13;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;


class CoreGame_IO implements KeyEventDispatcher {
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
//	private boolean upright = false;
//	private boolean upleft = false;
//	private boolean downright = false;
//	private boolean downleft = false;
	private boolean north = false;
	private boolean east = false;
	private boolean south = false;
	private boolean west = false;
//	private boolean northwest = false;
//	private boolean northeast = false;
//	private boolean southwest = false;
//	private boolean southeast = false;
	
	// Logic used by the class, set within the constructor
	CoreLogic logic;
	CoreGame_IO(CoreLogic inLogic) {
		logic =inLogic;
	}

	@Override //reacts to any key event no matter where the focus is. Thus it doesn't matter in what order objects are drawn to the screen
	public boolean dispatchKeyEvent(KeyEvent e) {

	
		
		if (e.getID() == KeyEvent.KEY_PRESSED) {

			switch (e.getKeyCode()) {
			case 87:									//87='w'
				up = true;
				if (down){
					logic.setDown(false);	
				}else if (left){
					logic.setLeft(false);
					logic.setUpLeft(true);	
				}else if (right){
					logic.setRight(false);
					logic.setUpRight(true);
				}else logic.setUp(true);				
				break;
				
			case 83:									//83='s'
				down = true;
				if (up){
					logic.setUp(false);	
				}else if (left){
					logic.setLeft(false);
					logic.setDownLeft(true);	
				}else if (right){
					logic.setRight(false);
					logic.setDownRight(true);
				}else logic.setDown(true);					
			 	break;
			 	
			case 68: 									//68='d'
				right = true;
				if (left){
					logic.setLeft(false);	
				}else if (up){
					logic.setUp(false);
					logic.setUpRight(true);	
				}else if (down){
					logic.setDown(false);
					logic.setDownRight(true);
				}else logic.setRight(true);					
				break;
				
			case 65: 									//65='a'
				left = true;
				if (right){
					logic.setRight(false);	
				}else if (up){
					logic.setUp(false);
					logic.setUpLeft(true);	
				}else if (down){
					logic.setDown(false);
					logic.setDownLeft(true);
				}else logic.setLeft(true);				
				break;
				
			case 38:									//38='Pfeil nach oben'
				north = true;
				if (south){
					logic.setSouth(false);	
				}else if (west){
					logic.setWest(false);
					logic.setNorthwest(true);	
				}else if (east){
					logic.setEast(false);
					logic.setNortheast(true);
				}else logic.setNorth(true);
				break;
				
			case 39:									//83='Pfeil nach rechts'
				east = true;
				if (west){
					logic.setWest(false);	
				}else if (north){
					logic.setNorth(false);
					logic.setNortheast(true);	
				}else if (south){
					logic.setSouth(false);
					logic.setSoutheast(true);
				}else logic.setEast(true);
			 	break;
			 	
			case 40: 									//68='Pfeil nach unten'
				south = true;
				if (north){
					logic.setNorth(false);	
				}else if (west){
					logic.setWest(false);
					logic.setSouthwest(true);	
				}else if (east){
					logic.setEast(false);
					logic.setSoutheast(true);
				}else logic.setSouth(true);
				break;
				
			case 37: 									//65='Pfeil nach links'
				west = true;
				if (east){
					logic.setEast(false);	
				}else if (north){
					logic.setNorth(false);
					logic.setNorthwest(true);	
				}else if (south){
					logic.setSouth(false);
					logic.setSouthwest(true);
				}else logic.setWest(true);
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

//			if(up || down || left || right){			//Abfrage ob überhaupt eine Bewegungstaste gedrückt ist.
//				if (up && !down){
//					if (left && !right){
//						logic.setUpLeft(true);
//						logic.setUp(false);
//						upleft = true;
//						left = false;
//						up = false;
//					}else if (right && !left){
//						logic.setUpRight(true);
//						logic.setUp(false);
//						upright = true;
//						right = false;
//						up = false;
//					}else if (!right && !left){
//						logic.setUp(true);
//					}
//				}else if (down && !up){
//					if (left && !right){
//						logic.setDownLeft(true);
//						logic.setDown(false);
//						downleft = true;
//						left = false;
//						down = false;
//					}else if (right && !left){
//						logic.setDownRight(true);
//						logic.setDown(false);
//						downright = true;
//						right = false;
//						down = false;
//					}else if (!right && !left){
//						logic.setDown(true);
//					}
//				}else if (!down && !up){
//					if (left && !right){
//						logic.setLeft(true);
//					}else if (right && !left){
//						logic.setRight(true);
//					}
//				}
//			}
			
			
		} 
		
		else if (e.getID() == KeyEvent.KEY_RELEASED) {

//			boolean reup = false;
//			boolean redown = false;
//			boolean releft = false;
//			boolean reright = false;
			
			switch (e.getKeyCode()) {
			case 87:									//87='w'
				up = false;
				logic.setUp(false);
				if (down){
					logic.setDown(true);
				}else if (left){
					logic.setUpLeft(false);
					logic.setLeft(true);
				}else if (right){
					logic.setUpRight(false);
					logic.setRight(true);
				}
				break;
				
			case 83:									//83='s'
			 	down = false;
				logic.setDown(false);
				if (up){
					logic.setUp(true);
				}else if (left){
					logic.setDownLeft(false);
					logic.setLeft(true);
				}else if (right){
					logic.setDownRight(false);
					logic.setRight(true);
				}
			 	break;
			 	
			case 68: 									//68='d'
				right = false;
				logic.setRight(false);
				if (left){
					logic.setLeft(true);
				}else if (up){
					logic.setUpRight(false);
					logic.setUp(true);
				}else if (down){
					logic.setDownRight(false);
					logic.setDown(true);
				}
				break;
				
			case 65: 									//65='a'
				left = false;
				logic.setLeft(false); 
				if (right){
					logic.setRight(true);
				}else if (up){
					logic.setUpLeft(false);
					logic.setUp(true);
				}else if (down){
					logic.setDownLeft(false);
					logic.setDown(true);
				}
				break;
			case 38:									//38='Pfeil nach oben'
				north = true;
				break;
			case 39:									//83='Pfeil nach rechts'
				east = true;
			 	break;
			case 40: 									//68='Pfeil nach unten'
				south = true;
				break;
			case 37: 									//65='Pfeil nach links'
				west = true;
				break;	
			}
			
//			if (upright){
//				if (!reright){
//					logic.setUpRight(false);  
//					logic.setUp(true);
//					upright = false;
//					up = true;
//				}else{
//					logic.setUpRight(false);  
//					logic.setRight(true);
//					upright = false;
//					right = true;
//				}
//			}else if (upleft){
//				if (!releft){
//					logic.setUpLeft(false);  
//					logic.setUp(true);
//					upleft = false;
//					up = true;
//				}else{
//					logic.setUpLeft(false);  
//					logic.setLeft(true);
//					upleft = false;
//					left = true;
//				}
//			}else if (downright){
//				if (!reright){
//					logic.setDownRight(false);  
//					logic.setDown(true);
//					downright = false;
//					down = true;
//				}else{
//					logic.setDownRight(false);  
//					logic.setRight(true);
//					downright = false;
//					right = true;
//				}
//			}else if (downleft){
//				if (!releft){
//					logic.setDownLeft(false);  
//					logic.setDown(true);
//					downleft = false;
//					down = true;
//				}else{
//					logic.setDownLeft(false);  
//					logic.setLeft(true);
//					downleft = false;
//					left = true;
//				}
//		
//			}
			
			
		}
		return false;
	}
	
}
