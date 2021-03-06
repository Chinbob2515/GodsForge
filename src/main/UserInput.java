package main;

import helpers.Connection;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import templates.TypeField;

// TODO FOR SOME REASON, this screen exits main as well as itself on end.

public class UserInput extends Screen{
	
	public UserInput() {
		super();
		interfaces = new Interface[]{
				new TextField(null, "Enter details:", 0.5, 0, 0, 0),
				new TypeField(null, "Enter Username", 0.5, 0.2, 0, 0),
				new TypeField(null, "Enter Passcode", 0.5, 0.4, 0, 0),
				new TextField(null, "Login", 0.3, 0.8, 0, 0){
					public void response(int eventKey){
						Connection.write("1 "+interfaces[1].getValue()+" "+interfaces[2].getValue());
						String s = Connection.receive();
						if(s.equals("NO")){
							interfaces[0].setValue("Enter VALID details:");
						} else if(s.equals("YES")){
							//parent.onEnd();
							launchScreen.parent = parent;
							launchScreen.run();
							//run = false;
						} else {
							interfaces[0].setValue("Something went very wrong.");
						}
					}
				},
				new TextField(null, "Register", 0.7, 0.8, 0, 0){
					public void response(int eventKey){
						Connection.write("0 "+interfaces[1].getValue()+" "+interfaces[2].getValue());
						String s = Connection.receive();
						System.out.println("register response: "+s);
						if(s.equals("NO")){
							interfaces[0].setValue("Enter a different username:");
						} else if(s.equals("YES")){
							launchScreen.parent = parent;
							launchScreen.run();
							//run = false;
						} else {
							interfaces[0].setValue("Something went very wrong.");
						}
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
