import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;


public class CountersWindow extends JPanel {

    private textAreaWithBackgrd lvl;
    private textAreaWithBackgrd points;
    private textAreaWithBackgrd lines;
    private BufferedImage spriteSheet;
    private JPanel yPanel = new JPanel();

    void setLvlCounter(int lvlValue){
        String s = new String(String.valueOf(lvlValue));
        lvl.setText(" "+s);
    }
    void setPointsCounter(int pointsValue){
        String s = new String(String.valueOf(pointsValue));
        points.setText(" "+s);
    }
    void setLinesCounter(int linesValue){
        String s = new String(String.valueOf(linesValue));
        lines.setText(" "+s);
    }
    CountersWindow(){
        File file = new File ("Textures\\countersText.png");
        try{spriteSheet = ImageIO.read(file);}
        catch(IOException e)
        {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
        lvl=    new textAreaWithBackgrd(new ImageIcon(spriteSheet.getSubimage(0,0,196,48)).getImage());
        points= new textAreaWithBackgrd(new ImageIcon(spriteSheet.getSubimage(0,48,196,48)).getImage());
        lines=  new textAreaWithBackgrd(new ImageIcon(spriteSheet.getSubimage(0,96,196,48)).getImage());
        lvl.   setPreferredSize(new Dimension(196,48));
        points.setPreferredSize(new Dimension(196,48));
        lines. setPreferredSize(new Dimension(196,48));
        lvl.    setBackground(new Color(1,1,1, (float) 0.01));
        points.setBackground(new Color(1,1,1, (float) 0.01));
        lines.  setBackground(new Color(1,1,1, (float) 0.01));
        lvl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        lvl.setFont(new Font( "Bahnschrift SemiBold SemiConden", Font.BOLD, 36 ));
        lvl.setEnabled(false);
        lvl.setLineWrap(false);
        lvl.setFocusable(false);
        lvl.setEditable(false);
        lvl.setText(" 1");
        points.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        points.setFont(new Font( "Bahnschrift SemiBold SemiConden", Font.BOLD, 36 ));
        points.setEnabled(false);
        points.setLineWrap(false);
        points.setFocusable(false);
        points.setEditable(false);
        points.setText(" 0");
        lines.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        lines.setFont(new Font( "Bahnschrift SemiBold SemiConden", Font.BOLD, 36 ));
        lines.setEnabled(false);
        lines.setLineWrap(false);
        lines.setFocusable(false);
        lines.setEditable(false);
        lines.setText(" 0");

        //lvl.setIc(new ImageIcon(spriteSheet.getSubimage(0,0,196,48)));
        //points.setIcon(new ImageIcon(spriteSheet.getSubimage(0,48,196,48)));
        //lines.setIcon(new ImageIcon(spriteSheet.getSubimage(0,96,196,48)));
        //gO = spriteSheet.createGraphics();
        //gO.setColor(Color.BLACK);
        //gO.setFont(new Font( "Bahnschrift SemiBold SemiConden", Font.BOLD, 36 ));
        //String s=new String("123");
        //lastLvl=s;
        //fm=gO.getFontMetrics();
        //gO.drawString("123",196-fm.stringWidth(s)-5,40);

        yPanel.setLayout(new BoxLayout(yPanel,BoxLayout.Y_AXIS));
        yPanel.add(Box.createRigidArea(new Dimension(0,24)));
        yPanel.add(lvl);
       yPanel.add(points);
       yPanel.add(lines);
       yPanel.add(Box.createRigidArea(new Dimension(0,24)));
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
        add(Box.createRigidArea(new Dimension(24,0)));
        add(yPanel);
        add(Box.createHorizontalGlue());

    }


}
