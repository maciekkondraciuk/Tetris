import javax.swing.*;
import java.awt.*;

public class textAreaWithBackgrd extends JTextArea {

    private Image img;
    textAreaWithBackgrd(Image image){
        super();
        img = image;
        setMaximumSize(new Dimension(225,48));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img,0,0,null);
        super.paintComponent(g);
    }
}
