import javax.swing.*;
import java.awt.*;

public class FenetreResultat {

    public FenetreResultat() {
        JFrame newFrame = new JFrame("Nouvelle fenêtre");
        newFrame.setBounds(100, 100, 400, 400);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.getContentPane().setLayout(new GridLayout(5, 1));  // Utilisation d'un GridLayout pour aligner les labels

        // Ajouter le label principal
        JLabel centerLabel = new JLabel("Label Principal", SwingConstants.CENTER);
        centerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        newFrame.getContentPane().add(centerLabel);

        // Ajouter 4 labels alignés en dessous
        for (int i = 0; i < 4; i++) {
            JLabel subLabel = new JLabel("Label " + (i + 1), SwingConstants.CENTER);
            newFrame.getContentPane().add(subLabel);
        }

        newFrame.setVisible(true);
    }
}
