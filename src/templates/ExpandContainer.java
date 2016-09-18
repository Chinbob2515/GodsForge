package templates;

import helpers.Graphics;

import org.newdawn.slick.opengl.Texture;

public class ExpandContainer extends Container{
	
	private boolean expanded, chosen;
	public double defwidth, defheight, rwidth, rheight;
	public TextField textField;
	public String defValue;

	public ExpandContainer(Texture tex, TextField txfld, double x, double y, Interface[] contains) {
		super(tex==null?Graphics.loadTex("goDown"):tex, x+txfld.dwidth/2, y, contains);
		defwidth = dwidth;
		defheight = dheight;
		rwidth = dwidth = (float)(Graphics.fontWidth(0, txfld.getValue()))/Graphics.WIDTH;
		rheight = dheight = (float)(Graphics.fontHeight(0, txfld.getValue()))/Graphics.HEIGHT;
		if(dwidth > defwidth)
			defwidth = dwidth;
		txfld.setY(txfld.getrY()-txfld.getrHeight());
		txfld.setX(dx);
		textField = txfld;
		defValue = txfld.getValue();
		hover = true; // REINFORCE THIS
		double heightcount = 0;
		for(Interface inter: contains){
			inter.setY((double)inter.dy+txfld.dheight+heightcount); // For some reason inter.dy HAS to be added in, so has to be 0.0
			heightcount += inter.dheight;
			inter.launchScreen = new fillValue(inter.getValue(), id); // Use an invisible screen to launch and then fill value.
		}
		defheight = heightcount;
		fitToScreen();
	}
	
	public ExpandContainer(Texture tex, String words, double x, double y, Interface[] contains){
		this(tex, new TextField(words, x, y), x, y, contains);
	}
	
	public void render(){
		textField.setY(textField.dy + dy + (expanded?0:getrHeight()));
		textField.render();
		textField.setY(textField.dy - dy - (expanded?0:getrHeight()));
		if(focus){
			super.render();
		} else if(expanded){
			expanded = !expanded;
			dwidth = rwidth;
			dheight = rheight;
			zindex = 1;
		}
	}
	
	public void setParent(Screen screen){
		super.setParent(screen);
		textField.setParent(parent);
	}
	
	public void response(int eventKey, int mousex, int mousey){
		super.response(eventKey, mousex, mousey);
		if(!expanded){
			zindex = 3;
			dwidth = defwidth;
			dheight = defheight;
			dheight += textField.dheight;
		}
		expanded = true;
	}
	
	public void setValue(String string){
		chosen = true;
		textField.setValue(string);
	}
	
	public String getValue(){
		if(chosen)
			return textField.getValue();
		else
			return null;
	}
	
	public static void setSize(TextField[] input, int size){
		for(TextField inter: input){
			inter.setSize(size);
			inter.dx = 0;
		}
	}
	
	public class fillValue extends Screen{
		
		public String value;
		public int id;
		
		public fillValue(String value, int id){
			this.value = value;
			this.id = id;
		}
		
		public void run(){
			for(Interface inter: parent.interfaces){
				if(inter.id == id)
					inter.setValue(value);
			}
		}
		
	}

}
