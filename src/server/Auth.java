package server;

public class Auth {
	
	public final String authLocation = "res/", userL = "users.txt", passL="pass.txt";
	
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
	
	public void addAuth(String user, String pass){
		String[] newUsers = new String[count+1];
		String[] newPasses = new String[count+1];
		for(int i = 0; i != count; i++) {
			newUsers[i] = userNames[i];
			newPasses[i] = passKeys[i];
		}
		newUsers[count] = user;
		newPasses[count] = pass;
		count++;
		userNames = newUsers;
		passKeys = newPasses;
	}
	
	public void removeAuth(int number){
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
	
	public void removeAuth(String user){
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
	
}
