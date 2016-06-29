package templates;

import helpers.Draw;
import helpers.Graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class TextField extends Interface{

	public String text;
	
	public TextField(Texture tex, String words, int x, int y, int width, int height){
		super(tex, x, y, width, height);
		text = words;
		this.width = width;
		this.height = height;
		this.hover = true;
	}
	
	public TextField(Texture tex, String words, double x, double y, double width, double height){
		super(tex, x, y, (float)(Graphics.fontWidth(0, words))/Graphics.WIDTH, (float)(Graphics.fontHeight(0, words))/Graphics.HEIGHT);
		text = words;
		//	width = (float)(Graphics.fontWidth(0, text))/Graphics.WIDTH;
		//	height = (float)(Graphics.fontHeight(0, text))/Graphics.HEIGHT;
		this.hover = true;
		if(dy + height/2 > 1.0)
			dy = 1.0-height/2;
	}
	
	public void render(){
		if(background != null){
			Draw.renderthistex(getRectangle(), background);
		}
		String draw = text;
		if(addi != null){
			draw = draw + addi[0];
		}
		if(adds != null){
			draw = draw + adds[0];
		}
		if(hover){
			if(inside(parent.mousex, parent.mousey)){
				Graphics.fonts[0].drawString(getX(), getY(), draw, new Color(0, 0, 0));
			}else{
				Graphics.fonts[0].drawString(getX(), getY(), draw);
			}
		}else{
			Graphics.fonts[0].drawString(getX()-Graphics.fontWidth(0, draw)/2, getY()-Graphics.fontHeight(0, draw)/2, draw);
		}
	}

}
