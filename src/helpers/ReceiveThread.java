package helpers;

import game.Game;
import game.Tile;
import transfer.ObjectServer;

public class ReceiveThread extends Thread{
	
	private boolean run = true;
	
	private Game owner;
	
	public ReceiveThread(Game game){
		owner = game;
	}
	
	@SuppressWarnings("unused")
	public void run(){
		while(run){
			String string = Connection.receive();
			int code = Integer.parseInt(string.split(":")[0]);
			String[] parts = string.split(":")[1].split(";");
			// Many codes are already dealt with elsewhere.
			switch(code){
			case 32:
				Tile tile = (Tile) ObjectServer.main();
				owner.world[tile.getX()][tile.getY()] = tile;
				break;
			default:
				System.out.println("Unknown code for received string: "+string);
				break;
			}
		}
	}
	
	public void end(){
		run = false;
	}
	
}
