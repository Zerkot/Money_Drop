public class Question {
    private String texte;
    private String[] réponses;
    private int bonneRéponseIndex;

    public Question(String texte, String[] réponses, int bonneRéponseIndex) {
        this.texte = texte;
        this.réponses = réponses;
        this.bonneRéponseIndex = bonneRéponseIndex;
    }

    public String getTexte() {
        return texte;
    }

    public String[] getRéponses() {
        return réponses;
    }

    public int getBonneRéponseIndex() {
        return bonneRéponseIndex;
    }
}
