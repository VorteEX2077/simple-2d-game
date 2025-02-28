import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Window extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    GamePanel gamePanel;
    JPanel gameOverPanel;
    JPanel mainMenuPanel;
    JPanel settingsPanel;
    JPanel powerUpPanel;
    JButton restartButton;
    JLabel jLabel;
    JLabel highScore;
    BoxLayout layout;
    Window windowObj;
    JButton buttonPlay;
    FileHandler fileHandler;
    String currentResolution;
    JButton buyButton1;
    JButton buyButton2;
    JButton buyButton3;
    JLabel doublePointsPowerUpLevel = new JLabel();
    JLabel magnetismPowerUpLevel = new JLabel();
    JLabel freezePowerUpLevel = new JLabel();

    Window() {
        //the panels
        windowObj = this;
        fileHandler = new FileHandler();
        mainMenuPanel = new JPanel();
        settingsPanel = new JPanel();
        powerUpPanel = new JPanel();
        gameOverPanel = new JPanel();
        gamePanel = new GamePanel(windowObj); // fIRST oBJECT

        jLabel = new JLabel();
        highScore = new JLabel();
        restartButton = new JButton();
        restartButton.setText("restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel = new GamePanel(windowObj); // created a new object gamepanel and named it the same name
                display(gamePanel);
                setTimeout(() -> {
                    gamePanel.requestFocus();
                    gamePanel.setFocusable(true);
                    gamePanel.requestFocusInWindow();
                    System.out.println("player colour: " + gamePanel.skins);
                    updatePowerUpButtonText();
                    gamePanel.startGame();
                }, 100);
            }
        });
        jLabel.setBackground(Color.GREEN);
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setPreferredSize(new Dimension(330, 100));
        jLabel.setFont(new Font("arial", Font.BOLD, 50));
        jLabel.setForeground(Color.red);
        jLabel.setText("GAME OVER");
        initSettingsPanel();
        initGameOverPanel();
        initMainMenuPanel();
        initPowerUpPanel();

        add(gameOverPanel);
        add(gamePanel);
        add(mainMenuPanel); // attaching Main menu panel to window
        pack();
        /* SET SIZE */
        setSize(fileHandler.widthFromFile, fileHandler.heightFromFile);
        setBackground(Color.red);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void initGameOverPanel() {
        layout = new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS);
        gameOverPanel.add(jLabel);
        gameOverPanel.add(restartButton);
        gameOverPanel.setBackground(Color.black);
        gameOverPanel.setBorder(new EmptyBorder(new Insets(250, 250, 250, 200)));
        gameOverPanel.setLayout(layout);
    }

    public void gameOver() {
        display(gameOverPanel);
    }

    public static void main(String[] args) {
        new Window();
    }

    public void resumeGame() {
        display(gamePanel);
        setTimeout(new Runnable() {
            @Override
            public void run() {
                gamePanel.requestFocus();
                gamePanel.setFocusable(true);
                gamePanel.requestFocusInWindow();
                gamePanel.startGame();
            }
        }, 500);
    }

    public void initMainMenuPanel() {
        //jlabels high score
        highScore.setForeground(Color.ORANGE);
        highScore.setFont(new Font("ARIAL", Font.BOLD, 25));
        highScore.setText("HIGH SCORE: " + fileHandler.getHighScore());
        buttonPlay = new JButton();
        JButton buttonSettings = new JButton();
        JButton buttonExit = new JButton();
        buttonPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });
        buttonSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display(settingsPanel);
            }
        });
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPlay.setText("PLAY");
        buttonSettings.setText("SETTINGS");
        buttonExit.setText("EXIT");
        mainMenuPanel.setBackground(Color.black);
        mainMenuPanel.setLayout(new GridBagLayout());

        // GridBagConstraints to control button positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;          // Center horizontally
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // Top, left, bottom, right padding
        // Add the first button
        gbc.gridy = 0; // Row 0
        mainMenuPanel.add(highScore, gbc);
        gbc.gridy = 1; // row 1
        mainMenuPanel.add(buttonPlay, gbc);

        // Add the second button
        gbc.gridy = 2; // Row 2
        mainMenuPanel.add(buttonSettings, gbc);

        // Add the third button
        gbc.gridy = 3; // Row 3
        mainMenuPanel.add(buttonExit, gbc);
    }

    public void initSettingsPanel() {
        //ComaBox initilisation + customisation
        JComboBox screenResComboBox = new JComboBox<>();
        JComboBox skinsComboBox = new JComboBox<>();
        JLabel comboBoxLabel1 = new JLabel("screen resolution:");
        JLabel comboBoxLabel2 = new JLabel("Skins");
        comboBoxLabel2.setForeground(Color.YELLOW);
        comboBoxLabel2.setFont(new Font("ARIAL", Font.BOLD, 20));
        skinsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    gamePanel.skins = String.valueOf(skinsComboBox.getSelectedItem());
                    System.out.println(gamePanel.skins + " from window class");
//                    if( == fileHandler.fileToSkin){
//
//                    }
                }
            }
        });
        screenResComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (screenResComboBox.getSelectedItem() == "800 x 600") {
                        WIDTH = 800;
                        HEIGHT = 600;
                    } else if (screenResComboBox.getSelectedItem() == "1280 x 720") {
                        WIDTH = 1280;
                        HEIGHT = 720;
                        ;
                    } else if (screenResComboBox.getSelectedItem() == "1920 x 1080") {
                        WIDTH = 1920;
                        HEIGHT = 1080;
                    }
                    currentResolution = String.valueOf(screenResComboBox.getSelectedItem());
                    setSize(WIDTH, HEIGHT);
                }
            }
        });
        skinsComboBox.addItem("green");
        skinsComboBox.addItem("purple");
        skinsComboBox.addItem("blue");
        skinsComboBox.addItem("white");
        skinsComboBox.addItem("red");
        skinsComboBox.addItem("yellow");
        screenResComboBox.addItem("800 x 600");
        screenResComboBox.addItem("1280 x 720");
        screenResComboBox.addItem("1920 x 1080");
        String resolutionStr = fileHandler.widthFromFile + " x " + fileHandler.heightFromFile;
        skinsComboBox.setSelectedItem(fileHandler.fileToSkin);
        screenResComboBox.setSelectedItem(resolutionStr);
        comboBoxLabel1.setForeground(Color.YELLOW);
        comboBoxLabel1.setFont(new Font("ARIAL", Font.BOLD, 20));
        //back button + adding components
        JButton backButton = new JButton("back");
        settingsPanel.setBackground(Color.black);
        settingsPanel.add(backButton);
        settingsPanel.add(comboBoxLabel1);
        settingsPanel.add(screenResComboBox);
        settingsPanel.add(comboBoxLabel2);
        settingsPanel.add(skinsComboBox);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileHandler.writeToFile(currentResolution, gamePanel.skins);
                display(mainMenuPanel);
            }
        });
    }

    public void display(JPanel a) {
        getContentPane().removeAll();
        getContentPane().add(a);
        repaint();
        printAll(getGraphics());
    }

    public void pauseMenu() {
        buttonPlay.setText("RESUME");
        display(mainMenuPanel);
        fileHandler.readFile();
        highScore.setText("HIGH SCORE: " + String.valueOf(fileHandler.getHighScore()));

    }

    public void updatePowerUpButtonText() {
        buyButton1.setText("BUY DOUBLE POINTS FOR: " + gamePanel.getDoublePointsPowerUPValue() + " POINTS");
        buyButton2.setText("BUY MAGNETISM FOR: " + gamePanel.getMagnetismPowerUpValue() + " POINTS");
        buyButton3.setText("BUY FREEZE FOR: " + gamePanel.getFreezePowerUpValue() + " POINTS");

        magnetismPowerUpLevel.setText("MAGNETISM POWER UP LEVEL: " + gamePanel.getMagnetismPowerUpValue()/ConstantVariables.POWER_UP_INITIAL_VAL);
        doublePointsPowerUpLevel.setText("DOUBLE POINTS POWER UP LEVEL: " + gamePanel.getMagnetismPowerUpValue()/ConstantVariables.POWER_UP_INITIAL_VAL);
        freezePowerUpLevel.setText("FREEZE POWER UP LEVEL: " + gamePanel.getMagnetismPowerUpValue()/ConstantVariables.POWER_UP_INITIAL_VAL);
    }

    public void initPowerUpPanel() {
        Dimension dimension = new Dimension(150, 40);
        JLabel powerUpTitle = new JLabel("POWER UP SHOP");
        powerUpTitle.setForeground(Color.YELLOW);
        powerUpTitle.setFont(new Font("ARIAL", Font.BOLD, 25));
        powerUpPanel.setBackground(Color.black);

        //buttons back, power ups
        JButton backButton = new JButton("RESUME");
        JButton button1 = new JButton("DOUBLE POINTS");
        JButton button2 = new JButton("MAGNETISM");
        JButton button3 = new JButton("FREEZE");


        powerUpPanel.add(backButton);
        powerUpPanel.add(powerUpTitle);
        backButton.addActionListener(e -> {
            resumeGame();
            updatePowerUpButtonText();
        });

        // Set layout for the main frame
        powerUpPanel.setLayout(new BorderLayout());

        // Top panel for the title
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.black);
        JLabel titleLabel = new JLabel("POWER UP SHOP");
        titleLabel.setForeground(Color.yellow);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        topPanel.add(titleLabel);

        // Left panel for buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.black);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));// 3 buttons in a column

        //change dimensions (sizes)
        button1.setPreferredSize(dimension);
        button1.setMinimumSize(dimension);
        button1.setMaximumSize(dimension);
        button2.setPreferredSize(dimension);
        button2.setMinimumSize(dimension);
        button2.setMaximumSize(dimension);
        button3.setPreferredSize(dimension);
        button3.setMinimumSize(dimension);
        button3.setMaximumSize(dimension);

//        buyButton1.setPreferredSize(dimension);
//        buyButton1.setMinimumSize(dimension);
//        buyButton1.setMaximumSize(dimension);
        button1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(button1);
        leftPanel.add(Box.createRigidArea(new Dimension(50, 20)));
        leftPanel.add(button2);
        leftPanel.add(Box.createRigidArea(new Dimension(50, 20)));
        leftPanel.add(button3);
        topPanel.add(backButton);

        buyButton1 = new JButton("BUY DOUBLE POINTS FOR: " + ConstantVariables.POWER_UP_INITIAL_VAL + " POINTS");
        buyButton2 = new JButton("BUY MAGNETISM FOR: " + ConstantVariables.POWER_UP_INITIAL_VAL + " POINTS");
        buyButton3 = new JButton("BUY FREEZE FOR: " + ConstantVariables.POWER_UP_INITIAL_VAL + " POINTS");
        buyButton1.addActionListener(e -> gamePanel.powerUps("double points"));
        buyButton2.addActionListener(e -> gamePanel.powerUps("magnetism"));
        buyButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gamePanel.powerUps("freeze");
            }
        });

        // Right panel with CardLayout for switching content
        JPanel rightPanel = new JPanel(new CardLayout());
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.BLACK);
        doublePointsPowerUpLevel.setText("DOUBLE POINTS POWER UP LEVEL: " + gamePanel.getMagnetismPowerUpValue());
        panel1.add(doublePointsPowerUpLevel);
        panel1.add(buyButton1);
        //panel1.add(new JLabel("This is Panel 1"));
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.BLACK);
        magnetismPowerUpLevel.setText("MAGNETISM POWER UP LEVEL: " + gamePanel.getMagnetismPowerUpValue());
        panel2.add(magnetismPowerUpLevel);
        panel2.add(buyButton2);
        //panel2.add(new JLabel("This is Panel 2"));
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.black);
        freezePowerUpLevel.setText("FREEZE POWER UP LEVEL: " + gamePanel.getMagnetismPowerUpValue());
        panel3.add(freezePowerUpLevel);
        panel3.add(buyButton3);
        // Add panels to the CardLayout
        rightPanel.add(panel1, "Panel 1");
        rightPanel.add(panel2, "Panel 2");
        rightPanel.add(panel3, "Panel 3");

        // Add action listeners to buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) rightPanel.getLayout();
                cl.show(rightPanel, "Panel 1");
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) rightPanel.getLayout();
                cl.show(rightPanel, "Panel 2");
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) rightPanel.getLayout();
                cl.show(rightPanel, "Panel 3");
            }
        });

        // Add panels to the main frame
        powerUpPanel.add(topPanel, BorderLayout.NORTH);
        powerUpPanel.add(leftPanel, BorderLayout.WEST);
        powerUpPanel.add(rightPanel, BorderLayout.CENTER);
    }


    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
}
