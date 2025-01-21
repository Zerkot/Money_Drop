import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuestionLoader {

    public static List<Question> loadQuestionsFromXML(String filePath) {
        List<Question> questions = new ArrayList<>();
        
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            
            NodeList questionNodes = document.getElementsByTagName("question");

            for (int i = 0; i < questionNodes.getLength(); i++) {
                Node questionNode = questionNodes.item(i);

                if (questionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element questionElement = (Element) questionNode;

                    // Extraire le texte de la question
                    String questionText = questionElement.getElementsByTagName("texte").item(0).getTextContent();

                    // Extraire les réponses
                    NodeList responseNodes = questionElement.getElementsByTagName("réponse");
                    String[] responses = new String[responseNodes.getLength()];
                    int correctAnswerIndex = -1;
                    
                    for (int j = 0; j < responseNodes.getLength(); j++) {
                        Element responseElement = (Element) responseNodes.item(j);
                        responses[j] = responseElement.getTextContent();
                        if (responseElement.getAttribute("correct").equals("true")) {
                            correctAnswerIndex = j;
                        }
                    }

                    // Ajouter la question à la liste
                    questions.add(new Question(questionText, responses, correctAnswerIndex));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }
}