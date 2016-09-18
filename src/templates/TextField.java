package templates;

import helpers.Graphics;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class TextField extends Interface{

	public String text;
	private int drawnBy = 0;
	
	@Deprecated
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
	
	public TextField(String words, double x, double y, double width, double height){
		this(null, words, x, y, width, height);
	}
	
	public TextField(String words, double x, double y, double width, double height, boolean hover){
		this(null, words, x, y, width, height, hover);
	}
	
	public TextField(Texture tex, String words, double x, double y, double width, double height, boolean hover){
		this(tex, words, x, y, width, height);
		this.hover = hover;
	}
	
	public TextField(String words, double x, double y){ // Basically everything else if pointless.
		this(null, words, x, y, 0, 0);
	}
	
	public TextField(String words, double x, double y, boolean hover){
		this(words, x, y);
		this.hover = hover;
	}
	
	public void render(){
		super.render();
		String draw = text;
		if(addi != null){
			draw = draw + addi[0];
		}
		if(adds != null){
			draw = draw + adds[0];
		}
		if(hover){
			if(inside(parent.mousex, parent.mousey)){
				Graphics.fonts[drawnBy].drawString(getX(), getY(), draw, new Color(0, 0, 0));
			}else{
				Graphics.fonts[drawnBy].drawString(getX(), getY(), draw);
			}
		}else{
			Graphics.fonts[drawnBy].drawString(getX(), getY(), draw);//Graphics.fonts[0].drawString(getX()-Graphics.fontWidth(0, draw)/2, getY()-Graphics.fontHeight(0, draw)/2, draw);
		}
	}
	
	public void setValue(String s){
		dx += dwidth/2;
		text = s;
		dwidth = (float)(Graphics.fontWidth(0, s))/Graphics.WIDTH;
		dx -= dwidth / 2;
	}
	
	public String getValue(){
		return text;
	}
	
	public void setSize(int size){
		Graphics.addFont(size);
		drawnBy = Graphics.fonts.length-1;
		setWidth((float)(Graphics.fontWidth(drawnBy, text))/Graphics.WIDTH);
		setHeight((float)(Graphics.fontHeight(drawnBy, text))/Graphics.HEIGHT);
	}

}
