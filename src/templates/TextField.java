package templates;

import helpers.Graphics;

import org.newdawn.slick.opengl.Texture;

public class TextField extends Interface{

	public String text;
	
	public TextField(Texture tex, String words, int x, int y, int width, int height){
		super(tex, x, y, width, height);
		text = words;
		this.width = width;
		this.height = height;
		if(words != null)
			this.hover = true;
	}
	
	public TextField(Texture tex, String words, double x, double y, double width, double height){
		super(tex, x, y, width, height);
		text = words;
		this.type = 1;
		if(words != null){
			width = (float)(Graphics.fontWidth(0, text))/Graphics.WIDTH;
			height = (float)(Graphics.fontHeight(0, text))/Graphics.HEIGHT;
		}
		this.dx = x - width/2;
		this.dy = y - height/2;
		if(this.dx > 1)
			this.dx = 1;
		else if(this.dx < 0)
			this.dx = 0;
		if(this.dy > 1)
			this.dy = 1;
		else if(this.dy < 0)
			this.dy = 0;
		this.dwidth = width;
		this.dheight = height;
		this.id = assignID();
		if(words != null)
			this.hover = true;
	}

}
