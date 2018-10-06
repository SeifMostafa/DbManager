package control;

import model.User;

public class AuthManager {

    User user;

    /**
     *
     * @return user
     */
    public User getUser() {
        return user;
    }
    /**
     * lic_code : to be generated from server or database sequence current
     * version uses 55 as lic_code for all copies of this program instance is an
     * instance of class and the constructor is private to follow singleton
     * design pattern
     */
    String lic_code;
    private static AuthManager instance;

    /**
     * to create object (not to die - singleton)
     */
    private AuthManager() {

    }

    /**
     *
     * @return get the object (instance)
     */
    public static AuthManager getInstance() {
        if (instance == null) {
            instance = new AuthManager();
        }
        return instance;
    }

    /**
     * auth user param. with current user is setup
     *
     * @param user_name :username to login
     * @param password_ : password to login
     * @return true if username and password is matched, false otherwise
     */
    public boolean auth(String user_name, String password_) {
        System.out.println(user_name + " ," + password_);
        System.out.println("this.user" + this.user.getUsername() + " , " + this.user.getPassword());
        return this.user.getUsername().equals(user_name) && this.user.getPassword().equals(password_);
    }

    /**
     * auth given lic_code with the lic_code
     *
     * @param license_code : current version uses 55 as license_code
     * @return true if the given license code is correct, false otherswise
     */
    public boolean auth(String license_code) {
        if (this.lic_code.equals(license_code)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * setup license_code
     *
     * @param license_code
     */
    public void setLic_code(String license_code) {
        this.lic_code = license_code;
    }

    /**
     * setup user
     *
     * @param u : to set user to auth. other users by. authManager compare
     * current user : read from cached file with input users
     */
    public void setUser(User u) {
        this.user = u;
    }

}
