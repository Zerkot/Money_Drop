import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerManager {
    private Timer timer;
    private int timeRemaining;
    private JLabel lblTimer;
    private Runnable onTimeOut;

    public TimerManager(JLabel lblTimer, Runnable onTimeOut) {
        this.lblTimer = lblTimer;
        this.onTimeOut = onTimeOut;
    }

    public void startTimer(int durationInSeconds) {
        timeRemaining = durationInSeconds;
        lblTimer.setText("Temps restant: " + timeRemaining + "s");

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining <= 0) {
                    timer.stop();
                    onTimeOut.run();
                } else {
                    timeRemaining--;
                    lblTimer.setText("Temps restant: " + timeRemaining + "s");
                }
            }
        });
        timer.start();
    }

    public void resetTimer(int durationInSeconds) {
        if (timer != null) {
            timer.stop();
        }
        startTimer(durationInSeconds);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
}
