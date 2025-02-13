import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FenetreResultat {
    private int questionNumber = 1;
    private int totalBalance; // Solde initialisé via le constructeur
    private int correctAnswerIndex; // Index de la bonne réponse, à calculer en fonction des propositions
    private Timer timer;
    private JLabel lblQuestion;
    private JLabel lblBalance;
    private JTextField[] wagerFields; // Champs pour les mises
    private JButton btnValidate;
    private JLabel lblTimer;
    private JLabel lblTitle;
    private List<Question> questions; // Liste des questions
    private int currentQuestionIndex = 0;

    public FenetreResultat(String valueFromWheel) {
        // Charger les questions depuis le fichier JSON
        questions = QuestionLoader.loadQuestionsFromJSON("C:/projetjava/Money_Drop/Money_Drop/question_test.json");
        
        // Vérifier si les questions sont bien chargées
        System.out.println("Questions chargées : ");
        for (Question q : questions) {
            System.out.println(q.getQuestion());
        }
        
        // Initialiser le solde avec la valeur de la roue
        totalBalance = Integer.parseInt(valueFromWheel.replace(" $", "").trim());

        // Fenêtre d'introduction avant de commencer à jouer
        JFrame introFrame = new JFrame("Introduction");
        introFrame.setBounds(100, 100, 900, 500);
        introFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        introFrame.setVisible(true);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.getContentPane().setLayout(new GridLayout(3, 1));

        // Titre d'introduction
        JLabel lblIntroTitle = new JLabel("Etes-vous prêt à commencer à jouer à Money Flop ?", SwingConstants.CENTER);
        lblIntroTitle.setFont(new Font("Arial", Font.BOLD, 24));
        introFrame.getContentPane().add(lblIntroTitle);

        // Message avec le solde initial
        String initialBalance = "Votre solde commence à " + totalBalance + " $";
        JLabel lblBalanceMessage = new JLabel(initialBalance, SwingConstants.CENTER);
        lblBalanceMessage.setFont(new Font("Arial", Font.PLAIN, 20));
        introFrame.getContentPane().add(lblBalanceMessage);

        // Bouton "Commencer"
        JButton btnStart = new JButton("Commencer");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer l'introduction et ouvrir la première question
                introFrame.dispose();
                openQuestionWindow();
            }
        });
        introFrame.getContentPane().add(btnStart);

        introFrame.setVisible(true);
    }

    private void openQuestionWindow() {
        // Fenêtre pour les questions
        JFrame frameResult = new JFrame("Money Flop");
        frameResult.setBounds(100, 100, 600, 500);
        frameResult.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameResult.setVisible(true);
        frameResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameResult.getContentPane().setLayout(new GridLayout(8, 1));
        
        if (questions != null && !questions.isEmpty()) {
            // Continue avec l'accès à la première question
            Question question = questions.get(0);
            // Affichage ou traitement de la question
        } else {
            // Gérer le cas où il n'y a pas de questions chargées
            System.out.println("Aucune question disponible.");
        }

        
        // Titre de la question
        lblTitle = new JLabel("Question N°1", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        frameResult.getContentPane().add(lblTitle);

        // Question
        lblQuestion = new JLabel(questions.get(currentQuestionIndex).getQuestion(), SwingConstants.CENTER);
        lblQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
        frameResult.getContentPane().add(lblQuestion);

        // Réponses possibles (horizontales avec champs de mise)
        JPanel answersPanel = new JPanel();
        answersPanel.setLayout(new GridLayout(2, 4, 10, 10));

        wagerFields = new JTextField[4];
        String[] propositions = questions.get(currentQuestionIndex).getPropositions();
        correctAnswerIndex = getCorrectAnswerIndex(propositions, questions.get(currentQuestionIndex).getCorrectAnswer());

        for (int i = 0; i < 4; i++) {
            JLabel btnAnswer = new JLabel(propositions[i]);

            JTextField wagerField = new JTextField();
            wagerFields[i] = wagerField;

            // Ajout du DocumentListener pour surveiller les changements dans chaque champ de mise
            wagerField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    checkWagers();  // Vérifier les mises après chaque modification
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    checkWagers();  // Vérifier les mises après chaque suppression
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    checkWagers();  // Vérifier les mises après un changement
                }
            });

            answersPanel.add(btnAnswer);
            answersPanel.add(wagerField);
        }
        frameResult.getContentPane().add(answersPanel);

        // Solde
        lblBalance = new JLabel("Solde: " + totalBalance + " $", SwingConstants.CENTER);
        lblBalance.setFont(new Font("Arial", Font.PLAIN, 18));
        frameResult.getContentPane().add(lblBalance);

        // Timer
        lblTimer = new JLabel("Temps restant: 60s", SwingConstants.CENTER);
        frameResult.getContentPane().add(lblTimer);

        // Bouton de validation
        btnValidate = new JButton("Valider");
        btnValidate.setEnabled(false); // Désactivé par défaut
        frameResult.getContentPane().add(btnValidate);

        // Action sur le bouton de validation
        btnValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour valider la réponse et mettre à jour le solde
                validateWagers();
                questionNumber++;
                if (questionNumber <= questions.size()) {
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

    private int getCorrectAnswerIndex(String[] propositions, String correctAnswer) {
        for (int i = 0; i < propositions.length; i++) {
            if (propositions[i].equals(correctAnswer)) {
                return i;
            }
        }
        return -1; // Si la bonne réponse n'est pas trouvée
    }

    private void validateWagers() {
        int totalWagered = 0;
        int wagerOnCorrectAnswer = 0;

        try {
            for (int i = 0; i < wagerFields.length; i++) {
                String text = wagerFields[i].getText().trim();
                
                // Si la case est vide, la considérer comme 0
                int wager = text.isEmpty() ? 0 : Integer.parseInt(text);
                
                totalWagered += wager;

                // Sauvegarder la mise sur la bonne réponse
                if (i == correctAnswerIndex) {
                    wagerOnCorrectAnswer = wager;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer des nombres valides dans les champs de mise.");
            return;
        }

        // Si tout le solde n'est pas misé, considérer le non-misé comme perdu
        if (totalWagered < totalBalance) {
            JOptionPane.showMessageDialog(null, "Attention : Vous avez perdu " + (totalBalance - totalWagered) + " $ qui n'ont pas été misés !");
        }

        // Calculer le nouveau solde en doublant la mise sur la bonne réponse
        int newBalance = wagerOnCorrectAnswer * 2;

        if (wagerOnCorrectAnswer == 0) {
            JOptionPane.showMessageDialog(null, "Vous avez perdu ! Vous n'avez rien misé sur la bonne réponse.");
            totalBalance = 0;
        } else {
            JOptionPane.showMessageDialog(null, "Bravo ! Votre mise sur la bonne réponse a été doublée : " + wagerOnCorrectAnswer + " x 2 = " + newBalance + " $");
            totalBalance = newBalance;
        }

        // Vérifier si le joueur peut continuer
        if (totalBalance == 0) {
            JOptionPane.showMessageDialog(null, "Vous avez perdu tout votre solde. Fin du jeu !");
            System.exit(0);
        }
        
        // Passer à la question suivante
        questionNumber++;
        if (questionNumber <= questions.size()) {
            updateQuestion();
            resetTimer();  // Redémarrer le timer pour la nouvelle question
        } else {
        	timer.stop();
            JOptionPane.showMessageDialog(null, "Vous avez terminé le jeu ! Solde final: " + totalBalance);
            System.exit(0);
        }
    }

    private void checkWagers() {
        boolean allFieldsValid = true;

        // Vérifier que toutes les mises sont valides et activer/désactiver le bouton de validation
        for (int i = 0; i < wagerFields.length; i++) {
            String text = wagerFields[i].getText().trim();
            if (!text.isEmpty()) {
                try {
                    // Vérifier que la mise est un nombre entier valide
                    int wager = Integer.parseInt(text);
                    if (wager < 0 || wager > totalBalance) {
                        allFieldsValid = false;  // Si la mise est invalide, désactiver le bouton
                    }
                } catch (NumberFormatException e) {
                    allFieldsValid = false;  // Si la mise n'est pas un nombre valide, désactiver le bouton
                }
            }
        }

        // Activer/désactiver le bouton de validation en fonction de l'état des mises
        btnValidate.setEnabled(allFieldsValid);
    }
    
    private void updateQuestion() {
        // Mettre à jour la question
        Question currentQuestion = questions.get(currentQuestionIndex);
        lblQuestion.setText(currentQuestion.getQuestion());
        lblTitle.setText("Question N°" + questionNumber);
        lblBalance.setText("Solde: " + totalBalance + " $");

        // Mettre à jour les propositions
        String[] propositions = currentQuestion.getPropositions();
        correctAnswerIndex = getCorrectAnswerIndex(propositions, currentQuestion.getCorrectAnswer());

        for (int i = 0; i < 4; i++) {
            wagerFields[i].setText("");  // Réinitialiser les champs de mise
        }

        // Vérifier l'état des mises après réinitialisation
        checkWagers();
    }

    private void startTimer() {
        final int[] timeRemaining = {60};

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining[0] <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(null, "Le temps est écoulé. Fin du jeu !");
                    System.exit(0);
                } else {
                    timeRemaining[0]--;
                    lblTimer.setText("Temps restant: " + timeRemaining[0] + "s");
                }
            }
        });
        timer.start();
    }

    private void resetTimer() {
        if (timer != null) {
            timer.stop();
        }
        startTimer();  // Redémarrer le timer pour la nouvelle question
    }

}
