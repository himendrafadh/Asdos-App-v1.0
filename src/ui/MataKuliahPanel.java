package ui;

import dao.MatakuliahDAO;
import model.Matakuliah;
// DITAMBAHKAN: Import untuk ExcelExporter
import util.ExcelExporter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MataKuliahPanel extends JPanel {

    private final Color PANEL_BACKGROUND_COLOR = new Color(245, 245, 245);
    private JTextField tfKodeMk, tfNamaMk;
    private JSpinner spinnerSks, spinnerSemester;
    private JButton btnSimpan, btnRefresh, btnEdit, btnHapus, btnExport;
    private JTable table;
    private DefaultTableModel tableModel;
    private MatakuliahDAO matakuliahDAO;

    public MataKuliahPanel() {
        matakuliahDAO = new MatakuliahDAO();
        
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

        tableModel = new DefaultTableModel(new String[]{"Kode MK", "Nama Mata Kuliah", "SKS", "Semester"}, 0) {
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
                BorderFactory.createEtchedBorder(), "Daftar Mata Kuliah", TitledBorder.LEFT, TitledBorder.TOP));
        tableContainerPanel.add(scrollPane, BorderLayout.CENTER);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(tableContainerPanel, gbc);
        
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
                BorderFactory.createEtchedBorder(), "Input Data Mata Kuliah", TitledBorder.LEFT, TitledBorder.TOP));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        tfKodeMk = new JTextField(15);
        tfNamaMk = new JTextField(20);
        spinnerSks = new JSpinner(new SpinnerNumberModel(3, 1, 6, 1));
        spinnerSemester = new JSpinner(new SpinnerNumberModel(1, 1, 14, 1));

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Kode MK:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfKodeMk, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Nama Mata Kuliah:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfNamaMk, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("SKS:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(spinnerSks, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Semester:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(spinnerSemester, gbc);

        controlPanel.add(formPanel, BorderLayout.CENTER);

        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        actionButtonPanel.setBackground(PANEL_BACKGROUND_COLOR);

        btnSimpan = new JButton("Simpan");
        btnEdit = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnRefresh = new JButton("Clear/Refresh");
        btnExport = new JButton("Export ke Excel"); // 
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
        btnExport.putClientProperty("JButton.buttonType", "roundRect"); // 

        actionButtonPanel.add(btnSimpan);
        actionButtonPanel.add(btnEdit);
        actionButtonPanel.add(btnHapus);
        actionButtonPanel.add(btnRefresh);
        actionButtonPanel.add(btnExport); // 

        controlPanel.add(actionButtonPanel, BorderLayout.SOUTH);

        btnSimpan.addActionListener(e -> simpanMatakuliah());
        btnEdit.addActionListener(e -> editMatakuliah());
        btnHapus.addActionListener(e -> hapusMatakuliah());
        btnRefresh.addActionListener(e -> {
            refreshTable();
            clearForm();
        });
        
        btnExport.addActionListener(e -> util.ExcelExporter.exportToExcel(table, this));
        
        return controlPanel;
    }

        private void simpanMatakuliah() {
        String kodeMk = tfKodeMk.getText().trim();
        String namaMk = tfNamaMk.getText().trim();
        int sks = (Integer) spinnerSks.getValue();
        int semester = (Integer) spinnerSemester.getValue();

        if (kodeMk.isEmpty() || namaMk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode MK dan Nama Mata Kuliah harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Matakuliah mk = new Matakuliah(kodeMk, namaMk, sks, semester);
        if (matakuliahDAO.insertMatakuliah(mk)) {
            JOptionPane.showMessageDialog(this, "Data mata kuliah berhasil disimpan.");
            refreshTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data (Kode MK mungkin sudah ada).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editMatakuliah() {
        if (tfKodeMk.isEnabled() || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang akan diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String kodeMk = tfKodeMk.getText().trim();
        String namaMk = tfNamaMk.getText().trim();
        int sks = (Integer) spinnerSks.getValue();
        int semester = (Integer) spinnerSemester.getValue();

        if (namaMk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Mata Kuliah harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Matakuliah mk = new Matakuliah(kodeMk, namaMk, sks, semester);
        if (matakuliahDAO.updateMatakuliah(mk)) {
            JOptionPane.showMessageDialog(this, "Data mata kuliah berhasil diupdate.");
            refreshTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data mata kuliah.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusMatakuliah() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang akan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String kodeMk = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus mata kuliah dengan Kode " + kodeMk + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (matakuliahDAO.deleteMatakuliah(kodeMk)) {
                JOptionPane.showMessageDialog(this, "Data mata kuliah berhasil dihapus.");
                refreshTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data mata kuliah.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void populateFormFromTable() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tfKodeMk.setText(tableModel.getValueAt(selectedRow, 0).toString());
            tfNamaMk.setText(tableModel.getValueAt(selectedRow, 1).toString());
            try {
                spinnerSks.setValue(Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString()));
                spinnerSemester.setValue(Integer.parseInt(tableModel.getValueAt(selectedRow, 3).toString()));
            } catch (NumberFormatException ex) {
                spinnerSks.setValue(1); 
                spinnerSemester.setValue(1);
            }
            tfKodeMk.setEnabled(false);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Matakuliah> listMk = matakuliahDAO.getAllMatakuliah();
        if (listMk != null) {
            for (Matakuliah mk : listMk) {
                tableModel.addRow(new Object[]{
                        mk.getKodeMk(), mk.getNamaMk(), mk.getSks(), mk.getSemester()
                });
            }
        }
    }

    private void clearForm() {
        tfKodeMk.setText("");
        tfNamaMk.setText("");
        spinnerSks.setValue(1); 
        spinnerSemester.setValue(1);
        tfKodeMk.setEnabled(true);
        table.clearSelection();
    }
}