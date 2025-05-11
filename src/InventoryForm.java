
import DAO.ProductDAO;
import DTO.Product;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class InventoryForm extends javax.swing.JFrame {
    private JTable tblInventory;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private JButton btnSearch, btnExport;
    
    public InventoryForm() {
        initUpdateComponents();
        loadInventory(null);
    }
    
    private void initUpdateComponents(){
        setTitle("Quan ly ton kho");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Search pannel
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Tim Kiem: "));
        txtSearch = new JTextField(20);
        searchPanel.add(txtSearch);
        btnSearch = new JButton("Tim");
        searchPanel.add(btnSearch);
        btnExport = new JButton("Xuat excel");
        searchPanel.add(btnExport);
        
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            loadInventory(keyword.isEmpty() ? null : keyword);
        });
        
        btnExport.addActionListener(e -> exportInventory());
        add(searchPanel, BorderLayout.NORTH);
        
        // Table
        tblInventory = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "ID", "Ten san pham", "Loai", "So Luong", "Gia ban", "han su dung"
        });
        tblInventory.setModel(model);
        
        // to mau do neu so luong < 5
        tblInventory.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            public Component getTableCellRenderComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                    int row, int colmn){
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, colmn);
                int qty = Integer.parseInt(table.getValueAt(row, 3).toString());
                if(qty < 5){
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
            
        });

        JScrollPane scrollPane = new JScrollPane(tblInventory);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadInventory(String keyword){
        model.setRowCount(0);
        List<Product> list = ProductDAO.searchProducts(keyword);
        for(Product p : list){
            Object[] row = new Object[]{
                p.getId(),
                p.getName(),
                p.getCategory(),
                p.getQuantity(),
                p.getSellPrice(),
                p.getExpiryDate()
            };
            model.addRow(row);
        }
    }
    
    private void exportInventory(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chon noi luu file ton kho");
        int userSelection = fileChooser.showSaveDialog(this);
        
        if(userSelection == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            if(!file.getName().endsWith(".csv")){
                file = new File(file.getAbsolutePath() + ".csv");
            }
            
            try (FileWriter fw = new FileWriter(file)) {
                for(int i = 0; i < model.getColumnCount(); i++){
                    fw.write(model.getColumnName(i) + ",");
                }
                fw.write("\n");
                for( int r = 0; r <model.getRowCount(); r++){
                    for(int c = 0; c < model.getColumnCount(); c++){
                        fw.write(model.getValueAt(r, c).toString() + ",");
                    }
                    fw.write("\n");
                }
                fw.flush();
                JOptionPane.showMessageDialog(this, "Xuat file than cong");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Loi khi xuat file" + e.getMessage());
            }
            
        }
    }
    
    

    /**
     * Creates new form InventoryForm
     */


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    /*
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    */
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
            java.util.logging.Logger.getLogger(InventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventoryForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
