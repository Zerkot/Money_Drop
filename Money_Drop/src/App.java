import java.awt.EventQueue;
import java.util.List;
public class App {

	public static void main(String[] args) {
		//List<Question> questions = loadQuestionsFromJSON(FILE_PATH);
        //for (Question q : questions) {
          //  System.out.println(q);}
        EventQueue.invokeLater(() -> {
            try {
                Fenetre window = new Fenetre();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}