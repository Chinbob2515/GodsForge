package game;

import helpers.Graphics;
import templates.*;

// /*

public class GameScreen extends Screen{
	
	public Game game;
	
	public GameScreen() {
		super();
		interfaces = new Interface[]{
				new Container(Graphics.loadTex("grey.png"), 0, 0, 1, 0.1, new Interface[]{
					new ExpandContainer(Graphics.loadTex("grey.png"), "Menu", 1, 0, new Interface[]{
						new TextField("First", 0, 0)
					})
				})
		};
		Interface a = ((ExpandContainer) ((Container) interfaces[0]).getInter(0)).getInter(0);
		a.LOG=true;
		System.out.println("logging: "+a.dwidth+" "+a.dheight);
	}
	
	public void start(){
		super.start();
		game = new Game();
	}

}
