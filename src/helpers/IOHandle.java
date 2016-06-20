package helpers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class IOHandle {
	
	public static Scanner getText(String path){
		Scanner file;
		try {
			file = new Scanner(new BufferedReader(new FileReader(path)));
			return file;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        return null;
	}

    public static String slurp(final InputStream is){
        final char[] buffer = new char[3019];
        final StringBuilder out = new StringBuilder();
        try {
            final Reader in = new InputStreamReader(is, "UTF-8");
            try {
                for (;;) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0)
                        break;
                    out.append(buffer, 0, rsz);
                }
            }
            finally {
                in.close();
            }
        }
        catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage() + " " + ex.getMessage());
        }
        catch (IOException ex) {
        	System.out.println(ex.getMessage() + " " +ex.getMessage());
        }
        return out.toString();
    }
    
    public static int[] getSettings(){
    	String settings = null;
    	try {
			settings = slurp(new FileInputStream("res/.settings"));
		} catch (FileNotFoundException e) {
			System.out.println("Failure!");
		}
    	Scanner scan = new Scanner(settings);
    	int sn = 5;
    	int[] answers = new int[sn+3];
    	
    	for(int i = 0; i != sn; i++){
    		try{
    			answers[i] = scan.nextInt();
    		} catch(java.util.NoSuchElementException e){
    			answers[i] = 0;
    		}
    	}
    	
    	scan.close();
    	return answers;
    	
    }
    
    public static HashMap<String, String> getMultiSettings(){
    	HashMap<String, String> answers = new HashMap<String, String>();
    	String settings = null;
    	try {
			settings = slurp(new FileInputStream("res/multiplayer.settings"));
		} catch (FileNotFoundException e) {
			System.out.println("Failure!");
		}
    	if(settings.equals("")){
    		return answers;
    	}
    	String[] keys = new String[]{"host", "port"};
    	String[] pairs = settings.split(";");
    	for(int i = 0; i != pairs.length; i++){
    		String[] pair = pairs[i].toLowerCase().trim().split(":");
    		boolean trip = false;
    		for(String key: keys){
    			if(pair[0].equals(key)){trip=true;}
    		}
    		if(!trip){System.out.println("Unrecognized key - "+pair[0]+":"+pair[1]+"!");return null;}
    		answers.put(pair[0], pair[1]);
    	}
    	return answers;
    }
    
    public static Document readXML(String loc){
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	Document dom = null;

		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(loc);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		dom.normalize();
		
		explore(dom.getDocumentElement());
		
		return dom;
    }
    
    public static void explore(Node node){
    	int oh = node.getChildNodes().getLength();
    	for(int i = 0; i != oh; i++){
    		if(node.getChildNodes().item(i) instanceof Text){
    			node.removeChild(node.getChildNodes().item(i));
    			i--;oh--;
    			continue;
    		}
    		if(node.getChildNodes().item(i).hasChildNodes()){
    			explore(node.getChildNodes().item(i));
    		}
    	}
    }
    
    public static void writeSettings(int[] settings){
    	PrintWriter writer = null;
		try {
			writer = new PrintWriter("res/.settings", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
			return;
		}
		for(int i: settings){
			writer.print(i);
			writer.print(" ");
		}
		writer.flush();
		writer.close();
    }
    
    public static int[][] loadLevel(String loc){
    	String string = null;
    	try{
    		string = slurp(new FileInputStream("saves/"+loc));
    	} catch(IOException e){
    		System.out.println("Level failed to load!");
    	}
    	String[] strings = string.split("\n");
    	int[][] answer = new int[strings.length][];
    	for(int y = 0; y != strings.length; y++){
    		String[] strings2 = strings[y].replace("\r", "").split(" ");
    		for(int x = 0; x != strings2.length;x++){
    			if(y == 0){
    				answer[x] = new int[strings2.length];
    			}
    			if(strings2[x].equals("")){
    				continue;
    			}
    			answer[x][y] = Integer.parseInt(strings2[x]);
    		}
    	}
    	return answer;
    }
	
}
