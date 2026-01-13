package ui;

import dao.NilaiDAO;
import model.Nilai;
// DITAMBAHKAN: Import untuk ExcelExporter
import util.ExcelExporter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NilaiPanel extends JPanel {

    private final Color PANEL_BACKGROUND_COLOR = new Color(245, 245, 245);
    private final Color READ_ONLY_FIELD_COLOR = new Color(230, 230, 230);

    private JTextField tfNim, tfKodeMK, tfNip, tfUts, tfUas, tfTugas, tfAkhir, tfGrade;
    private JButton btnSimpan, btnRefresh, btnEdit, btnHapus, btnExport;
    private JTable table;
    private DefaultTableModel tableModel;
    private NilaiDAO nilaiDAO;

    public NilaiPanel() {
        nilaiDAO = new NilaiDAO();

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        setBackground(PANEL_BACKGROUND_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel controlPanel = createControlPanel();
        gbc.gridy = 0;
        gbc.weighty = 0;
        add(controlPanel, gbc);

        tableModel = new DefaultTableModel(new String[]{"NIM", "Kode MK", "NIP", "UTS", "UAS", "Tugas", "Nilai Akhir", "Grade"}, 0) {
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
                BorderFactory.createEtchedBorder(), "Daftar Nilai Mahasiswa", TitledBorder.LEFT, TitledBorder.TOP));
        tableContainerPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableContainerPanel, gbc);

        addDocumentListeners();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                populateFormFromTable();
            }
        });

        refreshTable();
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(PANEL_BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Input Data Nilai", TitledBorder.LEFT, TitledBorder.TOP));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        tfNim = new JTextField();
        tfKodeMK = new JTextField();
        tfNip = new JTextField();
        tfUts = new JTextField();
        tfUas = new JTextField();
        tfTugas = new JTextField();
        tfAkhir = new JTextField();
        tfGrade = new JTextField();

        tfAkhir.setEditable(false);
        tfGrade.setEditable(false);
        tfAkhir.setBackground(READ_ONLY_FIELD_COLOR);
        tfGrade.setBackground(READ_ONLY_FIELD_COLOR);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("NIM:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfNim, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Kode MK:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfKodeMK, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("NIP:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfNip, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Nilai UTS:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfUts, gbc);
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Nilai UAS:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfUas, gbc);
        gbc.gridx = 2; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Nilai Tugas:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfTugas, gbc);
        gbc.gridx = 2; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Nilai Akhir:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfAkhir, gbc);
        gbc.gridx = 2; gbc.gridy = 3; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Grade:"), gbc);
        gbc.gridx = 3; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfGrade, gbc);

        controlPanel.add(formPanel, BorderLayout.CENTER);

        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionButtonPanel.setBackground(PANEL_BACKGROUND_COLOR);
        btnSimpan = new JButton("Simpan");
        btnEdit = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Clear/Refresh");
        btnExport = new JButton("Export ke Excel");

    
        if (MainFrame.FONT_REGULAR != null) {
            Font regularButtonFont = MainFrame.FONT_REGULAR.deriveFont(14f);
            btnSimpan.setFont(regularButtonFont);
            btnEdit.setFont(regularButtonFont);
            btnHapus.setFont(regularButtonFont);
            btnRefresh.setFont(regularButtonFont);
            btnExport.setFont(regularButtonFont);
        }

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

        btnSimpan.addActionListener(e -> simpanNilai());
        btnEdit.addActionListener(e -> editNilai());
        btnHapus.addActionListener(e -> hapusNilai());
        btnRefresh.addActionListener(e -> clearForm());
        btnExport.addActionListener(e -> util.ExcelExporter.exportToExcel(table, this));
        
        return controlPanel;
    }
    
        private void addDocumentListeners() {
        DocumentListener listener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { hitungNilaiOtomatis(); }
            @Override public void removeUpdate(DocumentEvent e) { hitungNilaiOtomatis(); }
            @Override public void changedUpdate(DocumentEvent e) { hitungNilaiOtomatis(); }
        };
        tfUts.getDocument().addDocumentListener(listener);
        tfUas.getDocument().addDocumentListener(listener);
        tfTugas.getDocument().addDocumentListener(listener);
    }
    
    private String calculateGrade(double nilaiAkhir) {
        if (nilaiAkhir >= 80) return "A";
        if (nilaiAkhir >= 75) return "AB";
        if (nilaiAkhir >= 70) return "B";
        if (nilaiAkhir >= 65) return "BC";
        if (nilaiAkhir >= 60) return "C";
        if (nilaiAkhir >= 55) return "CD";
        if (nilaiAkhir >= 40) return "D";
        return "E";
    }

    private void hitungNilaiOtomatis() {
        SwingUtilities.invokeLater(() -> {
            try {
                double uts = tfUts.getText().trim().isEmpty() ? 0 : Double.parseDouble(tfUts.getText().trim());
                double uas = tfUas.getText().trim().isEmpty() ? 0 : Double.parseDouble(tfUas.getText().trim());
                double tugas = tfTugas.getText().trim().isEmpty() ? 0 : Double.parseDouble(tfTugas.getText().trim());
                double akhir = (uts * 0.3) + (uas * 0.4) + (tugas * 0.3);
                String grade = calculateGrade(akhir);
                tfAkhir.setText(String.format("%.2f", akhir));
                tfGrade.setText(grade);
            } catch (NumberFormatException e) {
                tfAkhir.setText("");
                tfGrade.setText("");
            }
        });
    }

    private void simpanNilai() {
        try {
            String nim = tfNim.getText().trim();
            String kodeMK = tfKodeMK.getText().trim();
            String nip = tfNip.getText().trim();
            if (nim.isEmpty() || kodeMK.isEmpty() || nip.isEmpty() || 
                tfUts.getText().trim().isEmpty() || tfUas.getText().trim().isEmpty() || 
                tfTugas.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "NIM, Kode MK, NIP, dan semua komponen nilai harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double uts = Double.parseDouble(tfUts.getText().trim());
            double uas = Double.parseDouble(tfUas.getText().trim());
            double tugas = Double.parseDouble(tfTugas.getText().trim());
            double akhir = (0.3 * uts) + (0.4 * uas) + (0.3 * tugas);
            String grade = calculateGrade(akhir);
            Nilai nilai = new Nilai(nim, kodeMK, nip, uts, uas, tugas, akhir, grade);
            if (nilaiDAO.insertNilai(nilai)) {
                JOptionPane.showMessageDialog(this, "Data nilai berhasil disimpan.");
                refreshTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data nilai.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nilai UTS, UAS, dan Tugas harus berupa angka.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editNilai() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data nilai dari tabel yang akan diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String nim = tfNim.getText().trim();
            String kodeMK = tfKodeMK.getText().trim();
            String nip = tfNip.getText().trim();
            if (tfUts.getText().trim().isEmpty() || tfUas.getText().trim().isEmpty() || 
                tfTugas.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field nilai harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double uts = Double.parseDouble(tfUts.getText().trim());
            double uas = Double.parseDouble(tfUas.getText().trim());
            double tugas = Double.parseDouble(tfTugas.getText().trim());
            double akhir = (0.3 * uts) + (0.4 * uas) + (0.3 * tugas);
            String grade = calculateGrade(akhir);
            Nilai nilai = new Nilai(nim, kodeMK, nip, uts, uas, tugas, akhir, grade);
            if (nilaiDAO.updateNilai(nilai)) {
                JOptionPane.showMessageDialog(this, "Data nilai berhasil diupdate.");
                refreshTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal mengupdate data nilai.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Nilai UTS, UAS, dan Tugas harus berupa angka.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusNilai() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data nilai dari tabel yang akan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nim = tableModel.getValueAt(selectedRow, 0).toString();
        String kodeMK = tableModel.getValueAt(selectedRow, 1).toString();
        String nip = tableModel.getValueAt(selectedRow, 2).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus nilai untuk NIM: " + nim + ", Kode MK: " + kodeMK + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (nilaiDAO.deleteNilai(nim, kodeMK, nip)) {
                JOptionPane.showMessageDialog(this, "Data nilai berhasil dihapus.");
                refreshTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data nilai.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tfNim.setText(tableModel.getValueAt(selectedRow, 0).toString());
            tfKodeMK.setText(tableModel.getValueAt(selectedRow, 1).toString());
            tfNip.setText(tableModel.getValueAt(selectedRow, 2).toString());
            tfUts.setText(tableModel.getValueAt(selectedRow, 3).toString());
            tfUas.setText(tableModel.getValueAt(selectedRow, 4).toString());
            tfTugas.setText(tableModel.getValueAt(selectedRow, 5).toString());
            tfNim.setEnabled(false);
            tfKodeMK.setEnabled(false);
            tfNip.setEnabled(false);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Nilai> listNilai = nilaiDAO.getAllNilai();
        if (listNilai != null) {
            for (Nilai n : listNilai) {
                tableModel.addRow(new Object[]{
                        n.getNim(), n.getKodeMK(), n.getNip(),
                        n.getUts(), n.getUas(), n.getTugas(),
                        String.format("%.2f", n.getAkhir()), n.getGrade()
                });
            }
        }
    }

    private void clearForm() {
        tfNim.setText("");
        tfKodeMK.setText("");
        tfNip.setText("");
        tfUts.setText("");
        tfUas.setText("");
        tfTugas.setText("");
        tfAkhir.setText("");
        tfGrade.setText("");
        tfNim.setEnabled(true);
        tfKodeMK.setEnabled(true);
        tfNip.setEnabled(true);
        table.clearSelection();
    }
}