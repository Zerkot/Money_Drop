import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class WheelPanel extends JPanel {
    private Timer timer;
    private int angle = 0;
    private final String[] values = { "300 $", "400 $", "500 $", "600 $", "700 $", "800 $", "900 $", "1000 $" };
    private final int decelerationTime = 10000; // 10 secondes de décélération
    private long startTime;
    private boolean isSpinning = false;
    private String valueToDisplay;

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
    
    private int generateValidAngle() {
        Random random = new Random();
        int angle;
        while (true) {
            angle = random.nextInt(361); // Génère un angle entre 0 et 360
            if (isValidAngle(angle)) {
                return angle;
            }
        }
    }

    private boolean isValidAngle(int angle) {
        for (int i = 0; i < 360; i += 45) {
            if ((angle >= i - 15 && angle <= i + 15) || (angle >= i + 30 && angle <= i + 45)) {
                return false;
            }
        }
        return true;
    }


    public int startSpin() {
        // Initialiser un angle de départ aléatoire
    	angle = generateValidAngle();
        //angle = (int) (Math.random() * 360);
    	//angle = 22;
        // Calculer la section correspondant à l'angle initial
        int adjustedAngle = (angle) % 360;
        System.out.println(angle);
        System.out.println(adjustedAngle);
        int section = (adjustedAngle / 45) % 8;
        valueToDisplay = values[section]; // Valeur correspondante

        switch (section) {
        case 0:
            valueToDisplay = "400 $";
            break;
        case 1:
            valueToDisplay = "300 $";
            break;
        case 2:
            valueToDisplay = "1000 $";
            break;
        case 3:
            valueToDisplay = "900 $";
            break;
        case 4:
            valueToDisplay = "800 $";
            break;
        case 5:
            valueToDisplay = "700 $";
            break;
        case 6:
            valueToDisplay = "600 $";
            break;
        case 7:
            valueToDisplay = "500 $";
            break;
        default:
            valueToDisplay = "Angle hors des bornes";
            break;
    }
        
        
        // Lancer la rotation
        startTime = System.currentTimeMillis(); // Marquer l'heure de début
        isSpinning = true;
        timer.start();
        return section; // Retourner la section
    }

    private void stopWheel() {
    	// Pop-up unique avec le résultat et une option pour continuer
        int option = JOptionPane.showOptionDialog(
                this,
                "La roue s'est arrêtée sur : " + valueToDisplay,
                "Résultat",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"OK"},
                "OK"
        );
    	// Passer à la fenêtre des résultats si "OK" ou fermeture
        if (option == 0 || option == -1) {
            JFrame topLevelFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (topLevelFrame != null) {
                topLevelFrame.dispose();
            }
            new FenetreResultat(valueToDisplay);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setOpaque(false);
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
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
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
    public String getValueToDisplay() {
        return valueToDisplay;
    }

    private Color getColor(int index) {
        Color[] colors = { Color.RED, Color.PINK, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA, Color.LIGHT_GRAY };
        return colors[index % colors.length];
    }
}
