package server;

import java.net.*;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class FileServer extends Thread {

	private static Integer lock = new Integer(1);
	public static boolean run = true, doEnd, inProccess;
	public static HashMap<String, String> msettings;
	private static String[] uploads;
	private static int downloads;
	public static PrintWriter biggerOut;
	public static BufferedReader biggerIn;
	
	public static Integer plock = new Integer(1);
	
	public static ServerSocket serverSocket;

	public static void main(String[] uploads, int downloads){ // Files to upload, no of files to download
        
		if(inProccess)
			synchronized(lock){
				try {
					lock.wait();
				} catch (InterruptedException e) {System.out.println("Idk what to do");}
			}
		inProccess = true;
		
		FileServer.uploads = uploads;
		FileServer.downloads = downloads;
		
		FileServer server = new FileServer();
		server.start();
		
    }
	
	public static void log(String string){Server.log(string);}

	public FileServer(){}

	public void run(){
		log("Starting file server...");

        int portNumber = Integer.parseInt(Server.msettings.get("fileport"));
        
		serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
			socket = serverSocket.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        log("Server socket started.");
        
        try {
			System.out.println("read this: " + biggerIn.readLine());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        for(String string: uploads){
        	BufferedImage buf = null;
        	try {
				buf = ImageIO.read(new FileInputStream(string));
				ImageIO.write(buf, ".png", socket.getOutputStream());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        for(int i = 0; i != downloads; i++){
        	BufferedImage buf = null;
        	try {
				buf = ImageIO.read(socket.getInputStream());
	        	ImageIO.write(buf, ".png", new File("bob.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        biggerOut.println("go"); // You have to write before read, so unblock here.
        try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        synchronized(lock){
        	lock.notify();
        }
        synchronized(plock){
        	plock.notifyAll();
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

