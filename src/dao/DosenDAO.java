package dao;

import model.Dosen;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DosenDAO {

    public DosenDAO() {
    }

    public boolean insertDosen(Dosen dosen) {
        String sql = "INSERT INTO dosen (nip, nama_dosen, email_dosen) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk insertDosen.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dosen.getNip()); 
            stmt.setString(2, dosen.getNama());
            stmt.setString(3, dosen.getEmail());
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data dosen: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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

    public boolean updateDosen(Dosen dosen) {
        String sql = "UPDATE dosen SET nama_dosen = ?, email_dosen = ? WHERE nip = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk updateDosen.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dosen.getNama());
            stmt.setString(2, dosen.getEmail());
            stmt.setString(3, dosen.getNip()); 
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengupdate data dosen: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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

    public boolean deleteDosen(String nip) { 
        String sql = "DELETE FROM dosen WHERE nip = ?"; 
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk deleteDosen.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nip); 
            
            int rowsAffected = stmt.executeUpdate();
            success = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal menghapus data dosen: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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

    public List<Dosen> getAllDosen() {
        List<Dosen> listDosen = new ArrayList<>();
        String sql = "SELECT nip, nama_dosen, email_dosen FROM dosen"; 
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk getAllDosen.");
                return listDosen; 
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Dosen dosen = new Dosen(
                        rs.getString("nip"), 
                        rs.getString("nama_dosen"),
                        rs.getString("email_dosen")
                );
                listDosen.add(dosen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dosen: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listDosen;
    }
}