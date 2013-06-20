package hhu.propra_2013.gruppe_13;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;


class CoreGame_IO implements KeyEventDispatcher {
	private int		move = 0;
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private int		fire = 0;
	private boolean fireup = false;
	private boolean firedown = false;
	private boolean fireleft = false;
	private boolean fireright = false;



	
	// Logic used by the class, set within the constructor
	CoreLogic logic;
	CoreGame_IO(CoreLogic inLogic) {
		logic =inLogic;
	}

	@Override //reacts to any key event no matter where the focus is. Thus it doesn't matter in what order objects are drawn to the screen
	public boolean dispatchKeyEvent(KeyEvent e) {

	
		
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			move = 0;
			fire = 0;
			if (up) move++;								//Zaehle Anzahl gleichzeitig gedrueckter Bewegungstasten
			if (down) move++;
			if (left) move++;
			if (right) move++;
			if (fireup) fire++;							//Zaehle Anzahl gleichzeitig gedrueckter Feuertasten
			if (firedown) fire++;
			if (fireleft) fire++;
			if (fireright) fire++;

			
			switch (e.getKeyCode()) {
			case 87:									//Wenn Taste für Up gedrueckt wird 87='w'		
				up = true;
				if (move == 0){							//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setUp(true);
				}else if (move == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist
					if (down){
						logic.setDown(false);			//Wenn Down vorher gedrueckt war wird es auf false gesetzt damit sich die Figur nicht bewegt 
					}else if (left){
						logic.setLeft(false);			//Wenn Left vorher gedrueckt war wird Left auf false und Upleft auf true gesetzt 
						logic.setUpLeft(true);	
					}else if (right){
						logic.setRight(false);			//Wenn Left vorher gedrueckt war wird Left auf false und Upleft auf true gesetzt 
						logic.setUpRight(true);
					} 				
				}else if (move == 2)					//Wenn vorher zwei Bewegungstaste gedrueckt ist
				{
					if (down && left)
					{
						logic.setDownLeft(false);
						logic.setLeft(true);
					}else if (down && right)
					{
						logic.setRight(true);						
					}else if (left && right)
					{
						logic.setUp(true);
					}
				}else if (move == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist
					logic.setDown(false);
				}
				
			break;
				
			case 83:									//Wenn Taste für Down gedrueckt wird 83='s'
				down = true;				
				if (move == 0){ 						//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setDown(true);
				}else if (move == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist
					if (up){
						logic.setUp(false);	
					}else if (left){
						logic.setLeft(false);
						logic.setDownLeft(true);	
					}else if (right){
						logic.setRight(false);
						logic.setDownRight(true);
					} 				
				}else if (move == 2){					//Wenn vorher zwei Bewegungstaste gedrueckt ist
					if (up && left){
						logic.setUpLeft(false);
						logic.setLeft(true);
					}else if (up && right){
						logic.setUpRight(false);
						logic.setRight(true);						
					}else if (left && right){
						logic.setDown(true);
					}
				}else if (move == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist				
					logic.setUp(false);
				}
				break;

			case 68: 									//Wenn Taste für Right gedrueckt wird 68='d'
				right = true;
				if (move == 0){ 						//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setRight(true);
				}else if (move == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist				
					if (left){
						logic.setLeft(false);	
					}else if (up){
						logic.setUp(false);
						logic.setUpRight(true);	
					}else if (down){
						logic.setDown(false);
						logic.setDownRight(true);
					} 				
				}else if (move == 2){					//Wenn vorher zwei Bewegungstaste gedrueckt ist
					if (up && left){
						logic.setUpLeft(false);
						logic.setUp(true);
					}else if (down && left){
						logic.setDownLeft(false);
						logic.setDown(true);						
					}else if (up && down){
						logic.setRight(true);
					}
				}else if (move == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist	
						logic.setLeft(false);
				}	
				break;

			case 65: 									//Wenn Taste für Right gedrueckt wird 65='a'
				left = true;
				if (move == 0){ 						//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setLeft(true);
				}else if (move == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist			
					if (right){
						logic.setRight(false);	
					}else if (up){
						logic.setUp(false);
						logic.setUpLeft(true);	
					}else if (down){
						logic.setDown(false);
						logic.setDownLeft(true);
					} 				
				}else if (move == 2){					//Wenn vorher zwei Bewegungstaste gedrueckt ist		
					if (up && right){
						logic.setUpRight(false);
						logic.setUp(true);
					}else if (down && right){
						logic.setDownRight(false);
						logic.setDown(true);						
					}else if (up && down){
						logic.setLeft(true);
					}
				}else if (move == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist	
						logic.setRight(false);
				}	
				break;
				
			case 38:									//38='Pfeil nach oben'
				fireup = true;
				if (fire == 0){							//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setFireUp(true);
				}else if (fire == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist
					if (firedown){
						logic.setFireDown(false);			//Wenn Down vorher gedrueckt war wird es auf false gesetzt damit sich die Figur nicht bewegt 
					}else if (fireleft){
						logic.setFireLeft(false);			//Wenn Left vorher gedrueckt war wird Left auf false und Upleft auf true gesetzt 
						logic.setFireUpLeft(true);	
					}else if (fireright){
						logic.setFireRight(false);			//Wenn Left vorher gedrueckt war wird Left auf false und Upleft auf true gesetzt 
						logic.setFireUpRight(true);
					} 				
				}else if (fire == 2)					//Wenn vorher zwei Bewegungstaste gedrueckt ist
				{
					if (firedown && fireleft)
					{
						logic.setFireDownLeft(false);
						logic.setFireLeft(true);
					}else if (firedown && fireright)
					{
						logic.setFireRight(true);						
					}else if (fireleft && fireright)
					{
						logic.setFireUp(true);
					}
				}else if (fire == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist
					logic.setFireDown(false);
				}
				break;
				
			case 40: 									//68='Pfeil nach unten'
				firedown = true;				
				if (fire == 0){ 						//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setFireDown(true);
				}else if (fire == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist
					if (fireup){
						logic.setFireUp(false);	
					}else if (fireleft){
						logic.setFireLeft(false);
						logic.setFireDownLeft(true);	
					}else if (fireright){
						logic.setFireRight(false);
						logic.setFireDownRight(true);
					} 				
				}else if (fire == 2){					//Wenn vorher zwei Bewegungstaste gedrueckt ist
					if (fireup && fireleft){
						logic.setFireUpLeft(false);
						logic.setFireLeft(true);
					}else if (fireup && fireright){
						logic.setFireUpRight(false);
						logic.setFireRight(true);						
					}else if (fireleft && fireright){
						logic.setFireDown(true);
					}
				}else if (fire == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist				
					logic.setFireUp(false);
				}
				break;
				
			case 39:									//83='Pfeil nach rechts'
				fireright = true;
				if (fire == 0){ 						//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setFireRight(true);
				}else if (fire == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist				
					if (fireleft){
						logic.setFireLeft(false);	
					}else if (fireup){
						logic.setFireUp(false);
						logic.setFireUpRight(true);	
					}else if (firedown){
						logic.setFireDown(false);
						logic.setFireDownRight(true);
					} 				
				}else if (fire == 2){					//Wenn vorher zwei Bewegungstaste gedrueckt ist
					if (fireup && fireleft){
						logic.setFireUpLeft(false);
						logic.setFireUp(true);
					}else if (firedown && fireleft){
						logic.setFireDownLeft(false);
						logic.setFireDown(true);						
					}else if (fireup && firedown){
						logic.setFireRight(true);
					}
				}else if (fire == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist	
						logic.setFireLeft(false);
				}	
				break;
			 	
			case 37: 									//65='Pfeil nach links'
				fireleft = true;
				if (fire == 0){ 						//Wenn vorher keine Bewegungstaste gedrueckt ist
					logic.setFireLeft(true);
				}else if (fire == 1){					//Wenn vorher eine Bewegungstaste gedrueckt ist			
					if (fireright){
						logic.setFireRight(false);	
					}else if (fireup){
						logic.setFireUp(false);
						logic.setFireUpLeft(true);	
					}else if (firedown){
						logic.setFireDown(false);
						logic.setFireDownLeft(true);
					} 				
				}else if (fire == 2){					//Wenn vorher zwei Bewegungstaste gedrueckt ist		
					if (fireup && fireright){
						logic.setFireUpRight(false);
						logic.setFireUp(true);
					}else if (firedown && fireright){
						logic.setFireDownRight(false);
						logic.setFireDown(true);						
					}else if (fireup && firedown){
						logic.setFireLeft(true);
					}
				}else if (fire == 3){					//Wenn vorher drei Bewegungstaste gedrueckt ist	
						logic.setFireRight(false);
				}	
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
			case 77:
				logic.setShowMap(true);
				break;
			}						
			
		} 
		
		else if (e.getID() == KeyEvent.KEY_RELEASED) {

			move = 0;
			if (up) move++;								//Zaehle Anzahl gleichzeitig gedrueckter Bewegungstasten
			if (down) move++;
			if (left) move++;
			if (right) move++;
			fire = 0;
			if (fireup) fire++;							//Zaehle Anzahl gleichzeitig gedrueckter Feuertasten
			if (firedown) fire++;
			if (fireleft) fire++;
			if (fireright) fire++;
			
			switch (e.getKeyCode()) {
			case 87:									//Wenn Taste für Up geloest wird 87='w'
				up = false;
				logic.setUp(false);						//setzte alle Bewegungsrichtungen in der Logic auf false (funzt sonnst noch nicht) 
				logic.setUpRight(false);
				logic.setUpLeft(false);
				logic.setDown(false);
				logic.setDownRight(false);
				logic.setDownLeft(false);
				logic.setRight(false);
				logic.setLeft(false);
				if (move == 1){							//Wenn vorher eine Taste gedrueckt war 
					logic.setUp(false);
				}else if (move == 2){					//Wenn vorher zwei Taste gedrueckt war 
					if (down){
						logic.setDown(true);
					}else if (left){
						logic.setUpLeft(false);
						logic.setLeft(true);
					}else if (right){
						logic.setUpRight(false);
						logic.setRight(true);
					}
				}else if (move == 3){					//Wenn vorher drei Taste gedrueckt war 
					if (down && left){
						logic.setLeft(false);
						logic.setDownLeft(true);
					}else if (down && right){
						logic.setRight(false);
						logic.setDownRight(true);
					}else if (left && right){
						logic.setUp(false);
					}					
				}else if (move == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setDown(true);
				}
				break;
				
			case 83:									//Wenn Taste für Down geloest wird 83='s'
			 	down = false;
				logic.setUp(false);
				logic.setUpRight(false);
				logic.setUpLeft(false);
				logic.setDown(false);
				logic.setDownRight(false);
				logic.setDownLeft(false);
				logic.setRight(false);
				logic.setLeft(false);
			 	if (move == 1){							//Wenn vorher eine Taste gedrueckt war 
			 		logic.setDown(false);
			 	}else if (move == 2){					//Wenn vorher zwei Taste gedrueckt war 
			 		if (up){
			 			logic.setUp(true);
			 		}else if (left){
			 			logic.setDownLeft(false);
			 			logic.setLeft(true);
			 		}else if (right){
			 			logic.setDownRight(false);
			 			logic.setRight(true);
			 		}
			 	}else if (move == 3){					//Wenn vorher drei Taste gedrueckt war 
			 		if (up && left){
						logic.setLeft(false);
						logic.setUpLeft(true);
					}else if (up && right){
						logic.setRight(false);
						logic.setUpRight(true);
					}else if (left && right){
						logic.setDown(false);
					}
			 	}else if (move == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setUp(true);			 		
			 	}
			 	break;
			 	
			case 68: 									//Wenn Taste für Right geloest wird 68='d'
				right = false;
				logic.setUp(false);
				logic.setUpRight(false);
				logic.setUpLeft(false);
				logic.setDown(false);
				logic.setDownRight(false);
				logic.setDownLeft(false);
				logic.setRight(false);
				logic.setLeft(false);
				if (move == 1){							//Wenn vorher eine Taste gedrueckt war 
					logic.setRight(false);
				}else if (move == 2){					//Wenn vorher zwei Taste gedrueckt war 	
					if (left){
						logic.setLeft(true);
					}else if (up){
						logic.setUpRight(false);
						logic.setUp(true);
					}else if (down){
						logic.setDownRight(false);
						logic.setDown(true);
					}
				}else if (move == 3){					//Wenn vorher drei Taste gedrueckt war 
			 		if (left && up){
						logic.setUp(false);
						logic.setUpLeft(true);
					}else if (left && down){
						logic.setDown(false);
						logic.setDownLeft(true);
					}else if (up && down){
						logic.setRight(false);
					}
				}else if (move == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setLeft(true);
				}
				break;
				
			case 65: 									//Wenn Taste für Left geloest wird 65='a'
				left = false;
				logic.setUp(false);
				logic.setUpRight(false);
				logic.setUpLeft(false);
				logic.setDown(false);
				logic.setDownRight(false);
				logic.setDownLeft(false);
				logic.setRight(false);
				logic.setLeft(false);
				if (move == 1){							//Wenn vorher eine Taste gedrueckt war 
					logic.setLeft(false);
				}else if (move == 2){					//Wenn vorher zwei Taste gedrueckt war 	
					if (right){
						logic.setRight(true);
					}else if (up){
						logic.setUpLeft(false);
						logic.setUp(true);
					}else if (down){
						logic.setDownLeft(false);
						logic.setDown(true);
					}
				}else if (move == 3){					//Wenn vorher drei Taste gedrueckt war 
			 		if (right && up){
						logic.setUp(false);
						logic.setUpRight(true);
					}else if (right && down){
						logic.setDown(false);
						logic.setDownRight(true);
					}else if (up && down){
						logic.setRight(false);
					}
				}else if (move == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setRight(true);
				}
				break;
				
			case 38:									//38='Pfeil nach oben'
				fireup = false;
				logic.setFireUp(false);						//setzte alle Bewegungsrichtungen in der Logic auf false (funzt sonnst noch nicht) 
				logic.setFireUpRight(false);
				logic.setFireUpLeft(false);
				logic.setFireDown(false);
				logic.setFireDownRight(false);
				logic.setFireDownLeft(false);
				logic.setFireRight(false);
				logic.setFireLeft(false);
				if (fire == 1){							//Wenn vorher eine Taste gedrueckt war 
					logic.setFireUp(false);
				}else if (fire == 2){					//Wenn vorher zwei Taste gedrueckt war 
					if (firedown){
						logic.setFireDown(true);
					}else if (fireleft){
						logic.setFireUpLeft(false);
						logic.setFireLeft(true);
					}else if (fireright){
						logic.setFireUpRight(false);
						logic.setFireRight(true);
					}
				}else if (fire == 3){					//Wenn vorher drei Taste gedrueckt war 
					if (firedown && fireleft){
						logic.setFireLeft(false);
						logic.setFireDownLeft(true);
					}else if (firedown && fireright){
						logic.setFireRight(false);
						logic.setFireDownRight(true);
					}else if (fireleft && fireright){
						logic.setFireUp(false);
					}					
				}else if (fire == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setFireDown(true);
				}
				break;
			
			case 40: 									//68='Pfeil nach unten'
			 	firedown = false;
				logic.setFireUp(false);
				logic.setFireUpRight(false);
				logic.setFireUpLeft(false);
				logic.setFireDown(false);
				logic.setFireDownRight(false);
				logic.setFireDownLeft(false);
				logic.setFireRight(false);
				logic.setFireLeft(false);
			 	if (fire == 1){							//Wenn vorher eine Taste gedrueckt war 
			 		logic.setFireDown(false);
			 	}else if (fire == 2){					//Wenn vorher zwei Taste gedrueckt war 
			 		if (fireup){
			 			logic.setFireUp(true);
			 		}else if (fireleft){
			 			logic.setFireDownLeft(false);
			 			logic.setFireLeft(true);
			 		}else if (fireright){
			 			logic.setFireDownRight(false);
			 			logic.setFireRight(true);
			 		}
			 	}else if (fire == 3){					//Wenn vorher drei Taste gedrueckt war 
			 		if (fireup && fireleft){
						logic.setFireLeft(false);
						logic.setFireUpLeft(true);
					}else if (fireup && fireright){
						logic.setFireRight(false);
						logic.setFireUpRight(true);
					}else if (fireleft && fireright){
						logic.setFireDown(false);
					}
			 	}else if (fire == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setFireUp(true);			 		
			 	}
			 	break;
			 	
				
			case 39:									//83='Pfeil nach rechts'
				fireright = false;
				logic.setFireUp(false);
				logic.setFireUpRight(false);
				logic.setFireUpLeft(false);
				logic.setFireDown(false);
				logic.setFireDownRight(false);
				logic.setFireDownLeft(false);
				logic.setFireRight(false);
				logic.setFireLeft(false);
				if (fire == 1){							//Wenn vorher eine Taste gedrueckt war 
					logic.setFireRight(false);
				}else if (fire == 2){					//Wenn vorher zwei Taste gedrueckt war 	
					if (fireleft){
						logic.setFireLeft(true);
					}else if (fireup){
						logic.setFireUpRight(false);
						logic.setFireUp(true);
					}else if (firedown){
						logic.setFireDownRight(false);
						logic.setFireDown(true);
					}
				}else if (fire == 3){					//Wenn vorher drei Taste gedrueckt war 
			 		if (fireleft && fireup){
						logic.setFireUp(false);
						logic.setFireUpLeft(true);
					}else if (fireleft && firedown){
						logic.setFireDown(false);
						logic.setFireDownLeft(true);
					}else if (fireup && firedown){
						logic.setFireRight(false);
					}
				}else if (fire == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setFireLeft(true);
				}
				break;
		
			case 37: 									//65='Pfeil nach links'
				fireleft = false;
				logic.setFireUp(false);
				logic.setFireUpRight(false);
				logic.setFireUpLeft(false);
				logic.setFireDown(false);
				logic.setFireDownRight(false);
				logic.setFireDownLeft(false);
				logic.setFireRight(false);
				logic.setFireLeft(false);
				if (fire == 1){							//Wenn vorher eine Taste gedrueckt war 
					logic.setFireLeft(false);
				}else if (fire == 2){					//Wenn vorher zwei Taste gedrueckt war 	
					if (fireright){
						logic.setFireRight(true);
					}else if (fireup){
						logic.setFireUpLeft(false);
						logic.setFireUp(true);
					}else if (firedown){
						logic.setFireDownLeft(false);
						logic.setFireDown(true);
					}
				}else if (fire == 3){					//Wenn vorher drei Taste gedrueckt war 
			 		if (fireright && fireup){
						logic.setFireUp(false);
						logic.setFireUpRight(true);
					}else if (fireright && firedown){
						logic.setFireDown(false);
						logic.setFireDownRight(true);
					}else if (fireup && firedown){
						logic.setFireRight(false);
					}
				}else if (fire == 4){					//Wenn vorher vier Taste gedrueckt war 
					logic.setFireRight(true);
				}
				break;
			}
					
		}
		return false;
	}
	
}
