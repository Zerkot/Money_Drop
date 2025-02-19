import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FenetreResultat {
    private int questionNumber = 1;
    private int totalBalance;
    private int correctAnswerIndex;
    JFrame frameResult;
    private List<Question> questions;
    private int currentQuestionIndex;
    private LinkedList<Integer> availableIndices = new LinkedList<>();
    private JLabel lblQuestion, lblBalance, lblBalanceRes, lblTimer, lblTitle;
    private JTextField[] wagerFields;
    private JButton btnValidate;
    private JLabel[] answerLabels;
    private TimerManager timerManager;

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
        frameResult = new JFrame("Money Drop");
        frameResult.setBounds(100, 100, 600, 500);
        frameResult.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameResult.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameResult.getContentPane().setLayout(new GridLayout(8, 1));
        frameResult.getContentPane().setBackground(new Color(0, 51, 102));

        lblTitle = new JLabel("Question N°1", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        frameResult.getContentPane().add(lblTitle);

        lblQuestion = new JLabel(questions.get(currentQuestionIndex).getQuestion(), SwingConstants.CENTER);
        lblQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
        lblQuestion.setForeground(Color.WHITE);
        frameResult.getContentPane().add(lblQuestion);

        JPanel answersPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        answersPanel.setBackground(new Color(0, 51, 102));

        wagerFields = new JTextField[4];
        answerLabels = new JLabel[4];
        String[] propositions = questions.get(currentQuestionIndex).getPropositions();
        correctAnswerIndex = getCorrectAnswerIndex(propositions, questions.get(currentQuestionIndex).getCorrectAnswer());

        for (int i = 0; i < 4; i++) {
            answerLabels[i] = new JLabel(propositions[i], SwingConstants.CENTER);
            answerLabels[i].setFont(new Font("Arial", Font.BOLD, 16));
            answerLabels[i].setForeground(Color.WHITE);
            wagerFields[i] = new JTextField();

            wagerFields[i].getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) { checkWagers(); }
                public void removeUpdate(DocumentEvent e) { checkWagers(); }
                public void changedUpdate(DocumentEvent e) { checkWagers(); }
            });

            answersPanel.add(answerLabels[i]);
            answersPanel.add(wagerFields[i]);
        }
        frameResult.getContentPane().add(answersPanel);

        lblBalance = new JLabel("Solde: " + totalBalance + " $", SwingConstants.CENTER);
        lblBalance.setFont(new Font("Arial", Font.PLAIN, 18));
        lblBalance.setForeground(Color.WHITE);
        frameResult.getContentPane().add(lblBalance);

        lblBalanceRes = new JLabel("Solde restante: " + totalBalance + " $", SwingConstants.CENTER);
        lblBalanceRes.setFont(new Font("Arial", Font.PLAIN, 18));
        lblBalanceRes.setForeground(Color.WHITE);
        frameResult.getContentPane().add(lblBalanceRes);

        lblTimer = new JLabel("Temps restant: 90s", SwingConstants.CENTER);
        lblTimer.setFont(new Font("Arial", Font.PLAIN, 18));
        lblTimer.setForeground(Color.WHITE);
        frameResult.getContentPane().add(lblTimer);

        btnValidate = new JButton("Valider");
        btnValidate.setEnabled(false);
        btnValidate.setBackground(new Color(0, 102, 204));
        btnValidate.setForeground(Color.WHITE);
        btnValidate.addActionListener(e -> {
            timerManager.stopTimer();
            validateWagers();
            questionNumber++;
            if (questionNumber <= questions.size()) {
                updateQuestion();
                timerManager.resetTimer(90);
            } else {
                JOptionPane.showMessageDialog(frameResult, "Vous avez terminé le jeu ! Solde final: " + totalBalance);
                frameResult.dispose();
            }
        });

        frameResult.getContentPane().add(btnValidate);
        timerManager = new TimerManager(lblTimer, () -> {
            JOptionPane.showMessageDialog(null, "Temps écoulé !");
            validateWagers();
            questionNumber++;
            if (questionNumber <= questions.size()) {
                updateQuestion();
                timerManager.resetTimer(90);
            } else {
                JOptionPane.showMessageDialog(frameResult, "Vous avez terminé le jeu !");
                frameResult.dispose();
            }
        });

        timerManager.startTimer(90);
        frameResult.setVisible(true);
    }

    private int getCorrectAnswerIndex(String[] propositions, String correctAnswer) {
        for (int i = 0; i < propositions.length; i++) {
            if (propositions[i].equals(correctAnswer)) {
                return i;
            }
        }
        return -1;
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
            frameResult.dispose();             
            new FenetreGif().FenetreLose();
        }

        if (questionNumber <= 9) { // Limite à 10 questions
            updateQuestion();
            timerManager.resetTimer(90);
        } else {
            JOptionPane.showMessageDialog(null, "Vous avez terminé le jeu après 10 questions ! Solde final: " + totalBalance + " $");
            frameResult.dispose();             
            new FenetreGif().FenetreLose();
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
}
