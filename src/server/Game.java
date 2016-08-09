package server;

import game.Tile;

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
	public static int theight, twidth; // Tile width, tile height

    public Spin spin;
    
	public int idCounter = 0;
	public boolean run = true;
	
	// public [playerObject][] list (or is that just a spin or gods object?)
	public Tile[][] world;

	public boolean retry = false, showbd = false, started = false;
	public ArrayList<Integer> players = new ArrayList<Integer>();

	public int deathCount = -1;

	public Game(Spin tspin){
        spin = tspin;
	}

	public void init(){
        world = new Tile[3][];
        for(int y = 0; y != 3; y++){
        	for(int x = 0; x != 3; x++){
        		if(y == 0)
        			world[x] = new Tile[3];
        		world[x][y] = new Tile(x, y);
        	}
        }
		started = true;
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
