/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import javax.swing.JCheckBox;
import model.DbTable;

/**
 * to show tables ::GUI 
 * used in addUser so master user determine which tables to be available for user
 * @author dotnet2
 */
public class TablesJDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private ArrayList<JCheckBox> checkBox_tables2accessed;
    private boolean done = true;

    /**
     *
     * @return if selecting tables process is successfully done
     */
    public boolean isDone() {
        return done;
    }
    private ArrayList<DbTable> tables2selectFrom;

    /**
     * Creates new form TablesJDialog
     * @return 
     */
    @SuppressWarnings("deprecation")
	public ArrayList<DbTable> getAccessedTables() {
        ArrayList<DbTable> tables = new ArrayList<>();
        checkBox_tables2accessed.forEach((box) -> {
            tables2selectFrom.stream().filter((dbTable) -> (dbTable.getName().equals(box.getLabel())) && box.isSelected()).forEachOrdered((dbTable) -> {
                tables.add(dbTable);
            });
        });
        
        return tables;
    }

    TablesJDialog(ArrayList<DbTable> mTables) {
        if (!mTables.isEmpty()) {
            tables2selectFrom = mTables;
            checkBox_tables2accessed = new ArrayList<>();
            mTables.stream().map((table) -> new JCheckBox(table.getName())).forEachOrdered((box) -> {
                checkBox_tables2accessed.add(box);
            });
        }
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane_tables = new javax.swing.JScrollPane();
        jPanel_tables = new javax.swing.JPanel();
        jButton_ok = new javax.swing.JButton();
        jButton_cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tables");
        setMaximumSize(new java.awt.Dimension(500, 500));

        jScrollPane_tables.setMaximumSize(new java.awt.Dimension(500, 500));

        jPanel_tables.setAutoscrolls(true);
        jPanel_tables.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel_tables.setLayout(new javax.swing.BoxLayout(jPanel_tables, javax.swing.BoxLayout.Y_AXIS));
        for(JCheckBox box:checkBox_tables2accessed){
            jPanel_tables.add(box);
        }
        jScrollPane_tables.setViewportView(jPanel_tables);

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
                .addContainerGap(93, Short.MAX_VALUE)
                .addComponent(jButton_ok)
                .addGap(36, 36, 36)
                .addComponent(jButton_cancel)
                .addGap(121, 121, 121))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_tables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane_tables, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ok)
                    .addComponent(jButton_cancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_okActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton_okActionPerformed

    private void jButton_cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cancelActionPerformed
        done = false;
        this.dispose();
    }//GEN-LAST:event_jButton_cancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_cancel;
    private javax.swing.JButton jButton_ok;
    private javax.swing.JPanel jPanel_tables;
    private javax.swing.JScrollPane jScrollPane_tables;
    // End of variables declaration//GEN-END:variables
}
