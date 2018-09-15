package control;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class SQLBuilder {

    /*
	 * all void fns (builder) throw its result into this query string builder
	 * functions return boolean to indicate if the building operation is
	 * successfully done
     */
    public String query;
    private static SQLBuilder instance;

    public static SQLBuilder getInstance() {
        if (instance == null) {
            instance = new SQLBuilder();
        }
        return instance;
    }

    private SQLBuilder() {

    }

    public boolean buildSelectQuery(String table_name, ArrayList<String> cols_to_select,ArrayList<String> where_cols,
            ArrayList<SimpleEntry<String, String>> where_values,  ArrayList<String>operator,  boolean or) {
        String cond_where_multiple_cols = Messages.getString("SQLBuilder.empty_string");
        if (or) {
            cond_where_multiple_cols = Messages.getString("SQLBuilder.or");
        } else {
            cond_where_multiple_cols = Messages.getString("SQLBuilder.and");
        }
        String q = Messages.getString("SQLBuilder.select");
        if (cols_to_select.size() == 1 && cols_to_select.get(0).contains(Messages.getString("SQLBuilder.all"))) {
            q += Messages.getString("SQLBuilder.all_with_spaces");
        } else {
            if (cols_to_select.size() > 0) {

                for (int i = 0; i < cols_to_select.size() - 1; i++) {
                    q += cols_to_select.get(i) + Messages.getString("SQLBuilder.comma");
                }
                q += cols_to_select.get(cols_to_select.size() - 1);
            } else {
                System.err.println(Messages.getString("SQLBuilder.err_msg_cannot_select_null"));
                q = Messages.getString("SQLBuilder.empty_string");
                return false;
            }
        }
        q += Messages.getString("SQLBuilder.from") + table_name + Messages.getString("SQLBuilder.space_string"); //$NON-NLS-2$
        if (where_cols.size() > 0 && where_values.size() == where_cols.size()) {
            q += Messages.getString("SQLBuilder.where");
            for (int i = 0; i < where_cols.size() - 1; i++) {
                q += where_cols.get(i) + " " + operator.get(i)
                        + getQforValue(where_values.get(i).getValue(), where_values.get(i).getKey())
                        + cond_where_multiple_cols;
            }
            q += where_cols.get(where_cols.size() - 1) + " " + operator.get(where_cols.size() - 1)
                    + getQforValue(where_values.get(where_values.size() - 1).getValue(),
                            where_values.get(where_values.size() - 1).getKey());
        } else if (where_values.size() > where_cols.size()) {
            q += Messages.getString("SQLBuilder.where");
            for (int i = 0; i < where_values.size() - 1; i++) {
                q += where_cols.get(0) + " " + operator.get(0)
                        + getQforValue(where_values.get(i).getValue(), where_values.get(i).getKey())
                        + cond_where_multiple_cols;
            }
            q += where_cols.get(where_cols.size() - 1) + " " + operator.get(where_cols.size() - 1)
                    + getQforValue(where_values.get(where_values.size() - 1).getValue(),
                            where_values.get(where_values.size() - 1).getKey());
        } else {

            System.err.println(Messages.getString("SQLBuilder.err_msg_cannot_select_null"));
            q = Messages.getString("SQLBuilder.empty_string");
            return false;
        }

        this.query = q;
        return true;
    }

    public boolean buildInsertQuery(String table_name, ArrayList<String> table_cols,
            ArrayList<SimpleEntry<String, String>> new_values) {
        String q = Messages.getString("SQLBuilder.insert_into") + table_name;
        if (table_cols.size() > 0) {
            q += Messages.getString("SQLBuilder.openning_circle_bracket");
            for (int i = 0; i < table_cols.size() - 1; i++) {
                q += table_cols.get(i) + Messages.getString("SQLBuilder.comma");
            }
            q += table_cols.get(table_cols.size() - 1) + Messages.getString("SQLBuilder.closing_circle_bracket");
        }
        if (new_values.size() > 0) {
            q += Messages.getString("SQLBuilder.values_with_opening_circle_bracket");
            for (int i = 0; i < new_values.size() - 1; i++) {
                q += getQforValue(new_values.get(i).getValue(), new_values.get(i).getKey())
                        + Messages.getString("SQLBuilder.comma");
            }

            q += getQforValue(new_values.get(new_values.size() - 1).getValue(),
                    new_values.get(new_values.size() - 1).getKey())
                    + Messages.getString("SQLBuilder.closing_circle_bracket");
        } else {
            System.err.println(Messages.getString("SQLBuilder.err_msg_cannot_insert_null"));
            q = Messages.getString("SQLBuilder.empty_string");
            return false;
        }
        this.query = q;
        return true;
    }

    public boolean buildUpdateQuery(String table_name, ArrayList<String> cols_to_update,
            ArrayList<SimpleEntry<String, String>> new_values, ArrayList<String> where_cols,
            ArrayList<SimpleEntry<String, String>> where_values,ArrayList<String>operator, boolean or) {
        String cond_where_multiple_cols = Messages.getString("SQLBuilder.empty_string");
        if (or) {
            cond_where_multiple_cols = Messages.getString("SQLBuilder.or");
        } else {
            cond_where_multiple_cols = Messages.getString("SQLBuilder.and");
        }
        String q = Messages.getString("SQLBuilder.update_with_space") + table_name
                + Messages.getString("SQLBuilder.set");
        if (cols_to_update.size() > 0 && cols_to_update.size() <= new_values.size()) {
            for (int i = 0; i < cols_to_update.size() - 1; i++) {
                q += cols_to_update.get(i) + " " + operator.get(i)
                        + getQforValue(new_values.get(i).getValue(), new_values.get(i).getKey())
                        + cond_where_multiple_cols;
            }
            q += cols_to_update.get(cols_to_update.size() - 1) + " " + operator.get(cols_to_update.size() - 1)
                    + getQforValue(new_values.get(new_values.size() - 1).getValue(),
                            new_values.get(new_values.size() - 1).getKey());
        }
//        }else if(cols_to_update.size() < new_values.size()){
//                for (int i = 0; i < cols_to_update.size() - 1; i++) {
//                q += cols_to_update.get(i) + Messages.getString("SQLBuilder.equal_string")
//                        + getQforValue(new_values.get(new_values.size()-1).getValue(), new_values.get(new_values.size()-1).getKey())
//                        + cond_where_multiple_cols;
//            }
//            q += cols_to_update.get(cols_to_update.size() - 1) + Messages.getString("SQLBuilder.equal_string")
//                    + getQforValue(new_values.get(new_values.size() - 1).getValue(),
//                            new_values.get(new_values.size() - 1).getKey());
//        }
        if (where_cols.size() > 0 && where_values.size() == where_cols.size()) {
            q += Messages.getString("SQLBuilder.where");
            for (int i = 0; i < where_cols.size() - 1; i++) {
                q += where_cols.get(i) + " " + operator.get(i)
                        + getQforValue(where_values.get(i).getValue(), where_values.get(i).getKey())
                        + cond_where_multiple_cols;
            }
            q += where_cols.get(where_cols.size() - 1) + " " + operator.get(where_cols.size() - 1)
                    + getQforValue(where_values.get(where_values.size() - 1).getValue(),
                            where_values.get(where_values.size() - 1).getKey());
        } else if (where_values.size() > where_cols.size()) {
            q += Messages.getString("SQLBuilder.where");
            for (int i = 0; i < where_values.size() - 1; i++) {
                q += where_cols.get(0) + " " + operator.get(i)
                        + getQforValue(where_values.get(i).getValue(), where_values.get(i).getKey())
                        + cond_where_multiple_cols;
            }
            q += where_cols.get(where_cols.size() - 1) + " " + operator.get(where_cols.size() - 1)
                    + getQforValue(where_values.get(where_values.size() - 1).getValue(),
                            where_values.get(where_values.size() - 1).getKey());
        } else {
            System.err.println(Messages.getString("SQLBuilder.err_msg_cannot_update_null"));
            q = Messages.getString("SQLBuilder.empty_string");
            return false;
        }
        this.query = q;
       // System.out.println("q:(from update)" + this.query);
        return true;
    }

    public boolean buildDeleteQuery(String table_name, ArrayList<String> where_cols,
            ArrayList<SimpleEntry<String, String>> where_values,ArrayList<String>operator, boolean or) {
        String cond_where_multiple_cols = Messages.getString("SQLBuilder.empty_string");
        if (or) {
            cond_where_multiple_cols = Messages.getString("SQLBuilder.or");
        } else {
            cond_where_multiple_cols = Messages.getString("SQLBuilder.and");
        }
        String q = Messages.getString("SQLBuilder.delete_from") + table_name + Messages.getString("SQLBuilder.where"); //$NON-NLS-2$

        if (where_cols.size() > 0 && where_values.size() == where_cols.size()) {
            for (int i = 0; i < where_cols.size() - 1; i++) {
                q += where_cols.get(i) + " " + operator.get(i)
                        + getQforValue(where_values.get(i).getValue(), where_values.get(i).getKey())
                        + cond_where_multiple_cols;
            }
            q += where_cols.get(where_cols.size() - 1)+ " " + operator.get(where_cols.size() - 1)
                    + getQforValue(where_values.get(where_values.size() - 1).getValue(),
                            where_values.get(where_values.size() - 1).getKey());
        } else if (where_values.size() > where_cols.size()) {

            for (int i = 0; i < where_values.size() - 1; i++) {
                q += where_cols.get(0)+ " " + operator.get(0)
                        + getQforValue(where_values.get(i).getValue(), where_values.get(i).getKey())
                        + cond_where_multiple_cols;
            }
            q += where_cols.get(where_cols.size() - 1) + " " + operator.get(where_cols.size() - 1)
                    + getQforValue(where_values.get(where_values.size() - 1).getValue(),
                            where_values.get(where_values.size() - 1).getKey());
        } else {
            System.err.println(Messages.getString("SQLBuilder.err_msg_cannot_delete_without_where"));
            q = Messages.getString("SQLBuilder.empty_string");
            return false;
        }
        this.query = q;
      //  System.out.println("q:(from delete)" + this.query);

        return true;
    }

    public void buildGetDbTablesQuery() {
        this.query = Messages.getString("SQLBuilder.select_table_name_from_user_tables");
    }

    public boolean buildDescribeTable(String table_name) {
        if (table_name != Messages.getString("SQLBuilder.empty_string")) {
            this.query = "select * from " + table_name + " where rownum = 1";
            return true;
        } else {
            this.query = Messages.getString("SQLBuilder.empty_string");
            System.err.println(Messages.getString("SQLBuilder.err_msg_cannot_describe_table"));
            return false;
        }
    }

    public boolean buildgetRefQuery(String table_name) {
        if (table_name != null) {
            if (table_name.length() > 0) {
                this.query = "SELECT owner,\n"
                        + "       constraint_name,\n"
                        + "       constraint_type,\n"
                        + "       table_name,\n"
                        + "       column_name,\n"
                        + "       r_owner,\n"
                        + "       r_constraint_name\n"
                        + "  FROM all_constraints natural join all_cons_columns\n"
                        + " WHERE     constraint_type = 'R'\n"
                        + "       AND r_constraint_name IN\n"
                        + "              (SELECT constraint_name\n"
                        + "                 FROM all_constraints\n"
                        + "                WHERE constraint_type IN ('P', 'U') AND table_name = '" + table_name + "')";

                return true;
            } else {
                this.query = Messages.getString("SQLBuilder.empty_string");
                System.err.println("SQLBuilder:getRefQuery:null table_name");
                return false;
            }
        } else {
            this.query = Messages.getString("SQLBuilder.empty_string");
            System.err.println("SQLBuilder:getRefQuery:null table_name");
            return false;
        }
    }

    public boolean buildGetPKQuery(String table_name) {
        if (table_name != null) {
            if (table_name.length() > 0) {
                this.query = "  SELECT cols.table_name,\n"
                        + "         COLS.COLUMN_NAME,\n"
                        + "         cols.position,\n"
                        + "         cons.status,\n"
                        + "         cons.owner\n"
                        + "    FROM all_constraints cons, all_cons_columns cols\n"
                        + "   WHERE     cols.table_name = '" + table_name + "'\n"
                        + "         AND cons.constraint_type = 'P'\n"
                        + "         AND cons.constraint_name = cols.constraint_name\n"
                        + "         AND cons.owner = cols.owner";

                return true;
            } else {
                this.query = Messages.getString("SQLBuilder.empty_string");
                System.err.println("SQLBuilder:buildGetPKQuery:null table_name");
                return false;
            }
        } else {
            this.query = Messages.getString("SQLBuilder.empty_string");
            System.err.println("SQLBuilder:buildGetPKQuery:null table_name");
            return false;
        }
    }

    public void buildNormalSQLplus_DescribeTable(String table_name) {
        this.query = "desc " + table_name;
    }

    public void buildPLDescripeTable(String table_name) {
        this.query = "create or replace function getdesc_table(table_name VARCHAR) return ResultSet is begin return description = desc '" + table_name + "'; end;";
    }

    public void clear() {
        instance.query = Messages.getString("SQLBuilder.empty_string");
    }

    /*
	 * determine the suitable way to put value into query depending on its datatype
     */
    private String getQforValue(String dataType, String value) {
        String result = Messages.getString("SQLBuilder.empty_string");

        if (dataType.contains(Messages.getString("SQLBuilder.DATE"))
                || dataType.contains(Messages.getString("SQLBuilder.TIME"))) {
            if (value.contains(Messages.getString("SQLBuilder.SYSDATE"))) {
                result += value;
            } else {
                result += Messages.getString("SQLBuilder.single_quote") + value
                        + Messages.getString("SQLBuilder.single_quote");
            }
        } else if (dataType.contains(Messages.getString("SQLBuilder.NUMBER"))) {
            result += value;
        } else if (dataType.contains(Messages.getString("SQLBuilder.CHAR"))
                || dataType.contains(Messages.getString("SQLBuilder.STRING"))) {
            result += Messages.getString("SQLBuilder.single_colon") + value
                    + Messages.getString("SQLBuilder.single_colon");
        }
        return result;
    }

    /*
	 * the followeing set and get query fns to test or use with already built
	 * functions!
     */
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

}
