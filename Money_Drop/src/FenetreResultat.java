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
    private int totalWagered = 0;
    private Timer timer;
    private JLabel lblQuestion;
    private JLabel lblBalance;
    private JLabel lblBalanceRes;
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
        
        // Sélectionner le premier index aléatoirement
        currentQuestionIndex = availableIndices.poll();
        
        // Initialiser le solde avec la valeur de la roue
        totalBalance = Integer.parseInt(valueFromWheel.replace(" $", "").trim());
        
        // Fenêtre d'introduction avant de commencer à jouer
        JFrame introFrame = new JFrame("Introduction");
        introFrame.setBounds(100, 100, 900, 500);
        introFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        introFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        introFrame.getContentPane().setLayout(new BorderLayout());
        introFrame.getContentPane().setBackground(new Color(20, 30, 60)); // Bleu foncé
        
        // Panel principal avec BoxLayout pour réduire la taille
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(20, 30, 60));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        // Titre d'introduction
        String pseudo = Fenetre.getPseudo();  // Récupère le pseudo stocké
        JLabel lblIntroTitle = new JLabel("Bienvenu " + pseudo + ", es-tu prêt à commencer à jouer à Money Flop ?", SwingConstants.CENTER);
        lblIntroTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblIntroTitle.setForeground(Color.WHITE);
        lblIntroTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblIntroTitle);
        
        // Panel des règles du jeu
        JPanel reglesPanel = new JPanel();
        reglesPanel.setBackground(new Color(20, 30, 60));
        reglesPanel.setLayout(new FlowLayout());
        mainPanel.add(reglesPanel);
        JLabel lblRegles = new JLabel("<html><div style='text-align: left; font-size:16px; color:white;'>"
                + "<h2 style='color:white;'>Règles du jeu :</h2>"
                + "1. Le joueur démarre avec un montant défini par la roue de la fortune.<br>"
                + "2. À chaque question, il doit répartir son solde sur les différentes réponses.<br>"
                + "3. Si la réponse est correcte, le joueur double la mise correspondante.<br>"
                + "4. Si la réponse est incorrecte, le joueur perd l'argent misé sur cette option.<br>"
                + "5. Le but est de finir avec le plus d'argent possible après 10 questions.<br>"
                + "6. Si le timer atteint 0 avant validation, uniquement ce qui a été misé est compté et ce qui n'a pas été misé est perdu.<br>"
                + "</div></html>");
        lblRegles.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblRegles.setForeground(Color.WHITE);
        reglesPanel.add(lblRegles);
        mainPanel.add(reglesPanel);
        
        // Panel pour afficher le solde initial
        JPanel balancePanel = new JPanel();
        balancePanel.setBackground(new Color(20, 30, 60));
        JLabel lblBalanceMessage = new JLabel("Votre solde commence à " + totalBalance + " $", SwingConstants.CENTER);
        lblBalanceMessage.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblBalanceMessage.setForeground(Color.WHITE);
        balancePanel.add(lblBalanceMessage);
        mainPanel.add(balancePanel);
        
        // Panel pour l'image
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(new Color(20, 30, 60));
        ImageIcon gameIcon = new ImageIcon(new ImageIcon("money_flop.png").getImage().getScaledInstance(500, 250, Image.SCALE_SMOOTH)); // Redimensionner l'image
        //ImageIcon gameIcon = new ImageIcon("tktpelo.gif");
        JLabel lblImage = new JLabel(gameIcon);
        imagePanel.add(lblImage);
        mainPanel.add(imagePanel);
        
        // Panel du bouton "Commencer"
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(20, 30, 60));
        JButton btnStart = new JButton("Commencer");
        btnStart.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnStart.setBackground(new Color(50, 100, 200)); // Bleu clair
        btnStart.setForeground(Color.WHITE);
        btnStart.setFocusPainted(false);
        btnStart.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fermer l'introduction et ouvrir la première question
                introFrame.dispose();
                openQuestionWindow();
            }
        });
        buttonPanel.add(btnStart);
        mainPanel.add(buttonPanel);
        introFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        introFrame.setVisible(true);
    }

    private void openQuestionWindow() {
    	// Fenêtre pour les questions
    	JFrame frameResult = new JFrame("Money Drop");
    	frameResult.setBounds(100, 100, 600, 500);
    	frameResult.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	frameResult.setVisible(true);
    	frameResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frameResult.getContentPane().setLayout(new GridLayout(8, 1));

    	// Appliquer un thème bleu foncé et clair
    	frameResult.getContentPane().setBackground(new Color(0, 51, 102)); // Bleu foncé

    	// Titre de la question
    	lblTitle = new JLabel("Question N°1", SwingConstants.CENTER);
    	lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
    	lblTitle.setForeground(Color.WHITE); // Texte en blanc
    	frameResult.getContentPane().add(lblTitle);

    	// Question
    	lblQuestion = new JLabel(questions.get(currentQuestionIndex).getQuestion(), SwingConstants.CENTER);
    	lblQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
    	lblQuestion.setForeground(Color.WHITE); // Texte en blanc
    	frameResult.getContentPane().add(lblQuestion);

    	// Réponses possibles (horizontales avec champs de mise)
    	JPanel answersPanel = new JPanel();
    	answersPanel.setLayout(new GridLayout(2, 4, 10, 10));
    	answersPanel.setBackground(new Color(0, 51, 102)); // Même bleu foncé

    	wagerFields = new JTextField[4];
    	String[] propositions = questions.get(currentQuestionIndex).getPropositions();
    	correctAnswerIndex = getCorrectAnswerIndex(propositions, questions.get(currentQuestionIndex).getCorrectAnswer());

    	// Initialiser le tableau des labels de réponses
    	answerLabels = new JLabel[4];

    	for (int i = 0; i < 4; i++) {
    	    answerLabels[i] = new JLabel(propositions[i], SwingConstants.CENTER);
    	    answerLabels[i].setFont(new Font("Arial", Font.BOLD, 16));
    	    answerLabels[i].setForeground(Color.WHITE); // Texte des réponses en blanc
    	    wagerFields[i] = new JTextField();
    	    wagerFields[i].setBackground(new Color(255, 255, 255)); // Champs de mise en blanc
    	    wagerFields[i].setForeground(new Color(0, 51, 102)); // Texte des mises en bleu foncé

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
    	lblBalance.setForeground(Color.WHITE); // Texte en blanc
    	frameResult.getContentPane().add(lblBalance);
    	
    	// Solde restante
    	//lblBalanceRes = new JLabel("Solde: " + (totalBalance - totalWagered) + " $", SwingConstants.CENTER);
    	lblBalanceRes = new JLabel("Solde restante: " + (totalBalance - 0) + " $", SwingConstants.CENTER);
    	lblBalanceRes.setFont(new Font("Arial", Font.PLAIN, 18));
    	lblBalanceRes.setForeground(Color.WHITE); // Texte en blanc
    	frameResult.getContentPane().add(lblBalanceRes);

    	// Timer
    	lblTimer = new JLabel("Temps restant: 60s", SwingConstants.CENTER);
    	lblTimer.setFont(new Font("Arial", Font.PLAIN, 18));
    	lblTimer.setForeground(Color.WHITE); // Texte en blanc
    	frameResult.getContentPane().add(lblTimer);

    	// Bouton de validation
    	btnValidate = new JButton("Valider");
    	btnValidate.setEnabled(false); // Désactivé par défaut
    	btnValidate.setBackground(new Color(0, 102, 204)); // Bouton bleu clair
    	btnValidate.setForeground(Color.WHITE); // Texte du bouton en blanc
    	frameResult.getContentPane().add(btnValidate);


        // Action sur le bouton de validation
        btnValidate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique pour valider la réponse et mettre à jour le solde
            	timer.stop();
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
            JOptionPane.showMessageDialog(null, "Vous avez perdu ! Vous n'avez rien misé sur la bonne réponse. \n \n La bonne réponse était: " + questions.get(currentQuestionIndex).getCorrectAnswer() + "\n \n Anecdote: " + questions.get(currentQuestionIndex).getAnecdote());
            totalBalance = 0;
        } else {
            JOptionPane.showMessageDialog(null, "Bravo ! La bonne réponse était : " + questions.get(currentQuestionIndex).getCorrectAnswer() + " Votre mise sur la bonne réponse a été doublée : " + wagerOnCorrectAnswer + " x 2 = " + newBalance + " $" + "\n \n Anecdote: " + questions.get(currentQuestionIndex).getAnecdote());
            totalBalance = newBalance;
        }

        // Vérifier si le joueur peut continuer
        if (totalBalance == 0) {
            JOptionPane.showMessageDialog(null, "Vous avez perdu tout votre solde. Fin du jeu !");
            System.exit(0);
        }

        if (questionNumber <= 9) { // Limite à 10 questions
            updateQuestion();
            resetTimer();
        } else {
            JOptionPane.showMessageDialog(null, "Vous avez terminé le jeu après 10 questions ! Solde final: " + totalBalance + " $");
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
        lblBalanceRes.setText("Solde restante: " + (totalBalance - totalWagered) + " $");
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
        final int[] timeRemaining = {90};

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
