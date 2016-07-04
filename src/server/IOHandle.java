package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class IOHandle{

    public static final String COUNTRY_SETTINGS = "res/Server/countries.settings";
    public static final String MULTI_SETTINGS = "res/Server/multiplayer.settings";

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
    
    public static String slurp(String loc){
    	try {
			return slurp(new FileInputStream(loc));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		}
    }

    public static HashMap<String, String> getMultiSettings(){
    	HashMap<String, String> answers = new HashMap<String, String>();
    	String settings = null;
    	try {
			settings = slurp(new FileInputStream(MULTI_SETTINGS));
		} catch (FileNotFoundException e) {
			System.out.println("Failure!");
		}
    	if(settings.equals("")){
    		return answers;
    	}
    	String[] keys = new String[]{"port", "update_interval", "warmup_interval", "warmup_start", "min_players"};
    	String[] pairs = settings.split(";");
    	for(int i = 0; i != pairs.length; i++){
    		String[] pair = pairs[i].toLowerCase().trim().split(":");
    		boolean trip = false;
    		for(String key: keys){
    			if(pair[0].equals(key)){trip=true;}
    		}
    		if(!trip){System.out.println("Unrecognized key - "+pair[0]+":"+pair[1]+"!");continue;}
    		answers.put(pair[0], pair[1]);
    	}
    	return answers;
    }
    
}
