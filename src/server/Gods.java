package server;

import java.util.ArrayList;
import java.io.PrintWriter;

public class Gods extends Thread implements GameI{

	public static final int nInGame = 6;
	public static int updateInterval;

	public static int games = 0, clients = 0, id;
	public int playern;
	public static ArrayList<Spin> spins = new ArrayList<Spin>();
	private Spin spin;
	private PrintWriter out;
	public boolean runB = true, hi = false, observe = true;
	public ArrayList<String> send = new ArrayList<String>();
	
	public String user;
	
	public int GameId;

	public Gods(PrintWriter out, int id){
		this.out = out;
		Gods.id = id; // Just what? Static ID tracker?
		this.playern = clients % nInGame;
		clients++;
	}

	public void run(){
		Server.log("Gods started");
		while(observe){
			
		}
		// CURRENT LIMIT OF ADAPTION TO GODSFORGE IN THIS METHOD
		
		/*if(spins.size() * nInGame < clients){
			Server.log("new Spin started " + spins.size() +" "+ nInGame + " " + clients);
			spins.add(new Spin(this));
			spins.get(spins.size()-1).start();
		} else {
			spins.get(spins.size()-1).addPlayer(this);
		}
		spin = spins.get(spins.size()-1);*/
		Server.log("RUN STARTED ");
		while(runB){
			//Server.log("STILL RUNNNNIG " + hi);
			/*if(!send.isEmpty()){
				String string = send.remove(0);
				out.println(string);
				//Server.log("SENT STRING "+string);
			}*/
			try{
				Thread.sleep(4000);
			}catch(InterruptedException e){}
		}
		spin.removePlayer(playern);
		clients--;
		if(spin.nPlayers == 0){
			spins.remove(spins.indexOf(spin));
			spin.end();
			spin = null;
			Server.log("RUN ENDED, Removing Spin");
		} else {
            Server.log("RUN ENDED : " + spin.nPlayers);
        }
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
		/* Codes: 
		 *  0x - Pregame organisation
		 *  1x - In game instructions
		 *  2x - Meta information request
		 *  3x - Meta action request
		 *  4x - Error code
		 * */
	    int subpro, x, y, id, country, country2, reserve;
		send.add(string);
		//hi = true; Probably an old indicator? IDK?
		String[] strings = string.split(":");
		int protocol = Integer.parseInt(strings[0]);
		strings = strings[1].split(";");
		switch(protocol){
		case 0: // Request update for game information.
			String str = "0:";
			for(Spin spin: spins){
				str += spin.players.length+"-"+spin.rounds+";";
			}
			out.println(str);
			break;
		case 1: // Enter a game.
			spin = spins.get(Integer.parseInt(strings[0]));
			observe = false;
			break;
		case 2: // Create a game
			spins.add(new Spin());
			break;
		/*case 0: 		// Let's just gut the whole list of cases, and hope.
			Server.log(playern +" choosing country");
			spin.chooseCountry(playern, Integer.parseInt(strings[0]));
			break;
		case 1:
			this.end();
			break;
        case 10:
            subpro = Integer.parseInt(strings[0]);
            switch(subpro){
            case 0:
                x = Integer.parseInt(strings[1]);
                y = Integer.parseInt(strings[2]);
                id = Integer.parseInt(strings[3]);
                country = Integer.parseInt(strings[4]);
                if(strings.length == 6){
                    reserve = 2;
                } else {
                    reserve = 0;
                }
                synchronized(spin.game.countries[country].add){
                    spin.game.countries[country].add.add(new int[]{reserve, x, y, country, id});
                }
                break;
            case 1:
                // This should never be sent. The server is fully in charge of destroying soldiers.
                break;
            case 2:
                x = Integer.parseInt(strings[1]);
                y = Integer.parseInt(strings[2]);
                id = Integer.parseInt(strings[3]);
                synchronized(spin.game.countries[0].add){
                    spin.game.countries[0].add.add(new int[]{1, x, y, id});
                }
                break;
            }
            break;
        case 11:
            subpro = Integer.parseInt(strings[0]);
            x = Integer.parseInt(strings[2]);
            y = Integer.parseInt(strings[3]);
            country = Integer.parseInt(strings[4]);
            if(Integer.parseInt(strings[1]) == 1){
                switch(subpro){
                case 0:
                    spin.game.countries[country].AImineAdd(x, y);
                    break;
                case 1:
                    spin.game.countries[country].AIopiumAdd(x, y);
                    break;
                case 2:
                    break;
                }
            } else {
                Server.log("Nothing has been programmed to happen here");
            }
            break;
        case 14:
            subpro = Integer.parseInt(strings[0]);
            country = Integer.parseInt(strings[1]);
            country2 = Integer.parseInt(strings[2]);
            spin.game.setWar(country, country2, subpro==1);
            break;*/ 
		}
		Server.log("received string "+string);
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
