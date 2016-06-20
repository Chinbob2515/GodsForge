package helpers;

import java.util.ArrayList;
import java.util.Random;

import templates.Screen;

public class Settings {

	public static final boolean DEBUG = false;
	
	public static boolean loaded = false;
	public static ArrayList<Screen> screens = new ArrayList<Screen>();
	
	public static Random random;
	public static int[] generic;
	
	public static void init(){
		random = new Random();
		loaded = true;
		generic = IOHandle.getSettings();
	}
	
	@SuppressWarnings("unchecked")
	public static void end(){
		for(Screen screen: (ArrayList<Screen>)screens.clone()){
			screen.end();
		}
		random = null;
		generic[0] = Graphics.RWIDTH;
		generic[1] = Graphics.RHEIGHT;
		IOHandle.writeSettings(generic);
		loaded = false;
	}
	
}
