import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FenetreGif {

    public void FenetreLose() {
    	
        JFrame LoseFrame = new JFrame("Lose");
        LoseFrame.setBounds(100, 100, 600, 500);
        LoseFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        LoseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LoseFrame.getContentPane().setLayout(new GridBagLayout());
        LoseFrame.getContentPane().setBackground(new Color(20, 30, 60));

        JPanel gifPanel = new JPanel();
        gifPanel.setLayout(new GridLayout(2, 2));
        gifPanel.setBackground(new Color(0, 51, 102));

        // GIF1 lose
        JPanel GIF1 = new JPanel();
        GIF1.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon = new ImageIcon("ressources/GIFLOSE1.gif");
        JLabel lblImage = new JLabel(gameIcon);
        GIF1.add(lblImage);
        gifPanel.add(GIF1);

        // GIF2 lose
        JPanel GIF2 = new JPanel();
        GIF2.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon2 = new ImageIcon("ressources/GIFLOSE2.gif");
        JLabel lblImage2 = new JLabel(gameIcon2);
        GIF2.add(lblImage2);
        gifPanel.add(GIF2);

        // GIF3 lose
        JPanel GIF3 = new JPanel();
        GIF3.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon3 = new ImageIcon("ressources/GIFLOSE3.gif");
        JLabel lblImage3 = new JLabel(gameIcon3);
        GIF3.add(lblImage3);
        gifPanel.add(GIF3);

        // GIF4 lose
        JPanel GIF4 = new JPanel();
        GIF4.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon4 = new ImageIcon("ressources/GIFLOSE4.gif");
        JLabel lblImage4 = new JLabel(gameIcon4);
        GIF4.add(lblImage4);
        gifPanel.add(GIF4);

        GridBagConstraints gbcGifPanel = new GridBagConstraints();
        gbcGifPanel.gridx = 0;
        gbcGifPanel.gridy = 0;
        gbcGifPanel.weightx = 1;
        gbcGifPanel.weighty = 1;
        gbcGifPanel.fill = GridBagConstraints.BOTH;
        LoseFrame.getContentPane().add(gifPanel, gbcGifPanel);

        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 30, 60));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0)); // Espacement de 30 pixels du bas

        JButton replayButton = new JButton("Rejouer");
        JButton quitButton = new JButton("Quitter");

        // Style des boutons
        replayButton.setBackground(new Color(30, 144, 255)); // Bleu acier
        replayButton.setForeground(Color.WHITE);
        replayButton.setFocusPainted(false);
        replayButton.setBorderPainted(false);

        quitButton.setBackground(new Color(30, 144, 255)); // Bleu dodger
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);
        quitButton.setBorderPainted(false);
        
        replayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoseFrame.dispose(); // Ferme la fenêtre actuelle
                Fenetre window = new Fenetre();
                window.frame.setVisible(true);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Ferme l'application
            }
        });

        buttonPanel.add(replayButton);
        buttonPanel.add(quitButton);

        GridBagConstraints gbcButtonPanel = new GridBagConstraints();
        gbcButtonPanel.gridx = 0;
        gbcButtonPanel.gridy = 1;
        gbcButtonPanel.weightx = 1;
        gbcButtonPanel.weighty = 0;
        gbcButtonPanel.anchor = GridBagConstraints.SOUTH;
        LoseFrame.getContentPane().add(buttonPanel, gbcButtonPanel);

        LoseFrame.setVisible(true);
    }
    
    public void FenetreWin() {
        JFrame WinFrame = new JFrame("Win");
        WinFrame.setBounds(100, 100, 600, 500);
        WinFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        WinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WinFrame.getContentPane().setLayout(new GridBagLayout());
        WinFrame.getContentPane().setBackground(new Color(20, 30, 60));

        JPanel gifPanel = new JPanel();
        gifPanel.setLayout(new GridLayout(2, 2));
        gifPanel.setBackground(new Color(0, 51, 102));

        // GIF1 win
        JPanel GIF1 = new JPanel();
        GIF1.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon = new ImageIcon("ressources/GIFWIN1.gif");
        JLabel lblImage = new JLabel(gameIcon);
        GIF1.add(lblImage);
        gifPanel.add(GIF1);

        // GIF2 win
        JPanel GIF2 = new JPanel();
        GIF2.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon2 = new ImageIcon("ressources/GIFWIN2.gif");
        JLabel lblImage2 = new JLabel(gameIcon2);
        GIF2.add(lblImage2);
        gifPanel.add(GIF2);

        // GIF3 win
        JPanel GIF3 = new JPanel();
        GIF3.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon3 = new ImageIcon("ressources/GIFWIN3.gif");
        JLabel lblImage3 = new JLabel(gameIcon3);
        GIF3.add(lblImage3);
        gifPanel.add(GIF3);

        // GIF4 win
        JPanel GIF4 = new JPanel();
        GIF4.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon4 = new ImageIcon("ressources/GIFWIN4.gif");
        JLabel lblImage4 = new JLabel(gameIcon4);
        GIF4.add(lblImage4);
        gifPanel.add(GIF4);

        GridBagConstraints gbcGifPanel = new GridBagConstraints();
        gbcGifPanel.gridx = 0;
        gbcGifPanel.gridy = 0;
        gbcGifPanel.weightx = 1;
        gbcGifPanel.weighty = 1;
        gbcGifPanel.fill = GridBagConstraints.BOTH;
        WinFrame.getContentPane().add(gifPanel, gbcGifPanel);

        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 30, 60));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JButton replayButton = new JButton("Rejouer");
        JButton quitButton = new JButton("Quitter");

        // Style des boutons
        replayButton.setBackground(new Color(30, 144, 255));
        replayButton.setForeground(Color.WHITE);
        replayButton.setFocusPainted(false);
        replayButton.setBorderPainted(false);

        quitButton.setBackground(new Color(30, 144, 255)); 
        quitButton.setForeground(Color.WHITE);
        quitButton.setFocusPainted(false);
        quitButton.setBorderPainted(false);

        replayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WinFrame.dispose(); // Ferme la fenêtre actuelle
                Fenetre window = new Fenetre();
                window.frame.setVisible(true);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Ferme l'application
            }
        });

        buttonPanel.add(replayButton);
        buttonPanel.add(quitButton);

        GridBagConstraints gbcButtonPanel = new GridBagConstraints();
        gbcButtonPanel.gridx = 0;
        gbcButtonPanel.gridy = 1;
        gbcButtonPanel.weightx = 1;
        gbcButtonPanel.weighty = 0;
        gbcButtonPanel.anchor = GridBagConstraints.SOUTH;
        WinFrame.getContentPane().add(buttonPanel, gbcButtonPanel);

        WinFrame.setVisible(true);
    }
}
