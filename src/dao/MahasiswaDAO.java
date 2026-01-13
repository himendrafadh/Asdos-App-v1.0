package dao;

import model.Mahasiswa;
import util.JDBCUtil; // Pastikan kelas util ini ada dan berfungsi untuk koneksi JDBC

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MahasiswaDAO {

    public boolean insertMahasiswa(Mahasiswa mhs) {
        String sql = "INSERT INTO Mahasiswa (nim, nama, angkatan, prodi, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mhs.getNim());
            stmt.setString(2, mhs.getNama());
            stmt.setString(3, mhs.getAngkatan()); 
            stmt.setString(4, mhs.getProdi());   
            stmt.setString(5, mhs.getEmail());   
            
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }

    public boolean updateMahasiswa(Mahasiswa mhs) {
        String sql = "UPDATE Mahasiswa SET nama=?, angkatan=?, prodi=?, email=? WHERE nim=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mhs.getNama());
            stmt.setString(2, mhs.getAngkatan()); 
            stmt.setString(3, mhs.getProdi());   
            stmt.setString(4, mhs.getEmail());   
            stmt.setString(5, mhs.getNim());     
            
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
          
            return false;
        }
    }

    public boolean deleteMahasiswa(String nim) {
        String sql = "DELETE FROM Mahasiswa WHERE nim=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nim);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            
            return false;
        }
    }

    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> list = new ArrayList<>();
        
        String sql = "SELECT nim, nama, angkatan, prodi, email FROM Mahasiswa"; 
        
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Mahasiswa mhs = new Mahasiswa(
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getString("angkatan"), 
                        rs.getString("prodi"),    
                        rs.getString("email")     
                );
                list.add(mhs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return list;
    }
}