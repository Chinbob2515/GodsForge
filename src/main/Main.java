package main;

import server.Server;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import helpers.Connection;
import helpers.Graphics;
import helpers.Settings;

public class Main extends Screen{

	public Main() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new TextField(null, "Play online", 0.5, 0.2, 0.1, 0.1),
				new TextField(null, "Play offline", 0.5, 0.4, 0.1, 0.1){
					public void response(int eventKey){
						Settings.online = false;
						Server.main(new String[]{"false"});
						Connection.connect("localhost");
						launchScreen = new UserInput();
						launchScreen.parent = parent;
						launchScreen.run();
					}
				},
				new TextField(null, "Settings", 0.5, 0.6, 0.1, 0.1),
				new TextField(null, "Quit", 0.5, 0.8, 0.1, 0.1)
		};
		interfaces[0].launchScreen = new Connect();
		interfaces[2].launchScreen = new Setting();
		interfaces[3].quit = true;
	}

}
