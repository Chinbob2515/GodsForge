package main;

import helpers.Connection;
import helpers.Graphics;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import templates.TypeField;

public class UserInput extends Screen{

	public UserInput() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new TextField(null, "Enter details:", 0.5, 0, 0, 0),
				new TypeField(null, "Enter Username", 0.5, 0.2, 0, 0),
				new TypeField(null, "Enter Passcode", 0.5, 0.4, 0, 0),
				new TextField(null, "Login", 0.3, 0.8, 0, 0),
				new TextField(null, "Register", 0.7, 0.8, 0, 0)
		};
	}
	
	public void onEnd(){
		Connection.quit();
	}

}
