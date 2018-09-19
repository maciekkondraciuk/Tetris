import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class nextPeekWindow extends JPanel{

    private int width = 24*6;
    private int height = 24*4;
    private int[] x=new int[4];
    private int[] y=new int[4];
    int middle=2;
    private int yOffset=1, xOffset=0;
    int typeOffset;
    private BufferedImage spriteSheet;
    private Object.objectType typeNum;
    private GameTile[][] peekTilesMap=new GameTile[6][4];
    private JPanel[] rows=new JPanel[4];
    nextPeekWindow(){
        File file = new File ("Textures\\piecesText.png");
        try{spriteSheet = ImageIO.read(file);}
        catch(IOException e)
        {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }

        for(int j=0; j<4; j++)
        {
            rows[j]=new JPanel();
            rows[j].add(Box.createRigidArea(new Dimension(48,0)));
            for(int i=0; i<6; i++)
            {
                peekTilesMap[i][j]= new GameTile();
                peekTilesMap[i][j].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0,0,24,24))));
                rows[j].add(peekTilesMap[i][j]);
            }
            rows[j].setLayout(new BoxLayout(rows[j], BoxLayout.X_AXIS));
            rows[j].add(Box.createHorizontalGlue());
            this.add(rows[j]);
        }

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(width,height));

    }
    ImageIcon resizeIcon(ImageIcon img)
    {
        Image image = img.getImage();
        Image resized =image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    public void drawIObject() {
        x[0]= middle-1;     y[0]=yOffset;
        x[1]= middle;       y[1]=yOffset;
        x[2]= middle+1;     y[2]=yOffset;
        x[3]= middle+2;     y[3]=yOffset;
        typeNum=Object.objectType.I;
        draw();
    }
    public void drawLObject() {
        x[0]= middle-1;     y[0]=1;
        x[1]= middle;       y[1]=1;
        x[2]= middle+1;     y[2]=1;
        x[3]= middle+1;     y[3]=2;
        typeNum=Object.objectType.L;
        draw();
    }
    public void drawJObject() {
        x[0]= middle-1;     y[0]=yOffset+0;
        x[1]= middle;       y[1]=yOffset+0;
        x[2]= middle+1;     y[2]=yOffset+0;
        x[3]= middle-1;     y[3]=yOffset+1;
        typeNum=Object.objectType.J;
        draw();

    }
    public void drawTObject() {
        x[0]= middle-1;         y[0]=yOffset+0;
        x[1]= middle;           y[1]=yOffset+0;
        x[2]= middle+1;         y[2]=yOffset+0;
        x[3]= middle;           y[3]=yOffset+1;
        typeNum=Object.objectType.T;
        draw();
    }
    public void drawSObject() {
        x[0]= middle+1;   y[0]=yOffset+0;
        x[1]= middle;     y[1]=yOffset+0;
        x[2]= middle;     y[2]=yOffset+1;
        x[3]= middle-1;   y[3]=yOffset+1;
        typeNum=Object.objectType.S;
        draw();
    }
    public void drawZObject() {
        x[0]= middle;       y[0]=yOffset+1;
        x[1]= middle;       y[1]=yOffset+0;
        x[2]= middle-1;     y[2]=yOffset+0;
        x[3]= middle+1;     y[3]=yOffset+1;
        typeNum=Object.objectType.Z;
        draw();

    }
    public void drawOObject() {
        x[0]= middle;       y[0]=yOffset+1;
        x[1]= middle;       y[1]=yOffset+0;
        x[2]= middle+1;     y[2]=yOffset+0;
        x[3]= middle+1;     y[3]=yOffset+1;
        typeNum=Object.objectType.O;
        draw();
    }
    private void clean(){
        for(int i=0;i<6;i++)
        {
            for (int j=0;j<4;j++)
            {
                peekTilesMap[i][j].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0,0,24,24))));
            }
        }
    }
    private void draw(){
        switch (typeNum)
        {
            case I: typeOffset=1;break;
            case L: typeOffset=2;break;
            case J: typeOffset=3;break;
            case T: typeOffset=4;break;
            case O: typeOffset=5;break;
            case S: typeOffset=6;break;
            case Z: typeOffset=7;break;
            default:typeOffset=-1; break;
        }
        clean();
        if (typeOffset==-1)
        {
            peekTilesMap[0][1].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24,0,24,24))));
            peekTilesMap[2][1].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24,0,24,24))));
            peekTilesMap[3][2].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24,0,24,24))));
            peekTilesMap[4][2].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24,0,24,24))));
            peekTilesMap[5][1].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24,0,24,24))));
            peekTilesMap[4][0].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24,0,24,24))));
            return;
        }
        for(int i=0;i<4;i++)
        {
            peekTilesMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*typeOffset,0,24,24))));
        }
    }
}
