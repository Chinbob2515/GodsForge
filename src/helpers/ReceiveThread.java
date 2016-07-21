package helpers;

import game.GameScreen;
import game.Tile;
import transfer.ObjectServer;

public class ReceiveThread extends Thread{
	
	private boolean run = true;
	
	private GameScreen owner;
	
	public ReceiveThread(GameScreen game){
		owner = game;
	}
	
	public void run(){
		while(run){
			String string = Connection.receive();
			int code = Integer.parseInt(string.split(":")[0]);
			String[] parts = string.split(":")[1].split(";");
			// Many codes are already dealt with elsewhere.
			switch(code){
			case 32:
				Tile tile = (Tile) ObjectServer.main();
				
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
