package ui;

import dao.DosenDAO;
import model.Dosen;
import util.ExcelExporter;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DosenPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Color PANEL_BACKGROUND_COLOR = new Color(245, 245, 245);

    private JTextField tfNip, tfNama, tfEmail;
    private JButton btnSimpan, btnRefresh, btnEdit, btnHapus, btnExport;
    private JTable table;
    private DefaultTableModel tableModel;
    private DosenDAO dosenDAO;

    public DosenPanel() {
        dosenDAO = new DosenDAO();
        
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

        tableModel = new DefaultTableModel(new String[]{"NIP", "Nama Dosen", "Email"}, 0) {
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
                BorderFactory.createEtchedBorder(), "Daftar Dosen", TitledBorder.LEFT, TitledBorder.TOP));
        tableContainerPanel.add(scrollPane, BorderLayout.CENTER);
        
        gbc.gridy = 1;
        gbc.weighty = 1.0; 
        gbc.fill = GridBagConstraints.BOTH;
        add(tableContainerPanel, gbc);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tfNip.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    tfNama.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    tfEmail.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    tfNip.setEnabled(false);
                }
            }
        });

        refreshTable();
    }


    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBackground(PANEL_BACKGROUND_COLOR);
        controlPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Input Data Dosen", TitledBorder.LEFT, TitledBorder.TOP));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(PANEL_BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        tfNip = new JTextField(20);
        tfNama = new JTextField(20);
        tfEmail = new JTextField(20);
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("NIP:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfNip, gbc);
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Nama Dosen:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL; formPanel.add(tfNama, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE; formPanel.add(new JLabel("Email:"), gbc);
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

        // Action listeners
        btnSimpan.addActionListener(e -> simpanDosen());
        btnEdit.addActionListener(e -> editDosen());
        btnHapus.addActionListener(e -> hapusDosen());
        btnRefresh.addActionListener(e -> {
            refreshTable();
            clearForm();
        });
        btnExport.addActionListener(e -> util.ExcelExporter.exportToExcel(table, this));

        return controlPanel;
    }

    private void simpanDosen() {
        String nip = tfNip.getText().trim();
        String nama = tfNama.getText().trim();
        String email = tfEmail.getText().trim();
        if (nip.isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIP dan Nama Dosen harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Dosen dosen = new Dosen(nip, nama, email);
        boolean success = dosenDAO.insertDosen(dosen);
        if (success) {
            JOptionPane.showMessageDialog(this, "Data dosen berhasil disimpan.");
            refreshTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data dosen (NIP mungkin sudah ada).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editDosen() {
        if (tfNip.isEnabled() || table.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang akan diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nip = tfNip.getText().trim();
        String nama = tfNama.getText().trim();
        String email = tfEmail.getText().trim();
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Dosen harus diisi!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Dosen dosen = new Dosen(nip, nama, email);
        boolean success = dosenDAO.updateDosen(dosen);
        if (success) {
            JOptionPane.showMessageDialog(this, "Data dosen berhasil diupdate.");
            refreshTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data dosen.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusDosen() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel yang akan dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nip = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus dosen dengan NIP " + nip + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = dosenDAO.deleteDosen(nip);
            if (success) {
                JOptionPane.showMessageDialog(this, "Data dosen berhasil dihapus.");
                refreshTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data dosen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Dosen> listDosen = dosenDAO.getAllDosen();
        if (listDosen != null) {
            for (Dosen dosen : listDosen) {
                tableModel.addRow(new Object[]{dosen.getNip(), dosen.getNama(), dosen.getEmail()});
            }
        }
    }

    private void clearForm() {
        tfNip.setText("");
        tfNama.setText("");
        tfEmail.setText("");
        table.clearSelection();
        tfNip.setEnabled(true);
    }
}