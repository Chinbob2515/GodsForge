package main;

import helpers.Graphics;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import templates.TypeField;

public class Connect extends Screen{

	public Connect() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new TypeField(null, "Enter ip of server here", 0.5, 0.5, 0.1, 0.1),
				new TextField(null, "Connect to a server", 0.5, 0.0, 0.1, 0.1),
				new TextField(null, "Connect", 1.0, 1.0, 0.1, 0.1),
				new TextField(null, "Back", 0.0, 1.0, 0.1, 0.1)
		};
		interfaces[1].hover = false;
		interfaces[2].quit = true;
	}

}
