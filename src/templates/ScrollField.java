package templates;

import helpers.Graphics;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class ScrollField extends Interface{
	
	public final double SENSITIVITY = 0.1; // Since just using raw scroll data is way too responsive.
	public final double DEFAULTGAP = 0.01; // Default gap between members.
	
	private ArrayList<Interface> inters = new ArrayList<Interface>();
	public Interface focused;
	
	public boolean scrollHorizontal; // Not integrated yet- probably useless anyways.
	@SuppressWarnings("unused")
	private double xOffset, yOffset;
	
	public ScrollField(Texture tex, double x, double y, double width, double height, Interface[] contains) {
		super(tex, x, y, width, height);
		double yOff = 0;
		for(Interface inter: contains){ //Only works with relative co-ords? FIX FIX FIX
			inter.setY(inter.dy  + yOff + y);; // dy should be set relative to the container, i.e. this (but when wouldn't it be 0?)
			yOff += inter.dheight + DEFAULTGAP;
			inter.dwidth = width;
			inters.add(inter);
		}
		xOffset = yOffset = 0;
		detailedResponse = true;
	}
	
	public void onScroll(int dwheel){
		yOffset += dwheel * SENSITIVITY;
	}
	
	public void response(int eventKey, int mousex, int mousey){ // ScrollFields don't support launchScreens or quit fields.
		ArrayList<Interface> clicksOn = new ArrayList<Interface>();
		for(Interface inter: inters){
			inter.dy += yOffset / Graphics.HEIGHT; // Preserve original position.
		}
		for(Interface inter: inters){
			if(inter.onClick(mousex, mousey, eventKey)){clicksOn.add(inter);}
		}
		int max = -3;
		for(Interface inter: clicksOn){
			if(inter.zindex > max){max = inter.zindex;}
		}
		int id = -1;
		focused = null;
		for(Interface inter: clicksOn){
			if(inter.zindex == max){
				if(inter.detailedResponse)
					inter.response(eventKey);
				else
					inter.response(eventKey, mousex, mousey);
				inter.focus = true;
				id = inter.id;
				focused = inter;
				break;
			}
		}
		for(Interface inter: inters){
			if(inter.id != id){
				inter.focus = false;
			}
		}
		for(Interface inter: inters){
			inter.dy -= yOffset / Graphics.HEIGHT; // ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
		}
	}
	
	public void render(){
		super.render();
		for(Interface inter: inters){
			inter.setY(inter.dy + yOffset / Graphics.HEIGHT); // Preserve original position.
			inter.render();
			inter.setY(inter.dy - yOffset / Graphics.HEIGHT); // ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^
		}
	}
	
	public void setParent(Screen screen){
		parent = screen;
		for(Interface inter: inters){
			inter.setParent(screen);;
		}
	}
	
	public void setX(double x){
		for(Interface inter: inters){
			inter.setX(inter.dx - dx + x);
		}
		dx = x;
	}
	
	public void setY(double y){
		for(Interface inter: inters){
			inter.setX(inter.dy - dy + y);
		}
		dy = y;
	}
	
	public String getValue(){
		return ""+yOffset;
	}
	
	public void setValue(String string){
		yOffset = Double.parseDouble(string);
	}

}
