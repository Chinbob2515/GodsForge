package transfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ObjectServer extends Thread{
	
	public static final int port = 4445;
	
	public Object object;
	
	public void run(){
		object = ObjectServer.main();
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
        
        return object;
	}
	
	public static Object[] M_main(int n){
		
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
            return null;
        }

        Socket socket = null;
        ObjectInputStream in = null;

        try {
            socket = serverSocket.accept();
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
            return null;
        }
        
        Object[] objects = new Object[n];
	for(int i = 0; i != n; i++){
	Object object = null;
        try {
			object = in.readObject();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	objects[i] = object;
	}

        try {
	        in.close();
	        socket.close();
	        serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return object;
	}
	
}
