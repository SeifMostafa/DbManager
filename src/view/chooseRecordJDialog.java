/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.Utils;
import dbmanager.ViewManager;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author dotnet2
 */
public class chooseRecordJDialog extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[][] mData;
    private String[] mCols;
    private int choosenIndex = -1;

    public int getChoosenIndex() {
        return choosenIndex;
    }

    public void setChoosenIndex(int choosenIndex) {
        this.choosenIndex = choosenIndex;
    }

    /**
     * Creates new form chooseRecordJDialog
     */
    public chooseRecordJDialog(String[][] data, String[] cols) {
        this.mData = data;
        this.mCols = cols;

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_table_records = new javax.swing.ButtonGroup();
        jScrollPane_records_data = new javax.swing.JScrollPane();
        jTable_records_data = new javax.swing.JTable();
        jPanel_to_choose_record = new javax.swing.JPanel();
        jButton_ok = new javax.swing.JButton();
        jButton_cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Choose (Record) to keep");
        setMaximumSize(new java.awt.Dimension(500, 500));

        jTable_records_data.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTable_records_data.setModel(new javax.swing.table.DefaultTableModel(mData,mCols));
        jTable_records_data.setRowHeight(24);
        jTable_records_data.setAutoscrolls(true);
        jTable_records_data.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jScrollPane_records_data.setViewportView(jTable_records_data);

        jPanel_to_choose_record.setLayout(new javax.swing.BoxLayout(jPanel_to_choose_record, javax.swing.BoxLayout.Y_AXIS));
        for(int i=0;i<mData.length;i++){
            final int j=i;
            JRadioButton index = new JRadioButton();
            buttonGroup_table_records.add(index);
            index.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    choose_recordActionPerformed(j);
                }
            });
            index.setBorder(new EmptyBorder(3,3,3,3));
            jPanel_to_choose_record .add(index);
        }

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_to_choose_record, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane_records_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jButton_ok)
                .addGap(42, 42, 42)
                .addComponent(jButton_cancel)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane_records_data))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel_to_choose_record, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ok)
                    .addComponent(jButton_cancel))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_okActionPerformed
        if (choosenIndex == -1) {
            Utils.showError("U have to choose new item!");
        } else {
            ViewManager.setNewisExist(true);

            ViewManager.finishingUpdate.start();
            this.dispose();
        }
    }//GEN-LAST:event_jButton_okActionPerformed

    private void jButton_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelActionPerformed
        this.dispose();

    }//GEN-LAST:event_jButton_cancelActionPerformed
    private void choose_recordActionPerformed(int i) {
        setChoosenIndex(i);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_table_records;
    private javax.swing.JButton jButton_cancel;
    private javax.swing.JButton jButton_ok;
    private javax.swing.JPanel jPanel_to_choose_record;
    private javax.swing.JScrollPane jScrollPane_records_data;
    private javax.swing.JTable jTable_records_data;
    // End of variables declaration//GEN-END:variables
}
