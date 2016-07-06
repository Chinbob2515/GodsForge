package main;

import game.Game;
import helpers.Connection;
import helpers.Graphics;
import templates.ExpandContainer;
import templates.Interface;
import templates.Screen;
import templates.TextField;
import templates.TypeField;

public class CreatePlayer extends Screen{

	private String currentValue;
	
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
				new ExpandContainer(null, "Choose a greater domain", 0, 0.4, new Interface[]{
						new TextField("Race", 0, 0),
						new TextField("Water", 0, 0.0)
				}),
				new ExpandContainer(null, "Choose a lesser domain", 0, 0.5, new Interface[]{
						new TextField("Coolio", 0, 0),
						new TextField("Beans", 0, 0.0)
				}),
				new ExpandContainer(null, "Choose another lesser domain", 0, 0.6, new Interface[]{
						new TextField("These", 0, 0),
						new TextField("Are ineffective", 0, 0.0)
				}),
				null,
				new TextField(Graphics.loadTex("grey.png"), "", 0.0, 0, 0.2, 0.2)
		};
		interfaces[1].quit = true;
		interfaces[2].launchScreen = new Game();
		TextField[] inters = new TextField[Graphics.defaultImages.length+Graphics.userImages.length];
		for(int i = 0; i != inters.length; i++){
			String name;
			if(i < Graphics.defaultImages.length)
				name = Graphics.defaultNames[i];
			else
				name = Graphics.userNames[i-Graphics.defaultImages.length];
			inters[i] = new TextField(name, 0, 0.0*i);
			inters[i].setSize(30);
			inters[i].dx = 0;
		}
		interfaces[7] = new ExpandContainer(null, "Choose an image", 0, 0.3, inters);
		interfaces[8].dx = interfaces[7].dx+interfaces[7].dwidth;
		interfaces[8].dy = interfaces[7].dy;
		interfaces[8].dwidth = 0.1;
		interfaces[8].dheight = 0.1;
		System.out.println(interfaces[7].dx);
	}
	
	public void logic(){
		String val = interfaces[7].getValue();
		if(val != null){
			if(!val.equals(currentValue)){
				System.out.println("loading thing");
				interfaces[8].background = Graphics.loadTex("defaultImages/"+val);
				if(interfaces[8].background == null)
					interfaces[8].background = Graphics.loadTex("addedImages/"+val);
				currentValue = val;
			}
		}
	}
	
}
