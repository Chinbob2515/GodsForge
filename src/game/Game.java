package game;

import transfer.ObjectServer;

public class Game {
	
	public Tile[][] world;
	
	public Game(){
		world = (Tile[][])ObjectServer.instance.popObject();
	}
	
}
