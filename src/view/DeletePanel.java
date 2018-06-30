/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import model.DbTable;

/**
 *
 * @author dotnet2
 */
public class DeletePanel extends javax.swing.JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private DbTable selectedTable;
    ArrayList<DbTable> mTables;
    String[] table_names;

    private ArrayList<colPanel> colPanels;

    /**
     * Creates new form DeletePanel
     *
     * @param tables
     */
    public DeletePanel(ArrayList<DbTable> tables) {
        this.mTables = tables;
        int catalog_item_most_common_index = 0;
        table_names = new String[mTables.size()];
        for (int i = 0; i < mTables.size(); i++) {
            table_names[i] = mTables.get(i).getName();
            if (mTables.get(i).getName().equals("CATALOG_ITEM")) {
                catalog_item_most_common_index = i;
            }
        }

        colsPanel = new JPanel();

        initComponents();

        jLabel_where.setText(Messages.getString("DeletePanel.where")); //$NON-NLS-1$
        jLabel_where.setVisible(false);
        this.setAutoscrolls(true);

        this.setPreferredSize(
                new Dimension(getToolkit().getScreenSize().width - 100, getToolkit().getScreenSize().height - 200));

        jComboBox_table_names.setSelectedIndex(catalog_item_most_common_index);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup_cond_union = new javax.swing.ButtonGroup();
        jLabel_table_name = new javax.swing.JLabel();
        jComboBox_table_names = new javax.swing.JComboBox<>();
        jPanel_colsPanel = new javax.swing.JPanel();
        jPanel_mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        colsPanel = new javax.swing.JPanel();
        jLabel_cond_union = new javax.swing.JLabel();
        jRadioButton_or = new javax.swing.JRadioButton();
        jRadioButton_and = new javax.swing.JRadioButton();
        jLabel_where = new javax.swing.JLabel();

        jLabel_table_name.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel_table_name.setText("from table name");

        jComboBox_table_names.setModel(new javax.swing.DefaultComboBoxModel<>(table_names));
        jComboBox_table_names.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_table_namesActionPerformed(evt);
            }
        });

        jPanel_mainPanel.setBorder(new javax.swing.border.MatteBorder(null));
        jPanel_mainPanel.setAutoscrolls(true);
        jPanel_mainPanel.setLayout(new java.awt.GridLayout(1, 0));

        colsPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        colsPanel.setAutoscrolls(true);
        colsPanel.setLayout(new javax.swing.BoxLayout(colsPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(colsPanel);

        jPanel_mainPanel.add(jScrollPane1);

        jLabel_cond_union.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel_cond_union.setText("Condition uniuon:");

        jRadioButton_or.setText("OR");

        jRadioButton_and.setText("AND");

        jLabel_where.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel_where.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_where.setText("Where");
        jLabel_where.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttonGroup_cond_union.add(jRadioButton_and);
        buttonGroup_cond_union.add(jRadioButton_or);
        jRadioButton_or.setSelected(true);

        javax.swing.GroupLayout jPanel_colsPanelLayout = new javax.swing.GroupLayout(jPanel_colsPanel);
        jPanel_colsPanel.setLayout(jPanel_colsPanelLayout);
        jPanel_colsPanelLayout.setHorizontalGroup(
            jPanel_colsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_colsPanelLayout.createSequentialGroup()
                .addGroup(jPanel_colsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_colsPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel_cond_union)
                        .addGap(16, 16, 16)
                        .addComponent(jRadioButton_or)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton_and))
                    .addComponent(jPanel_mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
                .addGap(46, 46, 46))
            .addGroup(jPanel_colsPanelLayout.createSequentialGroup()
                .addComponent(jLabel_where)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_colsPanelLayout.setVerticalGroup(
            jPanel_colsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_colsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_where, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_colsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_cond_union)
                    .addComponent(jRadioButton_or)
                    .addComponent(jRadioButton_and))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel_table_name)
                .addGap(18, 18, 18)
                .addComponent(jComboBox_table_names, 0, 454, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jPanel_colsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_table_name)
                    .addComponent(jComboBox_table_names, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(352, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(76, 76, 76)
                    .addComponent(jPanel_colsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox_table_namesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_table_namesActionPerformed
        if (!jLabel_where.isVisible()) {
            jLabel_where.setVisible(true);
        }
        for (int i = 0; i < mTables.size(); i++) {
            if (mTables.get(i).getName()
                    .equals(((String) jComboBox_table_names.getSelectedItem()))) {
                setSelectedTable(mTables.get(i));
                colSetup();
            }
        }
    }//GEN-LAST:event_jComboBox_table_namesActionPerformed

    /*
 * depending on chosen table , draw cols
     */
    private void colSetup() {

        colsPanel.removeAll();
        colPanels = new ArrayList<>();

        for (int i = 0; i < selectedTable.getCols().size(); i++) {
            colPanel colPanel = new colPanel(selectedTable.getCols().get(i).getName());
            colPanels.add(colPanel);
            colsPanel.add(colPanel);
        }
        colsPanel.setAutoscrolls(true);

        revalidate();
        repaint();
    }

    public DbTable getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(DbTable selectedTable) {
        this.selectedTable = selectedTable;
    }

    public ArrayList<colPanel> getColPanels() {
        return colPanels;
    }

    public void setColPanels(ArrayList<colPanel> colPanels) {
        this.colPanels = colPanels;
    }

    public String getCond_where_multiple_cols() {
        if (jRadioButton_or.isSelected()) {
            return Messages.getString("SearchPanel.or"); //$NON-NLS-1$ //$NON-NLS-2$
        } else if (jRadioButton_and.isSelected()) {
            return Messages.getString("SearchPanel.and"); //$NON-NLS-1$
        } else {
            return Messages.getString("SearchPanel.or"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_cond_union;
    private javax.swing.JPanel colsPanel;
    private javax.swing.JComboBox<String> jComboBox_table_names;
    private javax.swing.JLabel jLabel_cond_union;
    private javax.swing.JLabel jLabel_table_name;
    private javax.swing.JLabel jLabel_where;
    private javax.swing.JPanel jPanel_colsPanel;
    private javax.swing.JPanel jPanel_mainPanel;
    private javax.swing.JRadioButton jRadioButton_and;
    private javax.swing.JRadioButton jRadioButton_or;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}