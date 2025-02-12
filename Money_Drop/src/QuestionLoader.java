import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

constant filepath = "C:\tktpelooo\Money_Drop\Money_Drop";
public class QuestionLoader {

    public static List<Question> loadQuestionsFromJSON(String filePath) {
        List<Question> questions = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(filePath)) {
            // Parse the entire JSON file
            Object obj = jsonParser.parse(reader);

            // Get the "quizz" object and then extract the specific levels
            JSONObject quizJson = (JSONObject) ((JSONObject) obj).get("quizz");

            // Process each level (e.g., débutant, confirmé, expert)
            for (Object level : quizJson.keySet()) {
                JSONArray questionsJsonArray = (JSONArray) quizJson.get(level);
                
                for (Object questionObject : questionsJsonArray) {
                    JSONObject questionJson = (JSONObject) questionObject;

                    // Extract question details
                    long id = (long) questionJson.get("id");
                    String questionText = (String) questionJson.get("question");
                    JSONArray propositionsJsonArray = (JSONArray) questionJson.get("propositions");
                    String[] propositions = new String[propositionsJsonArray.size()];
                    for (int i = 0; i < propositionsJsonArray.size(); i++) {
                        propositions[i] = (String) propositionsJsonArray.get(i);
                    }
                    String correctAnswer = (String) questionJson.get("réponse");
                    String anecdote = (String) questionJson.get("anecdote");

                    // Add the question to the list
                    questions.add(new Question(id, questionText, propositions, correctAnswer, anecdote));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}
