import javax.swing.*;
import java.awt.*;

public class Fenetre {
	
	private static String pseudo;
    JFrame frame;
    private JTextField textField;

    public Fenetre() {
        initialize();
    }
    
 // Getter et setter pour le pseudo

    public static String getPseudo() {

        return pseudo;

    }



    public static void setPseudo(String pseudo) {

        Fenetre.pseudo = pseudo;

    }
    
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1920, 1080);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Charger l'image de fond
        BackgroundPanel backgroundPanel = new BackgroundPanel("backgroundfenetre.jpg");
        backgroundPanel.setLayout(new GridBagLayout()); // GridBagLayout pour un meilleur contrôle
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1; // Remplissage horizontal

        // Label principal
        gbc.gridy = 0;
        gbc.weighty = 0.05; // 10% de la hauteur
        JLabel lblNewLabel = new JLabel("Bienvenue dans Money Flop !");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 42));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setForeground(Color.WHITE);
        backgroundPanel.add(lblNewLabel, gbc);

        // Sous-label
        gbc.gridy = 1;
        gbc.weighty = 0.05; // 5% de la hauteur
        JLabel lblNewLabel_1 = new JLabel("Veuillez rentrer votre pseudo");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setForeground(Color.WHITE);
        backgroundPanel.add(lblNewLabel_1, gbc);

        // Panel pour centrer le JTextField
        gbc.gridy = 2;
        gbc.weighty = 0.05; // 5% de la hauteur
        JPanel textFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textFieldPanel.setOpaque(false);
        textField = new JTextField(15);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textFieldPanel.add(textField);
        backgroundPanel.add(textFieldPanel, gbc);

        // Panel pour centrer le bouton
        gbc.gridy = 3;
        gbc.weighty = 0.05; // 5% de la hauteur
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        JButton btnNewButton = new JButton("Valider");
        buttonPanel.add(btnNewButton);
        backgroundPanel.add(buttonPanel, gbc);

        // Panneau de la roue (prend plus de place)
        gbc.gridy = 4;
        gbc.weighty = 0.2785; // 75% de la hauteur
        gbc.fill = GridBagConstraints.BOTH;
        JPanel wheelPanelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wheelPanelContainer.setOpaque(false);
        WheelPanel wheelPanel = new WheelPanel();
        wheelPanel.setPreferredSize(new Dimension(400, 400)); // Exemple : rétrécir la roue (400x400 pixels)
        wheelPanelContainer.add(wheelPanel);
        backgroundPanel.add(wheelPanelContainer, gbc);

        // Définir backgroundPanel comme le contentPane
        frame.setContentPane(backgroundPanel);

        // Action sur le bouton
        btnNewButton.addActionListener(e -> {
            String enteredPseudo = textField.getText().trim();
            if (enteredPseudo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un pseudo avant de lancer la roue !", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
            	Fenetre.setPseudo(enteredPseudo);
                wheelPanel.startSpin();
                btnNewButton.setEnabled(false);
            }
        });

        frame.setVisible(true);
    }

    // Panneau personnalisé pour afficher une image en arrière-plan
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String filePath) {
            backgroundImage = new ImageIcon(filePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        new Fenetre();
    }
}
