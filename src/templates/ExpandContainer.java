package templates;

import helpers.Graphics;

import org.newdawn.slick.opengl.Texture;

public class ExpandContainer extends Container{
	
	private boolean expanded;
	public double defwidth, defheight, rwidth, rheight;
	public TextField textField;
	public String defValue;

	public ExpandContainer(Texture tex, TextField txfld, double x, double y, Interface[] contains) {
		super(tex, x+txfld.dwidth/2, y, 0, 0, contains);
		defwidth = dwidth;
		defheight = dheight;
		rwidth = dwidth = (float)(Graphics.fontWidth(0, txfld.getValue()))/Graphics.WIDTH;
		rheight = dheight = (float)(Graphics.fontHeight(0, txfld.getValue()))/Graphics.HEIGHT;
		txfld.setY(txfld.getrY()-txfld.getrHeight());
		txfld.setX(dx);
		textField = txfld;
		defValue = txfld.getValue();
		hover = true; // REINFORCE THIS
		for(Interface inter: contains){
			inter.setY(inter.dy+txfld.dheight);
			inter.launchScreen = new fillValue(inter.getValue(), id);
		}
	}
	
	public ExpandContainer(Texture tex, String words, double x, double y, Interface[] contains){
		this(tex, new TextField(words, x, y), x, y, contains);
	}
	
	public void render(){
		textField.setY(textField.dy + dy + (expanded?0:getrHeight()));
		textField.render();
		textField.setY(textField.dy - dy - (expanded?0:getrHeight()));
		/*if(addi != null){
			draw = draw + addi[0];
		}
		if(adds != null){
			draw = draw + adds[0];
		}*/ // We'll never need this? Right?
		if(focus){
			//setY(dy + textField.height);
			super.render();
			//setY(dy - textField.height);
		} else if(expanded){
			expanded = !expanded;
			dwidth = rwidth;
			dheight = rheight;
		}
	}
	
	public void setParent(Screen screen){
		super.setParent(screen);
		textField.setParent(parent);
	}
	
	public void response(int eventKey, int mousex, int mousey){
		super.response(eventKey, mousex, mousey);
		if(!expanded){
			dwidth = defwidth;
			dheight = defheight;
			dheight += textField.dheight;
		}
		expanded = true;
	}
	
	public void setValue(String string){
		textField.setValue(string);
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
