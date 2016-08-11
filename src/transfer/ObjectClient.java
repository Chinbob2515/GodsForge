package transfer;

import helpers.Connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ObjectClient extends Thread{
	
	public static ObjectClient instance; // For the REAL client
	
	public static final int port = 4445;
	public static boolean PORTINUSE = false;
	
	public boolean run = true, end = false;
	
	public ArrayList<Object> objects = new ArrayList<Object>();
	
	public Integer lock = new Integer(1);
	
	public static void initClientInstance(){
		instance = new ObjectClient();
		instance.start();
	}
	
	public void run(){ 
		main();
	}
	
	public void main() {
		try {
			main(Connection.hostName);
		} catch (IOException e) {
			System.out.println("Shit"); // Unique error swear.
			e.printStackTrace();
		}
	}
	
	public void addObject(Object addObject){
		objects.add(addObject);
		synchronized(lock){
			lock.notify();
		}
	}
	
    public void main(String ip) throws IOException {
        Socket socket = null;
        
        socket = new Socket(ip, port + (PORTINUSE?1:0) /* for if we're in offline mode, as there needs to be two connections */);
        PORTINUSE = true;
        
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
        while(run){
    		if(objects.size() == 0)
            	synchronized(lock){
            		try {
    					lock.wait();
    				} catch (InterruptedException e) {
    					e.printStackTrace();
    				}
            	}
    		if(end){break;}
        	out.writeObject(objects.remove(0));
        }

        out.close();
        socket.close();
    }
    
    public void end(){
    	run = false;
    	end = true;
    	synchronized(lock){
    		lock.notify();
    	}
    }
}
