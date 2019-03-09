/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.AuthManager;
import control.StorageManager;
import control.Utils;
import control.Utils.SignOperator;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.User;
import model.User.PERMISSION;

/**
 * Creates new form LoginFrame to be login page
 * @author dotnet2
 */
public class LoginPage extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    boolean firstTime;
    boolean done = false;

    /**
     *  if login is successfully done
     * @return true if logging on process is done
     */
    public boolean isDone() {
        return done;
    }


    /**
     *
     * @param done to be set as done
     */

    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     *
     * @param isFirstTime to indicate if this is the first time this app is opened or not
     */
    public LoginPage(boolean isFirstTime) {
        this.firstTime = isFirstTime;
        initComponents();

        setLocation(getToolkit().getScreenSize().width / 4, getToolkit().getScreenSize().height / 4);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_title = new javax.swing.JLabel();
        jLabel_username = new javax.swing.JLabel();
        jLabel_password = new javax.swing.JLabel();
        jLabel_serial_num = new javax.swing.JLabel();
        jTextField_username = new javax.swing.JTextField();
        jTextField_serial_num = new javax.swing.JTextField();
        jButton_enter = new javax.swing.JButton();
        jButton_cancel = new javax.swing.JButton();
        jPasswordField = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");

        if(firstTime){
            jLabel_title.setText("Sign in");
        }else{
            jLabel_title.setText("Login");
        }

        jLabel_username.setText("Username");

        jLabel_password.setText("Password");

        if(!firstTime){
            jLabel_serial_num.setVisible(false);
        }
        jLabel_serial_num.setText("Serial number");

        if(!firstTime){
            jTextField_serial_num.setVisible(false);
        }

        jButton_enter.setText("Enter");
        jButton_enter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_enterActionPerformed(evt);
            }
        });

        jButton_cancel.setText("Cancel");
        jButton_cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_title))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_username)
                            .addComponent(jLabel_password)
                            .addComponent(jLabel_serial_num))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_username)
                            .addComponent(jPasswordField)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jButton_enter)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_cancel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField_serial_num))))
                .addGap(48, 48, 48))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_title)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_username)
                    .addComponent(jTextField_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_password)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_serial_num)
                    .addComponent(jTextField_serial_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_enter)
                    .addComponent(jButton_cancel))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_enterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_enterActionPerformed
        @SuppressWarnings("deprecation")
        String username = jTextField_username.getText().toString(), password = jPasswordField.getText().toString();
        if (!username.equals(Messages.getString("LoginPage.empty_string")) //$NON-NLS-1$
                && !password.equals(Messages.getString("LoginPage.empty_string"))) { //$NON-NLS-1$
            if (!username.contains(Messages.getString("LoginPage.space_string")) //$NON-NLS-1$
                    && Utils.checkLength(password, new Utils().PASSWORD_LENGTH, SignOperator.BIGGER)) {
                AuthManager authManager = AuthManager.getInstance();
                StorageManager storageManager = new StorageManager();
                if (firstTime) {
                    String lic_code = jTextField_serial_num.getText().toString();
                    // storageManager. get codes
                    // authManager. auth
                    if (lic_code.equals(Messages.getString("LoginPage.55"))) { //$NON-NLS-1$
                        User user = new User(username, password);
                        user.setPermissions(new PERMISSION[]{PERMISSION.MASTER});

                        authManager.setUser(user);

                        storageManager.saveUserData(user);
                        setDone(true);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(new JTextField(),
                                Messages.getString("LoginPage.err_msg_lic_code_wrong"), //$NON-NLS-1$
                                Messages.getString("LoginPage.err"), (Integer) 0); //$NON-NLS-1$
                    }

                } else {

                    ArrayList<User> users = storageManager.getUsers();
                    User user = new User(username, password);
                    authManager.setUser(user);
                    for (User u : users) {
                        if (authManager.auth(u.getUsername(), u.getPassword())) {
                            authManager.setUser(u);
                            setDone(true);
                            this.dispose();
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(new JTextField(),
                            Messages.getString("LoginPage.err_msg_username_password_not_matched"), //$NON-NLS-1$
                            Messages.getString("LoginPage.err"), //$NON-NLS-1$
                            (Integer) 0);
                }
            } else {
                JOptionPane.showMessageDialog(new JTextField(),
                        Messages.getString("LoginPage.err_msg_username_password_wrong_fornat") //$NON-NLS-1$
                        + new Utils().PASSWORD_LENGTH + Messages.getString("LoginPage.!"), //$NON-NLS-1$
                        Messages.getString("LoginPage.err"), (Integer) 0); //$NON-NLS-1$
            }
        } else {
            JOptionPane.showMessageDialog(new JTextField(),
                    Messages.getString("LoginPage.err_msg_username_password_cannot_left_empty"), //$NON-NLS-1$
                    Messages.getString("LoginPage.err"), //$NON-NLS-1$
                    (Integer) 0);
        }

    }//GEN-LAST:event_jButton_enterActionPerformed

    private void jButton_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelActionPerformed
        setDone(false);

        this.dispose();
        return;
    }//GEN-LAST:event_jButton_cancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_cancel;
    private javax.swing.JButton jButton_enter;
    private javax.swing.JLabel jLabel_password;
    private javax.swing.JLabel jLabel_serial_num;
    private javax.swing.JLabel jLabel_title;
    private javax.swing.JLabel jLabel_username;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextField_serial_num;
    private javax.swing.JTextField jTextField_username;
    // End of variables declaration//GEN-END:variables
}
