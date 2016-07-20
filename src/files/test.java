package files;

import java.io.IOException;

public class test {
	
	public static class startServer extends Thread{
		public void run(){
			Server.main("bob.png");
		}
	}
	
	public static void main(String[] args){
		startServer a = new startServer();
		a.start();
		try {
			Client.main("0.0.0.0", "res/city.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
