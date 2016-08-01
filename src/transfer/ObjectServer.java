package transfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;

public class ObjectServer extends Thread{
	
	public static final int port = 4445;
	
	public static ArrayList<Object> objects = new ArrayList<Object>();
	
	public void run(){
		ObjectServer.main();
	}
	
	public static Object main(){
		
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
            return null;
        }

        Socket socket = null;
        ObjectInputStream in = null;
	
	while(true){
	
        try {
            socket = serverSocket.accept();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
            return null;
        }
        
        Object object = null;
        try {
			object = in.readObject();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

        try {
	        in.close();
	        socket.close();
	        serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        objects.add(object);
	}
	}
	
}
