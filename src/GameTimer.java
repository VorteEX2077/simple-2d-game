import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {

    int secondsPassed;
    public GameTimer(timerListener listener){
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed = secondsPassed + 1;
                if(secondsPassed >= 10){
                    listener.timerOver();
                    ((Timer) e.getSource()).stop();
                } else{
                    listener.clockTick();
                }
            }
        });
        timer.start();
    }

    public interface timerListener {

        void timerOver();
        void clockTick(); // Abstract METHOD - Method without a body
    }
}
