import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    GamePanel gamePanel;
    JPanel gameOverPanel;
    JPanel mainMenuPanel;
    JPanel settingsPanel;
    JButton restartButton;
    JLabel jLabel;
    BoxLayout layout;
    Window windowObj;

    Window() {
        //the panels
        windowObj = this;
        mainMenuPanel = new JPanel();
        settingsPanel = new JPanel();
        gameOverPanel = new JPanel();
        gamePanel = new GamePanel(windowObj);

        jLabel = new JLabel();
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
        setResizable(false);
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
        JButton buttonPlay = new JButton();
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
        mainMenuPanel.add(buttonPlay);
        mainMenuPanel.add(buttonSettings);
        mainMenuPanel.add(buttonExit);
        mainMenuPanel.setBackground(Color.black);
    }

    public void initSettingsPanel(){
        JComboBox comboBox = new JComboBox<>();;
        JLabel comboBoxLabel = new JLabel("screen resolution:");
        comboBox.addItem("800x600");
        comboBox.addItem("1920 x 1080");
        comboBox.addItem("2560 x 1440");
        JButton backButton = new JButton("back");
        comboBoxLabel.setForeground(Color.ORANGE);
        comboBoxLabel.setFont(new Font("ARIAL", Font.BOLD, 20));
        settingsPanel.setBackground(Color.black);
        settingsPanel.add(backButton);
        settingsPanel.add(comboBoxLabel);
        settingsPanel.add(comboBox);
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
