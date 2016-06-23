package main;

import helpers.Graphics;
import templates.Interface;
import templates.Screen;

public class Connect extends Screen{

	public Connect() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new Interface(null, "heyyyyyyyyyyyyyy", 0.5, 0.5, 0.1, 0.1)
		};
	}

}
