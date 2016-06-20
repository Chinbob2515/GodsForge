package helpers;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Graphics {
	
	public static boolean loaded = false;

	public static DisplayMode backupDisplayMode;

	public final static int WIDTH = 1280, HEIGHT = 720;
	public static int RWIDTH, RHEIGHT;
	public static final String FONT_TYPE = "Times New Roman";
	public static UnicodeFont[] fonts;
	public static Texture[] images;
	
	//public static int mousex, mousey;
	
	public static void init(){
		init_LWJGL(IOHandle.getSettings());
		loadImages();
		loadSounds();
		initFont();
		loaded = true;
	}
	
	private static void loadImages(){
		images = new Texture[1];
		try {
			images[0] = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/black.png")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private static void loadSounds(){
		
	}
	
	@SuppressWarnings("unchecked")
	private static void initFont() {
		fonts = new UnicodeFont[1];
	    Font font = new Font(FONT_TYPE, Font.BOLD, 60);
	    fonts[0] = new UnicodeFont(font);
	    fonts[0].addAsciiGlyphs();
	    fonts[0].addGlyphs(400, 600);
	    fonts[0].getEffects().add(new ColorEffect(java.awt.Color.white));
	    
	    try {
	        fonts[0].loadGlyphs();
	    } catch (SlickException e) {
		    System.out.println("something went wrong here!");
		    e.printStackTrace();
		    Display.destroy();
	    }
	}
	
	private static void init_LWJGL(int[] settings){

		RWIDTH = settings[0];
		RHEIGHT = settings[1];
		
		DisplayMode displayMode = null;
        DisplayMode[] modes = null;
		try {
			modes = Display.getAvailableDisplayModes();
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}

         for (int i = 0; i < modes.length; i++){
             if (modes[i].getWidth() == RWIDTH && modes[i].getHeight() == RHEIGHT && modes[i].isFullscreenCapable()){
                    displayMode = modes[i];
             }
             if (modes[i].getWidth() == WIDTH && modes[i].getHeight() == HEIGHT && modes[i].isFullscreenCapable()){
                 backupDisplayMode = modes[i];
             }
         }
         
         if(displayMode == null){
        	 displayMode = new DisplayMode(RWIDTH, RHEIGHT);
         }
         
         if(Settings.DEBUG){
        	 displayMode = new DisplayMode(400, 400);
        	 RWIDTH = 400;
        	 RHEIGHT = 400;
         }
    	 
		try {
			Display.setInitialBackground(255, 255, 255);
			Display.setDisplayMode(settings[2]==1?backupDisplayMode:displayMode);
			RWIDTH = (settings[2]==1?backupDisplayMode:displayMode).getWidth();
			RHEIGHT = (settings[2]==1?backupDisplayMode:displayMode).getHeight();
			Display.setResizable(true);
			Display.setLocation(0, 0);
			Display.setFullscreen(settings[2] == 1);
			Display.setTitle("Gods' Forge");
	        try {
				Display.setIcon(new ByteBuffer[] {
				         new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/icon.png")), false, false, null),
				         new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File("res/icon.png")), false, false, null)
				         });
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, -1, 10);
		glMatrixMode(GL_MODELVIEW);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
        glEnable(GL_TEXTURE_2D);
	}
	
	public static Texture loadTex(String name){
		Texture answer = null;
		try {
			answer = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/"+name+".png")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return answer;
	}
	
	public static void setFullscreen(boolean full){
		try {
			Display.setFullscreen(full);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		RWIDTH = Display.getWidth();
        RHEIGHT = Display.getHeight();

        GL11.glViewport(0, 0, RWIDTH, RHEIGHT);
        GL11.glLoadIdentity();
	}
	
	public static int fontWidth(int font, String text){
		return fonts[font].getWidth(text);
	}
	
	public static int fontHeight(int font, String text){
		return fonts[font].getHeight(text);
	}
	
}
