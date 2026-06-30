/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author Dearis Mahendra
 */
public class Laporan extends javax.swing.JPanel {

    /**
     * Creates new form LaporanKeuangan
     */
    public Laporan() {
        initComponents();
        dcTanggal.setDateFormatString("dd-MM-yyyy");
        dcTanggal.setDate(new java.util.Date());
        
        harian.setVisible(false);
        bulanan.setVisible(false);
        
        txtCari.setText("Cari...");
        txtCari.setForeground(new java.awt.Color(153, 153, 153));
        
        javax.swing.JTextField dateEditor = (javax.swing.JTextField) dcTanggal.getDateEditor().getUiComponent();
        dateEditor.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (dateEditor.getText().trim().isEmpty()) {
                    dcTanggal.setDate(null);

                    javax.swing.table.DefaultTableModel modelKosong = (javax.swing.table.DefaultTableModel) tabelLaporan.getModel();
                    modelKosong.setRowCount(0);

                    scrollLaporan.setVisible(false);
                } else {
                    tampilkanDataLaporan();
                }
            }
        });
        tampilkanDataLaporan();
    }
    
    private String konversiTglIndo(String tglDb) {
        if (tglDb == null || tglDb.isEmpty()) {
            return "";
        }
        try {
            java.util.Date dateObj = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(tglDb);
            return new java.text.SimpleDateFormat("dd-MM-yyyy").format(dateObj);
        } catch (Exception e) {
            return tglDb;
        }
    }
    
    private String konversiPlatFormat(String platInput) {
        if (platInput == null || platInput.isEmpty()) {
            return "";
        }
        String platFormat = platInput.trim().toUpperCase().replace(" ", "");
        if (platFormat.matches("^[A-Z]{1,2}\\d+[A-Z]{1,3}$")) {
            platFormat = platFormat.replaceAll("^([A-Z]{1,2})(\\d+)([A-Z]{1,3})$", "$1 $2 $3");
        }
        return platFormat;
    }
    
    public void tampilkanDataLaporan() {
        String pilihan = cbJenisLaporan.getSelectedItem().toString();
        String keyword = txtCari.getText().trim();    
        
        if (pilihan.contains("Pendapatan")) {
            txtCari.setEnabled(false);
            txtCari.setText("Nonaktif..."); 
            txtCari.setForeground(new java.awt.Color(153, 153, 153));
            keyword = ""; 
        } else {
            txtCari.setEnabled(true);
            if (keyword.equals("Nonaktif...") || keyword.isEmpty()) {
                txtCari.setText("Cari...");
                txtCari.setForeground(new java.awt.Color(153, 153, 153));
                keyword = "";
            }
        }
        
        boolean cari = !keyword.isEmpty() && !keyword.equals("Cari...") && !keyword.equals("Nonaktif...");
        
        if (!cari && (pilihan.equals("Jenis Laporan") || pilihan.isEmpty())) {
            scrollLaporan.setVisible(false);
            return;
        }
        
        String tglPilihan = "";
        if (dcTanggal.getDate() != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            tglPilihan = sdf.format(dcTanggal.getDate());
        } else {
            tglPilihan = java.time.LocalDate.now().toString();
        }
        
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        tabelLaporan.setModel(model);
        String sql = "";
        
        if (keyword.equals("Cari...")) {
            keyword = "";
        }
        
        if (pilihan.contains("Masuk")) {
            model.addColumn("Kode Tarif");
            model.addColumn("Plat Nomor");
            model.addColumn("Jenis Kendaraan");
            model.addColumn("Tanggal Masuk");
            model.addColumn("Jam Masuk");
            
            if (cari) {
                sql = "SELECT l.kode_tarif, k.plat_nomor, k.jenis_kendaraan, l.tanggal AS tanggal_masuk, l.jam AS jam_masuk "
                    + "FROM log_aktivitas l LEFT JOIN data_kendaraan k ON l.kode_tarif = k.kode_tarif "
                    + "WHERE l.status = 'Kendaraan Masuk' AND (l.kode_tarif LIKE ? OR k.plat_nomor LIKE ?)";
            } else {
                sql = "SELECT l.kode_tarif, COALESCE(k.plat_nomor, '-') AS plat_nomor, COALESCE(k.jenis_kendaraan, '-') AS jenis_kendaraan, l.tanggal AS tanggal_masuk, l.jam AS jam_masuk "
                    + "FROM log_aktivitas l LEFT JOIN data_kendaraan k ON l.kode_tarif = k.kode_tarif "
                    + "WHERE l.status = 'Kendaraan Masuk' AND l.tanggal = ?";
            }
            
        } else if (pilihan.contains("Keluar")) {
            model.addColumn("Kode Tarif");
            model.addColumn("Plat Nomor");
            model.addColumn("Jenis Kendaraan");
            model.addColumn("Tanggal Keluar");
            model.addColumn("Jam Keluar");
            model.addColumn("Durasi");
            
            if (cari) {
                sql = "SELECT kode_tarif, plat_nomor, jenis_kendaraan, tanggal_keluar, jam_keluar, durasi FROM riwayat_parkir "
                    + "WHERE status = 'Keluar' AND (kode_tarif LIKE ? OR plat_nomor LIKE ?)";
            } else {
                sql = "SELECT kode_tarif, plat_nomor, jenis_kendaraan, tanggal_keluar, jam_keluar, durasi FROM riwayat_parkir "
                    + "WHERE status = 'Keluar' AND tanggal_keluar = ?";
            }
            
        } else if (pilihan.contains("Kendaraan Karcis Hilang")) {
            model.addColumn("Kode Tarif");
            model.addColumn("Plat Nomor");
            model.addColumn("Jenis Kendaraan");
            model.addColumn("Tanggal Masuk");
            model.addColumn("Tanggal Keluar");
            model.addColumn("Denda Karcis");
            
            if (cari) {
                sql = "SELECT kode_tarif, plat_nomor, jenis_kendaraan, tanggal_masuk, tanggal_keluar, denda FROM riwayat_parkir "
                    + "WHERE denda > 0 AND (kode_tarif LIKE ? OR plat_nomor LIKE ?)";
            } else {
                sql = "SELECT kode_tarif, plat_nomor, jenis_kendaraan, tanggal_masuk, tanggal_keluar, denda FROM riwayat_parkir "
                    + "WHERE denda > 0 AND tanggal_keluar = ?";
            }
            
        } else if (pilihan.contains("Kendaraan Hilang")) {
            model.addColumn("Kode Karcis");
            model.addColumn("Plat Nomor");
            model.addColumn("Jenis");
            model.addColumn("Warna");
            model.addColumn("Merk");
            model.addColumn("No Telp");
            model.addColumn("Tanggal Lapor");
            model.addColumn("Jam Lapor");
            model.addColumn("Status");
            
            if (cari) {
                sql = "SELECT kode_tarif, plat_nomor, jenis_kendaraan, warna, merk, no_telp, tanggal_lapor, jam_lapor, status FROM kendaraan_hilang "
                    + "WHERE kode_tarif LIKE ? OR plat_nomor LIKE ?";
            } else {
                sql = "SELECT kode_tarif, plat_nomor, jenis_kendaraan, warna, merk, no_telp, tanggal_lapor, jam_lapor, status FROM kendaraan_hilang "
                    + "WHERE tanggal_lapor = ?";
            }
            
        } else if (pilihan.contains("Pendapatan")) {
            model.addColumn("Kode Tarif");
            model.addColumn("Tanggal Keluar");
            model.addColumn("Jam Keluar");
            model.addColumn("Tarif Dasar");
            model.addColumn("Denda Karcis");
            model.addColumn("Sub Total");
            
            sql = "SELECT kode_tarif, tanggal_keluar, Jam_keluar, total_tarif, denda, (total_tarif + denda) AS sub_total "
                + "FROM riwayat_parkir WHERE status = 'Keluar' AND tanggal_keluar = ?";
        }

        try {
            java.sql.Connection conn = Koneksi.getConnection();
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            
            if (cari) {
                ps.setString(1, "%" + keyword + "%");
                ps.setString(2, "%" + keyword + "%");
            } else {
                ps.setString(1, tglPilihan);
            }
            
            java.sql.ResultSet rs = ps.executeQuery();            
            while (rs.next()) {                
                if (pilihan.contains("Masuk")) {
                    model.addRow(new Object[]{
                        rs.getString("kode_tarif"), 
                        konversiPlatFormat(rs.getString("plat_nomor")),
                        rs.getString("jenis_kendaraan"),
                        konversiTglIndo(rs.getString("tanggal_masuk")),
                        rs.getString("jam_masuk")
                    });
                } else if (pilihan.contains("Keluar")) {
                    model.addRow(new Object[]{
                        rs.getString("kode_tarif"),
                        konversiPlatFormat(rs.getString("plat_nomor")),
                        rs.getString("jenis_kendaraan"),
                        konversiTglIndo(rs.getString("tanggal_keluar")),
                        rs.getString("jam_keluar"),
                        rs.getString("durasi")
                    });
                } else if (pilihan.contains("Karcis Hilang")) {
                    model.addRow(new Object[]{
                        rs.getString("kode_tarif"),
                        konversiPlatFormat(rs.getString("plat_nomor")),
                        rs.getString("jenis_kendaraan"),
                        konversiTglIndo(rs.getString("tanggal_masuk")),
                        konversiTglIndo(rs.getString("tanggal_keluar")),
                        "Rp " + rs.getInt("denda")
                    });
                } else if (pilihan.contains("Kendaraan Hilang")) {
                    model.addRow(new Object[]{
                        rs.getString("kode_tarif"), 
                        rs.getString("plat_nomor"),
                        rs.getString("jenis_kendaraan"), 
                        rs.getString("warna"),
                        rs.getString("merk"),
                        rs.getString("no_telp"),
                        konversiTglIndo(rs.getString("tanggal_lapor")),
                        rs.getString("jam_lapor"),
                        rs.getString("status")
                    });
                } else if (pilihan.contains("Pendapatan")) {
                    model.addRow(new Object[]{
                        rs.getString("kode_tarif"),
                        konversiTglIndo(rs.getString("tanggal_keluar")),
                        rs.getString("jam_keluar"),
                        "Rp " + rs.getInt("total_tarif"),
                        "Rp " + rs.getInt("denda"),
                        "Rp " + rs.getInt("sub_total")
                    });
                }
            }
            
            tabelLaporan.setModel(model);
            scrollLaporan.setVisible(true);
            
            if (pilihan.contains("Pendapatan")) {
                String sqlHari = "SELECT SUM(total_tarif + denda) AS total FROM riwayat_parkir WHERE status = 'Keluar' AND tanggal_keluar = ?";
                java.sql.PreparedStatement psHari = conn.prepareStatement(sqlHari);
                
                psHari.setString(1, tglPilihan);
                
                java.sql.ResultSet rsHari = psHari.executeQuery();              
                java.text.DecimalFormat kursIndo = (java.text.DecimalFormat) java.text.DecimalFormat.getIntegerInstance();
                java.text.DecimalFormatSymbols formatRp = new java.text.DecimalFormatSymbols();
                
                formatRp.setGroupingSeparator('.'); 
                kursIndo.setDecimalFormatSymbols(formatRp);
                if(rsHari.next()){
                    int hariIni = rsHari.getInt("total");
                    harian.setText("Pendapatan Hari Ini: Rp " + kursIndo.format(hariIni));
                }
                
                String bulanPilihan = tglPilihan.substring(0, 7); 
                String sqlBulan = "SELECT SUM(total_tarif + denda) AS total FROM riwayat_parkir WHERE status = 'Keluar' AND tanggal_keluar LIKE ?";
                
                java.sql.PreparedStatement psBulan = conn.prepareStatement(sqlBulan);
                
                psBulan.setString(1, bulanPilihan + "%");
                java.sql.ResultSet rsBulan = psBulan.executeQuery();
                if(rsBulan.next()){
                    int bulanIni = rsBulan.getInt("total");
                    bulanan.setText("Pendapatan Bulan Ini: Rp " + kursIndo.format(bulanIni));
                }
                
                harian.setVisible(true);
                bulanan.setVisible(true);
                rsHari.close(); psHari.close(); rsBulan.close(); psBulan.close();
            } else {
                harian.setVisible(false);
                bulanan.setVisible(false);
            }
            
            scrollLaporan.revalidate();
            scrollLaporan.repaint();
            this.revalidate();
            this.repaint();
            
            rs.close(); ps.close();
        } catch (Exception e) {
            System.out.println("Gagal load database laporan: " + e.getMessage());
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
        cbJenisLaporan = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        scrollLaporan = new javax.swing.JScrollPane();
        tabelLaporan = new javax.swing.JTable();
        harian = new javax.swing.JLabel();
        bulanan = new javax.swing.JLabel();
        btnKembali = new javax.swing.JButton();
        dcTanggal = new com.toedter.calendar.JDateChooser();
        btnEdit = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel1ComponentShown(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setBackground(new java.awt.Color(204, 255, 255));
        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("Laporan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(444, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(441, 441, 441))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cbJenisLaporan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Jenis Laporan", "Kendaraan Masuk", "Kendaraan Keluar", "Kendaraan Karcis Hilang", "Kendaraan Hilang", "Pendapatan Keuangan" }));
        cbJenisLaporan.addActionListener(this::cbJenisLaporanActionPerformed);

        tabelLaporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        scrollLaporan.setViewportView(tabelLaporan);

        harian.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        harian.setText("Pendapatan Harian: Rp 0");

        bulanan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bulanan.setText("Pendapatan Bulanan: Rp 0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(harian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bulanan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(scrollLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(harian)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bulanan)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        btnKembali.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKembali.setText("Kembali");
        btnKembali.addActionListener(this::btnKembaliActionPerformed);

        dcTanggal.setDateFormatString("yyyy-MM-dd");
        dcTanggal.addPropertyChangeListener(this::dcTanggalPropertyChange);

        btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        txtCari.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCariFocusLost(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cbJenisLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnKembali)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbJenisLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dcTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnEdit)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKembali)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 950, 390));
    }// </editor-fold>//GEN-END:initComponents

    private void cbJenisLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbJenisLaporanActionPerformed
        // TODO add your handling code here:
        String pilihan = cbJenisLaporan.getSelectedItem().toString();
    
        if (pilihan.equals("Kendaraan Hilang")) {

            scrollLaporan.setVisible(false);
            harian.setVisible(false);
            bulanan.setVisible(false);

            javax.swing.JFrame frameInduk = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            FormKendaraanHilang dialogHilang = new FormKendaraanHilang(frameInduk, true);

            dialogHilang.setLocationRelativeTo(this);
            dialogHilang.setPanelLaporan(this); 

            tampilkanDataLaporan();
            dialogHilang.setVisible(true);
            return;
        }

        scrollLaporan.setVisible(true);
        harian.setVisible(true);
        bulanan.setVisible(true);
        tampilkanDataLaporan();
    }//GEN-LAST:event_cbJenisLaporanActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        // TODO add your handling code here:
        javax.swing.JFrame frameInduk = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frameInduk instanceof HalamanUtama) {
            HalamanUtama hu = (HalamanUtama) frameInduk;
            hu.pnlKonten.removeAll();
            hu.pnlKonten.repaint();
            hu.pnlKonten.revalidate();
        }
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void dcTanggalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcTanggalPropertyChange
        // TODO add your handling code here:
        if ("date".equals(evt.getPropertyName())) {
            tampilkanDataLaporan();
        }
    }//GEN-LAST:event_dcTanggalPropertyChange

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        // TODO add your handling code here:
        if (txtCari.getText().equals("Cari...")) {
            txtCari.setText("");
            txtCari.setForeground(new java.awt.Color(0, 0, 0));
        }
    }//GEN-LAST:event_txtCariFocusGained

    private void txtCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusLost
        // TODO add your handling code here:
        if (txtCari.getText().trim().isEmpty()) {
            txtCari.setText("Cari...");
            txtCari.setForeground(new java.awt.Color(153, 153, 153));
        }
    }//GEN-LAST:event_txtCariFocusLost

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        // TODO add your handling code here:
        String pilihan = cbJenisLaporan.getSelectedItem().toString();
        String keyword = txtCari.getText().trim();
        
        if (pilihan.equals("Jenis Laporan") || pilihan.isEmpty()) {
            if (!keyword.isEmpty() && !keyword.equals("Cari...")) {
                
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Silakan pilih Jenis Laporan terlebih dahulu sebelum melakukan pencarian kendaraan!", "Peringatan", 
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                
                txtCari.setText("Cari...");
                txtCari.setForeground(new java.awt.Color(153, 153, 153));
                tabelLaporan.requestFocus();
                return;
            }
        }
        
        tampilkanDataLaporan();
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        String pilihan = cbJenisLaporan.getSelectedItem().toString();
        
        if (pilihan.contains("Pendapatan")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Data Pendapatan Keuangan bersifat mutlak dan tidak boleh diedit petugas kasir!", "Keamanan Sistem", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        int barisTerpilih = tabelLaporan.getSelectedRow();
        if (barisTerpilih == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silakan pilih baris data tabel terlebih dahulu!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Object primaryKey = tabelLaporan.getValueAt(barisTerpilih, 0); 
        
        try {
            java.sql.Connection conn = Koneksi.getConnection();
            String sqlUpdate = "";
            java.sql.PreparedStatement psUpdate = null;
            
            if (pilihan.contains("Karcis Hilang")) {
                String[] opsiKarcis = {"Edit Plat Nomor", "Edit Denda Karcis"};
                String menuTerpilih = (String) javax.swing.JOptionPane.showInputDialog(this, 
                        "Pilih data yang ingin diedit:", "Pilihan Edit Karcis Hilang", 
                        javax.swing.JOptionPane.QUESTION_MESSAGE, null, opsiKarcis, opsiKarcis[0]);
                
                if (menuTerpilih == null) return;
                
                if (menuTerpilih.equals("Edit Plat Nomor")) {
                    String platLama = tabelLaporan.getValueAt(barisTerpilih, 1).toString();
                    String platBaru = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Perbaikan Plat Nomor:", platLama);
                    if (platBaru == null || platBaru.trim().isEmpty()) return;
                    
                    String platSimpan = platBaru.trim().toUpperCase().replace(" ", "");
                    sqlUpdate = "UPDATE riwayat_parkir SET plat_nomor = ? WHERE kode_tarif = ?";
                    psUpdate = conn.prepareStatement(sqlUpdate);
                    psUpdate.setString(1, platSimpan);
                    psUpdate.setString(2, primaryKey.toString());
                    
                } else if (menuTerpilih.equals("Edit Denda Karcis")) {
                    String dendaLamaRaw = tabelLaporan.getValueAt(barisTerpilih, 5).toString().replace("Rp ", "").replace(".", "").trim();
                    String dendaBaruStr = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Perbaikan Denda Karcis :", dendaLamaRaw);
                    
                    if (dendaBaruStr == null || dendaBaruStr.trim().isEmpty()) return;
                    
                    int dendaBaru = Integer.parseInt(dendaBaruStr.trim());
                    sqlUpdate = "UPDATE riwayat_parkir SET denda = ? WHERE kode_tarif = ?";
                    psUpdate = conn.prepareStatement(sqlUpdate);
                    psUpdate.setInt(1, dendaBaru);
                    psUpdate.setString(2, primaryKey.toString());
                }
                
            } else if (pilihan.contains("Kendaraan Hilang")) {
                String[] opsiHilang = {"Edit Plat Nomor", "Edit Status Kendaraan"};
                String menuTerpilih = (String) javax.swing.JOptionPane.showInputDialog(this, 
                        "Pilih data yang ingin diedit:", "Pilihan Edit Kendaraan Hilang", 
                        javax.swing.JOptionPane.QUESTION_MESSAGE, null, opsiHilang, opsiHilang[0]);
                
                if (menuTerpilih == null) return;
                
                if (menuTerpilih.equals("Edit Plat Nomor")) {
                    String platLama = tabelLaporan.getValueAt(barisTerpilih, 1).toString();
                    String platBaru = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Perbaikan Plat Nomor:", platLama);
                    if (platBaru == null || platBaru.trim().isEmpty()) return;
                    
                    String platSimpan = platBaru.trim().toUpperCase().replace(" ", "");
                    sqlUpdate = "UPDATE kendaraan_hilang SET plat_nomor = ? WHERE kode_tarif = ?";
                    psUpdate = conn.prepareStatement(sqlUpdate);
                    psUpdate.setString(1, platSimpan);
                    psUpdate.setString(2, primaryKey.toString());
                    
                } else if (menuTerpilih.equals("Edit Status Kendaraan")) {
                    String statusLama = tabelLaporan.getValueAt(barisTerpilih, 8).toString();
                    String[] opsiStatus = {"Hilang", "Ditemukan", "Selesai"};
                    String statusBaru = (String) javax.swing.JOptionPane.showInputDialog(this, 
                            "Pilih Status Kendaraan Hilang yang Baru:", "Update Status", 
                            javax.swing.JOptionPane.QUESTION_MESSAGE, null, opsiStatus, statusLama);
                    if (statusBaru == null) return;
                    
                    sqlUpdate = "UPDATE kendaraan_hilang SET status = ? WHERE kode_tarif = ?";
                    psUpdate = conn.prepareStatement(sqlUpdate);
                    psUpdate.setString(1, statusBaru);
                    psUpdate.setString(2, primaryKey.toString());
                }

            } else {
                int kolomPlat = 1;
                String platLama = tabelLaporan.getValueAt(barisTerpilih, kolomPlat).toString();
                String platBaru = javax.swing.JOptionPane.showInputDialog(this, "Masukkan Perbaikan Plat Nomor:", platLama);
                
                if (platBaru == null || platBaru.trim().isEmpty()) return;
                
                String platSimpan = platBaru.trim().toUpperCase().replace(" ", "");
                
                if (pilihan.contains("Masuk")) {
                    sqlUpdate = "UPDATE data_kendaraan SET plat_nomor = ? WHERE kode_tarif = ?";
                } else {
                    sqlUpdate = "UPDATE riwayat_parkir SET plat_nomor = ? WHERE kode_tarif = ?";
                }
                
                psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setString(1, platSimpan);
                psUpdate.setString(2, primaryKey.toString());
            }
            // Update ke database
            if (psUpdate != null) {
                int hasil = psUpdate.executeUpdate();
                if (hasil > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Data laporan berhasil diperbarui!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    tampilkanDataLaporan(); 
                    
                    scrollLaporan.setVisible(true);   
                    tampilkanDataLaporan();
                }
                psUpdate.close();
            }
        } catch (NumberFormatException nfe) {
            javax.swing.JOptionPane.showMessageDialog(this, "Input denda harus berupa angka!", "Format Salah", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengedit data: " + e.getMessage(), "Eror", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void jPanel1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1ComponentShown

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        tampilkanDataLaporan();
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnKembali;
    private javax.swing.JLabel bulanan;
    public javax.swing.JComboBox<String> cbJenisLaporan;
    private com.toedter.calendar.JDateChooser dcTanggal;
    private javax.swing.JLabel harian;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane scrollLaporan;
    private javax.swing.JTable tabelLaporan;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
