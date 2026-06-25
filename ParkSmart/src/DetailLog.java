/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */

/**
 *
 * @author Dearis Mahendra
 */
public class DetailLog extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DetailLog.class.getName());
    
    private String kodeTarifTerima;
    
    /**
     * Creates new form DetailLog
     */
    public DetailLog(java.awt.Frame parent, boolean modal, String kodeCari) {
        super(parent, modal);
        initComponents();
        
        this.kodeTarifTerima = kodeCari;
        ambilDataLengkap();
    }
    
    private void ambilDataLengkap() {
    String sql = "SELECT * FROM riwayat_parkir WHERE kode_tarif = ?";
        
        try {
            java.sql.Connection conn = Koneksi.getConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeTarifTerima);
            java.sql.ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblStatusKendaraan.setText("Kendaraan Keluar");
                
                String tglKeluarDb = rs.getString("tanggal_keluar");
                String waktuKeluarStr = rs.getString("waktu_keluar");
                try {
                    java.util.Date dateKeluar = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tglKeluarDb);
                    String tglKeluarFix = new java.text.SimpleDateFormat("dd MMM yyyy").format(dateKeluar);
                    lblWaktuKeluar.setText(tglKeluarFix + " - " + waktuKeluarStr + " | Oleh " + rs.getString("petugas_keluar"));
                } catch (Exception e) {
                    lblWaktuKeluar.setText(tglKeluarDb + " - " + waktuKeluarStr + " | Oleh " + rs.getString("petugas_keluar"));
                }
                
                lblKode.setText(kodeTarifTerima);
                String platDb = rs.getString("plat_nomor");
                String platFix = platDb.replaceAll("^([A-Z]{1,2})(\\d+)([A-Z]{1,3})$", "$1 $2 $3");
                lblPlat.setText(platFix + " - " + rs.getString("jenis_kendaraan"));
                
                String tglMasukDb = rs.getString("tanggal_masuk");
                String waktuMasukStr = rs.getString("waktu_masuk");
                try {
                    java.util.Date dateMasuk = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tglMasukDb);
                    String tglMasukFix = new java.text.SimpleDateFormat("dd MMM yyyy").format(dateMasuk);
                    lblWaktuMasuk.setText(tglMasukFix + " - " + waktuMasukStr);
                } catch (Exception e) {
                    lblWaktuMasuk.setText(tglMasukDb + " - " + waktuMasukStr);
                }
                
                lblPetugasMasuk.setText(rs.getString("petugas_jaga"));
                
                int dendaVal = rs.getInt("denda");
                int totalVal = rs.getInt("total_tarif");
                int tarifDasarVal = totalVal - dendaVal;
                
                String durasiStr = rs.getString("durasi");
                String jenisKendaraan = rs.getString("jenis_kendaraan").toLowerCase();
                
                String angkaDurasi = durasiStr.replaceAll("[^0-9]", ""); 
                if (angkaDurasi.isEmpty()) angkaDurasi = "0";
                int jamParkir = Integer.parseInt(angkaDurasi);
                
                int tarifPerJam;
                if (jenisKendaraan.contains("motor")) {
                    tarifPerJam = 2000;
                } else if (jenisKendaraan.contains("mobil")) {
                    tarifPerJam = 5000;
                } else {
                    tarifPerJam = (jamParkir > 0) ? (tarifDasarVal / jamParkir) : tarifDasarVal;
                }
                
                if (dendaVal == 0 && tarifDasarVal > (tarifPerJam * jamParkir) && jamParkir > 0) {
                    tarifDasarVal = tarifPerJam * jamParkir;
                }
                
                lblTarif.setText("Rp " + tarifDasarVal);
                lblDurasi.setText(durasiStr);
                lblDenda.setText("Rp " + dendaVal);
                lblTotal.setText("Rp " + totalVal);
                
                detailTarif.setText("Tarif Dasar - Rp " + tarifPerJam + "/Jam");
                detailTarif1.setText(durasiStr + " = Rp " + tarifDasarVal);
                
                if (dendaVal > 0) {
                    detailDenda.setText("Denda - Karcis Hilang");
                    detailDenda1.setText("Rp " + dendaVal);
                } else {
                    detailDenda.setText("Denda - Tidak Ada");
                    detailDenda1.setText("Rp 0");
                }
                
                detailTarif.setVisible(true);
                detailTarif1.setVisible(true);
                detailDenda.setVisible(true);
                detailDenda1.setVisible(true);
                
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Data riwayat keluar tidak ditemukan!");
            }
            
            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Gagal load data detail riwayat keluar: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblWaktuKeluar = new javax.swing.JLabel();
        lblStatusKendaraan = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblKode = new javax.swing.JLabel();
        lblPlat = new javax.swing.JLabel();
        lblWaktuMasuk = new javax.swing.JLabel();
        lblPetugasMasuk = new javax.swing.JLabel();
        lblDurasi = new javax.swing.JLabel();
        lblTarif = new javax.swing.JLabel();
        lblDenda = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        detailDenda = new javax.swing.JLabel();
        detailTarif = new javax.swing.JLabel();
        detailTarif1 = new javax.swing.JLabel();
        detailDenda1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(153, 34));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("Detail Log Aktivitas");

        jButton1.setFont(new java.awt.Font("Broadway", 1, 12)); // NOI18N
        jButton1.setText("<");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logo P.png"))); // NOI18N
        jLabel3.setText("ParkSmart");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 43, -1, -1));

        lblWaktuKeluar.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lblWaktuKeluar.setText("21 Jun 2026 - 21:37:33 | Oleh Dearis");
        jPanel1.add(lblWaktuKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 100, -1, -1));

        lblStatusKendaraan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblStatusKendaraan.setText("Kendaraan Keluar");
        jPanel1.add(lblStatusKendaraan, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 101, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(153, 153, 153));
        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 123, 405, 5));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Kode Tarif");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 134, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Plat Nomer");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 156, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Waktu Masuk");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 178, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Petugas Masuk");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 200, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Tarif Dasar");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 134, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Durasi Parkir");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 156, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Denda");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 178, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Total Tarif");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(377, 200, -1, -1));

        lblKode.setText("MT-7270");
        jPanel1.add(lblKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 134, -1, -1));

        lblPlat.setText("B 1234 ABC - Motor");
        jPanel1.add(lblPlat, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 156, -1, -1));

        lblWaktuMasuk.setText("21 Jun 2026 - 19:27");
        jPanel1.add(lblWaktuMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 178, -1, -1));

        lblPetugasMasuk.setText("Khabid");
        jPanel1.add(lblPetugasMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, -1, -1));

        lblDurasi.setText("2 jam");
        jPanel1.add(lblDurasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 156, -1, -1));

        lblTarif.setText("Rp 4.000");
        jPanel1.add(lblTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 134, -1, -1));

        lblDenda.setText("Rp 25.000");
        jPanel1.add(lblDenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 178, -1, -1));

        lblTotal.setText("Rp 29.000");
        jPanel1.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(456, 200, -1, -1));

        jSeparator2.setBackground(new java.awt.Color(204, 204, 204));
        jSeparator2.setForeground(new java.awt.Color(51, 51, 51));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 410, 10));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ParkSmart.jpg"))); // NOI18N
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 630, 250));

        detailDenda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        detailDenda.setText("Denda - Karcis Hilang");
        jPanel1.add(detailDenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, -1, -1));

        detailTarif.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        detailTarif.setText("Tarif Dasar - Rp 5.000/Jam");
        jPanel1.add(detailTarif, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, -1, -1));

        detailTarif1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        detailTarif1.setText("6 jam = Rp 30.000");
        jPanel1.add(detailTarif1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, -1, -1));

        detailDenda1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        detailDenda1.setText("Rp 50.000");
        jPanel1.add(detailDenda1, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 290, -1, -1));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ParkSmart.jpg"))); // NOI18N
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 630, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DetailLog dialog = new DetailLog(new javax.swing.JFrame(), true, "MT-7270");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel detailDenda;
    private javax.swing.JLabel detailDenda1;
    private javax.swing.JLabel detailTarif;
    private javax.swing.JLabel detailTarif1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblDenda;
    private javax.swing.JLabel lblDurasi;
    private javax.swing.JLabel lblKode;
    private javax.swing.JLabel lblPetugasMasuk;
    private javax.swing.JLabel lblPlat;
    private javax.swing.JLabel lblStatusKendaraan;
    private javax.swing.JLabel lblTarif;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblWaktuKeluar;
    private javax.swing.JLabel lblWaktuMasuk;
    // End of variables declaration//GEN-END:variables
}
