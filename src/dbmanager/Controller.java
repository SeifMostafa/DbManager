package dbmanager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import control.SQLBuilder;
import control.SQLExecutor;

import control.StorageManager;
import control.Utils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DbConfigs;
import model.DbTable;
import model.DbTable.dbCol;
import model.User;
import view.DbConfigsPage;
import view.LoginPage;

/**
 *
 * @author dotnet2
 */
public class Controller {

    /**
     * to call buildUpdateQuery from SQLBuilder , call update from SQExe. handle
     * any exceptions may happen in between handle any additional work related
     * to update process such as any animation, commit ..etc
     *
     * @param table_name : to update
     * @param cols_to_update columns to be updated from table_name
     * @param where_cols : where columns in where clause
     * @param where_values : where values in order for where_cols in where
     * clause
     * @param new_values : new values to update
     * @param operator : between where col. and its value such as like,=,> or
     * contains.
     * @param or : flag to determine if we combine where cols with "or" or "and"
     * logic operator!=.
     * @return boolean flag true if successfully updated, false otherwise
     */
    public boolean update(String table_name, ArrayList<String> cols_to_update,
            ArrayList<SimpleEntry<String, String>> new_values, ArrayList<String> where_cols,
            ArrayList<SimpleEntry<String, String>> where_values, ArrayList<String> operator, boolean or) {
        boolean assurnceForcorrection = false;
        //System.out.println(or);
        if ((table_name == null ? Messages.getString("Controller.empty_string") != null : !table_name.equals(Messages.getString("Controller.empty_string"))) || table_name != null) { //$NON-NLS-1$

            SQLBuilder builder = SQLBuilder.getInstance();
            if (cols_to_update.size() > 0 && new_values.size() > 0 && where_cols.size() > 0
                    && where_values.size() > 0) {

                assurnceForcorrection = builder.buildUpdateQuery(table_name, cols_to_update, new_values, where_cols,
                        where_values, operator, or);
                if (assurnceForcorrection) {

                    SQLExecutor executor = new SQLExecutor(builder);

                    try {
//                        ArrayList<String> to_select = new ArrayList<>();
//                        to_select.add("*");
//                        builder.buildSelectQuery(table_name, to_select, where_cols, where_values, or);
//                        ResultSet resultSet = executor.exe_select();
//                        if (resultSet != null) {
//                            if (resultSet.next()) {
//                                System.out.println("1"+resultSet.getString(1));
                        if (!executor.exe_update()) {
                            assurnceForcorrection = false;
                            String false_table_name = "INVENTORY_LINE_ITEM";
                            ArrayList<String> pk = new ArrayList<>();
                            pk.add("INVENTORY_ITEM_ID");

                            if (table_name.equals(false_table_name)) {
                                System.out.println("hello inv!");

                                builder.buildSelectQuery(false_table_name, pk, where_cols, where_values, operator, or);
                                ResultSet rs = executor.exe_select();
                                rs.next();
                                StorageManager storageManager = new StorageManager();
                                ArrayList<SimpleEntry<String, String>> old_INVENTORY_ITEM_ID = new ArrayList<>();
                                old_INVENTORY_ITEM_ID.add(new SimpleEntry<>(String.valueOf(rs.getLong(1)), "NUMBER"));

                                builder.buildSelectQuery(false_table_name, pk, cols_to_update, new_values, operator, or);
                                rs = executor.exe_select();
                                rs.next();
                                storageManager = new StorageManager();
                                ArrayList<SimpleEntry<String, String>> new_INVENTORY_ITEM_ID = new ArrayList<>();
                                new_INVENTORY_ITEM_ID.add(new SimpleEntry<>(String.valueOf(rs.getLong(1)), "NUMBER"));

                                ArrayList<DbTable> tables_contain_inv_item_id = storageManager.getTablesContain(pk, DbManager.dbConfigs.getLabel());
                                for (int i = 0; i < tables_contain_inv_item_id.size(); i++) {

                                    if (tables_contain_inv_item_id.get(i).getName().equals(false_table_name)) {
                                        tables_contain_inv_item_id.remove(i);

                                    }
                                }
                                boolean update_childs = true;
                                for (DbTable dbTable : tables_contain_inv_item_id) {
                                    update_childs = update(dbTable.getName(), pk, new_INVENTORY_ITEM_ID, pk, old_INVENTORY_ITEM_ID, operator, or);
                                    if (!update_childs) {
                                        break;
                                    }
                                }
                                boolean old_inv_item_delete = false;
                                if (update_childs) {
                                    old_inv_item_delete = delete(false_table_name, pk, old_INVENTORY_ITEM_ID, operator, or);
                                }
                                if (old_inv_item_delete) {
                                    System.out.println("done - cascade update!");
                                    assurnceForcorrection = true;

                                    if (!executor.exe_update()) {
                                        assurnceForcorrection = false;
                                    }
                                } else {
                                    System.out.println("not done! childs update: " + update_childs + " delete old: " + old_inv_item_delete);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.err.println(Messages.getString("Controller.update_err") + e.toString()); //$NON-NLS-1$
                        assurnceForcorrection = false;
                    }
                }
            } else {
                System.err.println(Messages.getString("Controller.parametters_shortage")); //$NON-NLS-1$
            }
        } else {
            System.err.println(Messages.getString("Controller.update_err") + Messages.getString("Controller.update_err_select_table")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        reset();
        return assurnceForcorrection;
    }

    /**
     * not used in this version.
     *
     * @param table : to update
     * @param cols_to_update columns to be updated from table
     * @param new_values : new values to update
     * @param where_cols : where columns in where clause
     * @param where_values : where values in order for where_cols in where
     * clause
     * @param operator : between where col. and its value such as like,=,> or
     * contains.
     * @param or : flag to determine if we combine where cols with "or" or "and"
     * logic operator!=.
     * @return boolean flag true if successfully updated, false otherwise
     */
    public boolean cascadeUpdate(DbTable table, ArrayList<String> cols_to_update,
            ArrayList<SimpleEntry<String, String>> new_values, ArrayList<String> where_cols,
            ArrayList<SimpleEntry<String, String>> where_values, ArrayList<String> operator, boolean or) {
        boolean result = false;
        SQLBuilder builder = SQLBuilder.getInstance();
        SQLExecutor executor = new SQLExecutor(builder);

        String pk = getPK(table.name);
        ArrayList<String> pks = new ArrayList<>();
        pks.add(pk);

        ArrayList<ref> refs = getRefinfo(table.name);

        ArrayList<SimpleEntry<String, String>> old_pks = new ArrayList<>();
        builder.buildSelectQuery(table.name, pks, where_cols, where_values, operator, or);
        ResultSet rs = executor.exe_select();

        try {
            while (rs.next()) {
                old_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), table.getColType(pk)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<SimpleEntry<String, String>> new_pks = new ArrayList<>();
        builder.buildSelectQuery(table.name, pks, cols_to_update, new_values, operator, or);
        rs = executor.exe_select();
        try {
            while (rs.next()) {
                new_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), table.getColType(pk)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean assurance = updateChildTables(refs, pks, new_pks, old_pks, operator, true);
        if (assurance) {
            return builder.buildDeleteQuery(table.name, where_cols, where_values, operator, or);
        }
        return result;
    }

    /**
     * not used in this version
     *
     * @param table : to cascade delete
     *
     * @param where_cols : where columns in where clause
     * @param where_values : where values in order for where_cols in where
     * clause
     * @param operator : between where col. and its value such as like,=,> or
     * contains.
     * @param or : flag to determine if we combine where cols with "or" or "and"
     * logic operator!=.
     * @return boolean flag true if successfully updated, false otherwise
     */
    public boolean cascadeDelete(DbTable table, ArrayList<String> where_cols,
            ArrayList<SimpleEntry<String, String>> where_values, ArrayList<String> operator, boolean or) {

        boolean result = false;
        SQLBuilder builder = SQLBuilder.getInstance();
        SQLExecutor executor = new SQLExecutor(builder);

        String pk = getPK(table.name);

        ArrayList<String> pks = new ArrayList<>();
        pks.add(pk);

        ArrayList<ref> refs = getRefinfo(table.name);

        ArrayList<SimpleEntry<String, String>> old_pks = new ArrayList<>();
        builder.buildSelectQuery(table.name, pks, where_cols, where_values, operator, or);
        ResultSet rs = executor.exe_select();

        try {
            while (rs.next()) {
                old_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), table.getColType(pk)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean assurance = deleteChilds(refs, old_pks, operator, true);
        System.out.println("assure: " + assurance);
        if (assurance) {
            builder.buildDeleteQuery(table.name, where_cols, where_values, operator, or);
            return executor.exe_delete();
        }
        return result;
    }

    /*
 * used in cascade delete
     */
    private boolean deleteChildTables(ArrayList<ref> refs, ArrayList<String> pks, ArrayList<SimpleEntry<String, String>> old_pks, ArrayList<String> operator, boolean or) {
        boolean isAllDeleted = true;
        SQLBuilder builder = SQLBuilder.getInstance();
        SQLExecutor executor = new SQLExecutor(builder);
        for (ref R : refs) {
            try {
                ArrayList<String> R_where_deleteCols = new ArrayList<>();
                R_where_deleteCols.add(R.col_name);
                builder.buildDeleteQuery(R.table_name, R_where_deleteCols, old_pks, operator, or);
                boolean done = executor.exe_delete();
                ArrayList<ref> refs_ = getRefinfo(R.table_name);
                if (refs_.size() > 0) {

                } else {

                }
                if (!done) {
                    StorageManager storageManager = new StorageManager();
                    String pk = getPK(R.table_name);
                    ArrayList<String> R_pks = new ArrayList<>();
                    R_pks.add(pk);

                    //ArrayList<ref> refs_ = getRefinfo(R.table_name);
                    ArrayList<SimpleEntry<String, String>> R_old_pks = new ArrayList<>();
                    builder.buildSelectQuery(R.table_name, R_pks, pks, old_pks, operator, or);
                    ResultSet rs = executor.exe_select();
                    if (rs != null) {
                        try {
                            while (rs.next()) {
                                R_old_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), storageManager.getDbTable(R.table_name).getColType(pk)));
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    if (R_old_pks.size() > 0) {
                        deleteChildTables(refs_, R_pks, R_old_pks, operator, true);
                    }
                }
            } catch (Exception e) {
                System.out.println("!done ,e: " + e.getMessage());
                StorageManager storageManager = new StorageManager();
                String pk = getPK(R.table_name);
                ArrayList<String> R_pks = new ArrayList<>();
                R_pks.add(pk);

                ArrayList<ref> refs_ = getRefinfo(R.table_name);

                ArrayList<SimpleEntry<String, String>> R_old_pks = new ArrayList<>();
                builder.buildSelectQuery(R.table_name, R_pks, pks, old_pks, operator, or);
                ResultSet rs = executor.exe_select();
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            R_old_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), storageManager.getDbTable(R.table_name).getColType(pk)));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (R_old_pks.size() > 0) {
                    deleteChildTables(refs_, R_pks, R_old_pks, operator, true);
                }
            }
        }
        return isAllDeleted;
    }

    /*
 * used in cascade delete
     */
    private boolean deleteChilds(ArrayList<ref> refs, ArrayList<SimpleEntry<String, String>> old_pks, ArrayList<String> operator, boolean or) {
        boolean isAllDeleted = true;
        ArrayList<ref> ref_childs = new ArrayList<>();
        SQLBuilder builder = SQLBuilder.getInstance();
        SQLExecutor executor = new SQLExecutor(builder);
        StorageManager storageManager = new StorageManager();
        ref_childs.addAll(refs);

        try {

            if (!ref_childs.isEmpty()) {
                //   ArrayList<ref> temp_refs = new ArrayList<>();
                // System.out.println("Hello " + stack_refs.size());
                for (ref R : ref_childs) {
                    //  System.out.println("REF: " + R.table_name +" , "+ R.col_name );
                    String pk = getPK(R.table_name);
                    ArrayList<String> Rpks = new ArrayList<>();
                    Rpks.add(pk);

                    ArrayList<String> old_pks_ = new ArrayList<>();
                    old_pks_.add(R.col_name);

                    ArrayList<ref> R_refs = getRefinfo(R.table_name);

                    ArrayList<SimpleEntry<String, String>> R_old_pks_ = new ArrayList<>(), R_old_pks = new ArrayList<>();

                    R_old_pks_.add(new SimpleEntry<>(old_pks.get(0).getKey(), storageManager.getDbTable(R.table_name).getColType(R.col_name)));

                    builder.buildSelectQuery(R.table_name, Rpks, old_pks_, R_old_pks_, operator, or);
                    System.out.println("q: " + builder.query);

                    ResultSet rs = executor.exe_select();
                    if (rs != null) {

                        try {
                            while (rs.next()) {
                                R_old_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), storageManager.getDbTable(R.table_name).getColType(pk)));
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (R_old_pks.size() > 0) {
                        // recursive
                        // System.out.println("Hello");

                        deleteChilds(R_refs, R_old_pks, operator, or);
                    } else {
                        // exe 
                        builder.buildDeleteQuery(R.table_name, old_pks_, R_old_pks_, operator, or);

                        System.out.println("q: (DELETE)" + builder.query);

                        executor.exe_delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isAllDeleted;
    }

    /**
     *
     * @param refs
     * @param pks
     * @param new_pks
     * @param old_pks
     * @param operator
     * @param or
     * @return
     */
    private boolean updateChildTables(ArrayList<ref> refs, ArrayList<String> pks, ArrayList<SimpleEntry<String, String>> new_pks, ArrayList<SimpleEntry<String, String>> old_pks, ArrayList<String> operator, boolean or) {
        boolean isAllUpdated = true;
        SQLBuilder builder = SQLBuilder.getInstance();
        SQLExecutor executor = new SQLExecutor(builder);
        for (ref R : refs) {
            try {
                ArrayList<String> R_update_cols = new ArrayList<>();
                R_update_cols.add(R.col_name);
                builder.buildUpdateQuery(R.table_name, R_update_cols, new_pks, R_update_cols, old_pks, operator, or);
                boolean done = executor.exe_update();
                if (!done) {
                    StorageManager storageManager = new StorageManager();
                    String pk = getPK(R.table_name);
                    ArrayList<String> R_pks = new ArrayList<>();
                    R_pks.add(pk);

                    ArrayList<ref> refs_ = getRefinfo(R.table_name);

                    ArrayList<SimpleEntry<String, String>> R_old_pks = new ArrayList<>();
                    builder.buildSelectQuery(R.table_name, R_pks, pks, old_pks, operator, or);
                    ResultSet rs = executor.exe_select();
                    if (rs != null) {
                        try {
                            while (rs.next()) {
                                R_old_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), storageManager.getDbTable(R.table_name).getColType(pk)));
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    ArrayList<SimpleEntry<String, String>> R_new_pks = new ArrayList<>();
                    builder.buildSelectQuery(R.table_name, R_pks, pks, new_pks, operator, or);
                    rs = executor.exe_select();
                    if (rs != null) {
                        try {
                            while (rs.next()) {
                                new_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), storageManager.getDbTable(R.table_name).getColType(pk)));
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (R_old_pks.size() > 0 && R_new_pks.size() > 0) {
                        updateChildTables(refs_, R_pks, R_new_pks, R_old_pks, operator, true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                StorageManager storageManager = new StorageManager();
                String pk = getPK(R.table_name);
                ArrayList<String> R_pks = new ArrayList<>();
                R_pks.add(pk);

                ArrayList<ref> refs_ = getRefinfo(R.table_name);

                ArrayList<SimpleEntry<String, String>> R_old_pks = new ArrayList<>();
                builder.buildSelectQuery(R.table_name, R_pks, pks, old_pks, operator, or);
                ResultSet rs = executor.exe_select();
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            R_old_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), storageManager.getDbTable(R.table_name).getColType(pk)));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                ArrayList<SimpleEntry<String, String>> R_new_pks = new ArrayList<>();
                builder.buildSelectQuery(R.table_name, R_pks, pks, new_pks, operator, or);
                rs = executor.exe_select();
                if (rs != null) {
                    try {
                        while (rs.next()) {
                            new_pks.add(new SimpleEntry<>(String.valueOf(rs.getString(pk)), storageManager.getDbTable(R.table_name).getColType(pk)));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (R_old_pks.size() > 0 && R_new_pks.size() > 0) {
                    updateChildTables(refs_, R_pks, R_new_pks, R_old_pks, operator, true);
                }
            }
        }
        return isAllUpdated;
    }

    /**
     *
     * @param table_name to build get primary key query
     * @return primary key for table_name
     */
    public String getPK(String table_name) {
        String pk = null;
        SQLBuilder builder = SQLBuilder.getInstance();
        boolean assurnceForcorrection = builder.buildGetPKQuery(table_name);
        if (assurnceForcorrection) {
            SQLExecutor executor = new SQLExecutor(builder);

            try {
                ResultSet pk_resSet = executor.exe_select();
                if (pk_resSet != null) {
                    pk_resSet.next();
                    pk = pk_resSet.getString("COLUMN_NAME");

                } else {
                    System.err.print("no pk found!");
                }
                executor.closeConnection();
            } catch (SQLException e) {
                System.err.print(e.getMessage());
                reset();
                return null;
            }
        } else {
            System.err.println("not correct table_name");
        }
        return pk;
    }

    /**
     *
     * @param table_name to get child tables for this given table
     * @return array of child tables for the given table
     */
    public ArrayList<ref> getRefinfo(String table_name) {
        ArrayList<ref> refs = new ArrayList<>();
        SQLBuilder builder = SQLBuilder.getInstance();
        boolean assurnceForcorrection = builder.buildgetRefQuery(table_name);
        if (assurnceForcorrection) {
            SQLExecutor executor = new SQLExecutor(builder);

            try {
                ResultSet ref_resSet = executor.exe_select();
                if (ref_resSet != null) {
                    while (ref_resSet.next()) {
                        ref ref_ = new ref(ref_resSet.getString("TABLE_NAME"), ref_resSet.getString("COLUMN_NAME"));
                        refs.add(ref_);
                    }
                } else {
                    System.err.print("no ref found!");
                }
                executor.closeConnection();
            } catch (SQLException e) {
                System.err.print(e.getMessage());
                reset();
                return null;
            }
        } else {
            System.err.println("not correct table_name");
        }
        return refs;
    }

    /**
     * to call buildInsertQuery from SQLBuilder , call update from SQExe. handle
     * any exceptions may happen in between handle any additional work related
     * to insert process such as any animation, commit ..etc
     *
     *
     * @param table_name select_table to insert into
     * @param table_cols table columns to insert values in
     * @param new_values values of columns to be inserted
     * @return
     */
    public boolean insert(String table_name, ArrayList<String> table_cols,
            ArrayList<SimpleEntry<String, String>> new_values) {
        boolean assurnceForcorrection = false;
        if ((table_name == null ? Messages.getString("Controller.empty_string") != null : !table_name.equals(Messages.getString("Controller.empty_string"))) || table_name != null) { //$NON-NLS-1$
            SQLBuilder builder = SQLBuilder.getInstance();
            if (table_cols.size() > 0 && new_values.size() > 0) {
                assurnceForcorrection = builder.buildInsertQuery(table_name, table_cols, new_values);
                if (assurnceForcorrection) {

                    SQLExecutor executor = new SQLExecutor(builder);
                    try {
                        if (!executor.exe_insert()) {
                            assurnceForcorrection = false;
                        }
                    } catch (Exception e) {
                        System.err.println(Messages.getString("Controller.sqlexe_insert_err") + e.toString()); //$NON-NLS-1$

                        assurnceForcorrection = false;
                    }
                }
            } else {
                System.err.println(Messages.getString("Controller.parametters_shortage")); //$NON-NLS-1$
            }
        } else {
            System.err.println(Messages.getString("Controller.insert_err") + Messages.getString("Controller.insert_err_select_table")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        reset();
        return assurnceForcorrection;
    }

    /**
     * to call buildDeleteQuery from SQLBuilder , call update from SQExe. handle
     * any exceptions may happen in between handle any additional work related
     * to delete process such as any animation, commit ..etc
     *
     * @param table_name select_table to delete from
     * @param where_cols where columns to be inserted in where clause
     * @param where_values where values in order for where columns, given from
     * user input and inserted in where clause
     * @param operator : between where col. and its value such as like,=,> or
     * contains.
     * @param or : flag to determine if we combine where cols with "or" or "and"
     * @return true if deleted successfully
     */
    public boolean delete(String table_name, ArrayList<String> where_cols,
            ArrayList<SimpleEntry<String, String>> where_values, ArrayList<String> operator, boolean or) {
        boolean assurnceForcorrection = false;
        if ((table_name == null ? Messages.getString("Controller.empty_string") != null : !table_name.equals(Messages.getString("Controller.empty_string"))) || table_name != null) { //$NON-NLS-1$
            SQLBuilder builder = SQLBuilder.getInstance();

            if (where_cols.size() > 0 && where_values.size() > 0) {
                assurnceForcorrection = builder.buildDeleteQuery(table_name, where_cols, where_values, operator, or);
                if (assurnceForcorrection) {

                    SQLExecutor executor = new SQLExecutor(builder);
                    try {
                        if (!executor.exe_delete()) {
                            assurnceForcorrection = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        assurnceForcorrection = false;
                    }
                }

            } else {
                System.err.println(Messages.getString("Controller.parametters_shortage")); //$NON-NLS-1$
            }
        } else {

            System.err.println(Messages.getString("Controller.insert_err") + Messages.getString("Controller.insert_err_select_table")); //$NON-NLS-1$ //$NON-NLS-2$
        }
        reset();
        return assurnceForcorrection;
    }

    /**
     * to call buildSelectQuery from SQLBuilder , call update from SQExe. handle
     * any exceptions may happen in between handle any additional work related
     * to search process such as any animation, commit ..etc
     *
     *
     * @param table select_table to select from
     * @param cols_to_select columns to select from table
     * @param where_cols where columns to be inserted in where clause
     * @param where_values where values in order for where columns, given from
     * user input and inserted in where clause
     * @param operator : between where col. and its value such as like,=,> or
     * contains.
     * @param or : flag to determine if we combine where cols with "or" or "and"
     * logic operator!=.
     * @return array of columns values based on cols_to_select and where caluse
     */
    public ArrayList<ArrayList<SimpleEntry<String, String>>> search(DbTable table, ArrayList<String> cols_to_select,
            ArrayList<String> where_cols, ArrayList<SimpleEntry<String, String>> where_values, ArrayList<String> operator, boolean or) {
        if (table != null) {
            SQLBuilder builder = SQLBuilder.getInstance();
            ArrayList<String> cleaned_where_cols = new ArrayList<>();
            ArrayList<dbCol> org_cols = table.getCols();
            where_cols.forEach((where_c) -> {
                org_cols.stream().filter((col) -> (col.getName().equals(where_c))).forEachOrdered((_item) -> {
                    cleaned_where_cols.add(where_c);
                });
            });
            if (cols_to_select.size() > 0 && where_cols.size() > 0 && where_values.size() > 0) {

                // prepare cols_to_select to build searchContent for JTable drawing correctly
                if (cols_to_select.get(0).equals(Messages.getString("Controller.select_all"))) { //$NON-NLS-1$
                    cols_to_select.clear();
                    table.getCols().forEach((col_name) -> {
                        cols_to_select.add(col_name.getName());
                    });
                }

                boolean assurnceForcorrection = builder.buildSelectQuery(table.getName(), cols_to_select,
                        cleaned_where_cols, where_values, operator, or);

                if (assurnceForcorrection) {
                    SQLExecutor executor = new SQLExecutor(builder);
                    try {
                        ResultSet resultSet = executor.exe_select();
                        if (resultSet != null) {

                            ArrayList<ArrayList<SimpleEntry<String, String>>> searchContent = new ArrayList<>();
                            while (resultSet.next()) {
                                ArrayList<SimpleEntry<String, String>> recordContent = new ArrayList<>();
                                for (String col : cols_to_select) {
                                    recordContent.add(new SimpleEntry<>(col, resultSet.getString(col)));
                                }
                                searchContent.add(recordContent);
                            }
                            executor.closeConnection();
                            reset();
                            return searchContent;
                        } else {
                            return null;
                        }
                    } catch (SQLException e) {
                        System.err.print(e.getMessage());
                        reset();
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                System.err.println(Messages.getString("Controller.parametters_shortage")); //$NON-NLS-1$
                reset();
                return null;

            }
        } else {
            reset();
            return null;
        }
    }

    /**
     * set cache files ready. flag first time based on condition of cache files
     */
    public void configApp() {

        String appPath = System.getProperty(Messages.getString("Controller.user_dir")); //$NON-NLS-1$
        File db_file = new File(appPath + Messages.getString("Controller.win_slash") + new Utils().DB_CONFIG_FILEPATH + ".bin"); //$NON-NLS-1$
        File config_file = new File(appPath + Messages.getString("Controller.win_slash") + new Utils().APP_Config_FILEPATH + ".bin"); //$NON-NLS-1$
        File tables_info_file = new File(appPath + Messages.getString("Controller.win_slash") + new Utils().TABLES_INFO_FILEPATH + ".bin"); //$NON-NLS-1$
        File user_data_file = new File(appPath + Messages.getString("Controller.win_slash") + new Utils().USERDATA_FILEPATH + ".bin"); //$NON-NLS-1$
        if (db_file.exists() && config_file.exists() && user_data_file.exists() && tables_info_file.exists()) {
            loggingIn(false);
        } else {
            loggingIn(true);

        }
    }

    /**
     *
     * @param firstTime if true open user register page, else open login page
     * and check existing user info. with the given input from logging on page.
     */
    public void loggingIn(boolean firstTime) {
        LoginPage loginPage = new LoginPage(firstTime);
        loginPage.setVisible(true);
        loginPage.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent arg0) {
                super.windowClosed(arg0);
                boolean loggingIndone = loginPageWindowAdapting(arg0, loginPage);
                if (loggingIndone) {
                    configDb(firstTime);
                }
            }
        });
    }

    /*
    configure database
    if first time open database configuration page to insert a database configuration
    else check existing database configurations
     */
    private void configDb(boolean firstTime) {
        if (firstTime) {
            openDbConfigPage();
        } else {
            StorageManager storageManager = new StorageManager();
            if (storageManager.ReadConfigurations()) {

                DbManager.dbConfigs = DbManager.schemas[0];
                DbConfigs configs = DbManager.dbConfigs;
                configs.configDB_URL();
                if (configs.testDbConnection()) {
                    configs.syncTables();
                    setConfigured(true);

                } else {
                    openDbConfigPage();
                }
            }
        }
    }

    /**
     * opens database configuration page
     */
    public void openDbConfigPage() {
        DbConfigsPage dbConfigsPage = new DbConfigsPage();
        dbConfigsPage.setVisible(true);
        dbConfigsPage.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent arg0) {
                super.windowClosed(arg0);
                boolean configIsdone = configPageWindowAdapting(arg0, dbConfigsPage);
                if (configIsdone) {
                    String appPath = System.getProperty(Messages.getString("Controller.user_dir")); //$NON-NLS-1$
                    try {
                        new File(appPath + Messages.getString("Controller.win_slash") + new Utils().DB_CONFIG_FILEPATH + ".bin").createNewFile(); //$NON-NLS-1$
                        new File(appPath + Messages.getString("Controller.win_slash") + new Utils().APP_Config_FILEPATH + ".bin").createNewFile(); //$NON-NLS-1$
                        new File(appPath + Messages.getString("Controller.win_slash") + new Utils().TABLES_INFO_FILEPATH + ".bin").createNewFile(); //$NON-NLS-1$
                        new File(appPath + Messages.getString("Controller.win_slash") + new Utils().USERDATA_FILEPATH + ".bin").createNewFile(); //$NON-NLS-1$
                        addSchema(dbConfigsPage.getConfigs());

                        setConfigured(configIsdone);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                        setConfigured(!configIsdone);

                    }
                }

            }
        });
    }

    /*
            return true if database configuration process is done, false otherwise.i.e return dbConfigsPage.isDone() return.

     */
    private boolean configPageWindowAdapting(WindowEvent event, DbConfigsPage dbConfigsPage) {

        return dbConfigsPage.isDone();
    }

    /*
        return true if logging on process is done, false otherwise.i.e return loginPage.isDone() return.
     */
    private boolean loginPageWindowAdapting(WindowEvent event, LoginPage loginPage) {

        return loginPage.isDone();
    }

    /**
     *
     * @param user app. user tp be stored on cache file.
     */
    public void addUser(User user) {
        StorageManager storageManager = new StorageManager();
        if (!checkifExist(user)) {
            storageManager.saveUserData(user);
        } else {
            Utils.showWarning("User is already exist!");
        }
    }

    /**
     *
     * @param configs database configuration to be saved on cache file.
     */
    public void addSchema(DbConfigs configs) {
        StorageManager storageManager = new StorageManager();
        if (!checkifExist(configs)) {

            storageManager.WriteConfigurations(configs);
        } else {
            Utils.showWarning("Schema is already exist!");

        }

    }

    /**
     *
     * @param user app. user to be checked if already exist or not.
     * @return true if exist, false otherwise
     */
    public boolean checkifExist(User user) {
        StorageManager storageManager = new StorageManager();
        ArrayList<User> exist_users = storageManager.getUsers();
        return exist_users.contains(user);

    }

    /**
     *
     * @param configs database configurations to be checked if already exist or
     * not
     * @return true if exist, false otherwise
     */
    public boolean checkifExist(DbConfigs configs) {
        if (dbmanager.DbManager.schemas != null) {

            ArrayList<DbConfigs> schemas_list = new ArrayList<>(Arrays.asList(dbmanager.DbManager.schemas));
            return schemas_list.contains(configs);
        } else {
            return false;
        }
    }

    /**
     * clear represented data, re-configure visibility of buttons,clear data in
     * builder
     */
    public void reset() {

        SQLBuilder builder = SQLBuilder.getInstance();
        builder.clear();
    }

    /**
     * set default configuration before giving the perms. to app. to start
     * working.
     *
     * @param b to set DbManager.readyToStart by its value
     */
    public void setConfigured(boolean b) {

        if (DbManager.readyToStart) {
            StorageManager storageManager = new StorageManager();
            storageManager.ReadConfigurations();

            String[] schema_strings = new String[DbManager.schemas.length];
            for (int i = 0; i < DbManager.schemas.length; i++) {
                schema_strings[i] = DbManager.schemas[i].getLabel();
            }
            ViewManager viewManager = new ViewManager(this, storageManager.getTables(schema_strings[0]), schema_strings);
            viewManager.revalidate();
            viewManager.repaint();
            viewManager.setVisible(true);
        } else {
            DbManager.readyToStart = b;
            DbManager.semaphore_stopMAIN.release();
        }

        return;
    }

    /**
     *
     */
    public Controller() {
        super();

    }

    /**
     * to refresh window with other (selected database ) tables and data
     */
    public void refresh() {
        StorageManager storageManager = new StorageManager();
        String[] schema_strings = new String[DbManager.schemas.length];
        for (int i = 0; i < DbManager.schemas.length; i++) {
            schema_strings[i] = DbManager.schemas[i].getLabel();
        }
        ViewManager viewManager = new ViewManager(this, storageManager.getTables(DbManager.dbConfigs.getLabel()), schema_strings);
        viewManager.setVisible(true);
    }

    /**
     * this class is used to get ref. info about table. ref. tables / child
     * tables.
     */
    public static class ref {

        /**
         *
         */
        public String table_name,
                /**
                 *
                 */
                col_name;

        ref(String mTable_name, String c_name) {
            this.table_name = mTable_name;
            this.col_name = c_name;
        }

    };
}
