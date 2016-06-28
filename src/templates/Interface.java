package templates;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

import helpers.Draw;
import helpers.Graphics;

public class Interface {
	
	private static int ID = 0;
	
	protected Texture background;
	protected int type; // Absolute, ratio
	
	protected int x, y, width, height;

	public boolean quit, hover, focus;
	public int[] addi, info;
	public String[] adds;
	public double dx, dy, dwidth, dheight;
	public Screen launchScreen, parent;
	public int id, zindex = 0;
	
	public Interface(Texture tex, int x, int y, int width, int height){
		background = tex;
		type = 0;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = assignID();
	}
	
	public Interface(Texture tex, double x, double y, double width, double height){
		background = tex;
		this.type = 1;
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
	}
	
	public static int assignID(){
		return ID++;
	}
	
	public void render(){
		if(background != null){
			Draw.renderthistex(getRectangle(), background);
		}
	}
	
	public boolean inside(int a, int b){
		if(a>getX() && a<getX()+getWidth() && b>getY() && b<getY()+getHeight())
			return true;
		else
			return false;
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	public boolean onClick(int a, int b, int eventKey){
		if(a>getX() && a<getX()+getWidth() && b>getY() && b<getY()+getHeight()){
			return true;
		}
		return false;
	}
	
	public void onButton(int key, boolean status){}
	public void onButton(boolean status){}
	
	public void response(int eventKey){
		if(quit){parent.run = false;}
		if(launchScreen != null){
			launchScreen.run();
		}
	};
	
	public int getX(){if(type == 0){return x;}else{return (int) (dx*Graphics.WIDTH);}}
	public int getY(){if(type == 0){return y;}else{return (int) (dy*Graphics.HEIGHT);}}
	public int getWidth(){if(type == 0){return width;}else{return (int) (dwidth*Graphics.WIDTH);}}
	public int getHeight(){if(type == 0){return height;}else{return (int) (dheight*Graphics.HEIGHT);}}
	
}
