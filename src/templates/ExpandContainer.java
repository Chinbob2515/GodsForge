package templates;

import helpers.Graphics;

import org.newdawn.slick.opengl.Texture;

public class ExpandContainer extends Container{
	
	public double defwidth, defheight, rwidth, rheight;
	public TextField textField;

	public ExpandContainer(Texture tex, TextField txfld, double x, double y, Interface[] contains) {
		super(tex, x, y, 0, 0, contains);
		rwidth = dwidth;
		rheight = rwidth;
		textField = txfld;
		defwidth = dwidth = (float)(Graphics.fontWidth(0, txfld.getValue()))/Graphics.WIDTH;
		defheight = dheight = (float)(Graphics.fontHeight(0, txfld.getValue()))/Graphics.HEIGHT;
		hover = true; // REINFORCE THIS
	}
	
	public ExpandContainer(Texture tex, String words, double x, double y, Interface[] contains){
		this(tex, new TextField(words, x, y), x, y, contains);
	}
	
	public void render(){
		textField.setY(textField.dy + dy);
		textField.render();
		textField.setY(textField.dy - dy);
		/*if(addi != null){
			draw = draw + addi[0];
		}
		if(adds != null){
			draw = draw + adds[0];
		}*/ // We'll never need this? Right?
		if(focus){
			setY(dy + textField.height);
			super.render();
			setY(dy - textField.height);
		}
	}

}
