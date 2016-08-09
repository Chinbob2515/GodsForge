package game;

import templates.Screen;

public class GameScreen extends Screen{
	
	public Game game;
	
	public GameScreen() {
		super();
	}
	
	public void start(){
		super.start();
		game = new Game();
	}

}
