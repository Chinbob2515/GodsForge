package transfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ObjectServer extends Thread{
	
	public static ObjectServer instance; // Used if a REAL client, because more than one isn't needed.
	
	public static final int port = 4445;
	public static boolean PORTINUSE = false;
	
	public boolean run = true, end = false;
	
	public ArrayList<Object> objects = new ArrayList<Object>();
	
	public Integer notifier = new Integer(1);
	
	private ObjectInputStream in;
	
	public static void initClientInstance(){
		instance = new ObjectServer();
		instance.start();
	}
	
	public void run(){
		main();
	}
	
	public ObjectServer(){
        
        try {
			throw new IOException();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public Object popObject(){
		if(objects.size() != 0){
			return objects.remove(0);
		} else {
			synchronized(notifier){
				try {
					notifier.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return objects.remove(0);
		}
	}
	
	public void main(){
		
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port + (PORTINUSE?1:0) /* for if we're in offline mode, as there needs to be two connections */);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
            return;
        }
        PORTINUSE = true;

        Socket socket = null;
        in = null;
        	
       	try {
       		socket = serverSocket.accept();
       		in = new ObjectInputStream(socket.getInputStream());
       	} catch (IOException ex) {
       		System.out.println("Can't accept client connection. ");
       		return;
       	}
       	
        while(run){
        	
        	Object obj = null;
        	try {
        		obj = in.readObject();
        	} catch (IOException e1) {
        		//e1.printStackTrace();
        		break; // Happens when end() is called, as the socket is closed
        	} catch (ClassNotFoundException e1) {
        		e1.printStackTrace();
        		continue;
        	}
        	objects.add(obj);
        	synchronized(notifier){
        		notifier.notifyAll();
        	}
        }

        try {
	        in.close();
	        socket.close();
	        serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void end(){
		run = false;
		end = true;
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
