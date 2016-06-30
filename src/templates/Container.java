package templates;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class Container extends Interface{
	
	public final double SENSITIVITY = 0.1; // Since just using raw scroll data is way too responsive.
	
	private ArrayList<Interface> inters = new ArrayList<Interface>();
	public Interface focused;
	
	public Container(Texture tex, double x, double y, double width, double height, Interface[] contains) {
		super(tex, x, y, width, height);// dy should be set relative to the container
		double minx = 1, miny = 1, maxx = 0, maxy = 0;
		for(Interface inter: contains){ //Only works with relative co-ords? FIX FIX FIX
			inter.setY(inter.dy + y); 
			inter.setX(inter.dx + x); 
			if(inter.dx < minx) minx = inter.dx;
			if(inter.dy < miny) miny = inter.dy;
			if(inter.dx > maxx) maxx = inter.dx;
			if(inter.dy > maxy) maxy = inter.dy;
			inters.add(inter);
		}
		this.dheight = maxy - miny;
		this.dwidth = maxx - minx;
		detailedResponse = true;
	}
	
	public void response(int eventKey, int mousex, int mousey){ // Containers don't support launchScreens or quit fields.
		ArrayList<Interface> clicksOn = new ArrayList<Interface>();
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
	}
	
	public void render(){
		super.render();
		for(Interface inter: inters){
			inter.render();
		}
	}
	
	public void setParent(Screen screen){
		parent = screen;
		for(Interface inter: inters){
			inter.setParent(screen);
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
			inter.setY(inter.dy - dy + y);
		}
		dy = y;
	}

}
