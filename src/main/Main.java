package main;

import templates.Interface;
import templates.Screen;
import helpers.Graphics;

public class Main extends Screen{

	public Main() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{new Interface(null, "Play", 0.5, 0.25, 0.1, 0.1),new Interface(null, "Settings", 0.5, 0.5, 0.1, 0.1),new Interface(null, "Quit", 0.5, 0.75, 0.1, 0.1)};
		interfaces[1].launchScreen = new Setting();
		interfaces[2].quit = true;
	}
	
	public void run(){
		start();
		while(run){
			check();
			render();
		}
	}

}
