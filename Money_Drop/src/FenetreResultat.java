import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreResultat {
    private int questionNumber = 1;
    private int totalBalance; // Solde initialisé via le constructeur
    private int correctAnswerIndex = 2; // Exemple de bonne réponse (index 0-based)
    private Timer timer;
    private JLabel lblQuestion;
    private JLabel lblBalance;
    private JTextField answerField;
    private JButton btnValidate;
    private JLabel lblTimer;
    private JLabel lblTitle; // Référence directe au JLabel du titre

    public FenetreResultat(String valueFromWheel) {
        // Initialiser le solde avec la valeur de la roue
        totalBalance = Integer.parseInt(valueFromWheel.replace(" $", "").trim());

        JFrame frameResult = new JFrame("Money Drop - Jeu");
        frameResult.setBounds(100, 100, 600, 500);
        frameResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameResult.getContentPane().setLayout(new GridLayout(7, 1));

        // Titre
        JLabel labelTitle = new JLabel("Question N°1", SwingConstants.CENTER);
        labelTitle.setFont(new Font("Arial", Font.BOLD, 24));
        frameResult.getContentPane().add(labelTitle);

        // Question
        lblQuestion = new JLabel("Quelle est la capitale de la France ?", SwingConstants.CENTER);
        lblQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
        frameResult.getContentPane().add(lblQuestion);

        // Réponses possibles (horizontales)
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Réponses alignées horizontalement
        for (int i = 1; i <= 4; i++) {
            JButton btnAnswer = new JButton("Réponse " + i);
            final int answerIndex = i - 1;  // L'index de la réponse, de 0 à 3
            btnAnswer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleAnswerSelection(answerIndex);
                }
            });
            answersPanel.add(btnAnswer);
        }
        frameResult.getContentPane().add(answersPanel);

        // Solde
        lblBalance = new JLabel("Solde: " + totalBalance + " $", SwingConstants.CENTER);
        lblBalance.setFont(new Font("Arial", Font.PLAIN, 18));
        frameResult.getContentPane().add(lblBalance);

        // Champ pour saisir une partie de la somme
        answerField = new JTextField("Saisir montant à miser");
        frameResult.getContentPane().add(answerField);

        // Timer
        lblTimer = new JLabel("Temps restant: 60s", SwingConstants.CENTER);
        frameResult.getContentPane().add(lblTimer);

        // Bouton de validation
        btnValidate = new JButton("Valider");
        frameResult.getContentPane().add(btnValidate);

        // Action sur le bouton
        btnValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour valider la réponse et mettre à jour le solde
                // (ici, vous pouvez ajouter votre logique pour valider la réponse et le montant misé)
                int amount = Integer.parseInt(answerField.getText().trim());
                totalBalance += amount; // Mettre à jour le solde (exemple)
                lblBalance.setText("Solde: " + totalBalance + " $");

                // Passer à la question suivante
                questionNumber++;
                if (questionNumber <= 10) {
                    updateQuestion();
                    resetTimer();
                } else {
                    JOptionPane.showMessageDialog(frameResult, "Vous avez terminé le jeu ! Solde final: " + totalBalance);
                    frameResult.dispose();
                }
            }
        });

        // Initialisation du timer
        startTimer();

        frameResult.setVisible(true);
    }

    private void handleAnswerSelection(int selectedAnswerIndex) {
        // Récupérer le montant misé
        int wagerAmount = Integer.parseInt(answerField.getText().trim());

        // Si la réponse sélectionnée est correcte
        if (selectedAnswerIndex == correctAnswerIndex) {
            // L'argent misé est doublé
            totalBalance += wagerAmount;
            JOptionPane.showMessageDialog(null, "Bonne réponse! Vous avez gagné " + wagerAmount + "!");
        } else {
            // L'argent misé est perdu
            totalBalance -= wagerAmount;
            JOptionPane.showMessageDialog(null, "Mauvaise réponse! Vous avez perdu " + wagerAmount + ".");
        }

        // Mettre à jour l'affichage du solde
        lblBalance.setText("Solde: " + totalBalance + " $");
    }

    private void updateQuestion() {
    	// Mettre à jour la question
        lblQuestion.setText("Nouvelle question (par exemple) ?");
        // Mettre à jour le titre avec le numéro de la question
        lblTitle.setText("Question N°" + questionNumber);
    }

    private void startTimer() {
        // Timer de 60 secondes
        int delay = 1000; // 1 seconde
        int period = 1000; // 1 seconde

        timer = new Timer(delay, new ActionListener() {
            private int timeLeft = 60;

            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                lblTimer.setText("Temps restant: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Le temps est écoulé !");
                    questionNumber++;
                    if (questionNumber <= 10) {
                        updateQuestion();
                        resetTimer();
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous avez terminé le jeu ! Solde final: " + totalBalance);
                    }
                }
            }
        });

        timer.start();
    }

    private void resetTimer() {
        // Redémarrer le timer
        timer.restart();
    }
}
