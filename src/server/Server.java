package server;

import java.net.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

import org.apache.commons.io.output.TeeOutputStream;
//import org.apache.commons.io.output.TeeOutputStream;

public class Server extends Thread {

	private static final boolean LOG = false, LOGTOFILE = false;
	public static boolean run = true, doEnd, inProccess;
	public static HashMap<String, String> msettings;
	
	public static ServerSocket serverSocket;

	public static void main(String[] args){ // new String[]{(boolean autonomous)}
		
		// TODO make it so you can start custom local servers?. (not sure what you'd customise yet)
		
		if(LOGTOFILE){
			try { // THIS STUFF JUST LOGS ALL THE OUTPUT FROM SYSOUT TO A FILE AS WELL- IT'S TOO ANOYING RIGHT NOW
            	FileOutputStream fos = new FileOutputStream(new java.io.File("logs/log"+System.currentTimeMillis()+".txt"));
            	//we will want to print in standard "System.out" and in "file"
            	TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
            	PrintStream ps = new PrintStream(myOut);
            	System.setOut(ps);
            	System.setErr(ps);
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
		}
        
		if(inProccess){
			log("ALREADY RUNNING, YOU SCRUB"); // The server should create Handlers for each person, and then Handlers can spawn 
			return; // 							  whatever they like. You should never need more than one Server instance, hence the 
		}//										  heavily reliance (in this class) on static variables.
		inProccess = true;
		
		Server server = new Server();
		server.start();

		if(args.length == 0 || args[0].equals("true")){
			doEnd = true;
	        Scanner scan = new Scanner(System.in);
			if(scan.nextLine().equals("restart")){
	            try{
	                Runtime.getRuntime().exec("java Server"); // This does it silently- TODO NOISY
	            }catch(IOException e){
	            	log("Restart failed");
	            }
			}
			Server.end();
		}
		
    }
	
	public static void log(String s){
		if(LOG){System.out.println("Server: "+s);}
	}

	public Server(){}

	public void run(){
		log("Starting server...");
		

		log("Setting up game classes...");
		msettings = IOHandle.getMultiSettings();

		Echo.main(null);
		Gods.main(null);

		log("Game classes set up");

        int portNumber = Integer.parseInt(msettings.get("port"));

        ArrayList<Handler> echos = new ArrayList<Handler>();
        int i = 0;
        
		serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        log("Server socket started.");

        while(run){
        	try{
                echos.add(new Handler(serverSocket.accept()));
       	    	echos.get(i).start();
       	    	i++;
       	    } catch (IOException e) {
       	    	log("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
        	    log(e.getMessage());
        	}

        }
	}

	public static void end(){
		Handler.write("Server stopping, bye.");
		run = false;
		log("Stopping server!");
		inProccess = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(doEnd)
			System.exit(0);
	}

}

