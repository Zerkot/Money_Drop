import java.awt.EventQueue;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
public class App {

	public static void main(String[] args) {
		//List<Question> questions = loadQuestionsFromJSON(FILE_PATH);
        //for (Question q : questions) {
          //  System.out.println(q);}
		try {
            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch (Exception ex) {

            System.err.println("Erreur lors de l'initialisation de FlatLaf : " + ex);

        }
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