package templates;

import helpers.Graphics;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class ScrollField extends Interface{
	
	public final double SENSITIVITY = 0.1; // Since just using raw scroll data is way too responsive.
	
	public ArrayList<Interface> inters = new ArrayList<Interface>();
	
	public boolean scrollHorizontal; // Not integrated yet- probably useless anyways.
	@SuppressWarnings("unused")
	private double xOffset, yOffset;
	
	public ScrollField(Texture tex, double x, double y, double width, double height, Interface[] contains) {
		super(tex, x, y, width, height);
		double yOff = 0;
		for(Interface inter: contains){ //Only works with relative co-ords? FIX FIX FIX
			inter.dy += yOff + y; // dy should be set relative to the container, i.e. this (but when wouldn't it be 0?)
			System.out.println(yOff);
			yOff += inter.dheight;
			inter.dwidth = width;
			inters.add(inter);
		}
		xOffset = yOffset = 0;
	}
	
	public void onScroll(int dwheel){
		yOffset += dwheel * SENSITIVITY;
	}
	
	public void render(){
		super.render();
		for(Interface inter: inters){
			inter.dy += yOffset / Graphics.HEIGHT;
			inter.render();
			inter.dy -= yOffset / Graphics.HEIGHT;
		}
	}
	
	public void setParent(Screen screen){
		parent = screen;
		for(Interface inter: inters){
			inter.parent = screen;
		}
	}

}
