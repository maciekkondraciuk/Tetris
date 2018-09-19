import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RightMenuPanel extends JPanel {

    private int width = 240;
    private int height = 480;
    private nextPeekWindow peekWindow = new nextPeekWindow();
    private rightTextMenu textMenu = new rightTextMenu();
    private CountersWindow countersWindow = new CountersWindow();

    RightMenuPanel(){


        add(Box.createRigidArea(new Dimension(240,48)));
        add(peekWindow);
        //add(Box.createRigidArea(new Dimension(240,0)));
        add(countersWindow);
        add(textMenu);
        add(Box.createVerticalGlue());
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(width,height));
    }
    nextPeekWindow getPeekWindow(){return peekWindow;}
    public void setLvlCounter(int value){countersWindow.setLvlCounter(value);}
    public void setPointsCounter(int value){countersWindow.setPointsCounter(value);}
    public void setLinesCounter(int value){countersWindow.setLinesCounter(value);}
}
