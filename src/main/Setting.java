package main;

import templates.Interface;
import templates.Screen;
import helpers.Graphics;
import helpers.Settings;

public class Setting extends Screen{

	public Setting() {
		super(Graphics.loadTex("black"));
		interfaces = new Interface[]{
				new Interface(null, "Fullscreen: ", 0.5, 0.25, 0.1, 0.1){
					public void response(int eventKey){
						info[0]++;
						info[0]%=2;
						setEnd();
						Graphics.setFullscreen(info[0]==1);
					}
					public void setEnd(){adds[0] = (info[0] == 1?"On":"Off");}
				},
				new Interface(null, "Back", 0.0, 0.9, 0.0, 0.0)
		};
		interfaces[1].quit = true;
		interfaces[0].info = new int[]{Settings.generic[2]};
		interfaces[0].adds = new String[]{""};
		interfaces[0].response(0);interfaces[0].response(0);
	}
	
	public void run(){
		start();
		while(run){
			check();
			render();
		}
		Settings.generic[2] = interfaces[0].info[0];
	}

}
