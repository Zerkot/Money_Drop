import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;

public class FenetreGif {
    
    public void FenetreLose() {
        JFrame WinFrame = new JFrame("Win");
        WinFrame.setBounds(100, 100, 600, 500);
        WinFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        WinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WinFrame.getContentPane().setLayout(new GridLayout(2, 2));
        WinFrame.getContentPane().setBackground(new Color(0, 51, 102)); // Bleu foncé

        // GIF1
        JPanel GIF1 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF1.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon = new ImageIcon("GIFLOSE1.gif");JLabel lblImage = new JLabel(gameIcon);
        GIF1.add(lblImage);
        WinFrame.add(GIF1);
        
     // GIF2
        JPanel GIF2 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF2.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon2 = new ImageIcon("GIFLOSE2.gif");JLabel lblImage2 = new JLabel(gameIcon2);
        GIF2.add(lblImage2);
        WinFrame.add(GIF2);
        
     // GIF2
        JPanel GIF3 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF3.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon3 = new ImageIcon("GIFLOSE3.gif");JLabel lblImage3 = new JLabel(gameIcon3);
        GIF3.add(lblImage3);
        WinFrame.add(GIF3);
        
     // GIF2
        JPanel GIF4 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF4.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon4 = new ImageIcon("GIFLOSE4.gif");JLabel lblImage4 = new JLabel(gameIcon4);
        GIF4.add(lblImage4);
        WinFrame.add(GIF4);
        
        
        WinFrame.setVisible(true); // Déplacer setVisible ici
    }
    
    public void FenetreWin() {
        JFrame WinFrame = new JFrame("Win");
        WinFrame.setBounds(100, 100, 600, 500);
        WinFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        WinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WinFrame.getContentPane().setLayout(new GridLayout(2, 2));
        WinFrame.getContentPane().setBackground(new Color(0, 51, 102)); // Bleu foncé

        // GIF1
        JPanel GIF1 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF1.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon = new ImageIcon("GIFWIN1.gif");JLabel lblImage = new JLabel(gameIcon);
        GIF1.add(lblImage);
        WinFrame.add(GIF1);
        
     // GIF2
        JPanel GIF2 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF2.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon2 = new ImageIcon("GIFWIN2.gif");JLabel lblImage2 = new JLabel(gameIcon2);
        GIF2.add(lblImage2);
        WinFrame.add(GIF2);
        
     // GIF2
        JPanel GIF3 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF3.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon3 = new ImageIcon("GIFWIN3.gif");JLabel lblImage3 = new JLabel(gameIcon3);
        GIF3.add(lblImage3);
        WinFrame.add(GIF3);
        
     // GIF2
        JPanel GIF4 = new JPanel();  // Renommé la variable pour éviter la confusion
        GIF4.setBackground(new Color(20, 30, 60));
        // Chargement de l'image
        ImageIcon gameIcon4 = new ImageIcon("GIFWIN4.gif");JLabel lblImage4 = new JLabel(gameIcon4);
        GIF4.add(lblImage4);
        WinFrame.add(GIF4);
        
        
        WinFrame.setVisible(true); // Déplacer setVisible ici
    }
    
}
