package model;

import java.util.ArrayList;
import java.util.Objects;

public class DbTable {
	public String name;
	ArrayList<dbCol> cols; // cols: col_name ,col_dataType

	public DbTable(String name, ArrayList<dbCol> cols) {
		super();
		this.name = name;
		this.cols = cols;
	}

    public DbTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public String getColType(String colName){
        for(dbCol c:this.cols){
            if(c.name.equals(colName)){
                return c.type;
            }
        }
        return null;
    }
        public dbCol getCol(String colName){
        for(dbCol c:this.cols){
            if(c.name.equals(colName)){
                return c;
            }
        }
        return null;
    }
	@Override
	public String toString() {
		return Messages.getString("DbTable.name") + name + Messages.getString("DbTable.cols") + cols + Messages.getString("DbTable.end_line"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public static class dbCol {
		String name, type;
		boolean acceptNull;

		public dbCol(String name, String type, boolean acceptNull) {
			super();
			this.name = name;
			this.type = type;
			this.acceptNull = acceptNull;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isAcceptNull() {
			return acceptNull;
		}

		public void setAcceptNull(boolean acceptNull) {
			this.acceptNull = acceptNull;
		}

		@Override
		public String toString() {
			return Messages.getString("DbTable.dbCol") + name + Messages.getString("DbTable.db_col_type") + type + Messages.getString("DbTable.db_col_accept_null") + acceptNull + Messages.getString("DbTable.closing_square_bracket"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
		

	}

	public String getName() {
		return name;
	}

	public ArrayList<dbCol> getCols() {
		return cols;
	};

    @Override
    public int hashCode() {
        int hash = 3;
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
        final DbTable other = (DbTable) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
	
}
