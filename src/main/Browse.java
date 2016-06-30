package main;

import helpers.Connection;
import helpers.Graphics;
import templates.Container;
import templates.Interface;
import templates.Screen;
import templates.ScrollField;
import templates.TextField;

public class Browse extends Screen{
	
	private long lastUpdate = System.currentTimeMillis();
	
	//private int servers = 0;
	
	public Browse() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new TextField(null, "Back", 0.0, 0, 0, 0),
				new TextField(null, "New Game", 1.0, 0, 0, 0),
				new ScrollField(null, 0, 0.1, 1, 1, new Interface[]{
						new TextField(null, "No servers right now", 0.5,0,0,0)
				}),
				new TextField(null, "Reload Games", 1.0, 1, 0, 0){
					public void response(int eventKey){
						doThing();
					}
				}
		};
		interfaces[0].quit = true;
		interfaces[1].launchScreen = new CreateGame();
		LOG = true;
		doThing();
	}
	
	public void logic(){
		if(System.currentTimeMillis() - lastUpdate > 10000){
			lastUpdate = System.currentTimeMillis();
			doThing();
		}
	}
	
	public void doThing(){
		Connection.write("0");
		String s = Connection.receive();
		System.out.println(s);
		proccess(s);
	}
	
	public void proccess(String code){
		code = code.trim();
		if(!code.split(":")[0].equals("0") || code.split(":").length < 2){
			return;//System.out.println("kill me now (as in exit)");
		}
		code = code.split(":")[1];
		if(code.endsWith(";"))
			code = code.substring(0, code.length()-1);
		String[] sections = code.split(";");
		int numOfInters = 0;
		for(int i = 0; i != sections.length; i++){
			if(sections[i].split("-").length < 2) break; // End of list
			numOfInters++;
		}
		Interface[] inters = new Interface[numOfInters];
		for(int i = 0; i != inters.length; i++){
			String[] bits = sections[i].split("-");
			if(bits.length < 2) break; // End of list
			inters[i] = new Container(null, 0.0, 0, 0, 0, new Interface[]{
					new TextField("Players: "+bits[0], 0.0, 0),
					new TextField("Rounds: "+bits[1], 1.0, 0)
			});
		}
		System.out.println(numOfInters+" many");
		interfaces[2] = new ScrollField(null, 0.1, 0.1, 0.8, 0.9, inters);
		interfaces[2].setParent(this);
	}
	
	public class CreateGame extends Screen{
		public CreateGame(){
			super(Graphics.loadTex("black"));
			interfaces = new Interface[]{
					new TextField("Set up game:", 0.5, 0, false), // TextField-s can be done in so many ways, but they're all distinct. 
					new TextField("Back", 0.0, 0),
					new TextField("Create Game", 0.5, 1){
						public void response(int eventkey){
							Connection.write("2");
							parent.run = false;
						}
					}
			};
			interfaces[1].quit = true;
		}
		
		public void logic(){
			
		}
	}

}
