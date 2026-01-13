package ui;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {

    private JPanel headerPanel;
    private JMenuBar menuBar;
    private JPanel contentAreaPanel;
    private JLabel headerTitleLabel;
    
    private final Color PRIMARY_COLOR = new Color(52, 152, 219);
    public static Font FONT_REGULAR;
    public static Font FONT_BOLD;
    public static Font FONT_EXTRA_BOLD;

    public MainFrame() {
        setTitle("Asdos App 1.0");
        setSize(500, 800);
        setMinimumSize(new Dimension(450, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        createHeader();
        createMenuBar();
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        contentAreaPanel = new JPanel(new BorderLayout());
        contentAreaPanel.setBackground(Color.WHITE);
        mainPanel.add(contentAreaPanel, BorderLayout.CENTER);
        
        showMainMenu();

    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY_COLOR);
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JMenu menuMaster = new JMenu("Select Option");
        menuMaster.setForeground(Color.WHITE);
        ImageIcon selectIcon = createMenuIcon("/resources/icons/option.png", 16);
        menuMaster.setIcon(selectIcon);
        
        ImageIcon homeIcon = createMenuIcon("/resources/icons/home.png", 16);
        ImageIcon mahasiswaIcon = createMenuIcon("/resources/icons/student.png", 16);
        ImageIcon nilaiIcon = createMenuIcon("/resources/icons/nilai.png", 16);
        ImageIcon dosenIcon = createMenuIcon("/resources/icons/dosen.png", 16);
        ImageIcon matakuliahIcon = createMenuIcon("/resources/icons/matakuliah.png", 16);

        JMenuItem menuHome = new JMenuItem("Halaman Utama", homeIcon);
        JMenuItem menuMahasiswa = new JMenuItem("Mahasiswa", mahasiswaIcon);
        JMenuItem menuNilai = new JMenuItem("Nilai", nilaiIcon);
        JMenuItem menuDosen = new JMenuItem("Dosen", dosenIcon);
        JMenuItem menuMataKuliah = new JMenuItem("Mata Kuliah", matakuliahIcon);
        

        menuHome.addActionListener(e -> showMainMenu());
        menuMahasiswa.addActionListener(e -> showMahasiswaPanel());
        menuNilai.addActionListener(e -> showNilaiPanel());
        menuDosen.addActionListener(e -> showDosenPanel());
        menuMataKuliah.addActionListener(e -> showMataKuliahPanel());

        menuMaster.add(menuHome);
        menuMaster.addSeparator();
        menuMaster.add(menuMahasiswa);
        menuMaster.add(menuDosen);
        menuMaster.add(menuMataKuliah);
        menuMaster.addSeparator();
        menuMaster.add(menuNilai);
        
        menuBar.add(menuMaster);
        setJMenuBar(menuBar);
    }
    
    private ImageIcon createMenuIcon(String path, int size) {
        URL imageUrl = MainFrame.class.getResource(path);
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            System.err.println("Resource ikon tidak ditemukan: " + path);

            return new ImageIcon(new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB));
        }
    }
    

    private void createHeader() {
        headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));

        headerTitleLabel = new JLabel("Aplikasi Asisten Dosen");
        headerTitleLabel.setForeground(Color.WHITE);
        headerPanel.add(headerTitleLabel);
    }

    public void showMainMenu() {
        headerPanel.setVisible(false);
        menuBar.setVisible(false);

        JPanel menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        URL logoUrl = MainFrame.class.getResource("/resources/LogoAsdos.png");
        if (logoUrl != null) {
            ImageIcon originalIcon = new ImageIcon(logoUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(325, 325, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            gbc.insets = new Insets(30, 0, 20, 0);
            menuPanel.add(logoLabel, gbc);
        }

        JLabel titleLabel = new JLabel("<html><b>Asisten Dosen Pribadi-mu</b></html>");
        titleLabel.setFont(titleLabel.getFont().deriveFont(22f));
        gbc.insets = new Insets(10, 0, 5, 0);
        menuPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Silahkan pilih opsi dibawah ini");
        subtitleLabel.setForeground(Color.GRAY);
        gbc.insets = new Insets(0, 0, 15, 0);
        menuPanel.add(subtitleLabel, gbc);
        
        JPanel separatorPanel = new JPanel(new GridBagLayout());
        separatorPanel.setBackground(Color.WHITE);
        GridBagConstraints sepGbc = new GridBagConstraints();
        sepGbc.weightx = 1.0; sepGbc.fill = GridBagConstraints.HORIZONTAL;
        separatorPanel.add(new JSeparator(), sepGbc);
        JLabel startLabel = new JLabel("Mau Mulai dari mana?");
        startLabel.setForeground(Color.LIGHT_GRAY);
        sepGbc.weightx = 0; sepGbc.fill = GridBagConstraints.NONE; sepGbc.insets = new Insets(0, 10, 0, 10);
        separatorPanel.add(startLabel, sepGbc);
        sepGbc.weightx = 1.0; sepGbc.insets = new Insets(0, 0, 0, 0); sepGbc.fill = GridBagConstraints.HORIZONTAL;
        separatorPanel.add(new JSeparator(), sepGbc);
        gbc.insets = new Insets(10, 50, 10, 50); gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(separatorPanel, gbc);
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.gridwidth = GridBagConstraints.REMAINDER;
        btnGbc.insets = new Insets(5, 0, 5, 0);
        
        JButton btnMahasiswa = createMenuButton("Data Mahasiswa", "/resources/mahasiswa.png");
        JButton btnDosen = createMenuButton("Data Dosen", "/resources/dosen.png");
        JButton btnMataKuliah = createMenuButton("Data Mata Kuliah", "/resources/matkul.png");
        JButton btnNilai = createMenuButton("Nilai Mahasiswa", "/resources/nilaimahasiswa.png");
        JButton btnAbout = createMenuButton("About Us", "/resources/aboutus.png");

        btnMahasiswa.addActionListener(e -> showMahasiswaPanel());
        btnDosen.addActionListener(e -> showDosenPanel());
        btnMataKuliah.addActionListener(e -> showMataKuliahPanel());
        btnNilai.addActionListener(e -> showNilaiPanel());
        btnAbout.addActionListener(e -> showAboutUsDialog());

        buttonPanel.add(btnMahasiswa, btnGbc);
        buttonPanel.add(btnDosen, btnGbc);
        buttonPanel.add(btnMataKuliah, btnGbc);
        buttonPanel.add(btnNilai, btnGbc);
        buttonPanel.add(btnAbout, btnGbc);
        
        gbc.insets = new Insets(10, 60, 0, 60);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        menuPanel.add(buttonPanel, gbc);
        gbc.fill = GridBagConstraints.NONE;

        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        menuPanel.add(new JPanel() {{ setOpaque(false); }}, gbc);
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel footerLabel1 = new JLabel("Dibuat dengan kasih sayang dan rasa bangga untuk dosen tercinta kami");
        footerLabel1.setForeground(Color.DARK_GRAY);
        gbc.insets = new Insets(10, 0, 2, 0);
        menuPanel.add(footerLabel1, gbc);
        JLabel footerLabelMatkul = new JLabel("Aplikasi ini dibuat untuk memenuhi matakuliah Aplikasi Komputer dan Basis Data");
        footerLabelMatkul.setForeground(Color.GRAY);
        gbc.insets = new Insets(0, 0, 4, 0);
        menuPanel.add(footerLabelMatkul, gbc);
        JLabel footerLabel2 = new JLabel("©EMpatGroup");
        footerLabel2.setForeground(Color.GRAY);
        gbc.insets = new Insets(0, 0, 20, 0);
        menuPanel.add(footerLabel2, gbc);

        setMainContent(menuPanel);
    }
    
    private void showAboutUsDialog() {

        JOptionPane.showMessageDialog(this, new AboutUsPanel(), "About Us", JOptionPane.PLAIN_MESSAGE);
    }
 

    private JButton createMenuButton(String text, String iconPath) {
        JButton button = new JButton(text);
        URL iconUrl = MainFrame.class.getResource(iconPath);
        if (iconUrl != null) {
            ImageIcon originalIcon = new ImageIcon(iconUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        }
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(20);
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.putClientProperty("JButton.buttonType", "roundRect");
        // button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return button;
    }

    private void showMahasiswaPanel() {
        headerPanel.setVisible(true);
        menuBar.setVisible(true);
        updateHeader("Manajemen Data Mahasiswa");
        setMainContent(new MahasiswaPanel());
    }

    private void showNilaiPanel() {
        headerPanel.setVisible(true);
        menuBar.setVisible(true);
        updateHeader("Manajemen Data Nilai");
        setMainContent(new NilaiPanel());
    }

    private void showDosenPanel() {
        headerPanel.setVisible(true);
        menuBar.setVisible(true);
        updateHeader("Manajemen Data Dosen");
        setMainContent(new DosenPanel());
    }

    private void showMataKuliahPanel() {
        headerPanel.setVisible(true);
        menuBar.setVisible(true);
        updateHeader("Manajemen Data Mata Kuliah");
        setMainContent(new MataKuliahPanel());
    }
    
    private void updateHeader(String title) {
        headerTitleLabel.setText(title);
        headerTitleLabel.setIcon(null);
        if (FONT_BOLD != null) {
            headerTitleLabel.setFont(FONT_BOLD.deriveFont(24f));
        }
    }

    private void setMainContent(JPanel contentPanel) {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentAreaPanel.removeAll();
        contentAreaPanel.add(scrollPane, BorderLayout.CENTER);
        contentAreaPanel.revalidate();
        contentAreaPanel.repaint();
    }
    
    private static Font loadCustomFont(String path) {
        try {
            InputStream fontStream = MainFrame.class.getResourceAsStream(path);
            if (fontStream == null) {
                System.err.println("Resource font tidak ditemukan: " + path);
                return null;
            }
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
            return customFont;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            FONT_REGULAR = loadCustomFont("/resources/font/Inter_18pt-Regular.ttf");
            FONT_BOLD = loadCustomFont("/resources/font/Inter_18pt-Bold.ttf");
            FONT_EXTRA_BOLD = loadCustomFont("/resources/font/Inter_18pt-ExtraBold.ttf");

        
            Font defaultFont = (FONT_REGULAR != null) ? FONT_REGULAR.deriveFont(14f) : new Font("Segoe UI", Font.PLAIN, 14);
            Font boldFont = (FONT_BOLD != null) ? FONT_BOLD.deriveFont(14f) : new Font("Segoe UI", Font.BOLD, 14);
            
            
            UIManager.put("Button.font", boldFont); 
            UIManager.put("Label.font", defaultFont);
            UIManager.put("TextField.font", defaultFont);
            UIManager.put("PasswordField.font", defaultFont);
            UIManager.put("TextArea.font", defaultFont);
            UIManager.put("Table.font", defaultFont);
            UIManager.put("TableHeader.font", boldFont);
            UIManager.put("TitledBorder.font", boldFont);
            UIManager.put("Menu.font", defaultFont);
            UIManager.put("MenuItem.font", defaultFont);
            UIManager.put("ComboBox.font", defaultFont);
            UIManager.put("JOptionPane.messageFont", defaultFont);
            
            
            UIManager.put("JOptionPane.buttonFont", defaultFont);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }}