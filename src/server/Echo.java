package server;

import java.util.ArrayList;
import java.io.PrintWriter;

public class Echo extends Thread implements GameI{

	public PrintWriter out;
	public boolean runB = true, hi = false;
	public ArrayList<String> send = new ArrayList<String>();

	public Echo(PrintWriter out){
		this.out = out;
	}

	public void run(){
		while(runB){
			if(!send.isEmpty()){
				String string = send.remove(0);
				out.println(string);
			} else if(hi){Server.log(send.isEmpty());hi=false;}
			try{
			Thread.sleep(4000);}catch(InterruptedException e){}
		}
	}

	public void receive(String string){
		send.add(string);
		hi = true;
	}

	public void end(){
		runB = false;
	}

	public static void main(String[] args){
		Server.log("Echo game set up");
	}

}
