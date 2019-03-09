package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * app. user: permission (search,update,insert delete ), username, password,
 * open tables for this users
 *
 * @author dotnet2
 */
public class User {

    public User() {
    }
    /**
     * master to indicate all of them .. DML operations (search, update,delete,insert)
     */
    public enum PERMISSION {

        SEARCH,
        UPDATE,
        DELETE,
        INSERT,
        MASTER
    };

    private String username, password;
    ArrayList<DbTable> access_tables = null;

    /**
     *
     * @param username to be set as user's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @param password to be set as user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return to get open tables for this user
     */
    public ArrayList<DbTable> getAccess_tables() {
        return access_tables;
    }

    /**
     *
     * @param access_tables to set open tables for this user
     */
    public void setAccess_tables(ArrayList<DbTable> access_tables) {
        this.access_tables = access_tables;
    }
    PERMISSION[] permissions = null;

    /**
     *
     * @return user's permissions
     */
    public PERMISSION[] getPermissions() {
        return permissions;
    }

    /**
     *
     * @param permissions to be set as user's permissions
     */
    public void setPermissions(PERMISSION[] permissions) {
        this.permissions = permissions;
    }

    /**
     *
     * @param username to be set as this.username
     * @param password to be set as this.password
     */
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        String User_String = username + Messages.getString("User.field_delimiter") + password; //$NON-NLS-1$
        if (permissions != null) {
            User_String += Messages.getString("User.field_delimiter"); //$NON-NLS-1$
            for (PERMISSION p : permissions) {
                User_String += p.toString() + Messages.getString("User.space_string"); //$NON-NLS-1$
            }
        }
        User_String += "\n";
        //System.out.println(User_String);
        return User_String;
    }

    /**
     *
     * @return return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.username);
        hash = 29 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

}
