import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class rightTextMenu extends JPanel {

    private JLabel[] textBoxes= new JLabel[3];
    BufferedImage spriteSheet;
    private JPanel yPanel= new JPanel();
    rightTextMenu(){
        File file = new File ("Textures\\textText.png");
        try{spriteSheet = ImageIO.read(file);}
        catch(IOException e)
        {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
        for(int i=0;i<3;i++){
            textBoxes[i]=new JLabel(new ImageIcon(spriteSheet.getSubimage(0,i*48,144,48)));
            yPanel.add(textBoxes[i]);
            textBoxes[i].setPreferredSize(new Dimension(144,48));

        }
        yPanel.setLayout(new BoxLayout(yPanel,BoxLayout.Y_AXIS));

        this.add(Box.createRigidArea(new Dimension(48,0)));
        this.add(yPanel);
        this.add(Box.createHorizontalGlue());
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }
}
