/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.Utils;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author dotnet2
 */
public class colPanel extends javax.swing.JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * Creates new form colPanel
     */
    String colName;

    public colPanel(String ColName) {
        this.colName = ColName;
        initComponents();
    }

    public void setcomboBoxSignOperatorsVisibility(boolean visible) {
        jComboBox_STATIC_OPERATORS.setVisible(visible);
    }

    public colPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel_colname = new javax.swing.JLabel();
        jTextField_colvalue = new javax.swing.JTextField();
        jComboBox_STATIC_OPERATORS = new javax.swing.JComboBox<>();

        setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        setMaximumSize(new java.awt.Dimension(500, 100));

        jLabel_colname.setFont(new java.awt.Font("Tempus Sans ITC", 0, 14)); // NOI18N
        jLabel_colname.setText(colName);

        jComboBox_STATIC_OPERATORS.setModel(new javax.swing.DefaultComboBoxModel<>(Utils.STATIC_OPERATORS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel_colname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_STATIC_OPERATORS, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_colvalue, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_colvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_colname)
                    .addComponent(jComboBox_STATIC_OPERATORS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox_STATIC_OPERATORS;
    private javax.swing.JLabel jLabel_colname;
    private javax.swing.JTextField jTextField_colvalue;
    // End of variables declaration//GEN-END:variables

    public JComboBox<String> getjComboBox_STATIC_OPERATORS() {
        return jComboBox_STATIC_OPERATORS;
    }

    public JTextField getjTextField_colvalue() {
        return jTextField_colvalue;
    }

    public void setjTextField_colvalue(String jTextField_colvalue) {
        this.jTextField_colvalue.setText(jTextField_colvalue);
    }

}
