import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Random;

public class GamePanel extends JPanel implements MouseMotionListener {

    Random random = new Random();
    String circleCollision;
    final int PLAYER_WIDTH = 30;
    final int PLAYER_HEIGHT = 30;
    final int Circle_Height_Width = 50;

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
    JButton button;

    int level;
    Window window;
    int seconds = 10;
    Thread countThread;
    int lvlSpeed;
    boolean isFruitEaten;
    Graphics2D graphic2d;
    Random randomObj;
    double fruitCordsX;
    double fruitCordsY;
    Shape starObj;
    int score = -1;
    int wallsPadding = 50;
    int topLeftX = wallsPadding, topLeftY = 60, bottomScreen;
    int fruits = 5;

    GamePanel(Window window) {
        this.window = window;
        isReachedLeftEnd = true;
        button = new JButton("pause");
        setBounds(999, 214, 914, 316);
        setLayout(null);
        add(button);
        randomObj = new Random();
        circleXCords = Window.WIDTH / 2;
        level = 1;
        circleYCords = 75;
//        System.out.println(circleXCords);
        ellipse = new Ellipse2D.Float(circleXCords, circleYCords, 50, 50);
        speed = 5;
        deltaDirection = 5;
        circleCollision = "BOTTOM";
        playerX = 400 - PLAYER_WIDTH;
        playerY = 300 - PLAYER_HEIGHT;
        lvlSpeed = 35;
        isFruitEaten = true;
        isGameRunning = true;
        addMouseMotionListener(this);
    }


    public void ballMovement() {
        if (isReachedBottom) {
            circleYCords = circleYCords - speed;// Going UP
            if (circleYCords <= topLeftY) {
                isReachedBottom = false;
                isReachedTop = true;
                changeSpeedAndDirection(); // method call
            }
            if (isReachedRightEnd) {
                circleXCords = circleXCords - deltaDirection;
            }
            if (isReachedLeftEnd) {
                circleXCords = circleXCords + deltaDirection;
            }
        }
        if (isReachedTop) {
            circleYCords = circleYCords + speed;   // GOING Down
            if (circleYCords >= bottomScreen - Circle_Height_Width - 5) {
                isReachedBottom = true;
                isReachedTop = false;
                changeSpeedAndDirection();
            }
            if (isReachedRightEnd) {
                circleXCords = circleXCords - deltaDirection;
            }
            if (isReachedLeftEnd) {
                circleXCords = circleXCords + deltaDirection;
            }
        }
        if (circleXCords >= Window.WIDTH - wallsPadding - Circle_Height_Width) {
            isReachedRightEnd = true;
            isReachedLeftEnd = false;
            changeSpeedAndDirection();
        }
        if (isReachedRightEnd) {
            circleXCords = circleXCords - deltaDirection;
        }
        if (circleXCords <= topLeftX) {
            isReachedLeftEnd = true;
            isReachedRightEnd = false;
            changeSpeedAndDirection();
        }
        if (isReachedLeftEnd) {
            circleXCords = circleXCords + deltaDirection;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        graphic2d = (Graphics2D) g;
        g.setColor(Color.green);
        g.setFont(new Font("ARIAL", Font.BOLD, 30));
        g.drawString("Score: " + score, topLeftX, 30);
        g.drawString("Level " + level, Window.WIDTH / 2 - 50, 30);
        g.drawString("Timer: " + seconds, Window.WIDTH - wallsPadding - 170, 30);
        ellipse.setFrame(circleXCords, circleYCords, 50, 50);
        gameOver = ellipse.intersects(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);


        //g.drawLine(20, 500, circleXCords, circleYCords);
        bottomScreen = Window.HEIGHT - 80;
        setBackground(Color.black);


        //sides
        g.setColor(Color.red);
        //graphic2d.setStroke(new BasicStroke(10));
        graphic2d.drawLine(topLeftX, topLeftY, Window.WIDTH - wallsPadding, topLeftY); //TOP
        graphic2d.drawLine(topLeftX, bottomScreen, Window.WIDTH - wallsPadding, bottomScreen);//BOTTOM
        graphic2d.drawLine(Window.WIDTH - wallsPadding, topLeftY,
                Window.WIDTH - wallsPadding, bottomScreen);//RIGHT
        graphic2d.drawLine(topLeftX, topLeftY, topLeftX, bottomScreen);//LEFT

        //will generate a fruit in a random area
        fruitGenerator(g);

        //ball movement
        if (!gameOver) {
            g.setColor(Color.red);
            g.fillOval(circleXCords, circleYCords, Circle_Height_Width, Circle_Height_Width);
            ballMovement();

            //player
            g.setColor(Color.green);
            g.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
            //System.out.println(playerX + ", " + playerY);

        }
        //Game over
        else {
            stopGame();
        }
    }

    // this is a method definition
    public void changeSpeedAndDirection() {
        speed = random.nextInt(10, 15);
        deltaDirection = random.nextInt(5, 10);
    }
    public void stopGame(){
        gameOver = true;
        isGameRunning = false;
        countThread.interrupt();
        window.gameOver();
    }


    public void startGame() {
        isGameRunning = true;
        gameOver = false;
        startCountdown();

        while (isGameRunning) {
            repaint();
            try {
                Thread.sleep(lvlSpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startCountdown() {
        countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!countThread.isInterrupted()) {
                    if (seconds <= 0)  {
                        System.out.println("works");
                        if(score >= fruits) {
                            System.out.println("works 2");
                            level = level + 1;
                            fruits = fruits + 5;
                            seconds = 10;
                        }
                        else{
                            stopGame();
                        }
                        if (lvlSpeed <= 5) {
                            // TODO: User wins the gmae: wins permanent trophy and its stored in file (hdd)
                            // FILE HANDLING IN JAVA
                        } else {
                            lvlSpeed = lvlSpeed - 1;
                        }
                    } else
                        seconds = seconds - 1;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        countThread.start();
    }

    public void fruitGenerator(Graphics g) {
        g.setColor(Color.YELLOW);
        //System.out.println(fruitCordsX + "," + fruitCordsY);
        if (isFruitEaten) {
            fruitCordsX = randomObj.nextInt(topLeftX + 20, Window.WIDTH - wallsPadding - 20);
            fruitCordsY = randomObj.nextInt(topLeftY + 20, bottomScreen - 20);
            starObj = createStar(fruitCordsX, fruitCordsY, 10, 20, 10, 50);
            score += 1;
            isFruitEaten = false;
        }
        if (starObj.intersects(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT)) {
            isFruitEaten = true;
        }
        graphic2d.fill(starObj);
    }

    private static Shape createStar(double centerX, double centerY,
                                    double innerRadius, double outerRadius, int numRays,
                                    double startAngleRad) {
        Path2D path = new Path2D.Double();
        double deltaAngleRad = Math.PI / numRays;
        for (int i = 0; i < numRays * 2; i++) {
            double angleRad = startAngleRad + i * deltaAngleRad;
            double ca = Math.cos(angleRad);
            double sa = Math.sin(angleRad);
            double relX = ca;
            double relY = sa;
            if ((i & 1) == 0) {
                relX *= outerRadius;
                relY *= outerRadius;
            } else {
                relX *= innerRadius;
                relY *= innerRadius;
            }
            if (i == 0) {
                path.moveTo(centerX + relX, centerY + relY);
            } else {
                path.lineTo(centerX + relX, centerY + relY);
            }
        }
        path.closePath();
        return path;
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (e.getY() >= topLeftY && e.getY() <= bottomScreen - PLAYER_HEIGHT) {
            playerY = e.getY();

        }
        if (e.getX() >= topLeftX && e.getX() <= Window.WIDTH - wallsPadding - PLAYER_WIDTH) {
            playerX = e.getX();
        }
    }
}
