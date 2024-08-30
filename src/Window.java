import javax.swing.*;
import java.awt.*;

public class Window {

    public static int WIDTH = 800;
    public static int HEIGHT = 600;

    GamePanel gamePanel = new GamePanel();
    Window(){

       JFrame jFrame = new JFrame();
       jFrame.add(gamePanel);

       jFrame.setSize(800, 600);
       jFrame.setBackground(Color.black);
       jFrame.setVisible(true);
       jFrame.setResizable(false);

       gamePanel.startGame();
    }

    public static void main(String[] args) {
        new Window();
    }
}
