import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {
    Random random = new Random();
    int deltaDirection;
    int speed;
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
        deltaDirection = random.nextInt(0,6);
        speed = random.nextInt(1,10);
        System.out.println(deltaDirection);
        setBackground(Color.black);
        g.setColor(Color.white);
        g.fillRect(20, 50, 750, 15);
        g.fillRect(20, 500, 750, 15);
        g.fillRect(755, 65, 15, 450);
        g.fillRect(20, 65, 15, 450);


        g.setColor(Color.red);
        g.fillOval(circleXCords, circleYCords, 50, 50);
        if(isReachedBottom){
            circleYCords = circleYCords - speed;// Going UP
            if(circleYCords <= 65) {
                isReachedBottom = false;
                isReachedTop = true;
            }
            if(isReachedRightEnd){
                circleXCords = circleXCords - deltaDirection;
            }
            if(isReachedLeftEnd){
                circleXCords = circleXCords + deltaDirection;
            }
        }
        if(isReachedTop) {
            circleYCords = circleYCords + speed;   // GOING Down
            if(circleYCords >= 450){
                isReachedBottom = true;
                isReachedTop = false;
            }
            if(isReachedRightEnd){
                circleXCords = circleXCords - deltaDirection;
            }
            if(isReachedLeftEnd){
                circleXCords = circleXCords + deltaDirection;
            }
        }
        if(circleXCords >= 700){
            isReachedRightEnd = true;
            isReachedLeftEnd = false;
        }
        if(isReachedRightEnd){
           circleXCords = circleXCords - deltaDirection;

        }
        if(circleXCords <= 30){
            isReachedLeftEnd = true;
            isReachedRightEnd = false;
        }
        if(isReachedLeftEnd){
            circleXCords = circleXCords + deltaDirection;
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
