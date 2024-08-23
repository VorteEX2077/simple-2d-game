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
        circleXCords = 20;
        circleYCords = 75;
        speed = 5;
        deltaDirection = 5;
        circleCollision = "BOTTOM";
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

    public void shootBallToSide(String side) {
        switch (side) {
            case "NONE" -> {

            }
            case "TOP" -> {
                circleYCords = circleYCords - speed;
                //circleXCords = circleXCords + deltaDirection;
            }
            case "BOTTOM" -> {
                circleYCords = circleYCords + speed;
                //circleXCords = circleXCords - deltaDirection;
            }
            case "LEFT" -> {
                circleXCords = circleXCords - speed;
                //circleYCords = circleYCords + speed;
            }
            case "RIGHT" -> {
                circleXCords = circleXCords + speed;
                //circleYCords = circleYCords - speed;
            }
        }
    }

    public void checkBallCollision() {
        String a = null;
        if (circleYCords <= 65) {
            circleCollision = "TOP";
             a = sides[random.nextInt(0, 4)];
            shootBallToSide(a);
        }
        if (circleYCords >= 440) {
            circleCollision = "BOTTOM";
             a = sides[random.nextInt(0, 4)];
            shootBallToSide(a);
        }
        if (circleXCords <= 30) {
            circleCollision = "LEFT";
             a = sides[random.nextInt(0, 4)];
            shootBallToSide(a);
        }
        if (circleXCords >= 700) {
            circleCollision = "RIGHT";
             a = sides[random.nextInt(0, 4)];
            shootBallToSide(a);
        }
        System.out.println(circleCollision + "-->" + a + " - " + circleYCords + "," + circleXCords);
    }

    public void startGame() {
        while (isGameRunning) {
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
