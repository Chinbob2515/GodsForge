package main;

import helpers.Graphics;
import templates.Interface;
import templates.Screen;
import templates.ScrollField;
import templates.TextField;

public class Browse extends Screen{

	public Browse() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new ScrollField(null, 0.0, 0, 1, 1, new Interface[]{
						new TextField(null, "hi", 0.0,0,0,0),
						new TextField(null, "hey", 0.0,0,0,0),
						new TextField(null, "wha?", 0.0,0,0,0)
				})
		};
	}

}
