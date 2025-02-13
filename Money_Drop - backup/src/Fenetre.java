import javax.swing.*;
import java.awt.*;

public class Fenetre {

    JFrame frame;
    private JTextField textField;

    public Fenetre() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1920, 1920);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer le programme à la fermeture de la fenêtre
        frame.getContentPane().setLayout(null);

        // Taille du JFrame
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        // Label principal
        JLabel lblNewLabel = new JLabel("Bienvenue dans Money Flop !");
        lblNewLabel.setFont(new Font("Univers Light Condensed", Font.BOLD, 34));
        int lblWidth = 355;
        int lblHeight = 30;
        lblNewLabel.setBounds((frameWidth - lblWidth) / 2, 20, lblWidth, lblHeight);
        //lblNewLabel.setBounds((frameWidth - lblWidth) / 1, 20, lblWidth, lblHeight);
        frame.getContentPane().add(lblNewLabel);

        // Sous-label
        JLabel lblNewLabel_1 = new JLabel("Veuillez rentrer votre pseudo");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblWidth = 260;
        lblHeight = 30;
        lblNewLabel_1.setBounds((frameWidth - lblWidth) / 2, 100, lblWidth, lblHeight);
        frame.getContentPane().add(lblNewLabel_1);

        // Champ de texte
        textField = new JTextField();
        int textFieldWidth = 200;
        int textFieldHeight = 30;
        textField.setBounds((frameWidth - textFieldWidth) / 2, 150, textFieldWidth, textFieldHeight);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        // Bouton "Valider"
        JButton btnNewButton = new JButton("Valider");
        int buttonWidth = 120;
        int buttonHeight = 30;
        btnNewButton.setBounds((frameWidth - buttonWidth) / 2, 200, buttonWidth, buttonHeight);
        frame.getContentPane().add(btnNewButton);

        // Panneau de la roue
        WheelPanel wheelPanel = new WheelPanel();
        int wheelPanelSize = 400; // Carré
        wheelPanel.setBounds((frameWidth - wheelPanelSize) / 2, 250, wheelPanelSize, wheelPanelSize);
        frame.getContentPane().add(wheelPanel);

     // Action sur le bouton
        btnNewButton.addActionListener(e -> {
            // Vérifier si le champ de texte est vide
            String pseudo = textField.getText().trim();
            if (pseudo.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez entrer un pseudo avant de lancer la roue !", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                wheelPanel.startSpin();
                btnNewButton.setEnabled(false);
                wheelPanel.getValueToDisplay(); // Obtenez la valeur de la roue
                //new FenetreResultat(value); // Passer la valeur à FenetreResultat
            }
        });

    }
}

