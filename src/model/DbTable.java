package model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * database table : table name, columns and their data types (dbCol)
 *
 * @author dotnet2
 */
public class DbTable {

    /**
     *
     */
    public String name;
    ArrayList<dbCol> cols; // cols: col_name ,col_dataType

    /**
     *
     * @param name table name
     * @param cols table columns
     */
    public DbTable(String name, ArrayList<dbCol> cols) {
        super();
        this.name = name;
        this.cols = cols;
    }

    /**
     *
     * @param colName to indicate table column
     * @return data type for colName
     */
    public String getColType(String colName) {
        for (dbCol c : this.cols) {
            if (c.name.equals(colName)) {
                return c.type;
            }
        }
        return null;
    }

    /**
     *
     * @param colName to indicate column
     * @return database column(dbCol)
     */
    public dbCol getCol(String colName) {
        for (dbCol c : this.cols) {
            if (c.name.equals(colName)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return Messages.getString("DbTable.name") + name + Messages.getString("DbTable.cols") + cols + Messages.getString("DbTable.end_line"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * database col (name , type and accept NULL flag)
     */
    public static class dbCol {

        String name, type;
        boolean acceptNull;

        /**
         *
         * @param name column name
         * @param type column data type
         * @param acceptNull accept null flag
         */
        public dbCol(String name, String type, boolean acceptNull) {
            super();
            this.name = name;
            this.type = type;
            this.acceptNull = acceptNull;
        }

        /**
         *
         * @return col. name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name to set column name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return to get col. type
         */
        public String getType() {
            return type;
        }

        /**
         *
         * @param type to set col. type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         *
         * @return true if accepting null, false if not
         */
        public boolean isAcceptNull() {
            return acceptNull;
        }

        /**
         *
         * @param acceptNull to set accept null flag
         */
        public void setAcceptNull(boolean acceptNull) {
            this.acceptNull = acceptNull;
        }

        @Override
        public String toString() {
            return Messages.getString("DbTable.dbCol") + name + Messages.getString("DbTable.db_col_type") + type + Messages.getString("DbTable.db_col_accept_null") + acceptNull + Messages.getString("DbTable.closing_square_bracket"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }

    }

    /**
     *
     * @return to return database table name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return table columns
     */
    public ArrayList<dbCol> getCols() {
        return cols;
    }

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
