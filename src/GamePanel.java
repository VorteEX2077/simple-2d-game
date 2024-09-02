import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class GamePanel extends JPanel implements MouseMotionListener {

    Random random = new Random();
    String circleCollision;
    int deltaDirection;
    int speed;
    int circleYCords;
    int circleXCords;
    int playerX;
    int playerY;
    boolean gameOver;
    boolean isReachedRightEnd;
    boolean isReachedLeftEnd;
    boolean isGameRunning;
    boolean isReachedBottom;
    boolean isReachedTop = true;
    Ellipse2D ellipse;
    String[] sides = {"TOP", "BOTTOM", "RIGHT", "LEFT"};
    int counter;

    GamePanel() {
        isReachedLeftEnd = true;
        circleXCords = Window.WIDTH / 2;
        circleYCords = 75;
        System.out.println(circleXCords);
        ellipse = new Ellipse2D.Float(circleXCords, circleYCords, 50, 50);
        speed = 5;
        deltaDirection = 5;
        circleCollision = "BOTTOM";
        playerX = 400 - 30;
        playerY = 300 - 30;
        isGameRunning = true;
        addMouseMotionListener(this);
    }


    public void ballMovement(){
        if(isReachedBottom){
            circleYCords = circleYCords - speed;// Going UP
            if(circleYCords <= 65) {
                isReachedBottom = false;
                isReachedTop = true;
                changeSpeed(); // method call
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
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        gameOver();
        ellipse.setFrame(circleXCords, circleYCords, 50, 50);
        gameOver = ellipse.intersects(playerX, playerY,30, 30);


        g.drawLine(20, 500, circleXCords, circleYCords);

        setBackground(Color.black);
        //sides
        g.setColor(Color.white);
        g.fillRect(20, 50, 750, 15); //TOP
        g.fillRect(20, 500, 750, 15);//BOTTOM
        g.fillRect(755, 65, 15, 450);//RIGHT
        g.fillRect(20, 65, 15, 450);//LEFT

        //ball movement
        g.setColor(Color.red);
        g.fillOval(circleXCords, circleYCords, 50, 50);
        ballMovement();

        //player
        g.setColor(Color.green);
        g.fillRect(playerX, playerY, 30, 30);

        //System.out.println("Speed:" + speed);
        //System.out.println(isReachedLeftEnd + " " + isReachedRightEnd);
    }

    // this is a method definition
    public void changeSpeed() {
        speed = random.nextInt(10, 15);
        deltaDirection = random.nextInt(5, 10);
    }

    public void startGame() {
        while (isGameRunning) {
            repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void gameOver(){
        if(gameOver == true){
        }
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        playerX = e.getX();
        playerY = e.getY();
    }
}
