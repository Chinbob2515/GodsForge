package server;

public class Auth {
	
	public static final String authLocation = "res/Server/", userL = "users.txt", passL="pass.txt";
	
	public String[] userNames;
	public String[] passKeys;
	
	public int count;
	
	public Auth(){
		loadAuths();
	}
	
	public void loadAuths(){
		String users = IOHandle.slurp(authLocation+userL);
		String passes = IOHandle.slurp(authLocation+passL);
		count = 0;
		String[] lines = users.split("\n");
		for(@SuppressWarnings("unused") String line: lines)count++;
		userNames = new String[count];
		for(int i = 0; i != count; i++)userNames[i] = lines[i];
		count = 0;
		lines = passes.split("\n");
		passKeys = new String[count]; // Count should be the same for both- if not, we SHOULD crash.
		for(int i = 0; i != count; i++)passKeys[i] = lines[i];
	}
	
	public String generateAuth(String user){
		String pass = "0000"; // GENERALLY ALL ROUND AWEFUL- TODO MAKE BETTER
		addAuth(user, pass);
		return pass;
	}
	
	public boolean addAuth(String user, String pass){
		String[] newUsers = new String[count+1];
		String[] newPasses = new String[count+1];
		for(String string: userNames){
			if(string.equals(user))
				return false;
		}
		for(int i = 0; i != count; i++) {
			newUsers[i] = userNames[i];
			newPasses[i] = passKeys[i];
		}
		newUsers[count] = user;
		newPasses[count] = pass;
		count++;
		userNames = newUsers;
		passKeys = newPasses;
		return true;
	}
	
	public void removeAuth(int number) throws Exception{
		if(number > -1){
			throw(new Exception("This should never be called")); // THIS FUNCTION SHOULD NEVER BE CALLED- WHEN WOULD YOU DELETE A USER!?!?!
		}
		String[] newUsers = new String[count-1];
		String[] newPasses = new String[count-1];
		int change = 0;
		for(int i = 0; i != count-1; i++) {
			if(i == number) change++;
			newUsers[i] = userNames[i+change];
			newPasses[i] = passKeys[i+change];
		}
		count--;
		userNames = newUsers;
		passKeys = newPasses;
	}
	
	public void removeAuth(String user) throws Exception{ // <-- See other removeAuth function
		int number = -1;
		for(int i = 0; i != count; i++){
			if(userNames[i].equals(user)){
				number = i;
				break;
			}
		}
		if(number != -1){
			removeAuth(number);
		}
	}
	
	public boolean checkAuth(String user, String pass){
		for(int i = 0; i != count; i++){
			if(userNames[i].equals(user)){
				if(passKeys[i].equals(pass))
					return true;
			}
		}
		return false;
	}
	
}
