package dao;

import model.Nilai;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class NilaiDAO {

    public NilaiDAO() {
    }

    public boolean insertNilai(Nilai nilai) {

        String sql = "INSERT INTO nilai (nim, kode_mk, nip, nilai_uts, nilai_uas, nilai_tugas, nilai_akhir, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk insertNilai.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nilai.getNim());
            stmt.setString(2, nilai.getKodeMK());

            stmt.setString(3, nilai.getNip()); 
            stmt.setDouble(4, nilai.getUts());
            stmt.setDouble(5, nilai.getUas());
            stmt.setDouble(6, nilai.getTugas());
            stmt.setDouble(7, nilai.getAkhir());
            stmt.setString(8, nilai.getGrade());
            
            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat menyimpan data nilai: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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

    public boolean updateNilai(Nilai nilai) {

        String sql = "UPDATE nilai SET nilai_uts = ?, nilai_uas = ?, nilai_tugas = ?, nilai_akhir = ?, grade = ? WHERE nim = ? AND kode_mk = ? AND nip = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk updateNilai.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, nilai.getUts());
            stmt.setDouble(2, nilai.getUas());
            stmt.setDouble(3, nilai.getTugas());
            stmt.setDouble(4, nilai.getAkhir());
            stmt.setString(5, nilai.getGrade());
            
            stmt.setString(6, nilai.getNim());
            stmt.setString(7, nilai.getKodeMK());
      
            stmt.setString(8, nilai.getNip());
            
            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat mengupdate data nilai: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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


    public boolean deleteNilai(String nim, String kodeMK, String nip) { 
    
        String sql = "DELETE FROM nilai WHERE nim = ? AND kode_mk = ? AND nip = ?"; 
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean success = false;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk deleteNilai.");
                return false;
            }
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nim);
            stmt.setString(2, kodeMK);
     
            stmt.setString(3, nip); 
            
            int affectedRows = stmt.executeUpdate();
            success = affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat menghapus data nilai: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
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

    public List<Nilai> getAllNilai() {
        List<Nilai> list = new ArrayList<>();

        String sql = "SELECT nim, kode_mk, nip, nilai_uts, nilai_uas, nilai_tugas, nilai_akhir, grade FROM nilai";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtil.getConnection();
            if (conn == null) {
                System.err.println("Koneksi database null dari JDBCUtil untuk getAllNilai.");
                return list;
            }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
          
                Nilai nilai = new Nilai(
                        rs.getString("nim"),
                        rs.getString("kode_mk"),
                        rs.getString("nip"), 
                        rs.getDouble("nilai_uts"),
                        rs.getDouble("nilai_uas"),
                        rs.getDouble("nilai_tugas"),
                        rs.getDouble("nilai_akhir"),
                        rs.getString("grade")
                );
                list.add(nilai);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saat mengambil semua data nilai: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}