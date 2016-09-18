package main;

import org.newdawn.slick.opengl.Texture;

import game.GameScreen;
import helpers.Connection;
import helpers.Draw;
import helpers.Graphics;
import templates.Container;
import templates.Interface;
import templates.Screen;
import templates.ScrollField;
import templates.TextField;
import templates.TypeField;

public class Browse extends Screen{
	
	private long lastUpdate = System.currentTimeMillis();
	
	private int serverSelect = -1;
	
	public Browse() {
		super();
		interfaces = new Interface[]{
				new TextField(null, "Back", 0.0, 0, 0, 0),
				new TextField(null, "New Game", 1.0, 0, 0, 0),
				new ScrollField(null, 0, 0.1, 1, 1, new Interface[]{
						new TextField(null, "No servers right now", 0.5,0,0,0,false)
				}),
				new TextField(null, "Reload Games", 0.0, 1, 0, 0){
					public void response(int eventKey){
						doThing();
					}
				},
				new TextField(null, "Connect", 1.0, 1, 0, 0){
					public void response(int eventKey){
						if(serverSelect < 0) return;
						Connection.write("1:"+serverSelect);
						String temp = Connection.receive();
						while(!temp.split(":")[0].equals("1")){
							System.out.println("temp is "+temp);
							temp = Connection.receive();
						}
						System.out.println("temp is "+temp);
						boolean old = temp.split(":")[1].equals("1");
						if(!old){
							launchScreen = new CreatePlayer();
						}
						super.response(eventKey);
					}
				}
		};
		interfaces[0].quit = true;
		interfaces[1].launchScreen = new CreateGame();
		interfaces[4].launchScreen = new GameScreen();
		LOG = true;
		//doThing(); Crashes things for no reason?
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
			inters[i] = new Container(null, 0, 0, new Interface[]{
					new TextField("Name: "+bits[3], 0.0, 0, false),
					new TextField("Players: "+bits[0], 0.0, 0.12, false),
					new TextField("Rounds: "+bits[1], 1.0, 0.12, false),
					new TextField("Max players: "+bits[2], 0, 0.24, false)
			}){
				public Texture grey2 = Graphics.loadTex("grey2"), grey = Graphics.loadTex("grey");
				public void renderBackground(){
					if(focus){
						Draw.renderthistex(getRectangle(), grey2);
					} else {
						Draw.renderthistex(getRectangle(), grey);
					}
				}
				public void response(int eventKey){
					serverSelect = addi[0];
					System.out.println("server select "+serverSelect);
				}
			};
			inters[i].addi = new int[]{i};
		}
		String scroll = interfaces[2].getValue();
		boolean focus = interfaces[2].focus;
		interfaces[2] = new ScrollField(null, 0, 0.1, 1, 0.9, inters); // Oh god so much overhead to switching elements in and out.
		interfaces[2].setValue(scroll);
		interfaces[2].focus = focus;
		if(focus)
			focused = interfaces[2];
		interfaces[2].setParent(this);
	}
	
	public class CreateGame extends Screen{
		public CreateGame(){
			super();
			interfaces = new Interface[]{
					new TextField("Set up game:", 0.5, 0, false), // TextField-s can be done in so many ways, but they're all distinct. 
					new TextField("Back", 0.0, 0),
					new TextField("Create Game", 0.5, 1){
						public void response(int eventkey){
							Connection.write("2:"+parent.interfaces[3].getValue());
							parent.run = false;
						}
					},
					new TypeField(null, "Name", 0.5, 0.2, 0, 0)
			};
			interfaces[1].quit = true;
		}
	}

}
