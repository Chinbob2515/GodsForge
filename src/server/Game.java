package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Font;

@SuppressWarnings("unused")
public class Game {

	public static final Random rand = new Random();
	public static final int WIDTH = 1000, HEIGHT = 1000;
	public static int theight, twidth; // Tile width, tile height

    public Spin spin;
    
	public int idCounter = 0;
	public boolean run = true;
	
	// public [playerObject][] list

	public boolean retry = false, showbd = false;
	public ArrayList<Integer> players = new ArrayList<Integer>();

	public int deathCount = -1;

	public Game(Spin tspin){
        spin = tspin;
	}

	public void init(){
        // Set up stuff, I guess
	}

	public void run() {

		init();

		run = true;

        Server.log("Game starting");

		while (run) {

			logic();
			if(!run){
				break;
			}

			update();

            try{Thread.sleep((long)(1000/60));}catch(InterruptedException e){Server.log("Since when has there ever been one of these?!?");}

		}

		finish();

	}

	public void logic(){

	}

	public void update(){
		
	}

    private void finish(){

    }

	public void end(){
        run = false;
	}

}
