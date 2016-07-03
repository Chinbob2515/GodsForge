package game;

// There can never be too much abstraction

public class Tile {
	
	private int x, y;
	
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
