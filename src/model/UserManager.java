package model;

import java.util.Map;
import java.util.TreeMap;

public class UserManager {
	private static UserManager theUserManager = null;
	private Map<String, User> users;
	
	public static UserManager getTheUserManager(){
		if( theUserManager == null ) theUserManager = new UserManager();
		return theUserManager;
	}
	private UserManager(){
		this.users = new TreeMap<String, User>();
	}
	public User find (String name) throws UserNotFoundException{
		User result = this.users.get(name);
		if (result == null) throw new UserNotFoundException(name);
		return result;
	}
	public User create (String name) throws UserException {
		this.checkName(name);
		User result = this.users.get(name);
		if (result != null) throw new UserAlreadyExistsException(name);
		User newUser = new User(name);
		this.users.put( name, newUser );
		return newUser;
	}
	private void checkName(String name)  throws UserException {
		if (name.length() < 3) throw new UserNameFormatException(name);
	}


}
