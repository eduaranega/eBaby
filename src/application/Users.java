package application;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private Map<String, User> mapUsers;
    
	private Users() {
		this.mapUsers = new HashMap<String, User>();
	}

	public static Users getInstance() {
		return new Users();
	}

    public boolean registerUser(User user) {
		if (mapUsers.containsKey(user.getUserName()))
			return false;
		else {
			mapUsers.put(user.getUserName(),user);
			return true;
		}		
	}
    
	public boolean findByUserName(String username) {
		if (mapUsers.containsKey(username))
			return true;
		else return false;
	}

	public boolean logIn(String username, String password) {
		if (findByUserName(username)) {
			if (password == mapUsers.get(username).getPassword()) {
				mapUsers.get(username).setLogged(true);
				return true; // password correct
			}	
			else return false; // wrong password
		}	
		else {
			return false; // username not found
		}
	}

	public boolean logOut(String username) {
		if (findByUserName(username)) {
			mapUsers.get(username).setLogged(false);
			return true;
		}
		else return false;
	}

}