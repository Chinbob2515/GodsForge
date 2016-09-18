package game;

import java.io.Serializable;

// There can never be too much abstraction

public class Tile implements Serializable{ 
	
	private static final long serialVersionUID = -1773397001509279748L;// In order to save and load via Object[In/Out]putStream
	
	private int x, y;
	public int landValue = 0;
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;		
	}

	// Maybe these functions will be needed sometime? 
	public int getX() {return x;}
	public void setX(int x) {this.x = x;}
	public int getY() {return y;}
	public void setY(int y) {this.y = y;}
	
}
