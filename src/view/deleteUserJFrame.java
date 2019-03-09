/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.Utils;
import java.util.ArrayList;
import model.User;

/**
 * delete user window , show all users and master user can delete from
 *
 * @author dotnet2
 */
public class deleteUserJFrame extends javax.swing.JFrame {

    
    private static final long serialVersionUID = 1L;
    User chosenUser = null;
    ArrayList<User> usersToDeleteFrom;

    /**
     * Creates new form deleteUserJFrame
     * @param users
     */
    public deleteUserJFrame(ArrayList<User> users) {
        this.usersToDeleteFrom = users;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList_users = new javax.swing.JList<>();
        jButton_ok = new javax.swing.JButton();
        jButton_cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("delete user");

        String[]user_strings = new String[usersToDeleteFrom.size()];
        for(int i=0;i<usersToDeleteFrom.size();i++){
            user_strings[i] = usersToDeleteFrom.get(i).toString();
        }
        jList_users.setModel(new javax.swing.AbstractListModel<String>() {
            /**
			 * current users 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = user_strings;
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList_users);

        jButton_ok.setText("OK");
        jButton_ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_okActionPerformed(evt);
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
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jButton_ok)
                .addGap(42, 42, 42)
                .addComponent(jButton_cancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ok)
                    .addComponent(jButton_cancel))
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelActionPerformed
        chosenUser = null;
        this.dispose();
    }//GEN-LAST:event_jButton_cancelActionPerformed

    private void jButton_okActionPerformed(java.awt.event.ActionEvent evt) {
        if (jList_users.getSelectedIndex() != -1) {
            this.chosenUser = usersToDeleteFrom.get(jList_users.getSelectedIndex());
            this.dispose();
        } else {
            Utils.showError("U have to choose user to be deleted or press canncel!");
        }
    }

    /**
     *
     * @return chosen user to be deleted
     */
    public User getChosenUser() {
        return chosenUser;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_cancel;
    private javax.swing.JButton jButton_ok;
    private javax.swing.JList<String> jList_users;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
