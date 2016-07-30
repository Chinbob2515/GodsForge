package transfer;

import helpers.Connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectClient extends Thread{
	
	public static final int port = 4445;
	
	private static int idCounter = 0;
	private static boolean PORT_IN_USE = false;
	private static Integer PORTLOCK = new Integer(1);
	
	public Object lock, object;
	
	public ObjectClient(Object lock, Object object){
		this.lock = lock;
		this.object = object;
	}
	
	public void run(){ // For if you want to silently wait until an objectserver is ready.
		try {
			synchronized(lock){
				lock.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ObjectClient.main(object);
	}
	
	public static void main(Object object) {
		try {
			main(Connection.hostName, object);
		} catch (IOException e) {
			System.out.println("Shit"); // Unique error swear.
			e.printStackTrace();
		}
	}
	
    public static void main(String ip, Object object) throws IOException {
        Socket socket = null;
        
        if(PORT_IN_USE)
        	synchronized(PORTLOCK){
        		try {
					PORTLOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        PORT_IN_USE = true;

        System.out.println("client: "+idCounter++);
        
        socket = new Socket(ip, port);
        
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
        out.writeObject(object);

        out.close();
        socket.close();
        
        PORT_IN_USE = false;
        synchronized(PORTLOCK){
        	PORTLOCK.notify();
        }
    }
}
