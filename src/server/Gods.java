package server;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class Gods extends Thread implements GameI{ //  Basically holding and relaying a single player's information.

	public static final int nInGame = 6;
	public static int updateInterval;
	public static Auth auth = new Auth();

	public static int games = 0, /*clients = 0,*/ id;
	//public int playern;
	public static ArrayList<Spin> spins = new ArrayList<Spin>();
	private Spin spin;
	private PrintWriter out;
	public BufferedReader in;
	public boolean runB = true, hi = false, observe = true;
	public Integer lock = new Integer(1); // Used for thread locking
	public ArrayList<String> send = new ArrayList<String>();
	
	public String[] greater, lesser;
	public String texLoc;
	public boolean addedTex;
	
	public String user, name;
	
	public int GameId;

	public Gods(PrintWriter out, int id, BufferedReader in){
		this.out = out;
		this.in = in;
		//FileServer.biggerOut = out;
		//FileServer.biggerIn = in;
		Gods.id = id; // Just what? Static ID tracker?
		//this.playern = clients % nInGame;
		//clients++;
	}

	public void run(){
		Server.log("Gods started");
		synchronized(lock){ // Wait until a game is actually chosen.
			while(observe){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		// CURRENT LIMIT OF ADAPTION TO GODSFORGE IN THIS METHOD
		
		Server.log("RUN STARTED ");
		while(runB){
			try{
				Thread.sleep(4000); // IDK why we need this class to be a thread at all- mostly just a symbolic state tracker. 
			}catch(InterruptedException e){}
		} // All wrapping up logic can be done below this, but IDK why it would be.
		
		// TODO Remove player from game?!?!
		
		// TODO Make a game deleting process. (probably each player has a "delete" flag to mutually raise, or can be replaced by AIs- and an all AI game disappears)
	}

	public void updateWarmup(double time, int players, String countries){
		String string;
		string = "2:"+time+";"+players+";"+countries;
		out.println(string);
	}

	public void sendReject(int country){
        String string;
        string = "3:"+country;
        Server.log(id + "rejecting country choice!");
        out.println(string);
        spin.sendAll(2, null, null);
	}

	public void send(String string){
        out.println(string);
	}

	@SuppressWarnings("unused")
	public void receive(String string){
		if(user == null){
			doAuthSend(string);
			return;
		}
		/* Codes: 
		 *  0x - Pregame organisation
		 *  1x - In game instructions
		 *  2x - Meta information request
		 *  3x - Meta action request
		 *  4x - Error code
		 *  
		 *  Use : as once main seperator, ; as a smaller unit seperator, and - or , as smaller ones.
		 * */
	    int subpro, x, y, id, country, country2, reserve;
		send.add(string);
		String[] strings = string.split(":");
		int protocol = Integer.parseInt(strings[0]);
		strings = strings[1].split(";");
		switch(protocol){
		case 0: // Request update for game information.
			String str = "0:";
			for(Spin spin: spins){
				str += spin.nPlayers+"-"+spin.rounds+"-"+spin.players.length+"-"+spin.name+";"; 
			}
			out.println(str);
			break;
		case 1: // Enter a game.
			Server.log(user+" entering game "+strings[0]);
			spin = spins.get(Integer.parseInt(strings[0]));
			out.println("1:"+(spin.addPlayer(this)?1:0));
			observe = false;
			synchronized(lock){
				lock.notifyAll();
			}
			break;
		case 2: // Create a game
			if(strings[0].equals(""))strings[0] = "Game Name";
			spins.add(new Spin(strings[0]));
			out.println("2:"); // Data flushhh! ( oh wait we have no info )
			break;
		case 3: // Create a player
			if(strings[0].equals(""))strings[0] = "McDougle"; // TODO- add list of names to randomly choose from.
			name = strings[0];
			greater = new String[]{strings[1]};
			lesser = new String[]{strings[2], strings[3]};
			texLoc = strings[4];
			addedTex = strings[5].equals("1");
			if(addedTex){
				System.out.println("doing file thing by server");
				/*FileServer.main(new String[0], 1);
				out.println("30:;"); //Unblock predictable file transfer.
				synchronized(FileServer.plock){
					try {
						FileServer.plock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/
			} else System.out.println("not doing file thing");
			break;
			
		case 30:
			//FileServer.main(new String[0], 1);
			break;
		}
		Server.log(user+" received string "+string);
	}
	
	public void doAuthSend(String string){
		String[] userKeyPair = string.split(" ");
		if(userKeyPair[0].equals("0")){ // Register a new user
			if(!auth.addAuth(userKeyPair[1], userKeyPair[2])){
				 out.println("NO");
				 return;
			}
		} else if(userKeyPair[0].equals("1")){ // Login as existing user.
			if(!auth.checkAuth(userKeyPair[1], userKeyPair[2])){
				out.println("NO"); // Super secret signal they failed authentication
				return;
			}
		} else { 
			// No other valid codes
			out.println("WHAT");
			return;
		}
		out.println("YES"); // Client has to flush something from the connection- send YES as placeholder, for not NO
		user = userKeyPair[0];
		return;
	}

	public void end(){
		runB = false;
		//spin.removePlayer(playern); THIS IS ALREADY DONE AT THE END OF THE LOOP
	}

	public static void main(String[] args){
	    updateInterval = Integer.parseInt(Server.msettings.get("update_interval"));
	    Spin.interval = Integer.parseInt(Server.msettings.get("warmup_interval"));
	    Spin.baseWarmup = Integer.parseInt(Server.msettings.get("warmup_start"));
	    Spin.minPlayers = Integer.parseInt(Server.msettings.get("min_players"));
		Server.log("Gods game started");
	}

}
