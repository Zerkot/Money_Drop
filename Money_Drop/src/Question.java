public class Question {
    private long id;
    private String question;
    private String[] propositions;
    private String correctAnswer;
    private String anecdote;

    // Constructor
    public Question(long id, String question, String[] propositions, String correctAnswer, String anecdote) {
        this.id = id;
        this.question = question;
        this.propositions = propositions;
        this.correctAnswer = correctAnswer;
        this.anecdote = anecdote;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getPropositions() {
        return propositions;
    }

    public void setPropositions(String[] propositions) {
        this.propositions = propositions;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getAnecdote() {
        return anecdote;
    }

    public void setAnecdote(String anecdote) {
        this.anecdote = anecdote;
    }
}
