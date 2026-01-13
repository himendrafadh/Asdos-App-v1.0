package dao;

import model.Matakuliah;
import util.JDBCUtil; // Menggunakan JDBCUtil untuk koneksi

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MatakuliahDAO {

   
    public MatakuliahDAO() {
        
    }

    public boolean insertMatakuliah(Matakuliah mk) {
        String sql = "INSERT INTO matakuliah (kode_mk, nama_mk, sks, semester) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection(); 
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk insertMatakuliah.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, mk.getKodeMk());
            stmt.setString(2, mk.getNamaMk());
            stmt.setInt(3, mk.getSks());
            stmt.setInt(4, mk.getSemester());
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data mata kuliah: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean updateMatakuliah(Matakuliah mk) {
        String sql = "UPDATE matakuliah SET nama_mk = ?, sks = ?, semester = ? WHERE kode_mk = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection(); 
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk updateMatakuliah.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, mk.getNamaMk());
            stmt.setInt(2, mk.getSks());
            stmt.setInt(3, mk.getSemester());
            stmt.setString(4, mk.getKodeMk());
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengupdate data mata kuliah: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean deleteMatakuliah(String kodeMk) {
        String sql = "DELETE FROM matakuliah WHERE kode_mk = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection(); 
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk deleteMatakuliah.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeMk);
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menghapus data mata kuliah: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    public List<Matakuliah> getAllMatakuliah() {
        List<Matakuliah> listMk = new ArrayList<>();
        String sql = "SELECT kode_mk, nama_mk, sks, semester FROM matakuliah";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection(); 
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk getAllMatakuliah.");
                return listMk; 
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Matakuliah mk = new Matakuliah(
                        rs.getString("kode_mk"),
                        rs.getString("nama_mk"),
                        rs.getInt("sks"),
                        rs.getInt("semester")
                );
                listMk.add(mk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data mata kuliah: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listMk;
    }
}