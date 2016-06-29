package server;

import java.net.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
//import org.apache.commons.io.output.TeeOutputStream;

public class Server extends Thread {

	public static boolean run = true;
	public static HashMap<String, String> msettings;

	public static void main(String[] args) throws IOException {

        /*try { THIS STUFF JUST LOGS ALL THE OUTPUT FROM SYSOUT TO A FILE AS WELL- IT'S TOO ANOYING RIGHT NOW
            FileOutputStream fos = new FileOutputStream(new java.io.File("logs/log"+System.currentTimeMillis()+".txt"));
            //we will want to print in standard "System.out" and in "file"
            TeeOutputStream myOut=new TeeOutputStream(System.out, fos);
            PrintStream ps = new PrintStream(myOut);
            System.setOut(ps);
            System.setErr(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

		//System.out.println("Starting server...");

		Server server = new Server();
		server.start();

		//System.out.println("Setting up game classes...");
		msettings = IOHandle.getMultiSettings();

		Echo.main(null);
		Gods.main(null);

		System.out.println("Game classes set up");

        	int portNumber = Integer.parseInt(msettings.get("port"));

        	ArrayList<Handler> echos = new ArrayList<Handler>();
        	int i = 0;

        	while(run){

        	    try{
        	        ServerSocket serverSocket = new ServerSocket(portNumber);
                    System.out.println("Server socket started.");
        	    	echos.add(new Handler(serverSocket.accept()));
        	    	echos.get(i).start();
        	    	i++;
        	    } catch (IOException e) {
        	        System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
        	        System.out.println(e.getMessage());
        	    }

        	}
    	}

	public Server(){}

	public void run(){
		Scanner scan = new Scanner(System.in);
		if(scan.nextLine().equals("restart")){
            try{
                Runtime.getRuntime().exec("java Server");
            }catch(IOException e){
		System.out.println("Restart failed");
            }
		}
		Server.end();
	}

	public static void end(){
		Handler.write("Server stopping, bye.");
		run = false;
		System.out.println("Stopping server!");
		System.exit(0);
	}

}

