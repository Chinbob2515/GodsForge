package templates;

import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

import helpers.Draw;
import helpers.Graphics;

public abstract class Interface {
	
	private static int ID = 0;
	
	public boolean LOG;
	public Texture background;
	protected int type; // Absolute, ratio
	
	protected int x, y, width, height;

	public boolean quit, hover, focus, detailedResponse;
	public int[] addi, info; // Only first element matters, meant
	public String[] adds; //    to function like a pointer.
	public double dx, dy, dwidth, dheight;
	public Screen launchScreen, parent;
	public int id, zindex = 0;
	
	@Deprecated
	public Interface(Texture tex, int x, int y, int width, int height){
		background = tex;			// ...
		type = 0; 					// THIS TYPE IS BASICALLY DEPRECATED AT THIS POINT- NOTHING WORKS WITH IT
		this.x = x;					// ...
		this.y = y;					// ...
		this.width = width;			// ...
		this.height = height;		// ...
		this.id = assignID();		// ...
		throw new RuntimeException("Never use this type of Interface");
	}
	
	public Interface(Texture tex, double x, double y, double width, double height){
		background = tex;
		this.type = 1;
		this.dx = x - width/2;
		this.dy = y - height/2;
		this.dwidth = width;
		this.dheight = height;
		fitToScreen();
		this.id = assignID();
	}
	
	public void fitToScreen(){
		if(dx+dwidth > 1)
			dx = 1 - dwidth;
		else if(dx < 0)
			dx = 0;
		if(dy+dheight > 1)
			dy = 1-dheight;
		else if(dy < 0)
			dy = 0;
	}
	
	public static int assignID(){
		return ID++;
	}
	
	public void render(){
		renderBackground();
		if(background != null){
			if(LOG)
				System.out.println("drawing back");
			Draw.renderthistex(getRectangle(), background);
		} else if (LOG)
			System.out.println("not drawing back");
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
	public void onScroll(int dwheel){}
	
	public void response(int eventKey){
		if(quit){parent.run = false;}
		if(launchScreen != null){
			parent.onEnd();
			launchScreen.parent = parent;
			launchScreen.run();
		}
	}
	
	public void response(int eventKey, int x, int y){}
	
	public void setParent(Screen screen){parent = screen;}
	
	public String getValue(){if(adds != null) return adds[0]; return null;}
	public void setValue(String value){if(adds != null)adds[0] = value;}
	
	public void renderBackground(){}
	
	public void setWidth(double width){
		dx += dwidth/2;
		dwidth = width;
		dx -= dwidth/2;
	}
	public void setHeight(double height){
		dy += dheight/2;
		dheight = height;
		dy -= dheight/2;
	}
	public void setX(double x){dx = x;}
	public void setY(double y){dy = y;}
	public int getrX(){if(type == 0){return x;}else{return (int) (dx);}}
	public int getrY(){if(type == 0){return y;}else{return (int) (dy);}}
	public int getX(){if(type == 0){return x;}else{return (int) (dx*Graphics.WIDTH);}}
	public int getY(){if(type == 0){return y;}else{return (int) (dy*Graphics.HEIGHT);}}
	public int getrWidth(){if(type == 0){return width;}else{return (int) (dwidth);}}
	public int getrHeight(){if(type == 0){return height;}else{return (int) (dheight);}}
	public int getWidth(){if(type == 0){return width;}else{return (int) (dwidth*Graphics.WIDTH);}}
	public int getHeight(){if(type == 0){return height;}else{return (int) (dheight*Graphics.HEIGHT);}}
	
	protected void Log(Object... objects){
		if(!LOG){return;}
		String string = "";
		for(Object obj: objects){string += obj.toString();}
		System.out.println(string);
	}
	
}
