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
		ObjectServer[] threads = new ObjectServer[1];//world.length*world[0].length];
		for(int i = 0; i != 1; i++){
			threads[i] = new ObjectServer();
			threads[i].start();
		}
		Connection.write("33");
		for(ObjectServer thread: threads)try{  thread.join();  }catch(InterruptedException e){e.printStackTrace();} // Wait for all threads to finish
		for(ObjectServer thread: threads){
			Tile tile = (Tile) thread.object;
			world[tile.getX()][tile.getY()] = tile;
		}
	}
	
}
