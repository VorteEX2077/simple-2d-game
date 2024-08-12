import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    int circleYCords;
    boolean isGameRunning;
    boolean isReachedBottom;
    boolean isReachedTop = true;
    GamePanel(){
        isGameRunning = true;
        System.out.println("gsame started");
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        setBackground(Color.black);
        System.out.println(circleYCords);
        g.setColor(Color.white);
        g.fillRect(20, 50, 750, 15);
        g.fillRect(20, 500, 750, 15);
        g.setColor(Color.red);
        g.fillOval(20, circleYCords, 50, 50);
        if(isReachedBottom){
            circleYCords = circleYCords - 3; // Going UP
            if(circleYCords <= 50) {
                isReachedBottom = false;
                isReachedTop = true;
            }
        }
        if(isReachedTop) {
            circleYCords = circleYCords + 3;   // GOING Down
            if(circleYCords >= 480){
                isReachedBottom = true;
                isReachedTop = false;
            }
        }
    }
    public void startGame(){
        while(isGameRunning){
            repaint();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
