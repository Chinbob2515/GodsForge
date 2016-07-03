package main;

import helpers.Connection;
import helpers.Graphics;
import helpers.Settings;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import templates.TypeField;

public class Connect extends Screen{

	public static String ip;
	
	public Connect() {
		super();
		Settings.online = true;
		interfaces = new Interface[]{
				new TypeField(null, "Enter ip of server here", 0.5, 0.5, 0.1, 0.1),
				new TextField(null, "Connect to a server", 0.5, 0.0, 0.1, 0.1),
				new TextField(null, "Connect", 1.0, 1.0, 0.1, 0.1),
				new TextField(null, "Back", 0.0, 1.0, 0.1, 0.1)
		};
		interfaces[1].hover = false;
		interfaces[2].launchScreen = new ConnectHelper();
		interfaces[3].quit = true;
	}
	
	public void onEnd(){
		ip = interfaces[0].getValue();
	}
	
	// Loading screen class here as well, bcs why not?
	public class ConnectHelper extends Screen{

		public ConnectHelper() {
			super(Graphics.loadTex("Black2"));
		}
		
		public void start(){
			super.start();
			int result = Connection.connect(ip);
			if(result == 0){
				run = false;
				parent.interfaces[0].setValue("Try with a BETTER ip");
				return;
			}
			new UserInput().run();
			run = false;
		}
		
	}

}
