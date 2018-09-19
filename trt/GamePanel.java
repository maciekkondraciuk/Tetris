import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener {
    private int width = 240;
    private int height = 480;
    private int tilesWidth=10;
    ObjectController objectController;
    private int tilesHeight=20;
    private BufferedImage spriteSheet;
    private JPanel[] rowsPanel=new JPanel[tilesHeight];
    //private JPanel kol[]= new JPanel[tilesWidth];

    private GameTile[][] tilesMap= new GameTile[tilesWidth][tilesHeight];
    GamePanel(RightMenuPanel rightMenuPanel)
    {
        Object.loadData(tilesMap,width,height,tilesWidth,tilesHeight);
        File file = new File ("Textures\\piecesText.png");
        try{spriteSheet = ImageIO.read(file);}
        catch(IOException e)
        {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

      //  setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        for(int j=0; j<tilesHeight; j++)
        {
            rowsPanel[j]=new JPanel();
            rowsPanel[j].add(Box.createHorizontalGlue());
        //    kol[i]= new JPanel();
          //  kol[i].setLayout(new BoxLayout(kol[i],BoxLayout.Y_AXIS));
            for(int i=0; i<tilesWidth; i++)
            {
                tilesMap[i][j]= new GameTile();
                tilesMap[i][j].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0,0,24,24))));
                tilesMap[i][j].setX(i);
                tilesMap[i][j].setY(j);
                tilesMap[i][j].addMouseListener(tilesMap[i][j]);
                rowsPanel[j].add(tilesMap[i][j]);
            }
            rowsPanel[j].setLayout(new BoxLayout(rowsPanel[j],BoxLayout.X_AXIS));
            this.add(rowsPanel[j]);
        }

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        this.setPreferredSize(new Dimension(width,height));
        objectController = new ObjectController();
        objectController.loadData(tilesMap,tilesWidth,tilesHeight);
        objectController.connectWithPeekWindow(rightMenuPanel.getPeekWindow());
        objectController.start();
        addKeyListener(this);
        setFocusable(true);
    }
ImageIcon resizeIcon(ImageIcon img)
{
    Image image = img.getImage();
    Image resized =image.getScaledInstance(width/tilesWidth, height/tilesHeight, Image.SCALE_SMOOTH);
    return new ImageIcon(resized);
}

@Override
    public void keyTyped(KeyEvent key)
{

}
@Override
    public void keyReleased(KeyEvent key){
   if(key.getKeyCode()== KeyEvent.VK_DOWN){objectController.moveNormal();}
    }
@Override
    public void keyPressed(KeyEvent key){
    if(key.getKeyCode()== KeyEvent.VK_RIGHT){objectController.moveRight();}
    else if(key.getKeyCode()== KeyEvent.VK_LEFT){objectController.moveLeft();}
    if(key.getKeyCode()== KeyEvent.VK_DOWN){objectController.moveFaster();}
    if(key.getKeyCode()== KeyEvent.VK_Z){objectController.rotate();}
    if(key.getKeyCode()== KeyEvent.VK_R){objectController.restartGame();}
    if(key.getKeyCode()== KeyEvent.VK_P){objectController.pauseGame();}
}
}
