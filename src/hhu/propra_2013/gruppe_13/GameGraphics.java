package hhu.propra_2013.gruppe_13;

import java.awt.*;
import javax.swing.*;

public abstract class GameGraphics {
	protected void draw() {
	}
	int hp;
	
	double x,y,r;
	
	abstract int getHP();
	
	abstract double getPosX();
	
	abstract double getPosY();
	
	abstract double getRad();
	
	abstract void setPosX();
	
	abstract void setPosY();
	
	abstract void setRad();
	
	abstract void setHP();
	
}
