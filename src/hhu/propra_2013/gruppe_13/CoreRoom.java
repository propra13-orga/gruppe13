package hhu.propra_2013.gruppe_13;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class CoreRoom {

	//Geerbtes
	Figure 		figure;
	
	//Sachen die jeder Raum weiß
	ArrayList<CoreGameObjects> content; //die alte innere Array list, enthält alle Objekte im Raum
	boolean isBossRoom, isShop, isFinished;

	
	//Variablen zur Erzeugung
	int element , column, line , dest;
	int randomNumber; //speichert die Zufallsvariablevariable in der Raumerzeugung, je nachdem ob es ein  bossraum ist oder nicht
	
	
	//Konstruktor
	CoreRoom(Figure inFigure){
		figure = inFigure;
	}
	
	
	//liest den Inhalt des Raums aus einer Datei ein
	public void buildRoom(String name){
		
		//Konstanten anpassen sobald es mehr Räume gibt!!! TODO: Automatisieren
		//festlegen welche Raumliste der Builder durchgeht
		if (name == "Raum"){
			int randomNumber =(int)(8*Math.random());
		}
		else if (name == "BossRaum"){
			int randomNumber =(int)(3*Math.random());	
			isBossRoom = true;
		}
		else if (name == "Shop"){
			int randomNumber = 0;
			isShop = true;
		}
		else if (name == "Start"){
			int randomNumber = 0;
		}
		
		try {
			InputStream roomStream = new FileInputStream("Level/"+name+randomNumber+".txt");
			Reader roomReader = new InputStreamReader (roomStream);
			
			element = 0;
			column = 0; 
			line = 0;	
			dest = 0;
			while ((element = roomReader.read()) != -1){ //Goes trough the whole raumX.txt, and spawns Objects at their Positions
				
				switch (element) { 	//ASCII: W=87 D=68 E=69
				case 'W':			//In order of probability
					content.add(new MISCWall(column-1+0.5, line-1+0.5, 1, 1, 1)); 	//-1 because the top left corner seems to have
					break;											//the coordinates 1:1
					
				case 'E':
					content.add(new EnemyMelee(column-1+0.5, line-1+0.5, 1, 1, figure, 0));
					break;

				case 'D': //looks where the door is, then sets destination accordingly
					//I have no clue why this works
					if (line == 0) 	{dest = 0;} //Door is on the upper edge of the field, door should lead up
					if (line == 13)	{dest = 2;} //Door is on the bottom edge of the field, door should lead down
					if (column==23)	{dest = 1;} //Door is on the right edge of the field, door should lead right
					if (column==0) 	{dest = 3;} //Door is on the left edge of the field, door should lead left
				
					content.add(new MISCDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, true, true, dest)); //creating door with correct destination
					break;	
						
				case 'I':
					content.add(new ItemCupACoffee(column-1+0.5, line-1+0.5, 1, 1, 1));
					break;
				
				case 'N':
					content.add(new MISCDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, true, true,4));//'Door' leads to the next floor
					break;
					
				case 'T': 
					content.add(new MISCTarget(column-1+0.5, line-1+0.5,1,1,1));
					break;
					
				case 'C':
					//to add NPC
					break;
					
				}
				column++; //sets column up for the next cycle of the switch-case
					
				if (column==25){ //since we use 0-24 chars per line, the 25th should trigger the next line
					column = 0;
					line++;
				}
			}
			
			roomReader.close();
			roomStream.close();
			
		} catch (IOException e) {
			System.out.println("File not found, system exiting.");
			System.exit(1);
		}

	}
	//Getter
	//***********************************************************************************************************
	boolean getBossRoom(){
		return isBossRoom;
	}
	
	boolean getShop(){
		return isShop;
	}
	
	boolean getFinished(){
		return isFinished;
	}
	//***********************************************************************************************************
	//Setter
	//***********************************************************************************************************
	void setFinished(boolean inFinished){
		isFinished = inFinished;
		return;
	}
}