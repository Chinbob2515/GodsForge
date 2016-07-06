package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;


public class IOHandle {

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
	
	public static String[] getListing(String loc){
		File dir = new File(loc);
		String[] list = dir.list();
		ArrayList<String> bob = new ArrayList<String>();
		for(String str: list){
			if(!str.startsWith("Thumb"))
				bob.add(str);
		}
		//for(int i = 0; i != list.length; i++){
		//	if(list[i].startsWith("Thumbs")){
		//		String[] list2 = new String[list.length-1];
		//		for(int a = 0; a != list.length-1; a++){
		//			list2[a] = list[a]
		//		}
		//	}
		//}
		String[] returns = new String[bob.size()];
		for(int i = 0; i != returns.length; i++) returns[i] = bob.get(i);
		return returns;
	}
	
	public static File[] getFileListing(String loc){
		String[] names = getListing(loc);
		if(!loc.endsWith("\\") && !loc.endsWith("/")) loc += "/";
		File[] files = new File[names.length];
		for(int i = 0; i != files.length; i++){files[i] = new File(loc+names[i]);System.out.println(loc+names[i]);}
		return files;
	}
	
}
