package main;

import templates.Interface;
import templates.Screen;
import helpers.Graphics;

public class Main extends Screen{

	public Main() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new Interface(null, "Play online", 0.5, 0.2, 0.1, 0.1),
				new Interface(null, "Play offline", 0.5, 0.4, 0.1, 0.1),
				new Interface(null, "Settings", 0.5, 0.6, 0.1, 0.1),
				new Interface(null, "Quit", 0.5, 0.8, 0.1, 0.1)
		};
		interfaces[0].launchScreen = new Connect();
		interfaces[2].launchScreen = new Setting();
		interfaces[3].quit = true;
	}

}
