/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.stok.barang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kury
 */
public class Transaksi extends javax.swing.JFrame {
    Long Total_transaks;
    Long jml_brangInt;
    Long HargaInt;
    Long kode_barangInt;
    ResultSet resultSet;
 
    void potongan(){
        HargaInt=(HargaInt*20)/100;
        Total_transaks = jml_brangInt*HargaInt;
    }
     
    
private DefaultTableModel tabelmodel;
    /**
     * Creates new form Transaksi
     */
    public Transaksi() {
        initComponents();
        
        tabelmodel = new DefaultTableModel();
        tabelbarang.setModel(tabelmodel);
        tabelTransaksi.setModel(tabelmodel);
        tabelmodel.addColumn("KODE TRANSAKSI");
        tabelmodel.addColumn("KODE BARANG");
        tabelmodel.addColumn("TANGGAL");
        tabelmodel.addColumn("JUMLAH");
        tabelmodel.addColumn("HARGA");
        tabelmodel.addColumn(" TOTAL HARGA");
             
        loadTabelBarang();
        selectTabelHargaJual();
        loadTabelTransaksi();
      
    }
    
    private void loadTabelBarang(){
        
        tabelmodel.getDataVector().removeAllElements();
        tabelmodel.fireTableDataChanged();
    Connection connection = DataBarang.getConnection();
    String sql ="select*from barang order by nama_barang";
    try{
        PreparedStatement statement= (PreparedStatement) connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        while(result.next()){
        Object[] o = new Object[5];
        o[0]=result.getLong("kode_barang");
        o[1]=result.getString("nama_barang");
        o[2]=result.getString("kategori");
        o[3]=result.getLong("harga");
        o[4]=result.getLong("stok");
        
         tabelmodel.addRow(o);
        }
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
private void loadTabelTransaksi(){
        
        tabelmodel.getDataVector().removeAllElements();
        tabelmodel.fireTableDataChanged();
    Connection connection = DataBarang.getConnection();
    String sql ="select*from transaksi order by kode_transaksi";
    try{
        PreparedStatement statement= (PreparedStatement) connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        while(result.next()){
        Object[] o = new Object[6];
        o[0]=result.getLong("kode_transaksi");
        o[1]=result.getString("kode_barang");
        o[2]=result.getString("tgl_transaksi");
        o[3]=result.getLong("jml_barang");
        o[4]=result.getLong("harga_barang");
        o[5]=result.getLong("tot_transaksi");
         tabelmodel.addRow(o);
        }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

private void selectTabelHargaJual(){
        Object header[] = {"Kode Barang","Nama Barang","Kategori","Harga","Stok"};
        DefaultTableModel defaultTable = new DefaultTableModel(null,header);
        tabelbarang.setModel(defaultTable);

        int baris = tabelbarang.getRowCount();
        for (int i = 0; i < baris; i++) {
            defaultTable.removeRow(i);
        }
 Connection connection = DataBarang.getConnection();
        String sql_select = "select kode_barang,nama_barang,kategori,harga,stok from barang where stok > 0";
        try {
            PreparedStatement statement= (PreparedStatement) connection.prepareStatement(sql_select);
        ResultSet result = statement.executeQuery();

            while(result.next()){
                String kodeBarang = result.getString(1);
                String namaBarang = result.getString(2);
                String Kategori = result.getString(3);
                String Harga = result.getString(4);
                String stok = result.getString(5);

                String kolom[] = {kodeBarang,namaBarang,Kategori,Harga,stok};
                defaultTable.addRow(kolom);
            }loadTabelBarang();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void loadTabelSearch(){
         String textsearchh= textsearch.getText();
          Long textsearchhInt;
           textsearchhInt = Long.parseLong(textsearchh);
        tabelmodel.getDataVector().removeAllElements();
        tabelmodel.fireTableDataChanged();
    Connection connection = DataBarang.getConnection();
    String sql ="select*from transaksi where kode_transaksi=?";
    try{
        PreparedStatement statement= (PreparedStatement) connection.prepareStatement(sql);
           statement.setLong(1,textsearchhInt);
        ResultSet result = statement.executeQuery();
        while(result.next()){
        Object[] o = new Object[6];
        o[0]=result.getLong("kode_transaksi");
        o[1]=result.getString("kode_barang");
        o[2]=result.getString("tgl_transaksi");
        o[3]=result.getLong("jml_barang");
        o[4]=result.getLong("harga_barang");
        o[5]=result.getLong("tot_transaksi");
         tabelmodel.addRow(o);
        }
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    private int cekStok(){
    int stok=0;
     Connection connection = DataBarang.getConnection();
     String kode_barang=(String) textkodebrng.getText();
     kode_barangInt= Long.parseLong(kode_barang);
    try{
        String total = "select stok from barang where kode_barang='"+kode_barangInt+"'";
         PreparedStatement statement = (PreparedStatement) connection.prepareStatement(total);
     resultSet = statement.executeQuery(total); 
     
     while(resultSet.next()){
     stok= Integer.parseInt(resultSet.getString(1));
     }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e.getMessage());
        System.out.println(""+ e.getMessage());}
    return stok;
    }
    
    private void UpdateStok(){
        Connection connection = DataBarang.getConnection();
        long stokBaru=0;
        String kode_barang=(String) textkodebrng.getText();
     kode_barangInt= Long.parseLong(kode_barang);
        try{
        stokBaru=cekStok()-jml_brangInt;
        String  total = "update barang set stok ='"+stokBaru+"'where kode_barang = '"+kode_barangInt+"'";
        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(total);
        statement.executeUpdate(total);
        }catch(Exception e){
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textkodebrng = new javax.swing.JTextField();
        tgl_transaksi = new javax.swing.JTextField();
        textjmlhbrang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        textsearch = new javax.swing.JTextField();
        textHarga = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        caribtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelTransaksi = new javax.swing.JTable();
        textTotHarga = new javax.swing.JTextField();
        btnbeli = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelbarang = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnulang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logotoko-picsay1.png"))); // NOI18N
        jLabel1.setText("TRANSAKSI PENJUALAN");

        jLabel3.setText("Kode Barang");

        jLabel4.setText("Tanggal");

        jLabel5.setText("Jumlah");

        textkodebrng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textkodebrngActionPerformed(evt);
            }
        });

        tgl_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgl_transaksiActionPerformed(evt);
            }
        });

        textjmlhbrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textjmlhbrangActionPerformed(evt);
            }
        });

        jLabel6.setText("Cari Kode Barang");

        textsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textsearchActionPerformed(evt);
            }
        });

        textHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textHargaActionPerformed(evt);
            }
        });

        jLabel7.setText("Harga");

        caribtn.setText("Cari");
        caribtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caribtnActionPerformed(evt);
            }
        });

        tabelTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelTransaksi);

        textTotHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textTotHargaActionPerformed(evt);
            }
        });

        btnbeli.setText("Bayar");
        btnbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbeliActionPerformed(evt);
            }
        });

        tabelbarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tabelbarang);

        jLabel9.setText("Tabel Barang");

        jLabel10.setText("Tabel Transaksi");

        jLabel11.setText("Total");

        jLabel8.setText("*Diskon 20% setiap pembelian barang diatas 10 ");

        btnulang.setText("Reset");
        btnulang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnulangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11)
                            .addGap(42, 42, 42)
                            .addComponent(textTotHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(108, 108, 108))
            .addGroup(layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tgl_transaksi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(textkodebrng, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(caribtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(37, 37, 37)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textjmlhbrang, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(textHarga)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnulang, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnbeli, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(184, 184, 184))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textkodebrng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(textHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tgl_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textjmlhbrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textsearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caribtn)
                    .addComponent(btnulang)
                    .addComponent(btnbeli))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel10)
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(textTotHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tgl_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgl_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tgl_transaksiActionPerformed

    private void textkodebrngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textkodebrngActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textkodebrngActionPerformed

    private void textsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textsearchActionPerformed

    private void textHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textHargaActionPerformed

    private void caribtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caribtnActionPerformed
        loadTabelSearch();// TODO add your handling code here:
    }//GEN-LAST:event_caribtnActionPerformed

    private void textTotHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textTotHargaActionPerformed
       
// TODO add your handling code here:
    }//GEN-LAST:event_textTotHargaActionPerformed

    private void tabelTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelTransaksiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelTransaksiMouseClicked

    private void textjmlhbrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textjmlhbrangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textjmlhbrangActionPerformed

    private void btnbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbeliActionPerformed
       String kode_barang = textkodebrng.getText();
       String jumlah_barang = textjmlhbrang.getText();
       String tgl_transaks = tgl_transaksi.getText();
       String harga = textHarga.getText();
        if(kode_barang.equals("")){
       JOptionPane.showMessageDialog(this, "Kolom Kode Barang tidak boleh dikosongkan");
       }else if(jumlah_barang.equals("")){
       JOptionPane.showMessageDialog(this, "Kolom Jumlah barang tidak boleh dikosongkan");
       }else if(tgl_transaks.equals("")){
       JOptionPane.showMessageDialog(this, "Kolom Tanggal tidak boleh dikosongkan");
       }else{
           Long kode_barangInt;
           try{
               kode_barangInt = Long.parseLong(kode_barang);
               jml_brangInt = Long.parseLong(jumlah_barang);
               HargaInt = Long.parseLong(harga);
           }catch(Exception e){
           JOptionPane.showMessageDialog(this, "Masukkan dengan angka");
           return;
           }
              try{
           Connection connection = DataBarang.getConnection();
       
           
           //kondisi jika membeli lebih dari 5
           if(jml_brangInt>10){
           potongan();
           }else{
            Total_transaks=jml_brangInt*HargaInt;
           }
           
           
           String query = "insert into transaksi (kode_barang,tgl_transaksi,jml_barang,harga_barang,tot_transaksi) values (?,?,?,?,?)";
           PreparedStatement statement = (PreparedStatement) connection.prepareStatement(query);
           statement.setString(1,kode_barang);
           statement.setString(2,tgl_transaks);
           statement.setString(3,jumlah_barang);
           statement.setLong(4,HargaInt);
           statement.setLong(5,Total_transaks);
           statement.executeUpdate();
           JOptionPane.showMessageDialog(this, "Data Berhasil di Simpan");
           UpdateStok();
             selectTabelHargaJual();
             loadTabelTransaksi();
           String totalstring=String.valueOf(Total_transaks);
        textTotHarga.setText(totalstring);
       }catch(Exception e){
           JOptionPane.showMessageDialog(this,"Data gagal disimpan");
           System.out.println(e);
       }
           
           
       }// TODO add your handling code here:
    }//GEN-LAST:event_btnbeliActionPerformed

    private void btnulangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnulangActionPerformed
        textkodebrng.setText("");
        tgl_transaksi.setText("");
        textjmlhbrang.setText("");
        textHarga.setText("");
        textsearch.setText(""); 
    
    }//GEN-LAST:event_btnulangActionPerformed

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbeli;
    private javax.swing.JButton btnulang;
    private javax.swing.JButton caribtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelTransaksi;
    private javax.swing.JTable tabelbarang;
    private javax.swing.JTextField textHarga;
    private javax.swing.JTextField textTotHarga;
    private javax.swing.JTextField textjmlhbrang;
    private javax.swing.JTextField textkodebrng;
    private javax.swing.JTextField textsearch;
    private javax.swing.JTextField tgl_transaksi;
    // End of variables declaration//GEN-END:variables
}
