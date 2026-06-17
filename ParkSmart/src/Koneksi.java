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
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Koneksi {
    private static Connection mysqlkonek;
    public static Connection koneksiDB() {
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
    
    public static void main(String[] args) {
        koneksiDB();
    }
}
