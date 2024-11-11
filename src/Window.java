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
    JButton restartButton;
    JLabel jLabel;
    JLabel highScore;
    BoxLayout layout;
    Window windowObj;
    JButton buttonPlay;
    FileHandler fileHandler;

    Window() {
        //the panels
        windowObj = this;
        fileHandler = new FileHandler();
        mainMenuPanel = new JPanel();
        settingsPanel = new JPanel();
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
                setTimeout(() -> gamePanel.startGame(), 100);
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

        add(gameOverPanel);
        add(gamePanel);
        add(mainMenuPanel); // attaching Main menu panel to window
        setSize(800, 600);
        setBackground(Color.red);
        setVisible(true);
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void initGameOverPanel(){
        layout = new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS);
        gameOverPanel.add(jLabel);
        gameOverPanel.add(restartButton);
        gameOverPanel.setBackground(Color.black);
        gameOverPanel.setBorder(new EmptyBorder(new Insets(250, 250, 250, 200)));
        gameOverPanel.setLayout(layout);
    }

    public void gameOver(){

        display(gameOverPanel);
    }

    public static void main(String[] args) {
        new Window();
    }

    public void initMainMenuPanel(){
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
                display(gamePanel);
                setTimeout(new Runnable() {
                    @Override
                    public void run() {
                        gamePanel.startGame();
                    }
                }, 500);
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
        JCheckBox checkBox = new JCheckBox();
        // JLabel of checkbox parameter customisation
        JLabel checkBoxSound = new JLabel("Sound:");
        checkBoxSound.setForeground(Color.ORANGE);
        checkBoxSound.setFont(new Font("ARIAL", Font.BOLD, 20));

        //ComaBox initilisation + customisation
        JComboBox comboBox = new JComboBox<>();;
        JLabel comboBoxLabel = new JLabel("screen resolution:");
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
               if(e.getStateChange() == ItemEvent.SELECTED) {
                   if(comboBox.getSelectedItem() == "800 x 600") {
                       WIDTH = 800; HEIGHT = 600;
                       setSize(WIDTH, HEIGHT);
                   }
                   else if (comboBox.getSelectedItem() == "1280 x 720") {
                       WIDTH = 1280; HEIGHT = 720;
                       setSize(WIDTH, HEIGHT);
                   } else if (comboBox.getSelectedItem() == "1920 x 1080") {
                       WIDTH = 1920; HEIGHT = 1080;
                       setSize(WIDTH,HEIGHT);
                   }
               }
            }

        });
        comboBox.addItem("800 x 600");
        comboBox.addItem("1280 x 720");
        comboBox.addItem("1920 x 1080");
        comboBoxLabel.setForeground(Color.ORANGE);
        comboBoxLabel.setFont(new Font("ARIAL", Font.BOLD, 20));
        //back button + adding components
        JButton backButton = new JButton("back");
        settingsPanel.setBackground(Color.black);
        settingsPanel.add(backButton);
        settingsPanel.add(comboBoxLabel);
        settingsPanel.add(comboBox);
        settingsPanel.add(checkBoxSound);
        settingsPanel.add(checkBox);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display(mainMenuPanel);
            }
        });
    }

    public void display(JPanel a){
        getContentPane().removeAll();
        getContentPane().add(a);
        repaint();
        printAll(getGraphics());
    }
    public void pauseMenu(){
        buttonPlay.setText("RESUME");
        display(mainMenuPanel);
        highScore.setText("HIGH SCORE: " + String.valueOf(fileHandler.getHighScore()));
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
