package main;

import helpers.Connection;
import helpers.Graphics;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import templates.TypeField;

// TODO FOR SOME REASON, this screen exits main as well as itself on end.

public class UserInput extends Screen{

	public UserInput() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new TextField(null, "Enter details:", 0.5, 0, 0, 0),
				new TypeField(null, "Enter Username", 0.5, 0.2, 0, 0),
				new TypeField(null, "Enter Passcode", 0.5, 0.4, 0, 0),
				new TextField(null, "Login", 0.3, 0.8, 0, 0){
					public void response(int eventKey){
						Connection.write("1 "+interfaces[1].getValue()+" "+interfaces[2].getValue());
						//parent.onEnd();
						launchScreen.parent = parent;
						launchScreen.run();
					}
				},
				new TextField(null, "Register", 0.7, 0.8, 0, 0){
					public void response(int eventKey){
						Connection.write("0 "+interfaces[1].getValue()+" "+interfaces[2].getValue());
						//parent.onEnd();
						launchScreen.parent = parent;
						launchScreen.run();
					}
				},
				new TextField(null, "Back", 0.0, 1, 0, 0)
		};
		interfaces[3].launchScreen = interfaces[4].launchScreen = new Browse();
		interfaces[5].quit = true;
	}
	
	public void onEnd(){
		Connection.quit();
	}

}
