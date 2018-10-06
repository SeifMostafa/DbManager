package model;

import java.util.ArrayList;
import java.util.Objects;

public class User {

    public User() {
    }
	public enum PERMISSION {
		SEARCH, UPDATE, DELETE, INSERT, MASTER
	};

	private String username, password;
        ArrayList<DbTable> access_tables=null;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<DbTable> getAccess_tables() {
        return access_tables;
    }

    public void setAccess_tables(ArrayList<DbTable> access_tables) {
        this.access_tables = access_tables;
    }
	PERMISSION[] permissions = null;

	public PERMISSION[] getPermissions() {
		return permissions;
	}

	public void setPermissions(PERMISSION[] permissions) {
		this.permissions = permissions;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		String User_String = username + Messages.getString("User.field_delimiter") + password; //$NON-NLS-1$
		if (permissions != null) {
			User_String +=Messages.getString("User.field_delimiter"); //$NON-NLS-1$
			for (PERMISSION p : permissions)
				User_String += p.toString() + Messages.getString("User.space_string"); //$NON-NLS-1$

		}
                User_String+="\n";
				//System.out.println(User_String);
		return User_String;
	}

	public String getUsername() {
		return username;
	}

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
