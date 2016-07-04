package main;

import game.Game;
import helpers.Connection;
import templates.ExpandContainer;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import templates.TypeField;

public class CreatePlayer extends Screen{

	public CreatePlayer(){
		super();
		interfaces = new Interface[]{
				new TextField("Set up your god:", 0.5, 0, false), // TextField-s can be done in so many ways, but they're all distinct. 
				new TextField("Back", 0.0, 1),
				new TextField("Finish", 1.0, 1){
					public void response(int eventkey){
						Connection.write("3:"+parent.interfaces[3].getValue());
						parent.run = false;
						super.response(eventkey);
					}
				},
				new TypeField(null, "Name", 0.5, 0.2, 0, 0),
				new ExpandContainer(null, "Choose a greater domain", 0, 0.3, new Interface[]{
						new TextField("Race", 0, 0),
						new TextField("Water", 0, 0.1)
				}),
				new ExpandContainer(null, "Choose a lesser domain", 0, 0.5, new Interface[]{
						new TextField("Coolio", 0, 0),
						new TextField("Beans", 0, 0.1)
				}),
				new ExpandContainer(null, "Choose another lesser domain", 0, 0.7, new Interface[]{
						new TextField("These", 0, 0),
						new TextField("Are ineffective", 0, 0.1)
				})
		};
		interfaces[1].quit = true;
		interfaces[2].launchScreen = new Game();
	}
	
}
