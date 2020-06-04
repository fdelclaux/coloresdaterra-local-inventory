package GUI;
import Database.Inventory;
 import static Database.Sales.sales;
 import java.awt.Color;
 import java.text.DateFormat;
 import java.text.DecimalFormat;
import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.util.ArrayList;
import java.util.Date;
 import javax.swing.JLabel;
 import javax.swing.JOptionPane;
 import javax.swing.RowFilter;
 import javax.swing.RowSorter;
 import javax.swing.SortOrder;
 import javax.swing.table.DefaultTableCellRenderer;
 import javax.swing.table.DefaultTableModel;
 import javax.swing.table.TableModel;
 import javax.swing.table.TableRowSorter;
import objects.Item;
import objects.PlateObject;
/**
 *
 * @author felipedelclaux
 */
public class ColoresDaTerraSales extends javax.swing.JFrame {

    /**
     * Creates new form ColoresDaTerraSales
     */
    public ColoresDaTerraSales(){
        initComponents();
        addPlatestoTable();
        order(SalesPageSort.getSelectedItem().toString());
        totals();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DisplayTable.getColumnModel().getColumn(5).setCellRenderer(leftRenderer);
        getContentPane().setBackground(Color.white);
    }
    
    public void order(String sort){ //Algorithm to order the table
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(DisplayTable.getModel());
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        int columnIndexToSort;
        switch(sort){
            case "Total Cost: Low-High":
                DisplayTable.setRowSorter(sorter);
                columnIndexToSort = 2;
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
                break;

            case "Total Cost: High-Low":
                DisplayTable.setRowSorter(sorter);
                columnIndexToSort = 2;
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
                break;

            case "Total Profit: Low-High":
                DisplayTable.setRowSorter(sorter);
                columnIndexToSort = 4;
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
                break;

            case "Total Profit: High-Low":
                DisplayTable.setRowSorter(sorter);
                columnIndexToSort = 4;
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
                break;

            case "Last Time Sold: Low-High":
                DisplayTable.setRowSorter(sorter);
                columnIndexToSort = 5;
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
                break;

            case "Last Time Sold: High-Low":
                DisplayTable.setRowSorter(sorter);
                columnIndexToSort = 5;
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.DESCENDING)); sorter.setSortKeys(sortKeys);
                sorter.sort();
                break;
        }
    }
     
    private void totals(){ //Algorithm to calculate all the visible totals in Sales page
        double totalCost = 0;
        double totalProfit =0;
        double totalRevenue = 0;
        for(int i = 0; i< Database.Inventory.inventory.size();i++){
            totalCost += Database.Inventory.inventory.get(i).getTotalCost();
        }
        for(int j = 0; j<sales.size();j++){
            totalProfit += sales.get(j).getTotalProfit();
            totalRevenue += sales.get(j).getTotalRevenue();
        }
        DecimalFormat df = new DecimalFormat("##.00");
        totCost.setText(df.format(totalCost));
        totProfit.setText(df.format(totalProfit));
        totRevenue.setText(df.format(totalRevenue));
     }
    
     private void addPlatestoTable(){
	DecimalFormat decimalformat = new DecimalFormat("##.00");
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	DefaultTableModel model = (DefaultTableModel) DisplayTable.getModel();
	Object rowData[] = new Object[7];
	for(int i = 0;i<sales.size();i++)
	{
            rowData[0] = sales.get(i).reference;
            rowData[1] = sales.get(i).description;
            rowData[2] = sales.get(i).plates.size();
            rowData[3] = Double.parseDouble(decimalformat.format(sales.get(i).getTotalCost()));
            rowData[4] = Double.parseDouble(decimalformat.format(sales.get(i).getTotalRevenue()));
            rowData[5] = Double.parseDouble(decimalformat.format(sales.get(i).getTotalProfit()));
            rowData[6] = dateFormat.format(sales.get(i).lastSold);
            model.addRow(rowData);
	}
    }

    private void SearchFilter(String search){
        final TableRowSorter<TableModel> sorter = new TableRowSorter<>(DisplayTable.getModel());
        DisplayTable.setRowSorter(sorter);
        RowFilter rf = RowFilter.regexFilter("(?i)"+search);
        sorter.setRowFilter(rf);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        DisplayTable = new javax.swing.JTable();
        SalesPageSort = new javax.swing.JComboBox<>();
        View = new javax.swing.JButton();
        Search = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        totCost = new javax.swing.JTextField();
        totProfit = new javax.swing.JTextField();
        totRevenue = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        DisplayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reference", "Description", "Total Sold", "Total Cost", "Total Revenue", "Total Profit", "Last Time Sold"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(DisplayTable);

        SalesPageSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Total Cost: Low-High", "Total Cost: High-Low", "Total Profit: Low-High", "Total Profit: High-Low", "Last Time Sold: Low-High", "Last Time Sold: High-Low" }));
        SalesPageSort.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SalesPageSortItemStateChanged(evt);
            }
        });

        View.setText("View");
        View.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewActionPerformed(evt);
            }
        });

        Search.setText("Search...");
        Search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchMouseClicked(evt);
            }
        });
        Search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                SearchKeyReleased(evt);
            }
        });

        jLabel1.setText("Total Cost of Inventory (€):");

        jLabel2.setText("Total Profit (€):");

        jLabel3.setText("Total Revenue (€):");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(totCost, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(totProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(totCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totRevenue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(totProfit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jMenu1.setText("File");

        jMenu4.setText("Save");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        jMenu1.add(jMenu4);

        jMenu5.setText("Quit");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenu1.add(jMenu5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Inventory");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Print");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SalesPageSort, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182)
                        .addComponent(Search))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(View)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalesPageSort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(View))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchMouseClicked
        Search.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_SearchMouseClicked

    private void ViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewActionPerformed
        if(DisplayTable.getSelectedRow() == -1){ // Check if any row is selected
            JOptionPane.showMessageDialog(this,
            "Please select a row before clicking view", "Error",
            JOptionPane.ERROR_MESSAGE);
        } 
        else{
            int r = DisplayTable.getSelectedRow();
            String ref = DisplayTable.getValueAt(r,0).toString(); int reference = Integer.parseInt(ref);
            int index = Database.Sales.findIndex(reference); objects.Item item = sales.get(index);
            new ViewInfoSales(item).setVisible(true);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_ViewActionPerformed

    private void SearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SearchKeyReleased
        String search = Search.getText();
        if(search.equals("")){
            order(SalesPageSort.getSelectedItem().toString()); 
        }
        else{ 
            SearchFilter(search);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_SearchKeyReleased

    private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
	Database.Inventory.lastSaved = new Date();
        boolean savedInv;
        boolean savedSal;
        savedInv = Database.Inventory.fileWriter(); 
        savedSal = Database.Sales.fileWriter(); 
        if(savedInv && savedSal){
            JOptionPane.showMessageDialog(this,
                "You have succesfully saved your changes!");
        }
        else if(!savedInv){
            JOptionPane.showMessageDialog(this,
                "Something has gone wrong when saving your changes made to the inventory, please try again",
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        else if(!savedSal){
            JOptionPane.showMessageDialog(this,
                "Something has gone wrong when saving your sales, please try again",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu4MouseClicked

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        new SaveCheck().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu5MouseClicked

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        this.dispose();
        new ColoresDaTerra().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2MouseClicked

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        new PrintSales().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu3MouseClicked

    private void SalesPageSortItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SalesPageSortItemStateChanged
        order(SalesPageSort.getSelectedItem().toString());        // TODO add your handling code here:
    }//GEN-LAST:event_SalesPageSortItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ColoresDaTerraSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ColoresDaTerraSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ColoresDaTerraSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ColoresDaTerraSales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ColoresDaTerraSales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable DisplayTable;
    private javax.swing.JComboBox<String> SalesPageSort;
    private javax.swing.JTextField Search;
    private javax.swing.JButton View;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField totCost;
    private javax.swing.JTextField totProfit;
    private javax.swing.JTextField totRevenue;
    // End of variables declaration//GEN-END:variables
}
