import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
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
    private int currentQuestionIndex;
    private LinkedList<Integer> availableIndices = new LinkedList<>();
    private JLabel[] answerLabels; // Ajouter ceci en tant qu'attribut de la classe
    
    public FenetreResultat(String valueFromWheel) {
        // Charger les questions depuis le fichier JSON
        questions = QuestionLoader.loadQuestionsFromJSON("Question_money_flop.json");
        
     // Initialiser la liste des indices
        for (int i = 0; i < questions.size(); i++) {
            availableIndices.add(i);
        }
        
        // Mélanger la liste pour l'aléatoire
        Collections.shuffle(availableIndices);
        //System.out.println(availableIndices);
        // Sélectionner le premier index aléatoirement
        currentQuestionIndex = availableIndices.poll();
        //System.out.println(currentQuestionIndex);
        // Vérifier si les questions sont bien chargées
//        System.out.println("Questions chargées : ");
//        for (Question q : questions) {
//            System.out.println(q);
//        }
        
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
        lblTitle = new JLabel("Question N°1" , SwingConstants.CENTER);
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

     // Initialiser le tableau des labels de réponses
        answerLabels = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            answerLabels[i] = new JLabel(propositions[i], SwingConstants.CENTER);
            wagerFields[i] = new JTextField();

            // Ajout du DocumentListener pour surveiller les mises
            wagerFields[i].getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    checkWagers();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    checkWagers();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    checkWagers();
                }
            });

            answersPanel.add(answerLabels[i]);
            answersPanel.add(wagerFields[i]);
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
                System.out.println(questionNumber);
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
            JOptionPane.showMessageDialog(null, "Bravo ! La bonne réponse était : " + questions.get(currentQuestionIndex).getCorrectAnswer() + " Votre mise sur la bonne réponse a été doublée : " + wagerOnCorrectAnswer + " x 2 = " + newBalance + " $");
            totalBalance = newBalance;
        }

        // Vérifier si le joueur peut continuer
        if (totalBalance == 0) {
            JOptionPane.showMessageDialog(null, "Vous avez perdu tout votre solde. Fin du jeu !");
            System.exit(0);
        }

        if (questionNumber <= 10) { // Limite à 10 questions
            updateQuestion();
            resetTimer();
        } else {
            JOptionPane.showMessageDialog(null, "Vous avez terminé le jeu après 10 questions ! Solde final: " + totalBalance);
            System.exit(0);
        }

        
    }

    private void checkWagers() {
        int totalWagered = 0;
        boolean allFieldsValid = true;

        for (JTextField field : wagerFields) {
            String text = field.getText().trim();
            if (!text.isEmpty()) {
                try {
                    int wager = Integer.parseInt(text);
                    if (wager < 0 || wager > totalBalance) {
                        allFieldsValid = false; // La mise doit être dans l'intervalle valide
                    }
                    totalWagered += wager;
                } catch (NumberFormatException e) {
                    allFieldsValid = false; // Vérifier que c'est bien un nombre
                }
            }
        }

        // Activer le bouton seulement si la somme des mises est égale au solde
        btnValidate.setEnabled(allFieldsValid && totalWagered == totalBalance);
    }
    
    private void updateQuestion() {
        if (!availableIndices.isEmpty()) {
            currentQuestionIndex = availableIndices.poll(); // Prend et retire l'élément
        } else {
            JOptionPane.showMessageDialog(null, "Toutes les questions ont été posées !");
            System.exit(0); // Quitter quand toutes les questions ont été posées
        }

        // Mettre à jour la question
        Question currentQuestion = questions.get(currentQuestionIndex);
        lblQuestion.setText(currentQuestion.getQuestion());
        lblTitle.setText("Question N°" + questionNumber);
        lblBalance.setText("Solde: " + totalBalance + " $");

     // Mettre à jour les propositions de réponses
        String[] propositions = currentQuestion.getPropositions();
        correctAnswerIndex = getCorrectAnswerIndex(propositions, currentQuestion.getCorrectAnswer());

        for (int i = 0; i < 4; i++) {
            answerLabels[i].setText(propositions[i]); // Mise à jour des labels des réponses
            wagerFields[i].setText(""); // Réinitialiser les champs de mise
        }

        // Réinitialiser les champs de mise
        for (int i = 0; i < wagerFields.length; i++) {
            wagerFields[i].setText("");
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

                    // Vérifier la somme des mises lorsque le temps est écoulé
                    int totalWagered = 0;
                    boolean wagerValid = true;

                    try {
                        // Calculer la somme des mises
                        for (int i = 0; i < wagerFields.length; i++) {
                            String text = wagerFields[i].getText().trim();
                            if (!text.isEmpty()) {
                                int wager = Integer.parseInt(text);
                                totalWagered += wager;
                            }
                        }

                        // Si la somme des mises est supérieure au solde
                        if (totalWagered > totalBalance) {
                            wagerValid = false;
                        }

                    } catch (NumberFormatException ex) {
                        wagerValid = false; // En cas d'erreur de format de nombre
                    }

                    if (!wagerValid) {
                        JOptionPane.showMessageDialog(null, "Le temps est écoulé et vous avez misé plus que votre solde ! Vous avez perdu.");
                        System.exit(0); // Terminer le jeu
                    } else {
                        // Si la somme des mises est valide, on peut continuer
                        JOptionPane.showMessageDialog(null, "Le temps est écoulé. Vous pouvez continuer.");
                        validateWagers(); // Valider les mises pour procéder
                        questionNumber++;
                        if (questionNumber <= questions.size()) {
                            updateQuestion();
                            resetTimer();
                        } else {
                            JOptionPane.showMessageDialog(null, "Vous avez terminé le jeu ! Solde final: " + totalBalance);
                            System.exit(0); // Fin du jeu
                        }
                    }
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
