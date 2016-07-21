package transfer;

import java.io.IOException;

public class test {
	
	public static class startServer extends Thread{
		public void run(){
			FileServer.main("bob.png");
		}
	}
	
	
	
	public static void main(String[] args){
		startServer a = new startServer();
		a.start();
		try {
			FileClient.main("0.0.0.0", "res/city.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		ObjectServer b = new ObjectServer();
		b.start();
		try {
			ObjectClient.main("0.0.0.0", new String[]{"hi"});
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			b.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(((String[])b.object)[0]);
	}
	
}
