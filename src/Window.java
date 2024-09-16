import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    JFrame jFrame;

    GamePanel gamePanel;
    JPanel gameOverPanel;
    JButton restartButton;
    JLabel jLabel;
    BorderLayout layout;

    Window() {
        //the panels
        jFrame = new JFrame();
        gameOverPanel = new JPanel();
        gamePanel = new GamePanel(this);

        jLabel = new JLabel();
        layout = new BorderLayout();
        restartButton = new JButton();
        restartButton.setText("restart");
        restartButton.addActionListener(this);
        gameOverPanel.setBackground(Color.red);
       // gameOverPanel.setLayout(layout);
        //jLabel.setForeground(Color.red);

        jLabel.setPreferredSize(new Dimension(330, 100));
        jLabel.setFont(new Font("arial", Font.BOLD, 50));
        jLabel.setText("GAME OVER");
        gameOverPanel.add(restartButton);
        gameOverPanel.add(jLabel,BorderLayout.CENTER);

        jFrame.add(gameOverPanel);
        jFrame.add(gamePanel);
        jFrame.setSize(800, 600);
        jFrame.setBackground(Color.black);
        jFrame.setVisible(true);
        jFrame.setResizable(false);


        gamePanel.startGame();
    }

    public void gameOver(){
        System.out.println("GAEM OVER");
        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(gameOverPanel);
        jFrame.repaint();
        jFrame.printAll(jFrame.getGraphics());
    }

    public static void main(String[] args) {
        new Window();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        gamePanel = new GamePanel(this); // created a new object gamepanel and named it the same name
        jFrame.getContentPane().removeAll();
        jFrame.getContentPane().add(gamePanel);
        jFrame.repaint();
        jFrame.printAll(jFrame.getGraphics());
        setTimeout(() -> gamePanel.startGame(), 1000);
    }
}
