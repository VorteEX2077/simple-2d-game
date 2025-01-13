import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements MouseMotionListener, KeyListener {

    String circleCollision;
    int PLAYER_WIDTH = 30;
    int PLAYER_HEIGHT = 30;
    int Circle_Height_Width = 50;

    int playerX;
    int playerY;
    boolean gameOver;

    boolean isGameRunning;

    boolean isCountDownTimerRunning;
    //Ellipse2D ellipse;


    int level;
    FileHandler fileHandler;
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
    int currentUserScore = 0;
    int wallsPadding = 50;
    int topLeftX = wallsPadding, topLeftY = 60, bottomScreen;
    boolean isMagnetismOn;
    boolean isFreezeOn;
    int scoreFromFruit = 1;
    int magnetSpeed = 30;
    Boolean isMagnetBought = false;
    boolean isFreezeBought = false;
    boolean isDoublePointsBought = false;
    JButton scoreBtn = new JButton();
    JButton lvlBtn = new JButton();
    JButton magnetBtn = new JButton();

    Font btnFont = new Font("ARIAL", Font.BOLD, 20);
    int backgroundFiller = 35;
    String skins = "green";
    Circle enemy1, enemy2, enemy3;
    List<Circle> listOfCircles = new ArrayList<>();

    //constructor of GamePanel
    GamePanel(Window window) {
        this.window = window;

        fileHandler = new FileHandler();
        //setLayout(null);
        randomObj = new Random();
        level = 1;
        //ellipse = new Ellipse2D.Float(circleXCords, circleYCords, 50, 50);
        circleCollision = "BOTTOM";
        playerX = 400 - PLAYER_WIDTH;
        playerY = 300 - PLAYER_HEIGHT;
        lvlSpeed = 35;
        isFruitEaten = true;
        isGameRunning = true;
        initPanels();
        enemy1 = new Circle(Color.PINK, Circle_Height_Width);
        enemy2 = new Circle(Color.CYAN, Circle_Height_Width);
        enemy3 = new Circle(Color.yellow, Circle_Height_Width);
        listOfCircles.add(enemy1);
        listOfCircles.add(enemy2);
        listOfCircles.add(enemy3);

        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseMotionListener(this);

        // Use BoxLayout to position panels horizontally without stretching
        setBackground(Color.black);
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

        // Create the first panel with two buttons
        //add(testLabel);
    }

    private void initPanels(){

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT)); // Align buttons to the left
        panel1.setBorder(BorderFactory.createEmptyBorder(10, topLeftX, 10, 10)); // Add padding
        scoreBtn.setForeground(Color.green);
        lvlBtn.setForeground(Color.green);
        scoreBtn.setFont(btnFont);
        lvlBtn.setFont(btnFont);
        makeCircular(scoreBtn);
        makeCircular(lvlBtn);

        panel1.add(scoreBtn, BoxLayout.X_AXIS);
        panel1.add(lvlBtn);
        panel1.setOpaque(false);
        panel1.setPreferredSize(new Dimension(150, 100));
        // Create the second panel with one button
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align buttons to the left
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 30)); // Add padding
        panel2.setPreferredSize(new Dimension(150, 100));
        makeCircular(magnetBtn);
        magnetBtn.setText("M");
        magnetBtn.setFont(btnFont);
        magnetBtn.setPreferredSize(new Dimension(50, 35));
        panel2.add(magnetBtn);
        panel2.setOpaque(false); // This method will make the panel's background transparent
        // Add panels to the frame
        add(panel1);
        add(panel2);
    }

    private void makeCircular(JButton button) {
        button.setPreferredSize(new Dimension(100, 35));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        button.setOpaque(false);
        //button.addActionListener(e -> System.out.println("Button clicked!"));
        button.setBorderPainted(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        setBackground(Color.black);

        graphic2d = (Graphics2D) g;
        for(Circle circle : listOfCircles) {
            if(!gameOver) circle.ballMovement();
            graphic2d.setColor(circle.getColor());
            graphic2d.fillOval(circle.getX(), circle.getY(), Circle_Height_Width,Circle_Height_Width);
        }

        //g.setColor(Color.green);
        //g.setFont(new Font("ARIAL", Font.BOLD, 30));
        scoreBtn.setText(String.valueOf(currentUserScore - 1));
        lvlBtn.setText("LVL " + level);
        //g.drawString("Score: " + (currentUserScore - 1), topLeftX, 30);
        //g.drawString("Level " + level, Window.WIDTH / 2 - 50, 30);
        //g.drawString("Timer: " + seconds, Window.WIDTH - wallsPadding - 170, 30);

       // ellipse.setFrame(circleXCords, circleYCords, 50, 50);
        //player and ball collision check
        gameOver = false;
        //gameOver = ellipse.intersects(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);

        //g.drawLine(20, 500, circleXCords, circleYCords);
        bottomScreen = Window.HEIGHT - 80;

        if (isMagnetismOn) {
            if (fruitCordsX > playerX) {
                fruitCordsX = fruitCordsX - magnetSpeed;
            }
            if (fruitCordsX < playerX) {
                fruitCordsX = fruitCordsX + magnetSpeed;
            }
            if (fruitCordsY > playerY) {
                fruitCordsY = fruitCordsY - magnetSpeed;
            }
            if (fruitCordsY < playerY) {
                fruitCordsY = fruitCordsY + magnetSpeed;
            }
            starObj = createStar(fruitCordsX, fruitCordsY, 10, 20, 10, 50);

            if(backgroundFiller < 15) g.setColor(Color.red);
            else g.setColor(Color.green);
            Point a = SwingUtilities.convertPoint(magnetBtn.getParent(), magnetBtn.getLocation(), window);
            g.fillRect(a.x - 8, magnetBtn.getY() + magnetBtn.getHeight(), magnetBtn.getWidth(),-backgroundFiller);
        }


        //System.out.println(magnetBtn.getX() + " " + magnetBtn.getY());


        //sides
        g.setColor(Color.red);
        //graphic2d.setStroke(new BasicStroke(10));
        graphic2d.drawLine(topLeftX, topLeftY, Window.WIDTH - wallsPadding, topLeftY); //TOP
        graphic2d.drawLine(topLeftX, bottomScreen, Window.WIDTH - wallsPadding, bottomScreen);//BOTTOM
        graphic2d.drawLine(Window.WIDTH - wallsPadding, topLeftY,
                Window.WIDTH - wallsPadding, bottomScreen);//RIGHT
        graphic2d.drawLine(topLeftX, topLeftY, topLeftX, bottomScreen);//LEFT

        //will generate a fruit in a random area
        starGenerator(g);

        //ball movement
        if (!gameOver) {
            if (isFreezeOn){
                g.setColor(Color.BLUE);
            }
            else{g.setColor(Color.red);}
            //g.fillOval(circleXCords, circleYCords, Circle_Height_Width, Circle_Height_Width);
//            if(level >= 5 && Window.WIDTH == 1920 && Window.HEIGHT == 1080)
//                g.fillOval(circle2XCords, circle2YCords, Circle_Height_Width, Circle_Height_Width);

            //player
            if (skins == "green") {g.setColor(Color.green);}
            else if(skins == "purple") {g.setColor(Color.MAGENTA);}
            else if(skins == "blue"){g.setColor(Color.CYAN);}
            else if (skins == "white") {g.setColor(Color.WHITE);}
            else {g.setColor(Color.green);}
              g.fillRect(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT);
            //System.out.println(playerX + ", " + playerY);

        }
        //Game over
        else {
            stopGame();
        }
    }

    public void stopGame() {
        gameOver = true;
        isGameRunning = false;
        isCountDownTimerRunning = false;
        if (currentUserScore > fileHandler.getHighScore()) {
            fileHandler.highScoreToFile(currentUserScore);
        }
        window.gameOver();
    }

    public void powerUps(String a) {
        //magnetism
        if (Objects.equals(a, "magnetism") && currentUserScore >= 20 ) {
            currentUserScore = currentUserScore - 20;
            isMagnetBought = true;
        } else {
            // TODO: POP up message to show if score is not enough
            isMagnetBought = false;
        }
        if (Objects.equals(a, "double points") && currentUserScore >= 1) {
            currentUserScore = currentUserScore - 10;
            isDoublePointsBought = true;
        }
        if (Objects.equals(a, "freeze") && currentUserScore >= 20){
            currentUserScore = currentUserScore - 20;
            isFreezeBought = true;
        }
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
        isCountDownTimerRunning = true;
        countThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isCountDownTimerRunning) {
                    if (seconds <= 0) {
                        level = level + 1;
                        seconds = 10;

                        if (lvlSpeed >= 5) {
                            // FILE HANDLING IN JAVA
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

    public void starGenerator(Graphics g) {
        g.setColor(Color.YELLOW);
        //System.out.println(fruitCordsX + "," + fruitCordsY);
        if (isFruitEaten) {
            fruitCordsX = randomObj.nextInt(topLeftX + 20, Window.WIDTH - wallsPadding - 20);
            fruitCordsY = randomObj.nextInt(topLeftY + 20, bottomScreen - 20);
            starObj = createStar(fruitCordsX, fruitCordsY, 10, 20, 10, 50);
            currentUserScore += scoreFromFruit;
            isFruitEaten = false;
        }
        if (starObj.intersects(playerX, playerY, PLAYER_WIDTH, PLAYER_HEIGHT)) {
            isFruitEaten = true;
        }
        graphic2d.fill(starObj); // This statement actually draw the star
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

    public void pauseGame() {
        isGameRunning = false;
        isCountDownTimerRunning = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pauseGame();
            window.pauseMenu();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            window.display(window.powerUpPanel);
            pauseGame();
        }
        if (e.getKeyCode() == KeyEvent.VK_1 && isMagnetBought){
            isMagnetismOn = true;
            isMagnetBought = false;
            // WE HAVE TO START THE TIMER OVER HERE
            GameTimer gameTimer = new GameTimer(new GameTimer.timerListener() {
                @Override
                public void timerOver() {
                    isMagnetismOn = false;
                }

                @Override
                public void clockTick() {
                    backgroundFiller = backgroundFiller - 3;
                }
            });
        }
        if (e.getKeyCode() == KeyEvent.VK_2 && isDoublePointsBought){
            isDoublePointsBought = false;
            GameTimer gameTimer = new GameTimer(new GameTimer.timerListener() {
                @Override
                public void timerOver() {
                    scoreFromFruit = 1;
                }

                @Override
                public void clockTick() {
                    scoreFromFruit = 2;
                }
            });
        }
        if (e.getKeyCode() == KeyEvent.VK_3 && isFreezeBought){
            isFreezeOn = true;
            isFreezeBought = false;
            GameTimer gameTimer = new GameTimer(new GameTimer.timerListener() {
                @Override
                public void timerOver() {
                    isFreezeOn = false;
                }

                @Override
                public void clockTick() {

                }
            });
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
