package hhu.propra_2013.gruppe_13;

public class CoreLevel {
	
	Figure figure;
	
	int stage = 0; //in welchen Level sind wir, nützlich für das Endziel 
	String boss; //damit man weiß was der Boss ist, hauptsächlich für den NPC
	
	int randomStartX = (int)(6*Math.random())+2;
	int randomStartY = (int)(6*Math.random())+2; //Variablen zum zufälligen setzen des Startraums
	
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
		stage = inStage;
		boss = inBoss;
		CoreRoom tempRoom = new CoreRoom(figure, stage, boss); //zwischenspeicher für einen Raum, zum hinzufügen benötigt
		CoreRoom level[][] = new CoreRoom[10][10]; //Sollte genug Platz für ausreichend seltsame Konstruktionen bieten, der erste Wert sei der X Wert
		boolean  construction[][] = new boolean[10][10];

		int maxRooms = 6+2*stage;
		int x,y; //aktuelle Koordinaten im Raum-Array
		
		int actRooms = 0; //Zahl der bereits existierend
		int probCounter =0;
		
		
		tempRoom.setType("Start");
		level[randomStartX][randomStartY] =  tempRoom;
		
		for (actRooms = 0;actRooms<maxRooms;actRooms++){
			
		}
	}
		
		
		
	
	
}
