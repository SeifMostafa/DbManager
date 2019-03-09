package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import model.DbConfigs;
import model.DbTable;
import model.DbTable.dbCol;
import model.User;
import model.User.PERMISSION;

/**
 * responsible for and read/write operations on cache or text files 
 * do operate with hard disk 
 * @author dotnet2
 */
public class StorageManager {

    String filepath;

    /**
     *
     */
    public static final int ENCRYPTION_PUB_KEY = 69;

    public StorageManager() {
        super();
    }

    /**
     *
     * @param dbLabel database label to indicate which database to get its
     * tables
     * @return array of DbTable refers to tables are exist in database indicated
     * by dbLabel
     */
    public ArrayList<DbTable> getTables(String dbLabel) {
        ArrayList<DbTable> result = new ArrayList<>();
        try {
            File file = new File(new Utils().TABLES_INFO_FILEPATH + "_" + dbLabel + ".bin");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            // String decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
            // line = decrypted_line;
            while (line != null) {
                String table_name = line.substring(line.indexOf(Messages.getString("StorageManager.name")) + 5, //$NON-NLS-1$
                        line.indexOf(Messages.getString("StorageManager.cols"))); //$NON-NLS-1$
                if (line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket")) != -1) { //$NON-NLS-1$
                    String[] col_string = line.substring(line.indexOf(Messages.getString("StorageManager.cols")) + 8, //$NON-NLS-1$
                            line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket"))) //$NON-NLS-1$
                            .split(Messages.getString("StorageManager.single_closing_square_bracket_with_comma")); //$NON-NLS-1$
                    DbTable dbTable;
                    ArrayList<dbCol> cols;
                    if (table_name != Messages.getString("StorageManager.empty_string") && col_string.length > 0) { //$NON-NLS-1$
                        cols = new ArrayList<>();
                        for (String s : col_string) {
                            String[] col_specifications = s.split(Messages.getString("StorageManager.space_string")); //$NON-NLS-1$
                            if (col_specifications.length == 4) {
                                cols.add(new dbCol(col_specifications[1].substring(6), col_specifications[2],
                                        Boolean.parseBoolean(col_specifications[3].substring(11))));
                            } else {
                                cols.add(new dbCol(col_specifications[1].substring(6), col_specifications[2],
                                        Boolean.parseBoolean(
                                                col_specifications[col_specifications.length - 1].substring(11))));
                            }
                        }
                        dbTable = new DbTable(table_name, cols);
                        result.add(dbTable);
                    } else {
                        System.err.println(Messages.getString("StorageManager.err_msg_tablesinfo_file_notfound")); //$NON-NLS-1$
                        bufferedReader.close();
                        return null;
                    }
                }
                line = bufferedReader.readLine();
                // decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
                // line = decrypted_line;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param chosenTableName to get database table
     * @return DbTable object carry table name and table columns with their data
     * types
     */
    public DbTable getDbTable(String chosenTableName) {
        try {
            File file = new File(new Utils().TABLES_INFO_FILEPATH + "_" + dbmanager.DbManager.dbConfigs.getLabel() + ".bin");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            // String decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
            // line = decrypted_line;
            while (line != null) {
                String table_name = line.substring(line.indexOf(Messages.getString("StorageManager.name")) + 5, //$NON-NLS-1$
                        line.indexOf(Messages.getString("StorageManager.cols"))); //$NON-NLS-1$
                if (line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket")) != -1) { //$NON-NLS-1$
                    String[] col_string = line.substring(line.indexOf(Messages.getString("StorageManager.cols")) + 8, //$NON-NLS-1$
                            line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket"))) //$NON-NLS-1$
                            .split(Messages.getString("StorageManager.single_closing_square_bracket_with_comma")); //$NON-NLS-1$
                    DbTable dbTable;
                    ArrayList<dbCol> cols;
                    if (table_name != Messages.getString("StorageManager.empty_string") && col_string.length > 0) { //$NON-NLS-1$
                        if (table_name.equals(chosenTableName)) {

                            cols = new ArrayList<>();
                            for (String s : col_string) {
                                String[] col_specifications = s.split(Messages.getString("StorageManager.space_string")); //$NON-NLS-1$
                                if (col_specifications.length == 4) {
                                    cols.add(new dbCol(col_specifications[1].substring(6), col_specifications[2],
                                            Boolean.parseBoolean(col_specifications[3].substring(11))));
                                } else {
                                    cols.add(new dbCol(col_specifications[1].substring(6), col_specifications[2],
                                            Boolean.parseBoolean(
                                                    col_specifications[col_specifications.length - 1].substring(11))));
                                }
                            }
                            dbTable = new DbTable(table_name, cols);
                            return dbTable;
                        }
                    } else {
                        System.err.println(Messages.getString("StorageManager.err_msg_tablesinfo_file_notfound")); //$NON-NLS-1$
                        bufferedReader.close();
                        return null;
                    }
                }
                line = bufferedReader.readLine();
                // decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
                // line = decrypted_line;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * @param username opened tables for specific user
     * @return array of database tables are opened for user indicated by given
     * @param username
     */
    public ArrayList<DbTable> getUserAccessedTables(String username) {
        ArrayList<DbTable> result = new ArrayList<>();
        try {
            File file = new File(new Utils().USERDATA_FILEPATH + "_" + username + ".bin");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            // String decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
            // line = decrypted_line;
            while (line != null) {
                String table_name = line.substring(line.indexOf(Messages.getString("StorageManager.name")) + 5, //$NON-NLS-1$
                        line.indexOf(Messages.getString("StorageManager.cols"))); //$NON-NLS-1$
                if (line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket")) != -1) { //$NON-NLS-1$
                    String[] col_string = line.substring(line.indexOf(Messages.getString("StorageManager.cols")) + 8, //$NON-NLS-1$
                            line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket"))) //$NON-NLS-1$
                            .split(Messages.getString("StorageManager.single_closing_square_bracket_with_comma")); //$NON-NLS-1$
                    DbTable dbTable;
                    ArrayList<dbCol> cols;
                    if (table_name != Messages.getString("StorageManager.empty_string") && col_string.length > 0) { //$NON-NLS-1$
                        cols = new ArrayList<>();
                        for (String s : col_string) {
                            String[] col_specifications = s.split(Messages.getString("StorageManager.space_string")); //$NON-NLS-1$
                            if (col_specifications.length == 4) {
                                cols.add(new dbCol(col_specifications[1].substring(6), col_specifications[2],
                                        Boolean.parseBoolean(col_specifications[3].substring(11))));
                            } else {
                                cols.add(new dbCol(col_specifications[1].substring(6), col_specifications[2],
                                        Boolean.parseBoolean(
                                                col_specifications[col_specifications.length - 1].substring(11))));
                            }
                        }
                        dbTable = new DbTable(table_name, cols);
                        result.add(dbTable);
                    } else {
                        System.err.println(Messages.getString("StorageManager.err_msg_tablesinfo_file_notfound")); //$NON-NLS-1$
                        bufferedReader.close();
                        return null;
                    }
                }
                line = bufferedReader.readLine();
                // decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
                // line = decrypted_line;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param col_names to get tables contains cols indicated by this col_names
     * (column names)
     * @param dbLabel to search under specific database indicated by this
     * dbLabel
     * @return array of database tables contains columns with the col_names
     */
    public ArrayList<DbTable> getTablesContain(ArrayList<String> col_names, String dbLabel) {
        ArrayList<DbTable> result = new ArrayList<>();
        try {
            File file = new File(new Utils().TABLES_INFO_FILEPATH + "_" + dbLabel + ".bin");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            // String decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
            // line = decrypted_line;
            while (line != null) {
                String table_name = line.substring(line.indexOf(Messages.getString("StorageManager.name")) + 5, //$NON-NLS-1$
                        line.indexOf(Messages.getString("StorageManager.cols"))); //$NON-NLS-1$
                if (line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket")) != -1) { //$NON-NLS-1$
                    String[] cols_string = line
                            .substring(line.indexOf(Messages.getString("StorageManager.cols_with_open_square_bracket")) //$NON-NLS-1$
                                    + 7,
                                    line.indexOf(Messages.getString("StorageManager.double_closing_square_bracket"))) //$NON-NLS-1$
                            .split(Messages.getString("StorageManager.comma")); //$NON-NLS-1$
                    boolean acceptTable = false;
                    for (String col : col_names) {
                        for (String col_string : cols_string) {
                            if (col_string.contains(col)) {
                                acceptTable = true;
                                break;
                            }
                        }
                    }

                    if (acceptTable) {
                        DbTable dbTable;
                        ArrayList<dbCol> cols;
                        if (table_name != Messages.getString("StorageManager.empty_string") && cols_string.length > 0) { //$NON-NLS-1$
                            cols = new ArrayList<>();
                            for (String s : cols_string) {
                                String[] col_specifications = s
                                        .split(Messages.getString("StorageManager.space_string")); //$NON-NLS-1$
                                cols.add(new dbCol(col_specifications[1].substring(6), col_specifications[2],
                                        Boolean.parseBoolean(col_specifications[3].substring(11))));
                            }
                            dbTable = new DbTable(table_name, cols);
                            result.add(dbTable);
                        } else {
                            System.err
                                    .println(Messages.getString("StorageManager.err_msg_tablesinfo_file_wrong_format")); //$NON-NLS-1$
                            bufferedReader.close();
                            return null;
                        }

                    }
                }
                line = bufferedReader.readLine();
                // decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
                // line = decrypted_line;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * table_name[col_name$data_type$NotNull]table_name[] read line substring [
     * to ] substring $ =>
     *
     * @param dbTable to be written in database
     * @param dbLabel to indicate database to write on.
     */
    public void appendTable(DbTable dbTable, String dbLabel) {
        File file = new File(new Utils().TABLES_INFO_FILEPATH + "_" + dbLabel + ".bin"); // set path from constants
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            // String word_to_write = dbTable.toString();
            // String encrypted_word = Encryptor.encrypt(word_to_write, ENCRYPTION_PUB_KEY);

            fileOutputStream.write(dbTable.toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param dbTable to add database table to a specific user
     * @param username to indicate the user whose dbTable(database table) is
     * opened.
     */
    public void appendUserAccessedTable(DbTable dbTable, String username) {
        File file = new File(new Utils().USERDATA_FILEPATH + "_" + username + ".bin"); // set path from constants
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            // String word_to_write = dbTable.toString();
            // String encrypted_word = Encryptor.encrypt(word_to_write, ENCRYPTION_PUB_KEY);

            fileOutputStream.write(dbTable.toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param table_name to get database table
     * @param dbLabel to indicate database
     * @return true if database contains the table indicated by table_name,
     * false otherwise.
     */
    @SuppressWarnings("resource")
    public boolean checkifTableExist(String table_name, String dbLabel) {

        File file = new File(new Utils().TABLES_INFO_FILEPATH + "_" + dbLabel + ".bin"); // set path from constants

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            // String decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
            // line = decrypted_line;
            while (line != null) {
                String tn = line.substring(line.indexOf(Messages.getString("StorageManager.name")) + 5, //$NON-NLS-1$
                        line.indexOf(Messages.getString("StorageManager.cols"))); //$NON-NLS-1$
                if (table_name.equals(tn)) {
                    return true;
                }
                line = bufferedReader.readLine();
                // decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
                // line = decrypted_line;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    /**
     *
     * @param user to be written
     */
    public void saveUserData(User user) {
        File file = new File(new Utils().USERDATA_FILEPATH + ".bin"); // set path from constants

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            // String word_to_write = user.toString();
            // String encrypted_word = Encryptor.encrypt(word_to_write, ENCRYPTION_PUB_KEY);
            fileOutputStream.write(user.toString().getBytes());

            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user.getAccess_tables() != null) {
            file = new File(new Utils().USERDATA_FILEPATH + "_" + user.getUsername() + ".bin"); // set path from
            // constants

            try {
                if (!file.exists()) {
                    file.createNewFile();

                    user.getAccess_tables().forEach((dbTable) -> {
                        appendUserAccessedTable(dbTable, user.getUsername());
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("nulls");
        }
    }

    /**
     *
     * @param user to be deleted
     */
    public void deleteUser(User user) {
        ArrayList<User> users = this.getUsers();
        boolean FOUND = false;
        for (int i = 0; i < users.size(); i++) {
            if (user.equals(users.get(i))) {
                new File(new Utils().USERDATA_FILEPATH + "_" + user.getUsername() + ".bin").deleteOnExit();
                users.remove(i);
                FOUND = true;
                break;
            }
        }
        System.out.println(FOUND);
        if (FOUND) {
            File file = new File(new Utils().USERDATA_FILEPATH + ".bin");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                // String word_to_write = "";
                // String encrypted_word = Encryptor.encrypt(word_to_write, ENCRYPTION_PUB_KEY);
                fileOutputStream.write("".getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(users.size());
            for (User u : users) {
                this.saveUserData(u);

            }
        }
    }

    /**
     * get registered/added users
     *
     * @return array of users are registered
     */
    public ArrayList<User> getUsers() {
        ArrayList<User> result = new ArrayList<User>();
        try {
            FileReader fileReader = new FileReader(new File(new Utils().USERDATA_FILEPATH + ".bin"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                String user_fields[] = line.split("@@");

                String user_name = user_fields[0];
                String password = user_fields[1];
                line = user_fields[2];

                String[] perms;
                if (line.length() > 0 && !line.contains(Messages.getString("StorageManager.space_string"))) { //$NON-NLS-1$
                    perms = new String[1];
                    perms[0] = line;
                } else {
                    perms = line.split(Messages.getString("StorageManager.space_string")); //$NON-NLS-1$
                }

                PERMISSION[] permissions = new PERMISSION[perms.length];
                for (int i = 0; i < perms.length; i++) {
                    if (perms[i].equals(PERMISSION.MASTER.toString())) {
                        permissions[i] = PERMISSION.MASTER;
                        break;
                    } else {
                        if (perms[i].equals(PERMISSION.SEARCH.toString())) {
                            permissions[i] = PERMISSION.SEARCH;

                        } else if (perms[i].equals(PERMISSION.UPDATE.toString())) {
                            permissions[i] = PERMISSION.UPDATE;
                        } else if (perms[i].equals(PERMISSION.DELETE.toString())) {
                            permissions[i] = PERMISSION.DELETE;
                        } else {
                            permissions[i] = PERMISSION.INSERT;
                        }
                    }
                }
                User user = new User(user_name, password);

                user.setPermissions(permissions);

                if (new File(new Utils().USERDATA_FILEPATH + "_" + user.getUsername() + ".bin").exists()) {
                    ArrayList<DbTable> accessed_tables = getUserAccessedTables(user_name);
                    user.setAccess_tables(accessed_tables);
                }
                System.out.println("User:" + user.getUsername() + " , " + user.getPassword());
                result.add(user);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * read db configs into main class schemas object
     *
     * @return true if read process is successfully done, false otherwise
     */
    public boolean ReadConfigurations() {
        ArrayList<DbConfigs> schemas = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(new File(new Utils().DB_CONFIG_FILEPATH + ".bin"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            // String decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
            // line = decrypted_line;
            while (line != null) {
                DbConfigs dbConfigs1 = new DbConfigs();
                String[] dbconfig_fields = line.split("--");
                // System.out.println(dbconfig_fields[0]);

                dbConfigs1.setPORT_NUM(dbconfig_fields[0]);
                dbConfigs1.setHOST(dbconfig_fields[1]);
                dbConfigs1.setSERVICE_NAME(dbconfig_fields[2]);
                dbConfigs1.setUSER_NAME(dbconfig_fields[3]);
                dbConfigs1.setPASSWORD(dbconfig_fields[4]);
                dbConfigs1.configDB_URL();
                schemas.add(dbConfigs1);
                line = bufferedReader.readLine();

                // decrypted_line = Decryptor.decrypt(line, ENCRYPTION_PUB_KEY);
                // line = decrypted_line;
            }
            dbmanager.DbManager.schemas = new DbConfigs[schemas.size()];
            schemas.toArray(dbmanager.DbManager.schemas);
            // System.out.println("schemas are configured!");
            bufferedReader.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @param configs database configuration (username/password@host:port/sid)
     * to be written in cache files.
     *
     */
    public void WriteConfigurations(DbConfigs configs) {
        File file = new File(new Utils().DB_CONFIG_FILEPATH + ".bin"); // set path from constants
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(configs.toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * to encrypt text in cache files .. not used in this version
     */
    public static class Encryptor {

        /**
         *
         * @param password
         * @param pk
         * @return
         */
        public static String encrypt(String password, int pk) {
            String encryptedPassword = "";
            encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
            encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
            for (int i = 0; i < password.length(); i++) {
                encryptedPassword += (char) ((int) password.charAt(i) + pk);

                if (i == 4) {
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                }
                if (i == 7) {
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                }
                if (i == 8) {
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                }
                if (i == 9) {
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                    encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
                }
            }
            encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
            encryptedPassword += String.valueOf(0 + new Random().nextInt(9 - 0 + 1));
            return encryptedPassword;
        }
    }

    /**
     * to decrypt text in cache files ..not used in this version
     */
    public static class Decryptor {

        /**
         *
         * @param encryptedPassword
         * @param pk
         * @return
         */
        public static String decrypt(String encryptedPassword, int pk) {
            String password = "";

            for (int i = 2; i < encryptedPassword.length() - 2; i++) {
                password += (char) ((int) encryptedPassword.charAt(i) - pk);

                if (i == 6) {
                    i += 2;
                }
                if (i == 11) {
                    i++;
                }
                if (i == 13) {
                    i++;
                }
                if (i == 15) {
                    i += 4;
                }
            }
            return password;
        }
    }

}
