package game;

import transfer.ObjectServer;
import helpers.Connection;

public class Game {
	
	public Tile[][] world;
	
	public Game(){
		world = new Tile[3][];
		for(int i = 0; i != world.length; i++){
			world[i] = new Tile[3];
		}
		if(!Connection.receive().equals("33:;"))
			System.out.println("oh god what has happened while loading tiles hint hint");
		world = (Tile[][])ObjectServer.instance.popObject();
		Connection.write("33");
	}
	
}
