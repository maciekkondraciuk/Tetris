import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel implements KeyListener {
    private int panelWidth = 240;
    private int panelHeight = 480;
    private int tilesInRow =10;
    private int tilesInKol =20;
    private ObjectController objectController;
    private BufferedImage spriteSheet;
    private JPanel[] rowsPanel=new JPanel[tilesInKol];

    private GameTile[][] tilesMap= new GameTile[tilesInRow][tilesInKol];
    GamePanel(RightMenuPanel rightMenuPanel)
    {
        Object.loadData(tilesMap, panelWidth, panelHeight, tilesInRow, tilesInKol);

        File file = new File ("Textures\\piecesText.png");
        try{spriteSheet = ImageIO.read(file);}
        catch(IOException e)
        {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

        for(int j = 0; j< tilesInKol; j++)
        {
            rowsPanel[j]=new JPanel();
            rowsPanel[j].add(Box.createHorizontalGlue());

            for(int i = 0; i< tilesInRow; i++)
            {
                tilesMap[i][j]= new GameTile();
                tilesMap[i][j].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0,0,24,24))));
                tilesMap[i][j].setX(i);
                tilesMap[i][j].setY(j);
                rowsPanel[j].add(tilesMap[i][j]);
            }
            rowsPanel[j].setLayout(new BoxLayout(rowsPanel[j],BoxLayout.X_AXIS));
            this.add(rowsPanel[j]);
        }

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(Box.createGlue());
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        objectController = new ObjectController();
        objectController.connectWithPeekWindow(rightMenuPanel.getPeekWindow());
        objectController.start();
        addKeyListener(this);
        setFocusable(true);
    }
ImageIcon resizeIcon(ImageIcon img)
{
    Image image = img.getImage();
    Image resized =image.getScaledInstance(panelWidth / tilesInRow, panelHeight / tilesInKol, Image.SCALE_SMOOTH);
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

        switch(key.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: {objectController.moveRight();  break;}
            case KeyEvent.VK_LEFT:  {objectController.moveLeft();   break;}
            case KeyEvent.VK_DOWN:  {objectController.moveFaster(); break;}
            case KeyEvent.VK_Z:     {objectController.rotate();     break;}
            case KeyEvent.VK_R:     {objectController.restartGame();break;}
            case KeyEvent.VK_P:     {objectController.pauseGame();  break;}
        }
    }
}
