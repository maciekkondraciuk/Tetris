import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame(){
        super("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        RightMenuPanel rightMenuPanel = new RightMenuPanel();
        GamePanel gamePanel = new GamePanel(rightMenuPanel);
        CountersController.connect(rightMenuPanel);
        add(Box.createVerticalGlue());
        add(gamePanel);
        add(rightMenuPanel);
        setLayout(new FlowLayout());
        setLayout(new BoxLayout(this.getContentPane(),BoxLayout.X_AXIS));
        add(Box.createVerticalGlue());
        //setResizable(false);
        setVisible(true);
        pack();
    }
}
