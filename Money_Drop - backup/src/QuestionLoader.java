import java.nio.file.Paths;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class QuestionLoader {
    private static final String FILE_PATH = Paths.get("C:", "projetjava", "Money_Drop", "Money_Drop", "Question_money_flop.json").toString();

    public static List<Question> loadQuestionsFromJSON(String filePath) {
        List<Question> questions = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            // Parser le fichier JSON
            Object obj = jsonParser.parse(reader);

            if (!(obj instanceof JSONObject)) {
                throw new RuntimeException("Le fichier JSON doit être un objet JSON valide.");
            }

            JSONObject jsonObject = (JSONObject) obj;
            JSONObject quizJson = (JSONObject) jsonObject.get("quizz");

            if (quizJson == null) {
                throw new RuntimeException("Clé 'quizz' introuvable dans le JSON.");
            }

            // Récupération de l'objet 'fr' qui est un JSONObject
            JSONObject fr = (JSONObject) quizJson.get("fr");

            // Traitement des niveaux comme 'débutant' sous 'fr'
            for (Object level : fr.keySet()) {
                Object levelObj = fr.get(level);

                // Vérification si c'est un JSONArray
                if (levelObj instanceof JSONArray) {
                    JSONArray questionsJsonArray = (JSONArray) levelObj;

                    for (Object questionObject : questionsJsonArray) {
                        JSONObject questionJson = (JSONObject) questionObject;

                        long id = (long) questionJson.get("id");
                        String questionText = (String) questionJson.get("question");
                        JSONArray propositionsJsonArray = (JSONArray) questionJson.get("propositions");

                        String[] propositions = new String[propositionsJsonArray.size()];
                        for (int i = 0; i < propositionsJsonArray.size(); i++) {
                            propositions[i] = (String) propositionsJsonArray.get(i);
                        }

                        String correctAnswer = (String) questionJson.get("réponse");
                        String anecdote = (String) questionJson.get("anecdote");

                        questions.add(new Question(id, questionText, propositions, correctAnswer, anecdote));
                    }
                } else {
                    System.err.println("Erreur : Le niveau '" + level + "' ne contient pas un tableau de questions.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    public static void main(String[] args) {
        List<Question> questions = loadQuestionsFromJSON(FILE_PATH);
        for (Question q : questions) {
            System.out.println(q);
        }
    }
}
