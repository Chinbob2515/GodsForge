package transfer;

public class test {
	
	public static class startServer extends Thread{
		public void run(){
			FileServer.main("bob.png");
		}
	}
	
	public static void main(String[] args){
		/*startServer a = new startServer();
		a.start();
		try {
			FileClient.main("0.0.0.0", "res/city.png");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		ObjectServer b = new ObjectServer();
		b.start();
		ObjectClient c = new ObjectClient();
		c.start();
		c.addObject(new String[]{"first","second"});
		//System.out.println(((String[])ObjectServer.popObject()));
		for(String i: (String[])b.popObject()){
			System.out.println(i);
		}
		c.end();
		b.end();
	}
	
}
