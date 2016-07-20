package files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	
	// I HATE FILE TRANSFER. USE THIS ONE TO UPLOAD FILES, EVEN IF YOU'RE NOT A CLIENT, AND DO IT ONE BY ONE. OR ELSE.
	
	public static final int port = 4444;
	
    public static void main(String ip, String path) throws IOException {
        Socket socket = null;

        socket = new Socket(ip, port);

        File file = new File(path);
        
        byte[] bytes = new byte[16 * 1024]; // idk why this length, i copied this from https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        socket.close();
    }
}