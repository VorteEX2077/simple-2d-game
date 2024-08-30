import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    Random random = new Random();
    String circleCollision;
    int deltaDirection;
    int speed;
    int circleYCords;
    int circleXCords;
    boolean isReachedRightEnd;
    boolean isReachedLeftEnd;
    boolean isGameRunning;
    boolean isReachedBottom;
    boolean isReachedTop = true;
    String[] sides = {"TOP", "BOTTOM", "RIGHT", "LEFT"};

    GamePanel() {
        isReachedLeftEnd = true;
        circleXCords = Window.WIDTH / 2;
        circleYCords = 75;
        speed = 5;
        deltaDirection = 5;
        circleCollision = "BOTTOM";
        isGameRunning = true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

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
                speed = random.nextInt(5,20);
                deltaDirection = random.nextInt(5,10);
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
                changeSpeed();
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
            changeSpeed();
        }
        if(isReachedRightEnd){
           circleXCords = circleXCords - deltaDirection;
        }
        if(circleXCords <= 30){
            isReachedLeftEnd = true;
            isReachedRightEnd = false;
            changeSpeed();
        }
        if(isReachedLeftEnd){
            circleXCords = circleXCords + deltaDirection;
        }

        //System.out.println("Speed:" + speed);
        //System.out.println(isReachedLeftEnd + " " + isReachedRightEnd);
    }

    private void changeSpeed() {
        speed = random.nextInt(10, 15);
        deltaDirection = random.nextInt(5, 10);
    }

    public void startGame() {
        while (isGameRunning) {
            repaint();
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
