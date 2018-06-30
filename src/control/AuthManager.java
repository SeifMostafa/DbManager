package control;

import java.util.Random;

import model.User;

public class AuthManager {
	User user;

	public User getUser() {
		return user;
	}

	String lic_code;
	private static AuthManager instance;

	/*
	 * to create object (not to die - single ton)
	 */
	private AuthManager() {

	}

	/*
	 * get the object (instance)
	 */
	public static AuthManager getInstance() {
		if (instance == null) {
			instance = new AuthManager();
		}
		return instance;
	}

	/*
	 * auth user param. with current user is setup
	 */
	public boolean auth(String user_name, String password_) {
		if (this.user.getUsername().equals(user_name) && this.user.getPassword().equals(password_))
			return true;
		else
			return false;
	}
	
	/*
	 * auth given lic_code with the lic_code
	 */
	public boolean auth(String license_code) {
		if (this.lic_code.equals(license_code))
			return true;
		else
			return false;

	}
	
	/*
	 *	setup license_code 
	 */
	public void setLic_code(String license_code) {
		this.lic_code = license_code;
	}
	
	/*
	 * setup user
	 */
	public void setUser(User u) {
		this.user = u;
	}

	

}
