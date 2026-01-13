package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class AboutUsPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Color SUBTLE_TEXT_COLOR = new Color(130, 130, 130);

    public AboutUsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // --- Bagian Atas: Logo ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(Color.WHITE);
        URL logoUrl = getClass().getResource("/resources/LogoAsdos.png");
        if (logoUrl != null) {
            ImageIcon logoIcon = new ImageIcon(new ImageIcon(logoUrl).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            JLabel logoLabel = new JLabel(logoIcon);
            headerPanel.add(logoLabel);
        }
        add(headerPanel, BorderLayout.NORTH);

        // --- Bagian Tengah: Informasi Aplikasi ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        // Judul Aplikasi
        JLabel titleLabel = new JLabel("Asdos App v1.0");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24f));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Jarak

        // Deskripsi per baris
        String[] descriptionLines = {
            "Aplikasi ini dibuat sebagai",
            "Asisten Dosen untuk",
            "mempermudah",
            "manajemen data",
            "akademik."
        };
        
        for (String line : descriptionLines) {
            JLabel descLineLabel = new JLabel(line);
            descLineLabel.setFont(descLineLabel.getFont().deriveFont(16f)); // Ukuran font deskripsi
            descLineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            infoPanel.add(descLineLabel);
        }

        infoPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Jarak

        // Ucapan Terima Kasih
        JLabel thankYouLabel = new JLabel("Terimakasih telah menggunakan aplikasi kami!");
        thankYouLabel.setFont(thankYouLabel.getFont().deriveFont(14f));
        thankYouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Copyright
        JLabel copyrightLabel = new JLabel("©EMpatGroup");
        copyrightLabel.setForeground(SUBTLE_TEXT_COLOR);
        copyrightLabel.setFont(copyrightLabel.getFont().deriveFont(12f));
        copyrightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(thankYouLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(copyrightLabel);

        infoPanel.add(Box.createVerticalGlue());
        
        add(infoPanel, BorderLayout.CENTER);

        // --- Bagian Bawah: Kontributor ---
        JPanel contributorsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contributorsPanel.setBackground(Color.WHITE);
        contributorsPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        contributorsPanel.add(createContributorLabel("/resources/contributors/himen.png"));
        contributorsPanel.add(createContributorLabel("/resources/contributors/nathan.png"));
        contributorsPanel.add(createContributorLabel("/resources/contributors/tompul.png"));
        contributorsPanel.add(createContributorLabel("/resources/contributors/septi.png"));

        add(contributorsPanel, BorderLayout.SOUTH);
    }

    private JLabel createContributorLabel(String imagePath) {
        URL imgUrl = getClass().getResource(imagePath);
        if (imgUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imgUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage));
        } else {
            JLabel placeholder = new JLabel("Foto?");
            placeholder.setPreferredSize(new Dimension(80, 80));
            placeholder.setHorizontalAlignment(JLabel.CENTER);
            placeholder.setBorder(BorderFactory.createEtchedBorder());
            return placeholder;
        }
    }
}