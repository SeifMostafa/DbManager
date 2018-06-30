/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import java.util.concurrent.Semaphore;

import control.StorageManager;
import model.DbConfigs;
import view.LoginPage;

/**
 *
 * @author dotnet2
 */
public class DbManager {
    
    public static DbConfigs[] schemas;
    static Boolean readyToStart = false;
    public static Semaphore semaphore_stopMAIN;
    public static DbConfigs dbConfigs;
    private static DbManager dbManager;
    
    public static void main(String[] args) {
        
       try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (Messages.getString("MAIN.ui_look_win").equals(info.getName())) { //$NON-NLS-1$
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        dbManager = getInstance();
        StorageManager storageManager = new StorageManager();
        Controller controller = new Controller();
        ViewManager viewManager;
        try {
            semaphore_stopMAIN = new Semaphore(0);
            controller.configApp();
            semaphore_stopMAIN.acquire();

        } catch (InterruptedException e) {
            System.err.print(e.getMessage());
        }
        if (readyToStart) {
               // System.out.println("Hello!" +DbManager.schemas.length );
            if (DbManager.schemas == null) {
                DbManager.schemas = new DbConfigs[1];
                DbManager.schemas[0] = DbManager.dbConfigs;
            }
            String[] schema_strings = new String[DbManager.schemas.length];
            
            for (int i = 0; i < DbManager.schemas.length ; i++) {
                schema_strings[i] = DbManager.schemas[i].getLabel();
            }
            viewManager = new ViewManager(controller, storageManager.getTables(schema_strings[0]), schema_strings);
            viewManager.setVisible(true);

        } else {
            // close with thanks! message return; }
            return;
        }
       
    	
    }

    /*
	 * to create object (not to die - single ton)
     */
    private DbManager() {
        
    }
    /*
    get the object (instance)
     */
    public static DbManager getInstance() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }
//    public static ArrayList<String> readExcelSheet(){
//    	
//    }
}
