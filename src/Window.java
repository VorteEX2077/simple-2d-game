import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window implements ActionListener {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    GamePanel gamePanel;
    JButton jButton;

    Window() {
        gamePanel = new GamePanel(this);
        jButton = new JButton();
        JFrame jFrame = new JFrame();
        jButton.addActionListener(this);
        jFrame.add(gamePanel);
        jFrame.setSize(800, 600);
        jFrame.setBackground(Color.black);
        jFrame.setVisible(true);
        jFrame.setResizable(false);

        gamePanel.startGame();

    }

    public void gameOver(){
       // jButton.setBounds(WIDTH/2, HEIGHT/2 + 50, 40, 30);
        jButton.setText("restart");
        System.out.println("gameover() called");
        gamePanel.add(jButton);
        gamePanel.revalidate();
    }

    public static void main(String[] args) {
        new Window();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("working");
    }
}
