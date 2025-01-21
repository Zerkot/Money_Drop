import java.awt.EventQueue;

public class App {

	public static void main(String[] args) {
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
