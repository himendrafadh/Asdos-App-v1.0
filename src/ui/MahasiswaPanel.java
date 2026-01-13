 
package ui;

import dao.MahasiswaDAO;
import model.Mahasiswa;

import util.ExcelExporter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MahasiswaPanel extends JPanel {

    private final Color PANEL_BACKGROUND_COLOR = new Color(245, 245, 245);

    private JTextField tfNim, tfNama, tfAngkatan, tfProdi, tfEmail;
    private JButton btnSimpan, btnRefresh, btnEdit, btnHapus, btnExport;
    private JTable table;
    private DefaultTableModel tableModel;
    private MahasiswaDAO mahasiswaDAO;

    public MahasiswaPanel() {
        mahasiswaDAO = new MahasiswaDAO();

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(PANEL_BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // === 1. PANEL KONTROL (Form Input dan Tombol Aksi) ===
        JPanel controlPanel = createControlPanel();
        gbc.gridy = 0;
        gbc.weighty = 0; 
        add(controlPanel, gbc);

        // === 2. TABEL DATA ===
        tableModel = new DefaultTableModel(new String[]{"NIM", "Nama", "Angkatan", "Prodi", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setShowGrid(true);
        table.setGridColor(new Color(220, 220, 220));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(PANEL_BACKGROUND_COLOR);
        
        JPanel tableContainerPanel = new JPanel(new BorderLayout());
        tableContainerPanel.setBackground(PANEL_BACKGROUND_COLOR);
        tableContainerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Daftar Mahasiswa", TitledBorder.LEFT, TitledBorder.TOP));
        tableContainerPanel.add(scrollPane, BorderLayout.CENTER);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(tableContainerPanel, gbc);
        
        // === Event Listener untuk Tabel ===
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tfNim.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    tfNama.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    tfAngkatan.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    tfProdi.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    tfEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    tfNim.setEnabled(false);
                }
            }
        });

        refreshTable();
    }
    

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(PANEL_BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Input Data Mahasiswa", TitledBorder.LEFT, TitledBorder.TOP));
        
       
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        tfNim = new JTextField(20);
        tfNama = new JTextField(20);
        tfAngkatan = new JTextField(20);
        tfProdi = new JTextField(20);
        tfEmail = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfNim, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Angkatan:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfAngkatan, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Prodi:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfProdi, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfEmail, gbc);
        controlPanel.add(formPanel, BorderLayout.CENTER);


        // === Panel Tombol Aksi ===
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionButtonPanel.setBackground(PANEL_BACKGROUND_COLOR);

        btnSimpan = new JButton("Simpan");
        btnEdit = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Clear/Refresh");
        btnExport = new JButton("Export ke Excel");

        // tempat mengatur font
        if (MainFrame.FONT_REGULAR != null) {
            Font regularButtonFont = MainFrame.FONT_REGULAR.deriveFont(14f);
            btnSimpan.setFont(regularButtonFont);
            btnEdit.setFont(regularButtonFont);
            btnHapus.setFont(regularButtonFont);
            btnRefresh.setFont(regularButtonFont);
            btnExport.setFont(regularButtonFont);
        }

        // Styling tombol 
        btnSimpan.putClientProperty("JButton.buttonType", "roundRect");
        btnEdit.putClientProperty("JButton.buttonType", "roundRect");
        btnHapus.putClientProperty("JButton.buttonType", "roundRect");
        btnRefresh.putClientProperty("JButton.buttonType", "roundRect");
        btnExport.putClientProperty("JButton.buttonType", "roundRect");

        actionButtonPanel.add(btnSimpan);
        actionButtonPanel.add(btnEdit);
        actionButtonPanel.add(btnHapus);
        actionButtonPanel.add(btnRefresh);
        actionButtonPanel.add(btnExport);

        controlPanel.add(actionButtonPanel, BorderLayout.SOUTH);

        btnSimpan.addActionListener(e -> simpanMahasiswa());
        btnEdit.addActionListener(e -> editMahasiswa());
        btnHapus.addActionListener(e -> hapusMahasiswa());
        btnRefresh.addActionListener(e -> {
            refreshTable();
            clearForm();
        });
        btnExport.addActionListener(e -> util.ExcelExporter.exportToExcel(table, this));

        return controlPanel;
    }
    
    private void simpanMahasiswa() {
        String nim = tfNim.getText().trim();
        String nama = tfNama.getText().trim();
        String angkatan = tfAngkatan.getText().trim();
        String prodi = tfProdi.getText().trim();
        String email = tfEmail.getText().trim();
        if (nim.isEmpty() || nama.isEmpty() || angkatan.isEmpty() || prodi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM, Nama, Angkatan, dan Prodi harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Mahasiswa mhs = new Mahasiswa(nim, nama, angkatan, prodi, email);
        boolean success = mahasiswaDAO.insertMahasiswa(mhs);
        if (success) {
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan.");
            refreshTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data (NIM mungkin sudah ada).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void editMahasiswa() {
        if (tfNim.isEnabled() || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang akan diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nim = tfNim.getText().trim();
        String nama = tfNama.getText().trim();
        String angkatan = tfAngkatan.getText().trim();
        String prodi = tfProdi.getText().trim();
        String email = tfEmail.getText().trim();
        if (nama.isEmpty() || angkatan.isEmpty() || prodi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama, Angkatan, dan Prodi harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Mahasiswa mhs = new Mahasiswa(nim, nama, angkatan, prodi, email);
        boolean success = mahasiswaDAO.updateMahasiswa(mhs);
        if (success) {
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate.");
            refreshTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void hapusMahasiswa() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang a kan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nim = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus mahasiswa dengan NIM " + nim + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = mahasiswaDAO.deleteMahasiswa(nim);
            if (success) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                refreshTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Mahasiswa> list = mahasiswaDAO.getAllMahasiswa();
        for (Mahasiswa m : list) {
            tableModel.addRow(new Object[]{m.getNim(), m.getNama(), m.getAngkatan(), m.getProdi(), m.getEmail()});
        }
    }
    private void clearForm() {
        tfNim.setText("");
        tfNama.setText("");
        tfAngkatan.setText("");
        tfProdi.setText("");
        tfEmail.setText("");
        table.clearSelection();
        tfNim.setEnabled(true);
    }
}