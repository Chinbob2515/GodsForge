package main;

import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import helpers.*;

public class Init {
	
	public static void main(String[] args){
		Settings.init();
		Graphics.init();
		Main main = new Main();
		main.run();
		end();
	}
	
	public static void end(){
		Settings.end();
		Display.destroy();
		AL.destroy();
		System.exit(0);
	}
}
