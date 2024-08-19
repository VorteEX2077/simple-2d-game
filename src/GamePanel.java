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
    GamePanel(){
        isReachedLeftEnd = true;
        circleXCords = 20;
        circleYCords = 75;
        speed = 5;
        deltaDirection = 5;
        circleCollision = "NONE";
        isGameRunning = true;
        //System.out.println("gsame started");
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

        checkBallCollision();
        switch (circleCollision) {
            case "NONE" -> {

            }
            case "TOP" -> {
                circleYCords = circleYCords + speed;
                circleXCords = circleXCords + deltaDirection;
            }
            case "BOTTOM" -> {
                circleYCords = circleYCords - speed;
                circleXCords = circleXCords - deltaDirection;
            }
            case "LEFT" -> {
                circleXCords = circleXCords + deltaDirection;
                circleYCords = circleYCords + speed;
            }
            case "RIGHT" -> {
                circleXCords = circleXCords - deltaDirection;
                circleYCords = circleYCords - speed;
            }
        }

//        if(isReachedBottom){
//            circleYCords = circleYCords - speed;// Going UP
//            if(circleYCords <= 65) {
//                isReachedBottom = false;
//                isReachedTop = true;
//                speed = random.nextInt(5,20);
//            }
//            if(isReachedRightEnd){
//                circleXCords = circleXCords - deltaDirection;
//            }
//            if(isReachedLeftEnd){
//                circleXCords = circleXCords + deltaDirection;
//            }
//        }
//        if(isReachedTop) {
//            circleYCords = circleYCords + speed;   // GOING Down
//            if(circleYCords >= 450){
//                isReachedBottom = true;
//                isReachedTop = false;
//                speed = random.nextInt(5,20);
//            }
//            if(isReachedRightEnd){
//                circleXCords = circleXCords - deltaDirection;
//            }
//            if(isReachedLeftEnd){
//                circleXCords = circleXCords + deltaDirection;
//            }
//        }
//        if(circleXCords >= 700){
//            isReachedRightEnd = true;
//            isReachedLeftEnd = false;
//            speed = random.nextInt(5,20);
//            deltaDirection = random.nextInt(5,10);
//        }
//        if(isReachedRightEnd){
//           circleXCords = circleXCords - deltaDirection;
//        }
//        if(circleXCords <= 30){
//            isReachedLeftEnd = true;
//            isReachedRightEnd = false;
//            speed = random.nextInt(5,20);
//            deltaDirection = random.nextInt(5,10);
//        }
//        if(isReachedLeftEnd){
//            circleXCords = circleXCords + deltaDirection;
//        }

        //System.out.println("Speed:" + speed);
        //System.out.println(isReachedLeftEnd + " " + isReachedRightEnd);
    }

    public void checkBallCollision(){
        if(circleYCords <= 65){
            circleCollision = "TOP";
            speed = random.nextInt(5,20);
            deltaDirection = random.nextInt(5,10);
        } else if (circleYCords >= 450) {
            circleCollision = "BOTTOM";
            speed = random.nextInt(5,20);
            deltaDirection = random.nextInt(5,10);
        } else if (circleXCords <= 30) {
            circleCollision = "LEFT";
            speed = random.nextInt(5,20);
            deltaDirection = random.nextInt(5,10);
        } else if (circleXCords >= 700) {
            circleCollision = "RIGHT";
            speed = random.nextInt(5,20);
            deltaDirection = random.nextInt(5,10);
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
