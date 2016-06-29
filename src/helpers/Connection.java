package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import server.Server;

public class Connection {
	
	public static int portNumber = 26656;
	public static String hostName;
	
	private static PrintWriter out;
	private static BufferedReader in;

	public static int connect(String host){ // Returns 0 for bad, 1 for good
		hostName = host;
		try {
			Socket echoSocket = new Socket(hostName, portNumber);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			out.println("game 1");
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			return 0;
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			return 0;
		} 
		return 1;
	}
	
	public static String receive(){
		try {
			return in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void write(String string){
		out.println(string);
	}
	
	public static void quit(){
		out.println("exit");
		if(!Settings.online){
			Server.end();
		}
	}
	
}
