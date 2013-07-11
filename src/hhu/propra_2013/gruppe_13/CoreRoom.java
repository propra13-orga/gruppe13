
package hhu.propra_2013.gruppe_13;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Liest Räume aus Dateien aus und enthält Raumspezifische Informationen, sowie Methoden um diese auszulesen und zu verändern
 * @author 	Gruppe13
 * @see		CoreLevel
 * @see		CoreLogic
 */


class CoreRoom {

	private static final long serialVersionUID = 1L;
	//Sachen die jeder Raum weiß
	private ArrayList<CoreGameObjects> content; //die alte innere Array list, enthält alle Objekte im Raum
	private boolean 	isBossRoom, isShop;
	private boolean 	hasTopNeighbour, hasBottomNeighbour, hasLeftNeighbour, hasRightNeighbour;
	private boolean 	isFinished;//for later use in the map
	
	//Variablen zur Erzeugung
	private int 		element , column, line , dest;
	private int 		randomNumber; //speichert die Zufallsvariablevariable in der Raumerzeugung, je nachdem ob es ein  bossraum ist oder nicht
	private String 		type;
	private int 		stage; //Nummer des Levels, für den NPC
	private int			mode;
	private int 		anzahlRaum, anzahlBoss;
	
	// Stream and Reader for reading data from file
	private InputStream roomStream 	= null;
	private Reader roomReader		= null;
	
	/**
	 * Ein Raum enthält alle Objekte eines Raums, also alle Objekte die gleichzeitig auf dem Bildschirm sein können
	 * @param inStage			Levelnummer
	 * @param inBoss			Name des Levelbosses, derzeit ungenutzt
	 * @param inTopNeighbour	true: der Raum hat einen oberen Nachbarn false: er hat keinen
	 * @param inBottomNeighbour	true: der Raum hat einen unteren Nachbarn false: er hat keinen
	 * @param inLeftNeighbour	true: der Raum hat einen linken Nachbarn false: er hat keinen
	 * @param inRightNeighbour	true: der Raum hat einen rechten Nachbarn false: er hat keinen
	 * @param inMode			Schwierigkeitsgrad
	 * @param inanzahlRaum		Anzahl der Raumdateien als Obergrenze für die Zufallsauswahl der Datei aus der ein Raum generiert
	 * @param inanzahlBoss		Anzahl der Bossraumdateien als Obergrenzen für die Zufallsauswahl der Datei aus der ein Bossraum generiert wird
	 */
	
	
	
	// Constructor
	CoreRoom(int inStage, String inBoss, boolean inTopNeighbour, boolean inBottomNeighbour, boolean inLeftNeighbour, boolean inRightNeighbour, int inMode, int inanzahlRaum, int inanzahlBoss){
//		figure 				= inFigure;
		content 			= new ArrayList<CoreGameObjects>();
		stage 				= inStage;
		mode				= inMode;
		
		//festlegen wo die Nachbarn des Raums liegen, wird zum Türsetzen gebraucht
		hasTopNeighbour 	= inTopNeighbour;  
		hasBottomNeighbour 	= inBottomNeighbour;
		hasLeftNeighbour 	= inLeftNeighbour;
		hasRightNeighbour 	= inRightNeighbour;
		
		//Angeben der Anzahl der verschiedenen Räume für den Zufallsgenerator
		anzahlRaum = inanzahlRaum;
		anzahlBoss = inanzahlBoss;
	}
	
	/**
	 * Setzt den Typ des Raums (Start, Normal, Boss, Shop) fest, wird beim generieren der Räume in CoreLevel gesetzt
	 * @param inType Typ des Raums
	 * @see	CoreLevel
	 */
	
	void setType(String inType) {
		type = inType;
		return;
	}
	
	/**
	 * Generiert den Raum, Informationen wie Raumtyp, Stage, Boss werden vorher per Konstruktor/Setter festgelegt, daher keine Parameter
	 * Vorgehensweise:
	 * Je nach Raumtyp wird auf Basis der Anzahl der zur Verfügung stehenden txt-Dateien eine Zufallszahl gewählt
	 * Diese Datei wird dann ausgelesen und entsprechend ein Raum generiert
	 * @exception wirft IO Exceptions falls Raumdateien nicht vorhanden sind
	 */
	
	
	//liest den Inhalt des Raums aus einer Datei ein
	void buildRoom(){
		
		//Die Anzahl der möglichen Räume ist nun über CoreLevel.count(Boss)Raum bekannt und muss nichtmehr bei neuen Räumen im Code geändert werden
		//festlegen welche Raumliste der Builder durchgeht
		if (type == "Raum"){
			randomNumber =(int)(anzahlRaum*Math.random());
		}
		else if (type == "BossRaum"){
			randomNumber =(int)(anzahlBoss*Math.random());	
			isBossRoom = true;
		}
		else if (type == "Shop"){
			randomNumber = 0;
			isShop = true;
		}
		else if (type == "Start"){
			randomNumber = 0;
		}
		
	
		
		// First read all Walls into the ArrayList, that way Walls will  be drawn in  the background
		try {
			roomStream = new FileInputStream("Level/"+type+"/"+type+randomNumber+".txt");
			roomReader = new InputStreamReader (roomStream);
			
			element = 0;
			column = 0; 
			line = 0;	
			dest = 0;
			
			while ((element = roomReader.read()) != -1){ //Goes trough the whole raumX.txt, and spawns Objects at their Positions
				switch (element) { 	//ASCII: W=87 D=68 E=69
				case 'W':			
					content.add(new MiscWall(column-1+0.5, line-1+0.5, 1, 1, 1)); 	//-1 because the top left corner seems to have
					break;															//the coordinates 1:1
				}
				
				column++; //sets column up for the next cycle of the switch-case
				
				if (column==25){ //since we use 24 chars per line, the 25th should trigger the next line
					column = 0;
					line++;
				}
			}
		}
		catch (IOException e) {
			System.exit(1);
		} finally {
			try {
				roomReader.close();
			} catch (IOException e) {
			}
			try {
				roomStream.close();
			} catch (IOException e) {
			}
		}
		
		// Put all other stuff into the ArrayList
		try {
			roomStream = new FileInputStream("Level/"+type+"/"+type+randomNumber+".txt");
			roomReader = new InputStreamReader (roomStream);
			
			element = 0;
			column = 0; 
			line = 0;	
			dest = 0;
			
			while ((element = roomReader.read()) != -1){ //Goes trough the whole raumX.txt, and spawns Objects at their Positions
				
				switch (element) {
				
				case 'E':
					content.add(new EnemyRanged(column-1+0.5, line-1+0.5, 1, 1, Enemy.ENEMY_FIRE_SHOOTING, stage, mode));
					break;

				case 'D': //looks where the door is, then sets destination accordingly
					//I have no clue why this works
					if (line == 0 && hasTopNeighbour)		{dest = 0; content.add(new MiscDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, dest));} //Door is on the upper edge of the field, door should lead up
					if (line == 14 && hasBottomNeighbour)	{dest = 2; content.add(new MiscDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, dest));} //Door is on the bottom edge of the field, door should lead down
					if (column==23 && hasRightNeighbour)	{dest = 1; content.add(new MiscDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, dest));} //Door is on the right edge of the field, door should lead right
					if (column==0 && hasLeftNeighbour)		{dest = 3; content.add(new MiscDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, dest));} //Door is on the left edge of the field, door should lead left
					 //creating door with correct destination
					break;	
						
				case 'I':
					int randItem;
					randItem = (int)(4*Math.random());
					
					switch(randItem){
					
					case 0 :
						content.add(new ItemCupACoffee(column-1+0.5, line-1+0.5, 1, 1, 1));
						break;
						
					case 1 :
						content.add(new ItemChocolateBar(column-1+0.5, line-1+0.5, 1, 1, 1));
						break;
						
					case 2 :
						content.add(new ItemArmor(column-1+0.5, line-1+0.5, 1, 1, 1));
						break;
					case 3:
						content.add(new ItemImproveWeapon(column-1+0.5, line-1+0.5, 1, 1, 1));
						break;
					}
					
					break;
				
				case 'S':
					//content.add(new MISCDoor(column-1+0.5, line-1+0.5, 1, 1, 0.5, 4));//'Door' leads to the next floor
					break;
					
				case 'R': 
					content.add(new ItemResurrect(column-1+0.5, line-1+0.5,1,1));
					break;
					
				case 'G':
					content.add(new ItemMoney (column-1+0.5, line-1+0.5,1,1));
					break;
					
				case 'N':
					if (type == "Start"){
						content.add(new MiscNPC (column-1+0.5, line-1,1,1, "this is a stub",stage, "Start", this));
					}
					else {
						content.add(new MiscNPC (column-1+0.5, line-1+0.5,1,1, "this is a stub", stage, "Quest", this));
					}
					
					break;
				case 'F':
					content.add(new EnemyMelee(column-1+0.5, line-1+0.5, 1, 1, Enemy.ENEMY_FIGURE_RUN, stage, mode));

					break;
				case 'B':

					content.add(new EnemyBossMelee(column-1+0.5, line-1+0.5, 1 , 1 , Enemy.ENEMY_FIGURE_RUN, stage, this, mode));
					break;				
				}
				column++; //sets column up for the next cycle of the switch-case
					
				if (column==25){ //since we use 24 chars per line, the 25th should trigger the next line
					column = 0;
					line++;
				}
			}
			
		} catch (IOException e) {
			System.exit(1);
		} finally {
			try {
				roomReader.close();
			} catch (IOException e) {
			}
			try {
				roomStream.close();
			} catch (IOException e) {
			}
		}
		
	}
	
	//Getter
	//***********************************************************************************************************
	
	/**
	 * Gibt an ob ein Raum 'fertig' ist und die Türen geöffnet werden können
	 * @return true: der Raum ist abgearbeitet und die Türen können geöffnet werden false: der Raum enthält noch Gegner, de Türen sind geschlossen
	 */
	
	boolean getFinished(){
		return isFinished;
	}
	
	/**
	 * Gibt an ob es sich um einen Bossraum handelt
	 * @return true: es ist ein Bossraum false: kein Bossraum
	 */
	
	boolean getBossRoom(){
		return isBossRoom;
	}
	
	/**
	 * Gibt an ob es sich um einen Shop handelt
	 * @return true: es ist ein Shop false: kein Sjop
	 */
	
	boolean getShop(){
		return isShop;
	}
	
	/**
	 * Gibt die ArrayList der Objekte im Raum an
	 * @return Gibt die ArrayList von GameObjects zurück die in diesem Raum enthalten sind
	 * @see CoreLogic
	 */
	
	
	ArrayList<CoreGameObjects> getContent() {
		return content;
	}
	
	
//entfernt da momentan ungenutzt
//	boolean getTopNeighbour(){
//		return hasTopNeighbour;
//	}
//	
//	boolean getBottomNeighbour(){
//		return hasTopNeighbour;
//	}
//	
//	boolean getLeftNeighbour(){
//		return hasLeftNeighbour;
//	}
//	
//	boolean getRightNeighbour(){
//		return hasLeftNeighbour;
//	}
	
	/**
	 * Liefert den Schwierigkeitsgrad, wird vom NPC genutzt um passende Gegner zu spawnen
	 * @return Gibt den Schwierigkeitsgrad zurück
	 */
	
	int getMode(){
		return mode;
	}
	//***********************************************************************************************************
	//Setter
	//***********************************************************************************************************
	
	/**
	 * setzt fest ob der Raum abgeschlossen ist
	 * @param inFinished festlegen ob der Raum fertig ist oder nicht
	 */
	
	void setFinished(boolean inFinished){
		isFinished = inFinished;
		return;
	}
//entfernt da Momentan ungenutzt
//	void setTopNeighbour(boolean inTopNeighbour){
//		hasTopNeighbour = inTopNeighbour;
//		return;
//	}
//	
//	void setBottomNeighbour(boolean inBottomNeighbour){
//		hasBottomNeighbour = inBottomNeighbour;
//		return;
//	}
//	
//	void setLeftNeighbour(boolean inLeftNeighbour){
//		hasLeftNeighbour = inLeftNeighbour;
//		return;
//	}
//	
//	void setRightNeighbour(boolean inRightNeighbour){
//		hasRightNeighbour = inRightNeighbour;
//		return;
//	}
}
