package hhu.propra_2013.gruppe_13;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MISCNPC extends CoreGameObjects{
	//Variables  taken from enemy - some are maybe Hyper-Fluid (sic)
	private boolean talk;
	private int 	hp;
	private double 	x, y;
	private double	r;
	private double 	v_x, v_y;
	private double 	height, width;
	private int		strength;
	//private int 	type;
	private String 	text; 
	private int 	stage; 		//NPC should know which area he is in, so he can refer to the level Theme or something
	private String 	boss;	//NPC should know what the area boss is, so he can say funny stuff about him 
	private String 	stageone,stagetwo,stagethree;
	private long 	npcTalkTime;
	CoreRoom		currentRoom; //For spawning Enemies upon Collision
	
	private String	type;//Unterscheidung ob "Story" oder Quest NPC
	private String	questAssault;
	
 	
	//Constructor for NPC - TODO Think what the NPC should be able to do, and implement the useful thoughts
	MISCNPC(double initX, double initY, double initHeight, double initWidth, String inBoss, int inStage, String inType, CoreRoom inRoom){
		
		x = initX;
		y = initY;
		
		v_x = 0;
		v_y = 0;
		
		type = inType;
		
		currentRoom = inRoom;
		
		height = initHeight;
		width = initWidth;
		r = Math.max(width, height)+v_x*v_x+v_y*v_y;
		hp = 1; 		//should not be harmed at all, but GameObjects wants it kinda badly
		strength = 1;	//should harm anyone else either, but you never know when a NPC will snap...
		//Stuff to tell the NPC what he can talk about
		boss = inBoss;
		stage = inStage;
		
		text = " ";
		stageone = "Wow you started the game, congratulations /sarcasm."+"The Boss on this level is a red circle.";
		stagetwo = "Ok, so you managed to defeat the red circle, maybe you are able to defeat the next red circle.";
		stagethree = "I am kinda impressed now... maybe you are able to see the victory screen if you live after you fought the last red circle.";
		questAssault = "So you are really stupid enough to fall for our simple Trap. Kill it with Melee!";
	}
	
	
	//Methods from GameObjects - we probably won't need all of them

	/*-----------------------------------------------------------------------------------------------------*/
	//Getter
	@Override
	int getHP() {
		// TODO Auto-generated method stub
		return hp;
	}

	@Override
	double getPosX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	double getPosY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	double getRad() {
		// TODO Auto-generated method stub
		return r;
	}

	@Override
	double getVX() {
		// TODO Auto-generated method stub
		return v_x;
	}

	@Override
	double getVY() {
		// TODO Auto-generated method stub
		return v_y;
	}
	
	@Override
	double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}
	/*----------------------------------------------------------------------------------------------__*/
	//Setter
	@Override
	void setPos(double inX, double inY) {
		// TODO Auto-generated method stub
		x = inX;
		y = inY;
	}

	@Override
	void setSpeed(double inVX, double inVY) {
		// TODO Auto-generated method stub
		v_x = inVX;
		v_y = inVY;
	}

	@Override
	void setRad(double inR) {
		// TODO Auto-generated method stub
		r = inR;
	}

	@Override
	void setHP(int inHP) {
		// TODO Auto-generated method stub
		hp = inHP;
	}
	
	
	/*-------------------------------------------------------------------------------------------*/
	//Graphics
	@Override
	void draw(Graphics2D g, int xOffset, int yOffset, double step) {
		g.setColor(Color.green);
		g.fillOval(xOffset+(int)Math.round((x-width/2.)*step),  yOffset+(int)Math.round((y-height/2.)*step), (int)Math.round(step*width), (int)Math.round(step*height));
		
		
		if(talk){
		Font font = new Font("Arial", Font.PLAIN, (int)step/2);
		g.setFont(font);
		g.setColor(Color.white);
		g.fillRect(xOffset-(int)step/2, yOffset+(int)(step*14), (int)(step*23), (int)step*3 );
		g.setColor(Color.black);
		g.drawString(text, xOffset, yOffset+(int)(step*14+step/2));
		this.talkTimer();
		}
	}

	@Override
	void takeDamage(int type, int strength) {
		// TODO Auto-generated method stub
		
	}


// from here it's actual new NPC Stuff (and Stuff)
	void talk(){
		if (type == "Start"){ 
			switch(stage){
			case 1:
				text = stageone;
				break;
			case 2:
				text = stagetwo;
				break;
			case 3:
				text = stagethree;
				break;
			}
			talk = true;
			npcTalkTime = System.currentTimeMillis();
		}
		else if (type == "Quest"){
			int quest = (int)(Math.random()*1);
			switch (quest){
			case 0: //der NPC ist ein verkleideter Feind
				text = questAssault;
				talk = true;
				npcTalkTime = System.currentTimeMillis();
			}
		}
	}
	
	private void talkTimer(){ //timer for how long the npc talks
		if (type == "Start"){
			if(System.currentTimeMillis() >= npcTalkTime + 10000) talk = false;
		}
		else if (type == "Quest"){ //Falls es ein Quest NPC ist
			if(System.currentTimeMillis()>= npcTalkTime + 5000){
				talk = false; 
				currentRoom.getContent().add(new EnemyMelee(x, y, 1, 1, Enemy.ENEMY_FIGURE_RUN, stage, currentRoom.getMode()));
				currentRoom.getContent().remove(this);//NPC nach dem reden entfernen
			}
		}
	}
	
	
}
