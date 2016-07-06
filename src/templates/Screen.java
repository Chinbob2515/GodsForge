package templates;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;
import helpers.Draw;
import helpers.Graphics;
import helpers.Settings;
import static helpers.Graphics.HEIGHT;
import static helpers.Graphics.WIDTH;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.opengl.Texture;

public abstract class Screen {
	
	private static final String resLocation = "Screens/";
	
	protected boolean LOG;
	
	private Texture background;
	protected Texture[] textures;
	protected ArrayList<Integer[]> statics;
	protected ArrayList<Integer[]> dynamics;
	
	private boolean setted;
	protected Interface focused;
	public boolean run;
	public Screen parent;
	public int translate_x = 0, translate_y = 0, mousex = 0, mousey = 0;
	public final String NAME = this.getClass().getSimpleName();
	
	public Interface[] interfaces;
	
	public Screen(Texture background){
		if(background == null)
			background = Graphics.loadTex(resLocation+NAME);
		this.background = background;
		statics = new ArrayList<Integer[]>(); // Completely forgot the point of this- maybe originally meant to simple interfaces?
		dynamics = new ArrayList<Integer[]>(); 
		Settings.screens.add(this);
		interfaces = new Interface[0]; // To prevent null pointer exceptions
	}
	
	public Screen(){
		this(null); // For some reason you can't access "this" while invoking a constructor, so can't reference class name.
	}
	
	protected void render(){
		if(!setted){
			setParent();
			setted = true;
		}
		glClear(GL_COLOR_BUFFER_BIT);
		
		glPushMatrix();
		
		glTranslatef(translate_x, translate_y, 0);
		
		if (Display.wasResized()) {
            Graphics.RWIDTH = Display.getWidth();
            Graphics.RHEIGHT = Display.getHeight();

            GL11.glViewport(0, 0, Graphics.RWIDTH, Graphics.RHEIGHT);
            GL11.glLoadIdentity();
		}
		
		// Retrieve the "true" coordinates of the mouse.
		mousex = (int) (Mouse.getX() * ((float)(WIDTH) / Graphics.RWIDTH) - translate_x);
		mousey = (int) (HEIGHT - Mouse.getY() * ((float)(HEIGHT) / Graphics.RHEIGHT) - 1 - translate_y);
		if(background != null)
			Draw.renderthistex(new Rectangle(0,0,WIDTH, HEIGHT), background);
		
		if(interfaces != null){
			for(int i = -3; i <= 3; i ++){
				for(Interface inter: interfaces){
					if(inter.zindex == i)
						inter.render();
				}
			}
		}
		render2();
		
		if(Display.isCloseRequested()){
			Settings.end();
		}

		glPopMatrix();
		Display.update();
		Display.sync(60);
	}
	
	public void start(){
		run = true;
	}
	
	public void run(){
		start();
		while(run){
			check();
			render();
			logic();
		}
		onEnd();
	}
	
	public void check(){
		Mouse.poll();
		while(Mouse.next()){
			if(Mouse.getEventButtonState()){
				int eventKey = Mouse.getEventButton();
				ArrayList<Interface> clicksOn = new ArrayList<Interface>();
				for(Interface inter: interfaces){
					if(inter.LOG) System.out.println("thing onclick "+inter.onClick(mousex, mousey, eventKey));
					if(inter.onClick(mousex, mousey, eventKey)){clicksOn.add(inter);}
				}
				int max = -3;
				for(Interface inter: clicksOn){
					if(inter.zindex > max){max = inter.zindex;}
				}
				int id = -1;
				focused = null;
				for(Interface inter: clicksOn){
					if(inter.LOG) System.out.println("thing clicked");
					if(inter.zindex == max){
						if(inter.detailedResponse)
							inter.response(eventKey, mousex, mousey);
						else
							inter.response(eventKey);
						inter.focus = true;
						id = inter.id;
						focused = inter;
						break;
					}
				}
				for(Interface inter: interfaces){
					if(inter.id != id){
						inter.focus = false;
					}
				}
				
			}
		}
		int dWheel = Mouse.getDWheel();
		if(focused != null)
			focused.onScroll(dWheel);
		Keyboard.poll();
		while(Keyboard.next()){
			int key = Keyboard.getEventKey();
			boolean state = Keyboard.getEventKeyState();
			if(key == Keyboard.KEY_ESCAPE && state){
				onEscapeButton();
			}
			if(focused != null){
				focused.onButton(key, state);
			}
		}
	}
	
	public void onEscapeButton(){
		run = false;
	}
	
	public void setParent(){
		for(Interface inter: interfaces){
			inter.setParent(this);
		}
	}
	
	public void end(){
		run = false;
		if(textures != null){
			for(Texture tex: textures){
				tex.release();
			}
		}
		if(background != null)
			background.release();
		Settings.screens.remove(this);
	}

	public void onEnd(){}
	public void render2(){} // Probably never going to be used, but it's a small overhead.
	public void logic(){}

}
