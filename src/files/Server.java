package files;

import java.io.*;
import java.net.*;

public class Server {
	
	public static final int port = 4444;
	
    public static void main(String path) {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
            return;
        }

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            socket = serverSocket.accept();
            in = socket.getInputStream();
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
            return;
        }

        try {
            out = new FileOutputStream(path);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. ");
            return;
        }

        byte[] bytes = new byte[16*1024];

        int count;
        try {
			while ((count = in.read(bytes)) > 0) {
			    out.write(bytes, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
			out.close();
	        in.close();
	        socket.close();
	        serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}