import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    int circleYCords;
    int circleXCords;
    boolean isReachedRightEnd;
    boolean isReachedLeftEnd;
    boolean isGameRunning;
    boolean isReachedBottom;
    boolean isReachedTop = true;
    GamePanel(){
        isReachedLeftEnd = true;
        circleXCords = 20;
        circleYCords = 75;
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
        g.fillOval(circleXCords, circleYCords, 50, 50);
        if(isReachedBottom){
            circleYCords = circleYCords - 5;// Going UP
            if(circleYCords <= 75) {
                isReachedBottom = false;
                isReachedTop = true;
            }
            if(isReachedRightEnd){
                circleXCords = circleXCords - 2;
            }
            if(isReachedLeftEnd){
                circleXCords = circleXCords + 2;
            }
        }
        if(isReachedTop) {
            circleYCords = circleYCords + 5;   // GOING Down
            if(circleYCords >= 450){
                isReachedBottom = true;
                isReachedTop = false;
            }
            if(isReachedRightEnd){
                circleXCords = circleXCords - 2;
            }
            if(isReachedLeftEnd){
                circleXCords = circleXCords + 2;
            }
        }
        if(circleXCords >= 750){
            isReachedRightEnd = true;
            isReachedLeftEnd = false;
        }
        if(isReachedRightEnd){
           circleXCords = circleXCords - 2;

        }
        if(circleXCords <= 20){
            isReachedLeftEnd = true;
            isReachedRightEnd = false;
        }
        if(isReachedLeftEnd){
            circleXCords = circleXCords + 2;
        }
    }
    public void startGame(){
        while(isGameRunning){
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
