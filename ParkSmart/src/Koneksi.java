/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dearis Mahendra
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection mysqlkonek;
    public static Connection getConnection() {
        if (mysqlkonek == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/db_parksmart";
                String user = "root";
                String pass = "";
                
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                mysqlkonek = DriverManager.getConnection(url, user, pass);
                
                System.out.println("Koneksi Database Berhasil, Lek!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e.getMessage());
            }
        }
        return mysqlkonek;
    }
    
    public static void catatAktivitas(String namaPetugas, String status, String kodeTarif) {
        String sql = "INSERT INTO log_aktivitas (tanggal, waktu, petugas, status, kode_tarif) VALUES (CURDATE(), CURTIME(), ?, ?, ?)";
        
        try {
            Connection conn = Koneksi.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, namaPetugas);
            ps.setString(2, status);
            ps.setString(3, kodeTarif);
            
            ps.executeUpdate(); // Eksekusi tembak ke tabel log_aktivitas lek!
            System.out.println("Log aktivitas berhasil dicatat!");
        } catch (SQLException e) {
            System.out.println("Gagal mencatat log ke MySQL! Errornya: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        getConnection();
    }
}
