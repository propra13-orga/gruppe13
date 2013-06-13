package hhu.propra_2013.gruppe_13;

public class CoreLevel {
	
	Figure figure;
	
	int stage = 0; //in welchen Level sind wir, nützlich für das Endziel 
	String boss; //damit man weiß was der Boss ist, hauptsächlich für den NPC
	
	int randomStartX = (int)(6*Math.random())+2;
	int randomStartY = (int)(6*Math.random())+2; //Variablen zum zufälligen setzen des Startraums
	
	//Array muss der Klasse bekannt sein um eine get Methode zu implementieren, wird beim Builder resettet 
	CoreRoom level[][] = new CoreRoom[10][10]; //Sollte genug Platz für ausreichend seltsame Konstruktionen bieten, der erste Wert sei der X Wert
	//Konstruktor
	CoreLevel(Figure inFigure){
		figure = inFigure;
	}
	
	//Getter
	//*************************************************************************************
	
	
	String getBoss(){
		return boss;
	}
	
	int getStage(){
		return stage;
	}
	
	int getStartX(){
		return randomStartX;
	}
	
	int getStartY(){
		return randomStartY;
	}
	
	CoreRoom getRoom(int inX, int inY){ //gibt einen Raum mit koordinaten X,Y zurück
		return level[inX][inY]; //falls das ganze Array erwünscht ist muss man auf der 'anderen seite' 2 for schleifen schachteln und durchlaufen lassen
	}
	//***************************************************************************************
	//Setter
	
	void setStage(int inStage){
		stage = inStage;
		return;
	}
	
	void setBoss(String inBoss){
		boss = inBoss;
		return;
	}
	//**********************************************************************************************
	
	//LevelBuilder
	void buildLevel(int inStage, String inBoss){//Stage beginnt bei 1 zu zählen
		//Hier initialisiert damit es nur ein Level in der Logik gibt das jedes mal neu gebaut wird
		System.out.println("X "+randomStartX+" Y "+randomStartY);
		//Hier wird das Raum Array resettet, da ich nicht bei jedem Aufruf ein neues (wie bei construction) spawnen kann
		for (int i = 0; i<10; i++){
			for (int j = 0; j < 10; j++){
				level[i][j] = null;
			}
		}
		stage = inStage;
		boss = inBoss;
		boolean tempTop, tempBottom, tempLeft, tempRight;//für die Bestimmung der Nachbarn eines Raums
		CoreRoom tempRoom;// = new CoreRoom(figure, stage, boss); //zwischenspeicher für einen Raum, zum hinzufügen benötigt

		boolean  construction[][] = new boolean[10][10]; //Erst Bestimmung der Positionen der Räume damit bei der Erzeugung die Positionen der Nachbarn bekannt sind

		int maxRooms = 6+2*stage;
		int x,y; //aktuelle Koordinaten im Raum-Array
		
		int actRooms = 0; //Zahl der bereits existierenden Räume
		int probCounter =0; //garantiert das alle X Möglichkeiten ein Raum entsteht damit die Schleife nicht ewig läuft
		
		construction[randomStartX][randomStartY] = true;

		//was im folgenden passieren soll:
		//gehe das construction array durch
		//prüfe ob ein Nachbar des aktuellen Feldes von einem Raum besetzt ist (deswegen werden die Ränder und Ecken speziell betrachtet, daher der aufgeblähte Code)
		//falls ^ja würfeln ob dort ein neuer Raum spawnt
		//falls ja actRooms++, repeat
		//falls nein probCounter++, bei einem bestimmten probCounter Wert wird garantiert ein Raum gespawnt
		
		while (actRooms <= maxRooms){ //Laufe bis alle Räume gesetzt sind
			for (y=0; y<10; y++){	//gehe Array durch
				for (x=0; x<10; x++){ //Beginne Fälle bei den Ecken, dnn müssen die nicht mehr bei den Rändern betrachtet werden (falls ich else if richtig verstanden habe)
					if (x == 0 && y == 0  && actRooms < maxRooms){    //obere linke Array Ecke
						if (construction[x+1][y] == true || construction[x][y+1] == true){ //falls es einen Nachbarn gibt
							if(Math.random()<0.3 || probCounter ==5) { //würfle ob es einen Raum geben soll, oder setze ihn falls die letzten 5 Fälle kein Raum gesetzt wurde
								construction[x][y] = true; //Raum setzen
								actRooms++; //es gibt nun einen Raum mehr
								probCounter = 0; //der Raum-Garantie-Counter wird resetet
							}
							else {probCounter++;} // falls es keinen Raum gibt wird der Raum-Garantie-Counter erhöht
						}
					}//weitere Zweige analog
					else if (x == 0 && y == 9 && actRooms < maxRooms) { //obere rechte Ecke
						if (construction[x+1][y] == true || construction[x][y-1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
					}
					else if (x == 9 && y ==0 && actRooms < maxRooms){ //rechter Array Rand
						if (construction[x-1][y] == true || construction[x][y+1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
					}
					else if (x == 9 && y == 9 && actRooms < maxRooms){ //unterer Array Rand
						if (construction[x-1][y] == true || construction[x][y-1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
					}
					else if (x != 0 && x != 9 && y == 0 && actRooms < maxRooms){ //oberer Rand
						if (construction[x+1][y] == true || construction[x-1][y] == true || construction[x][y+1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
					}
					else if (x != 0 && x != 9 && y == 9 && actRooms < maxRooms){ //unterer Rand
						if (construction[x+1][y] == true || construction[x-1][y] == true || construction[x][y-1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
					}
					else if (x == 0 && y != 0 && y != 9 && actRooms < maxRooms){ //linker Rand
						if (construction[x+1][y] == true || construction[x][y+1] == true || construction[x][y-1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
					}
					else if (x == 9 && y != 0 && y != 9 && actRooms < maxRooms){ //rechter Rand
						if (construction[x-1][y] == true || construction[x][y+1] == true || construction[x][y-1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
					}
					else if (x != 0 && x != 9 && y != 0 && y!= 9){ //irgendwo mittig
						if (construction[x+1][y] == true || construction[x-1][y] == true || construction[x][y+1] == true || construction[x][y-1] == true){
							if(Math.random()<0.3 || probCounter == 5) {
								construction[x][y] = true;
								actRooms++;
								probCounter = 0;
							}
							else {probCounter++;}
						}
						
					}
				}
			}	
		}
		//Ab hier enthält construction die 'Blaupause' des levels
		//jetzt wird construction in ein Raum Array umgesetzt
		for (x = 0; x<10; x++){
			for (y = 0; y < 10; y++){
				tempTop = false; //Reset der Nachbarschaftsvariablen
				tempBottom = false;
				tempLeft = false;
				tempRight = false;
				if (construction[x][y] == true){ //wieder mit ewiger Fallunterscheidung für Ränder und Ecken
					if (x != 0 && x != 9 && y == 0){ //obere Kante
						if (construction[x-1][y] == true){tempLeft = true;} //prüfe ob es nachbarn gibt
						if (construction[x+1][y] == true){tempRight = true;} //falls ja:
						if (construction[x][y+1] == true){tempBottom = true;}//spawne raum mit passenden Nachbarn
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x != 0 && x != 9 && y == 9){//untere Kante
						if (construction[x-1][y] == true){tempLeft = true;}
						if (construction[x+1][y] == true){tempRight = true;}
						if (construction[x][y-1] == true){tempTop = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x == 0 && y == 0){ //obere linke ecke
						if (construction[x+1][y] == true){tempRight = true;}
						if (construction[x][y-1] == true){tempBottom = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x == 9 && y == 0){ //obere rechte ecke
						if (construction[x-1][y] == true){tempLeft = true;}
						if (construction[x][y-1] == true){tempBottom = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x == 0 && y != 0 && y != 9){ //linker Rand
						if (construction[x+1][y] == true){tempRight = true;}
						if (construction[x][y+1] == true){tempTop = true;}
						if (construction[x][y-1] == true){tempBottom = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x == 9 && y != 0 && y != 9){ //rechter Rand
						if (construction[x-1][y] == true){tempLeft = true;}
						if (construction[x][y+1] == true){tempTop = true;}
						if (construction[x][y-1] == true){tempBottom = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x == 0 && y == 9){// untere linke ecke
						if (construction[x+1][y] == true){tempRight = true;}
						if (construction[x][y+1] == true){tempTop = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x == 9 && y == 9){ //untere rechte ecke
						if (construction[x-1][y] == true){tempLeft = true;}
						if (construction[x][y+1] == true){tempTop = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
					else if (x != 0 && x != 9 && y !=0 && y != 9){ //irgendwo mittig
						if (construction[x-1][y] == true){tempLeft = true;}
						if (construction[x+1][y] == true){tempRight = true;}
						if (construction[x][y+1] == true){tempTop = true;}
						if (construction[x][y-1] == true){tempBottom = true;}
						tempRoom = new CoreRoom(figure, stage, boss,tempTop, tempBottom, tempLeft, tempRight);
						level[x][y] = tempRoom;
						level[x][y].setType("Raum");
						level[x][y].buildRoom();
						//System.out.println("links "+tempLeft+" rechts "+tempRight+" oben "+tempTop+" unten "+tempBottom);
					}
				}
			}
		}
		System.out.println("Level Fertig!");
		for(y = 0;y <= 9;y++){
			for(x = 0;x <= 9;x++){
				System.out.print(construction [x][y] +"  ");
			}
			System.out.println();
		}
		//Ab jetzt ist das level Array fertig <- gelogen
		//TODO Bossraum und shop hinzufügen!!
		
		
	}
}
