package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import control.SQLBuilder;
import control.SQLExecutor;
import control.StorageManager;
import control.Utils;
import java.util.Objects;

public class DbConfigs {

    private String PORT_NUM;
    private String HOST;
    private String SERVICE_NAME;
    public String USER_NAME;
    public String PASSWORD;
    public String DB_URL;
    public String DB_DRIVER_URL;
    public ArrayList<DbTable> tables;

    public DbConfigs() {
        DB_DRIVER_URL = Messages.getString("DbConfigs.oracle_driver"); //$NON-NLS-1$
    }

    public ArrayList<DbTable> getTables() {
        return tables;
    }

    public void setTables(ArrayList<DbTable> tables) {
        this.tables = tables;
    }

    public void setPORT_NUM(String pORT_NUM) {
        this.PORT_NUM = pORT_NUM;
    }

    public void setHOST(String hOST) {
        this.HOST = hOST;
    }

    public void setSERVICE_NAME(String sERVICE_NAME) {
        this.SERVICE_NAME = sERVICE_NAME;
    }

    public void setUSER_NAME(String uSER_NAME) {
        this.USER_NAME = uSER_NAME;
    }

    public void setPASSWORD(String pASSWORD) {
        this.PASSWORD = pASSWORD;
    }

    /*
	 * use port_num,host,service_name to build DB_URL
     */
    public void configDB_URL() {

        this.DB_URL = Messages.getString("DbConfigs.db_url_intro") + this.HOST + Messages.getString("DbConfigs.colon") + this.PORT_NUM + Messages.getString("DbConfigs.forward_slash") + this.SERVICE_NAME; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public boolean testDbConnection() {
        boolean result = false;
        try {
            Class.forName(this.DB_DRIVER_URL);
            Connection mConnection = DriverManager.getConnection(this.DB_URL, this.USER_NAME,
                    this.PASSWORD);

            if (!mConnection.isClosed()) {
                result = true;
            }
            mConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
            Utils.showError(e.getMessage());
            return result;
        }
        return result;
    }

    /*
	 * check if tables exist in storage are exactly what exist in db
     */
    public void syncTables() {
        StorageManager storageManager = new StorageManager();
        //storageManager.WriteConfigurations(this);
        SQLBuilder sqlBuilder = SQLBuilder.getInstance();
        sqlBuilder.buildGetDbTablesQuery();
        SQLExecutor sqlExecutor = new SQLExecutor(sqlBuilder);
        ArrayList<String> table_names = sqlExecutor.getTables();
        table_names.forEach((table_name) -> {
            boolean isExist = storageManager.checkifTableExist(table_name,dbmanager.DbManager.dbConfigs.getLabel());
            if (!isExist) {
                sqlBuilder.buildDescribeTable(table_name);
                DbTable dbTable = sqlExecutor.getDbTable(table_name);
                if (dbTable != null) {
                    storageManager.appendTable(dbTable,dbmanager.DbManager.dbConfigs.getLabel());
                }
            }
        });
    }

    public String getLabel() {
        return this.USER_NAME + "@" + DbConfigs.this.SERVICE_NAME;
    }

    @Override
    public String toString() {
        return this.PORT_NUM + "--" + this.HOST + "--" + this.SERVICE_NAME + "--" + this.USER_NAME //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + "--" + this.PASSWORD + Messages.getString("DbConfigs.end_line"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.HOST);
        hash = 43 * hash + Objects.hashCode(this.SERVICE_NAME);
        hash = 43 * hash + Objects.hashCode(this.USER_NAME);
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
        final DbConfigs other = (DbConfigs) obj;
        if (!Objects.equals(this.HOST, other.HOST)) {
            return false;
        }
        if (!Objects.equals(this.SERVICE_NAME, other.SERVICE_NAME)) {
            return false;
        }
        if (!Objects.equals(this.USER_NAME, other.USER_NAME)) {
            return false;
        }
        return true;
    }

}
