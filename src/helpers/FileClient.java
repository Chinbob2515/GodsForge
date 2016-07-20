package helpers;

import java.net.*;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class FileClient extends Thread {

	private static Integer lock = new Integer(1);
	public static boolean run = true, doEnd, inProccess;
	public static HashMap<String, String> msettings;
	private static String[] uploads;
	private static int downloads;
	
	public static ServerSocket serverSocket;

	public static void main(String[] uploads, int downloads){ // Files to upload, no of files to download
        System.out.println("waiting for lock");
		if(inProccess)
			synchronized(lock){
				try {
					lock.wait();
				} catch (InterruptedException e) {System.out.println("Idk what to do");}
			}
		System.out.println("process of down/up-load starting");
		inProccess = true;
		
		FileClient.uploads = uploads;
		FileClient.downloads = downloads;
		
		FileClient server = new FileClient();
		server.start();
		System.out.println("client side started");
    }

	public FileClient(){}

	public void run(){

        int portNumber = 25567;
        
		Socket socket = null;
		try {
			socket = new Socket(Connection.hostName, portNumber);
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
        Connection.write("go"); // You have to write before read, so unblock here.
        Connection.receive();
        for(int i = 0; i != downloads; i++){
        	BufferedImage buf = null;
        	try {
				buf = ImageIO.read(socket.getInputStream());
	        	ImageIO.write(buf, ".png", new File("bob.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        synchronized(lock){
        	lock.notify();
        }
	}

	public static void end(){
		run = false;
		inProccess = false;
		/*try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		if(doEnd)
			System.exit(0);
	}

}

