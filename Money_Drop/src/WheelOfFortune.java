import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WheelOfFortune extends JPanel {
    private static final int DIAMETER = 300; // Diamètre de la roue
    private static final String[] VALUES = {"100 €", "200 €", "500 €", "1000 €", "2000 €", "5000 €"}; // Les sommes d'argent
    private double angle = 0; // Angle actuel de la roue
    private Timer timer;
    private double speed = 0; // Vitesse de rotation
    private boolean spinning = false;

    public WheelOfFortune() {
        setPreferredSize(new Dimension(400, 400));
        
        // Bouton pour faire tourner la roue
        JButton spinButton = new JButton("Tourner la roue !");
        spinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!spinning) {
                    startSpin();
                }
            }
        });

        setLayout(new BorderLayout());
        add(spinButton, BorderLayout.SOUTH);
    }

    private void startSpin() {
        spinning = true;
        speed = 10 + new Random().nextDouble() * 5; // Vitesse initiale aléatoire
        timer = new Timer(20, new ActionListener() { // Timer pour l'animation
            @Override
            public void actionPerformed(ActionEvent e) {
                angle += speed;
                speed *= 0.97; // Réduire progressivement la vitesse
                if (speed < 0.1) { // Arrêter la roue
                    timer.stop();
                    spinning = false;
                    String selectedValue = getSelectedValue();
                    JOptionPane.showMessageDialog(WheelOfFortune.this, 
                            "Vous avez gagné : " + selectedValue, "Résultat", JOptionPane.INFORMATION_MESSAGE);
                }
                repaint();
            }
        });
        timer.start();
    }

    private String getSelectedValue() {
        int segment = (int)((angle % 360) / (360.0 / VALUES.length));
        return VALUES[(VALUES.length - segment - 1) % VALUES.length];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner la roue
        int radius = DIAMETER / 2;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        for (int i = 0; i < VALUES.length; i++) {
            g2d.setColor(i % 2 == 0 ? Color.CYAN : Color.ORANGE);
            double startAngle = i * (360.0 / VALUES.length) + angle;
            g2d.fillArc(centerX - radius, centerY - radius, DIAMETER, DIAMETER, 
                        (int) startAngle, (int) (360.0 / VALUES.length));
        }

        // Dessiner le texte des montants
        for (int i = 0; i < VALUES.length; i++) {
            double theta = Math.toRadians(i * (360.0 / VALUES.length) + angle + (360.0 / (2 * VALUES.length)));
            int textX = (int) (centerX + Math.cos(theta) * radius * 0.7);
            int textY = (int) (centerY - Math.sin(theta) * radius * 0.7);
            g2d.setColor(Color.BLACK);
            g2d.drawString(VALUES[i], textX - 15, textY);
        }

        // Dessiner l'aiguille
        g2d.setColor(Color.RED);
        g2d.fillPolygon(new int[]{centerX, centerX - 10, centerX + 10}, 
                        new int[]{centerY - radius - 10, centerY - radius + 10, centerY - radius + 10}, 3);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Roue de la Fortune");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new WheelOfFortune());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
