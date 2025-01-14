import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Fenetre {

    private JFrame frame;
    private JTextField textField;
    private Timer timer;
    private int angle = 0;
    private final String[] values = { "100", "200", "300", "400", "500", "600", "700", "800" };
    private final int decelerationTime = 10000; // 10 secondes de décélération
    private long startTime;
    private boolean isSpinning = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Fenetre window = new Fenetre();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Fenetre() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Bienvenue dans Money Flop !");
        lblNewLabel.setFont(new Font("Univers Light Condensed", Font.BOLD, 34));
        lblNewLabel.setBounds(149, 11, 367, 66);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Veuillez rentrer votre pseudo");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel_1.setBounds(181, 91, 265, 25);
        frame.getContentPane().add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(238, 159, 138, 25);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Valider");
        btnNewButton.setBounds(258, 195, 100, 23);
        frame.getContentPane().add(btnNewButton);

        WheelPanel wheelPanel = new WheelPanel();
        wheelPanel.setBounds(150, 250, 400, 400);
        frame.getContentPane().add(wheelPanel);

        btnNewButton.addActionListener(e -> {
            wheelPanel.startSpin();
        });
    }

    class WheelPanel extends JPanel {
        
        public WheelPanel() {
            timer = new Timer(50, e -> {
                if (isSpinning) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    // Calculer la décélération (vitesse diminuant en fonction du temps)
                    double decelerationFactor = 1 - Math.min(1, (double) elapsedTime / decelerationTime);
                    angle += 10 * decelerationFactor; // Augmenter la vitesse au début

                    repaint();

                    // Si le temps est écoulé, arrêter la roue
                    if (elapsedTime >= decelerationTime) {
                        isSpinning = false;
                        stopWheel();
                    }
                }
            });
        }

        public void startSpin() {
            // Initialiser un angle de départ aléatoire
            angle = (int) (Math.random() * 360);
            startTime = System.currentTimeMillis(); // Marquer l'heure de début
            isSpinning = true;
            timer.start();
        }

        private void stopWheel() {
            // Calculer la valeur sur laquelle la roue s'est arrêtée
            int numSections = values.length;
            int section = (int) ((angle / 360) % numSections);
            String value = values[section];
            JOptionPane.showMessageDialog(frame, "La roue s'est arrêtée sur : " + value);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 2 - 20;

            // Dessiner les sections de la roue
            int numSections = values.length;
            int arcAngle = 360 / numSections;
            for (int i = 0; i < numSections; i++) {
                g2d.setColor(getColor(i));
                g2d.fillArc(centerX - radius, centerY - radius, 2 * radius, 2 * radius, angle + i * arcAngle, arcAngle);

                // Dessiner les valeurs
                g2d.setColor(Color.BLACK);
                double textAngle = Math.toRadians(-(angle + i * arcAngle + arcAngle / 2)); // Inversion du sens
                int textX = (int) (centerX + Math.cos(textAngle) * (radius * 0.7));
                int textY = (int) (centerY + Math.sin(textAngle) * (radius * 0.7));

                // Centrer les nombres
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(values[i]);
                int textHeight = fm.getHeight();

                g2d.drawString(values[i], textX - textWidth / 2, textY + textHeight / 4);
            }

            // Dessiner l'aiguille
            g2d.setColor(Color.BLACK);
            g2d.fillPolygon(
                new int[] { centerX, centerX - 10, centerX + 10 },
                new int[] { centerY - radius, centerY - radius + 20, centerY - radius + 20 },
                3
            );
        }

        private Color getColor(int index) {
            Color[] colors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.PINK };
            return colors[index % colors.length];
        }
    }
}
