package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.DbTable;
import oracle.net.aso.i;
import view.colPanel;

public class Utils {

    public enum SignOperator {
        BIGGER, SMALLER, EQUAL
    };
    /*
	 * // public static final delimiters public final String Delimiter_PORT_NUM =
	 * "PORT"; public final String Delimiter_HOST = "HOST"; public final String
	 * Delimiter_SERVICE = "SERVICE"; public final String Delimiter_DB_URL =
	 * "DBURL"; public final String Delimiter_DB_DRIVER_URL = "DB_DRIVER_URL";
	 * public final String Delimiter_DB_CONFIG_FILEPATH = ""; public final String
	 * Delimiter_USERDATA_FILEPATH = ""; public final String
	 * Delimiter_TABLES_INFO_FILEPATH = "";
     */

    // public app config paths
    public final String DB_CONFIG_FILEPATH = Messages.getString("Utils.DB_CONFIG_FILEPATH"); //$NON-NLS-1$
    public final String USERDATA_FILEPATH = Messages.getString("Utils.USERDATA_FILEPATH"); //$NON-NLS-1$
    public final String TABLES_INFO_FILEPATH = Messages.getString("Utils.TABLES_INFO_FILEPATH"); //$NON-NLS-1$
    public final String APP_Config_FILEPATH = Messages.getString("Utils.APP_Config_FILEPATH"); //$NON-NLS-1$
    public static final String[] STATIC_OPERATORS = new String[]{Messages.getString("Utils.equal"), Messages.getString("Utils.biggerThan"), Messages.getString("Utils.lessThan"), Messages.getString("Utils.notequal"), Messages.getString("Utils.biggerThanorEqual"), Messages.getString("Utils.lessThanOrEqual"), Messages.getString("Utils.contains"), Messages.getString("Utils.like")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
    public final int PASSWORD_LENGTH = 6;

    /*
	 *	check if s is totally number 
     */
    public static boolean isNum(String s) {
        for (int i = 0; i < s.length(); i++) // ascii for 0-9 : 48-57
        {
            if (!(((int) s.charAt(i) >= 48 && (int) s.charAt(i) <= 57) /* || (int) s.charAt(i) == 88 */)) {
                return false;
            }
        }
        return true;
    }

    /*
	 * check if string contains spaces
     */
    public static boolean containSpaces(String string) {
        if (string.contains(Messages.getString("Utils.space_string"))) //$NON-NLS-1$
        {
            return true;
        } else {
            return false;
        }
    }

    /*
	 * check s length depending on operator and given length 
     */
    public static boolean checkLength(String s, int length, SignOperator operator) {
        switch (operator) {
            case BIGGER:
                if (s.length() >= length) {
                    return true;
                } else {
                    return false;
                }
            case EQUAL:
                if (s.length() == length) {
                    return true;
                } else {
                    return false;
                }
            case SMALLER:
                if (s.length() <= length) {
                    return true;
                } else {
                    return false;
                }
            default:
                break;

        }
        return false;

    }

    // only "." address separator
    public boolean isHost(String host) {
        /*char dot_separator = '.';
		int counter = 0;
		for (int i = 0; i < host.length(); i++) {
			if (host.charAt(i) == dot_separator)
				counter++;
		}
		if (counter == 3 && host.length() > 3)
			return true;
		else
			return false;*/
        return true;
    }

    public boolean iSDB_URL(String db_url) {
        if (db_url.contains(Messages.getString("Utils.at")) && db_url.contains(Messages.getString("Utils.colon"))) //$NON-NLS-1$ //$NON-NLS-2$
        {
            return true;
        } else {
            return false;
        }
    }

    /*	public static void testdescibeAllTables() {
		StorageManager manager = new StorageManager();
		
		ArrayList<DbTable> tables= manager.getTables();
		
		for(DbTable table:tables) {
			System.out.println(table.getName());
		}
		
	}*/
    public static void showError(String message) {
        JOptionPane.showOptionDialog(null, message, Messages.getString("Utils.err"), JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, new Object[]{}, null); //$NON-NLS-1$
    }

    public static void showCongrats(String message) {
        JOptionPane.showOptionDialog(null, message, Messages.getString("Utils.info"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{}, null); //$NON-NLS-1$

    }

    public static void showWarning(String message) {
        JOptionPane.showOptionDialog(null, message, "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{}, null); //$NON-NLS-1$

    }
//    public static String readFileIntoString(String filepath) {
//    	String Return="";
//    	try {
//            FileReader fileReader = new FileReader(new File(filepath));
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            String line = bufferedReader.readLine();
//            while (line != null) {
//            	Return+=line;
//            	line = bufferedReader.readLine();
//            }
//            bufferedReader.close();
//            return Return;
//    	}catch(Exception e) {
//    		e.printStackTrace();
//    		
//    		return Messages.getString("ViewManager.help_string");
//    	}
//            
//    }

    public static JScrollPane getScrollforText(JTextArea textArea_done) {
        JScrollPane jScrollPane = new JScrollPane(textArea_done);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setViewportView(textArea_done);
        return jScrollPane;
    }

    public static ArrayList<colPanel> handleEasyInsertionMMC_SITE(ArrayList<DbTable.dbCol> cols) {
        ArrayList<colPanel> colPanels = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            colPanel colPanel = new colPanel(cols.get(i).getName());
            colPanel.setcomboBoxSignOperatorsVisibility(false);
            // set default values
            switch (cols.get(i).getName()) {
                case "SITE_ID":
                    colPanel.setjTextField_colvalue("(select max(site_id)+1 from mmc_site)");
                    break;
                case "OWNER_SITE":
                    colPanel.setjTextField_colvalue("0");
                    break;
                case "ACTIVE":
                case "ALLOW_USING_11G":
                    colPanel.setjTextField_colvalue("Y");
                    break;
                case "UPDATE_USER_ID":
                    colPanel.setjTextField_colvalue("MMC");
                    break;
                case "UPDATE_SITE_ID":
                    colPanel.setjTextField_colvalue("2");
                    break;
                case "UPDATE_TIMESTAMP":
                    colPanel.setjTextField_colvalue("SYSDATE");
                    break;
                case "LOCATION_TIERS_USED":
                    colPanel.setjTextField_colvalue("5");
                    break;
                case "SITE_NAME_ABBR":
                case "LOCATION_FIELD_LABEL2":
                case "LOCATION_FIELD_LABEL3":
                case "LOCATION_FIELD_LABEL4":
                case "LOCATION_FIELD_LABEL5":
                case "LOCATION_FIELD_LABEL6":
                    colPanel.setjTextField_colvalue("NA");
                    break;

                case "ALLOW_3AT_REQUEST":
                case "USING_XREF_FIELD_UNIT":
                case "ALLOW_3AT_STATE_DIRECT":
                case "ALLOW_1AR_REQUEST":
                case "ALLOW_RECEIVE_RESTORATION":
                    colPanel.setjTextField_colvalue("N");
                    break;
                case "GEO_AREA_ID":
                    colPanel.setjTextField_colvalue("0المنطقة الشمالية العسكرية,1الجيش الثانى الميدانى,2القوات الجوية,3القوات البحرية,4قوات حرس الحدود,5المدفعية,6الجيش الثالث الميدانى,7المنطقة الغربية العسكرية,8المنطقة المركزية العسكرية,9المنطقة الجنوبية العسكرية,10قوات الدفاع الجوى,11قيادة وحدات الصاعقة,12اخرى,13قيادة قوات شرق القناه");
                    break;
                case "SITE_TYPE_ID":
                    colPanel.setjTextField_colvalue("0Root,1Branch of Service,2Department,3Computer Center,4فرع فني,5مراقبة عهدة,6ميناء,7مستودع/ ورشة,8قسم,9مجموعة,10Procurement Branch,11معامل,12فرع نظم,13وحدة,14فرع حرب الكترونية,15فرع طبي,16فرع كيما,17فرع ذخيرة,18فرع إشارة,19فرع تعيينات,20فرع مهمات,21فرع وقود,22فرع مركبات");
                    break;

            }
            colPanels.add(colPanel);
        }
        return colPanels;
    }
        public static ArrayList<colPanel> handleEasyInsertionFIELD_UNIT(ArrayList<DbTable.dbCol> cols) {
        ArrayList<colPanel> colPanels = new ArrayList<>();
        for (int i = 0; i < cols.size(); i++) {
            colPanel colPanel = new colPanel(cols.get(i).getName());
            colPanel.setcomboBoxSignOperatorsVisibility(false);
            // set default values
            switch (cols.get(i).getName()) {
                case "FIELD_UNIT_ID":
                    colPanel.setjTextField_colvalue("(select max(field_unit_id)+1 from field_unit)");
                    break;
                case "OWNER_SITE":
                    colPanel.setjTextField_colvalue("0");
                    break;
                case "ACTIVE":
                case "PRIMARY_UNIT":
                    colPanel.setjTextField_colvalue("Y");
                    break;
                case "UPDATE_USER_ID":
                    colPanel.setjTextField_colvalue("MMC");
                    break;
                case "UPDATE_SITE_ID":
                    colPanel.setjTextField_colvalue("2");
                    break;
                case "UPDATE_TIMESTAMP":
                    colPanel.setjTextField_colvalue("SYSDATE");
                    break;
                case "UNIT_ID_FIELD1":
                    colPanel.setjTextField_colvalue("1");
                    break;
                case "UNIT_ID_FIELD2":
                case "UNIT_ID_FIELD3":
                case "UNIT_ID_FIELD4":
                case "UNIT_ID_FIELD5":
                    colPanel.setjTextField_colvalue("NA");
                    break;

                case "GEO_AREA_ID":
                    colPanel.setjTextField_colvalue("0المنطقة الشمالية العسكرية,1الجيش الثانى الميدانى,2القوات الجوية,3القوات البحرية,4قوات حرس الحدود,5المدفعية,6الجيش الثالث الميدانى,7المنطقة الغربية العسكرية,8المنطقة المركزية العسكرية,9المنطقة الجنوبية العسكرية,10قوات الدفاع الجوى,11قيادة وحدات الصاعقة,12اخرى,13قيادة قوات شرق القناه");
                    break;
                case "MILITARY_STATION_ID":
                    colPanel.setjTextField_colvalue("7");
                    break;

            }
            colPanels.add(colPanel);
        }
        return colPanels;
    }
}
