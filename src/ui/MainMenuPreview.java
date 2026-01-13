package ui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.net.URL;

public class MainMenuPreview {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("FlatLaf Look and Feel tidak didukung.");
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pratinjau Main Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 700);
            frame.setLocationRelativeTo(null);

            JPanel mainMenuPanel = createMainMenuPanel();
            frame.add(mainMenuPanel);

            frame.setVisible(true);
        });
    }

    public static JPanel createMainMenuPanel() {
        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Logo Utama ---
        URL logoUrl = MainMenuPreview.class.getResource("/resources/LogoAsdos.png");
        if (logoUrl != null) {
            ImageIcon originalIcon = new ImageIcon(logoUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            gbc.insets = new Insets(20, 0, 20, 0);
            menuPanel.add(logoLabel, gbc);
        } else {
            System.err.println("Resource tidak ditemukan: /resources/LogoAsdos.png");
            JLabel logoFallback = new JLabel("ASDOS APP");
            logoFallback.setFont(new Font("Segoe UI", Font.BOLD, 36));
            gbc.insets = new Insets(20, 0, 20, 0);
            menuPanel.add(logoFallback, gbc);
        }

        // --- Judul dan Subjudul ---
        JLabel titleLabel = new JLabel("Asisten Dosen Pribadi-mu");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 5, 0);
        menuPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Silahkan pilih opsi dibawah ini");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 20, 0);
        menuPanel.add(subtitleLabel, gbc);

        // --- Panel untuk Tombol ---
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.gridwidth = GridBagConstraints.REMAINDER;
        btnGbc.insets = new Insets(4, 0, 4, 0);
        btnGbc.weightx = 1.0;

        JButton btnMahasiswa = createMenuButton("Data Mahasiswa", "/resources/mahasiswa.png");
        JButton btnDosen = createMenuButton("Data Dosen", "/resources/dosen.png");
        JButton btnMataKuliah = createMenuButton("Data Mata Kuliah", "/resources/matkul.png");
        JButton btnNilai = createMenuButton("Nilai Mahasiswa", "/resources/nilaimahasiswa.png");

        btnMahasiswa.addActionListener(e -> System.out.println("Tombol 'Data Mahasiswa' diklik."));
        btnDosen.addActionListener(e -> System.out.println("Tombol 'Data Dosen' diklik."));
        btnMataKuliah.addActionListener(e -> System.out.println("Tombol 'Data Mata Kuliah' diklik."));
        btnNilai.addActionListener(e -> System.out.println("Tombol 'Nilai Mahasiswa' diklik."));

        buttonPanel.add(btnMahasiswa, btnGbc);
        buttonPanel.add(btnDosen, btnGbc);
        buttonPanel.add(btnMataKuliah, btnGbc);
        buttonPanel.add(btnNilai, btnGbc);

        gbc.insets = new Insets(10, 80, 20, 80);
        menuPanel.add(buttonPanel, gbc);

        // --- Teks Footer ---
        JLabel footerLabel1 = new JLabel("Dibuat dengan kasih sayang dan rasa bangga untuk dosen tercinta kami");
        footerLabel1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerLabel1.setForeground(Color.DARK_GRAY);
        footerLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(20, 0, 2, 0);
        menuPanel.add(footerLabel1, gbc);

        JLabel footerLabel2 = new JLabel("@EMpatGroup");
        footerLabel2.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel2.setForeground(Color.GRAY);
        footerLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(0, 0, 20, 0);
        menuPanel.add(footerLabel2, gbc);

        return menuPanel;
    }

    private static JButton createMenuButton(String text, String iconPath) {
        JButton button = new JButton(text);
        URL iconUrl = MainMenuPreview.class.getResource(iconPath);
        if (iconUrl != null) {
            ImageIcon originalIcon = new ImageIcon(iconUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } else {
            System.err.println("Icon tidak ditemukan: " + iconPath);
        }

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(245, 245, 245));
        button.setForeground(Color.BLACK);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(20);
        Border padding = BorderFactory.createEmptyBorder(12, 20, 12, 20);
        button.setBorder(padding);
        button.setFocusPainted(false);
        return button;
    }
}