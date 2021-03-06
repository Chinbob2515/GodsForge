package templates;

import helpers.Draw;
import helpers.Graphics;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class TypeField extends Interface{
	// For interactive text input.

	private String defString, text;
	
	public TypeField(Texture tex, String def, double x, double y, double width, double height) {
		super(Graphics.loadTex("inv"), x, y, (float)(Graphics.fontWidth(0, def))/Graphics.WIDTH, (float)(Graphics.fontHeight(0, def))/Graphics.HEIGHT);
		defString = def; // string to show when there is no other
		text = "";
	}
	
	public void render(){
		Draw.drawSquare(x-width/2, y-height/2, width, height);
		if(!text.equals("")){
			Graphics.fonts[0].drawString(getX()/*-Graphics.fontWidth(0, text)/2*/, getY()/*-height/2*/, text);
		} else {
			Graphics.fonts[0].drawString(getX()/*-Graphics.fontWidth(0, defString)/2*/, getY()/*-height/2*/, defString, new Color(255, 255, 255, 125));
		}
	}
	
	public void onButton(int key, boolean status){
		if(!status){return;}
		String typed = Keyboard.getKeyName(key).toLowerCase(); // typed representation of the character ("b", "a", "back")
		if(typed.length() > 1){ // If the character isn't a conventional character, it will be a word rather than a single character
			if(typed.equals("period")){text += ".";}
			if(typed.equals("back")){
				if(text.length() == 0){return;}
				text = text.substring(0, text.length()-1);
			}
			return;
		}
		text += typed; // Normal characters just get typed- I don't know of any which can't be in a url, or care.
	}
	
	public String getValue(){
		return text;
	}
	
	public void setValue(String value){
		text = value;
	}

}
