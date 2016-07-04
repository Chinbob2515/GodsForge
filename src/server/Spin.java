package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Spin extends Thread{

    public static float interval, baseWarmup;
    public static int minPlayers, idCounter = 0;
    public static boolean modded = false;

	public Gods[] players = new Gods[Gods.nInGame];
	public int nPlayers = 0, rounds = 0;
	public float warmup = baseWarmup; //Seconds
	public boolean runb = true, gameStarted = false;
	public int[] playersc = new int[Gods.nInGame];
	public String name;
	public Game game;

	public Spin(String word){
		name = word;
		for(int i = 0; i != players.length; i++){
			playersc[i] = -1;
		}
	}
	
	public int connect(Gods god){ // To plug your "Gods" object into this instance, for the right user (0-Failed;1-Done).
		for(int i = 0; i != players.length; i++){
			if(players[i] == null)continue;
			if(players[i].user.equals(god.user)){
				god.GameId = players[i].GameId;
				players[i] = god;
				return 1;
			}
		}
		return 0;
	}

	public boolean addPlayer(Gods gods){
		if(connect(gods) == 1){return true;} // If you can plug it in place of user's other instance, do.
		players[nPlayers++] = gods;
		gods.GameId = idCounter++;
		sendPlayer(nPlayers - 1, 0, null, null);
		return false;
	}

	public void removePlayer(){
	    Server.log("Using default remove for "+nPlayers);
		players[nPlayers--] = null;
		Server.log("Player removed, there are now "+nPlayers+" players.");
	}

	public void removePlayer(int num){
		if(num == nPlayers - 1){removePlayer();return;}
		Server.log("removing : "+num+" out of "+nPlayers);
		for(int i = num; i != nPlayers; i++){
            Server.log("i : "+i+" + num : "+num+" + nPlayers : "+nPlayers);
			players[i] = i==(Gods.nInGame-1)?null:players[i+1];
		}
		nPlayers--;
	}

	public void sendPlayer(int player, int code, int[] ints, String[] strings){
		String string = "";
		switch(code){
		case 0:
			for(int i = 0; i != players.length; i++){
				if(players[i] == null || playersc[i] == -1){continue;}
				string += playersc[i] + ",";
			}
			if(string != ""){
				string = string.substring(0,string.length()-1);
			}
			players[player].updateWarmup(warmup, nPlayers, string);
			break;
		}
	}

	public void sendAll(int code, int[] ints, String[] strings){
		// Compile the string to be sent.
		String string = "";
		switch(code){
		case 2:
			for(int i = 0; i != players.length; i++){
				if(players[i] == null || playersc[i] == -1){continue;}
				string += playersc[i] + ",";
			}
			if(string != ""){
                string = string.substring(0,string.length()-1);
			}
			break;
        case 4:
            string = code+":"+strings[0];
            break;
        case 5:
        case 10:
        case 11:
        case 13:
        case 14:
            string += code+":";
            for(int i = 0; i != ints.length; i++){
                string += ints[i]+(i==ints.length-1?"":";");
            }
            break;
		}
		// Send the info.
		for(int i = 0; i != nPlayers; i++){
			switch(code){
			case 2:
				players[i].updateWarmup(warmup, nPlayers, string);
            break;
            case 4:
            case 5:
            case 10:
            case 11:
            case 13:
            case 14:
                players[i].send(string);
                break;
            case 12:
                players[i].send("12:;");
                break;
			}
		}
	}

	public void run(){
		Server.log("Spin started");
		try{
            sendAll(4, null, new String[]{IOHandle.slurp(new FileInputStream(IOHandle.COUNTRY_SETTINGS)).replaceAll("[\\t\\n\\x0B\\f\\r]","")});
		} catch(FileNotFoundException e){
            Server.log("oh no");
		}
		sendAll(5, new int[]{minPlayers}, null);
		while(true){
			warmup -= interval / 1000;
			if(!(warmup > 0)){
                if(minPlayers <= nPlayers){
                    warmup = (float)0.0;
                    sendAll(2, null, null);
                    break;
                } else {
                    warmup = baseWarmup;
                }
			}
			sendAll(2, null, null);
			try{
				Thread.sleep((long)interval);
			} catch(InterruptedException e){}
		}
		gameStarted = true;
		game = new Game(this);
        game.run();
	}

	public void end(){
        if(game != null){
            game.run = false;
            game = null;
        }
	}

}
