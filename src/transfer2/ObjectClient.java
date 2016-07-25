package transfer;

import helpers.Connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectClient {
	
	public static final int port = 4445;
	
	public static void main(Object object) {
		try {
			main(Connection.hostName, object);
		} catch (IOException e) {
			System.out.println("Shit"); // Unique error swear.
		}
	}
	
    public static void main(String ip, Object object) throws IOException {
        Socket socket = null;

        socket = new Socket(ip, port);
        
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
        out.writeObject(object);

        out.close();
        socket.close();
    }
	
    public static void main(String ip, Object[] objects) throws IOException {
        Socket socket = null;

        socket = new Socket(ip, port);
        
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        
	for(Object object: objects){
        	out.writeObject(object);
	}

        out.close();
        socket.close();
    }
}
