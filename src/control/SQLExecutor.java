package control;

import dbmanager.DbManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.DbTable;
import model.DbTable.dbCol;

public class SQLExecutor {

    SQLBuilder mBuilder;
    Connection mConnection;

    /*
	 * returned boolean: get assurance that update is happened successfully
	 * 
     */
    public void openConnection() throws SQLException {
        try {
            Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);
            mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL, DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SQLExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SQLExecutor(SQLBuilder builder) {
        super();
        this.mBuilder = builder;
    }

    public boolean exe_insert() {
        try {

            dbmanager.DbManager.getInstance();
            Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);
            dbmanager.DbManager.getInstance();
            dbmanager.DbManager.getInstance();
            dbmanager.DbManager.getInstance();
            mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL, DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD);
            Statement state = mConnection.createStatement();
            state.execute(mBuilder.query);
            mConnection.close();
            return true;
        } catch (Exception e) {
            System.err.println(Messages.getString("SQLExecutor.exe_insert") + e.getMessage()); //$NON-NLS-1$
            System.out.println(mBuilder.query);
            return false;
        }
    }

    public ArrayList<String> getTables() {
        ArrayList<String> tables = new ArrayList<>();
        try {
            Class.forName(dbmanager.DbManager.dbConfigs.DB_DRIVER_URL);
            mConnection = DriverManager.getConnection(dbmanager.DbManager.dbConfigs.DB_URL, dbmanager.DbManager.dbConfigs.USER_NAME, dbmanager.DbManager.dbConfigs.PASSWORD);
            Statement state = mConnection.createStatement();
            ResultSet resultSet = state.executeQuery(mBuilder.query);
            while (resultSet.next()) {

                tables.add(resultSet.getString(Messages.getString("SQLExecutor.TABLE_NAME"))); //$NON-NLS-1$

            }
            mConnection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(Messages.getString("SQLExecutor.sqlexe_getTable_err") + e.toString()); //$NON-NLS-1$
            System.out.println(mBuilder.query);

        }
        return tables;
    }

    public ResultSet exe_pl_desc(String table_name) {
        ResultSet table = null;
        try {
            dbmanager.DbManager.getInstance();
            Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);
            dbmanager.DbManager.getInstance();
            mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL, DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD);
            Statement statement = mConnection.createStatement();
            statement.execute(mBuilder.query);
            statement.close();
            CallableStatement callableStatement = mConnection.prepareCall("{? = call getdesc_table(?)}");
            callableStatement.registerOutParameter(1, Types.ARRAY);
            callableStatement.setString(2, table_name);
            table = callableStatement.getResultSet();
            // mConnection.close();
        } catch (Exception e) {
            System.err.println(Messages.getString("SQLExecutor.sqlexe_getTable_err") + e.toString()); //$NON-NLS-1$
            System.out.println(mBuilder.query);

        }
        return table;
    }

    public DbTable getDbTable(String table_name) {
        DbTable dbTable = null;
        try {
            Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);
            mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL, DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD);
            Statement state = mConnection.createStatement();
            ResultSet resultSet = state.executeQuery(mBuilder.query);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int cols_count = resultSetMetaData.getColumnCount();
            ArrayList<dbCol> cols = new ArrayList<>();
            for (int i = 1; i <= cols_count; i++) {
                String col_name = resultSetMetaData.getColumnName(i);
                String col_type = resultSetMetaData.getColumnTypeName(i);
                int isNullable = resultSetMetaData.isNullable(i);
                boolean acceptNull = false;
                if (isNullable == 0) {
                    acceptNull = true;
                }
                cols.add(new dbCol(col_name, col_type, acceptNull));
            }
            dbTable = new DbTable(table_name, cols);
            mConnection.close();
        } catch (Exception e) {
            System.err.println(Messages.getString("SQLExecutor.sqlexe_getDbTable_err") + e.toString()); //$NON-NLS-1$
            System.out.println(mBuilder.query);

        }
        return dbTable;
    }

    public ResultSet exe_select() {
        try {
            Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);

            mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL, DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD);
            Statement state = mConnection.createStatement();
            ResultSet resultset = state.executeQuery(mBuilder.query);
            if (resultset != null) {
                return resultset;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println(Messages.getString("SQLExecutor.sqlexe_select_err") + e.toString()); //$NON-NLS-1$
            System.out.println(mBuilder.query);

            return null;
        }
    }

    //
    public boolean exe_update() {
        try {
            
            Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);
            mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL, DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD);
            Statement state = mConnection.createStatement();
            state.executeUpdate(mBuilder.query);
            mConnection.close();
            return true;
        } catch (Exception e) {
            System.err.println(Messages.getString("SQLExecutor.sqlexe_update_err") + e.toString()); //$NON-NLS-1$
            System.out.println(mBuilder.query);
            return false;
        }
    }

    // get assurance that delete is happened successfully
    public boolean exe_delete() {
        try {
            Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);
            mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL, DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD);
            Statement state = mConnection.createStatement();
            state.execute(mBuilder.query);
            mConnection.close();
            return true;
        } catch (Exception e) {
            System.err.println(Messages.getString("SQLExecutor.sqlexe_delete_err") + e.toString()); //$NON-NLS-1$
            System.out.println(mBuilder.query);

            return false;
        }
    }
    
    
    /*
	 * public void exe_alter() { try { Class.forName(DbManager.dbConfigs.DB_DRIVER_URL);
	 * mConnection = DriverManager.getConnection(DbManager.dbConfigs.DB_URL,
	 * DbManager.dbConfigs.USER_NAME, DbManager.dbConfigs.PASSWORD); Statement state =
	 * mConnection.createStatement(); state.execute(mBuilder.query);
	 * mConnection.close(); }catch(Exception e) {
	 * System.err.println("SQLEXE.exe_delete error: " + e.toString());
	 * System.out.println(mBuilder.query);
	 * 
	 * } }
     */
    public void closeConnection() {
        try {
            mConnection.close();
        } catch (SQLException e) {
            System.err.println(Messages.getString("SQLExecutor.sqlexe_close_conn_err") + e.toString()); //$NON-NLS-1$
        }
    }
}
