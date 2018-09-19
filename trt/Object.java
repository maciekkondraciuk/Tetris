import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Object {
    public enum objectType{
        I,
        L,
        J,
        T,
        O,
        S,
        Z
    }
    objectType typeNum;
    private int typeOffset=0;
    private int x[] = new int[4];
    private int y[] = new int[4];
    private boolean moveDown = true;
    private boolean moveRight = true;
    private boolean moveLeft = true;
    private boolean stopMoving = false;
    static BufferedImage spriteSheet;
    private static GameTile[][] tileMap;
    private static int mapWidth;
    private static int mapHeight;
    private static double speed =1;
    private static double resSpeed=1;
    private static double speedFactor=0.15;
    private CountersController countersController = new CountersController();
    private static int width;
    private static int height;
    private int remY[]=new int[4];
    private static int[] freeInRow,freeInKol;
    public static void loadData(GameTile[][] inMapTile, int inMapWidth, int inMapHeight, int inWidth, int inHeight) {
        tileMap=inMapTile;
        mapWidth=inMapWidth;
        mapHeight=inMapHeight;
        width=inWidth;
        height=inHeight;
        freeInRow=new int[height];
        freeInKol=new int[width];
        for(int i=0;i<height;i++) freeInRow[i]=width;
        for(int i=0;i<width;i++) freeInKol[i]=height;
        File file = new File ("Textures\\piecesText.png");
        try{spriteSheet = ImageIO.read(file);}
        catch(IOException e)
        {
            System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
        }
    }
    static ImageIcon resizeIcon(ImageIcon img) {
        Image image = img.getImage();
        Image resized =image.getScaledInstance(mapWidth/width, mapHeight/height, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }
    void moveRight() {
        moveRight=true;
        for(int i=0; i<4; i++)
        {
            if(x[i]>width-2 || tileMap[x[i]+1][y[i]].getIsSomething()==true){moveRight=false;}
        }
        if(moveRight){

            for(int i=0;i<4;i++) {

                x[i]++;
                tileMap[x[i] - 1][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0, 0, 24, 24))));
            }
            for(int i=0;i<4;i++)
            {
                tileMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*typeOffset,0,24,24))));
            }
        }
    }
    void moveLeft() {
        moveLeft=true;
        for(int i=0; i<4; i++)
        {
            if(x[i]<1 || tileMap[x[i]-1][y[i]].getIsSomething()==true){moveLeft=false;}
        }
        if(moveLeft){
            for(int i=0;i<4;i++) {
                x[i]--;
                tileMap[x[i] + 1][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0, 0, 24, 24))));
            }
            for(int i=0;i<4;i++)
            {
                tileMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*typeOffset,0,24,24))));

            }
        }


    }
    void moveDown() {
        for(int i=0; i<4; i++)
        {
            if(y[i]>height-2 || tileMap[x[i]][y[i]+1].getIsSomething()==true){moveDown=false;}
        }
        if(moveDown){
            for(int i=0;i<4;i++) {
                y[i]++;

                tileMap[x[i]][y[i] - 1].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0, 0, 24, 24))));
            }
            for(int i=0;i<4;i++)
            {
                tileMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*typeOffset,0,24,24))));
            }
        }else{
            for(int i=0;i<4;i++)
            {
                tileMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*typeOffset,0,24,24))));
                tileMap[x[i]][y[i]].putSomething();
                freeInRow[y[i]]--;
                freeInKol[x[i]]--;
                remY[i]=y[i];
            }
            CountersController.calculatePoints();
            for(int i=0;i<4;i++)
            {   if(freeInRow[y[i]]==0)
                {
                    cleanRow(y[i]);i--;
                }
            }
            stopMoving=true;
        }
    }
    public static void restartSpeed(){speed=1;resSpeed=1;}
    boolean canBeBuild(){
        for (int i=0;i<4;i++)
        {
            if(tileMap[x[i]][y[i]].getIsSomething()==true){return false;}
        }
        return true;
    }
    public static double getSpeed(){return speed;}
    public static double getResSpeed(){return resSpeed;}
    private void cleanRow(int row){
        countersController.addLine();
        speed+= CountersController.getLvl()*speedFactor;
        resSpeed+=CountersController.getLvl()*speedFactor;
        for (int i=0;i<width;i++)
        {
            tileMap[i][row].cleanSomething();
            tileMap[i][row].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*8,0,24,24))));
            try {
                sleep(50 );
            } catch (InterruptedException e) {

                System.out.print("watek obslugi objektow zostal przerwany");
                return;
            }
            tileMap[i][row].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0,0,24,24))));
        }
        for(int i=row;i>0;i--){freeInRow[i]=freeInRow[i-1];}
        freeInRow[0]=width;
        useGravity(row);

    }
    private void useGravity(int row){
        for (int i=0;i<width;i++)
        {
            for(int j=row;j>0;j--)
            {
                if(tileMap[i][j-1].getIsSomething()) tileMap[i][j].putSomething();
                else tileMap[i][j].cleanSomething();
                tileMap[i][j].setIcon(tileMap[i][j-1].getIcon());

            }
            tileMap[i][0].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0,0,24,24))));
        }

    }
    public boolean getStopMoving(){return stopMoving;}
    public int getX(int i){return x[i];}
    public int getY(int i){return y[i];}
    private Object(){}
    private void firstDraw(){
        switch (typeNum)
        {
            case I: typeOffset=1;break;
            case L: typeOffset=2;break;
            case J: typeOffset=3;break;
            case T: typeOffset=4;break;
            case O: typeOffset=5;break;
            case S: typeOffset=6;break;
            case Z: typeOffset=7;break;
        }
        for(int i=0;i<4;i++)
        {
            tileMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*typeOffset,0,24,24))));
        }
    }

    public void rotate(){
        int tempX;
        int tempY;
        int remX[]=new int[4];
        int remY[]=new int[4];
        boolean canRotate=true;
        for(int i=0;i<4;i++) {
            tileMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0, 0, 24, 24))));
            remX[i]=x[i];
            remY[i]=y[i];
        }

       for (int i=0;i<4;i++)
       {
            tempX= x[i]-x[1];
            tempY = y[i]-y[1];
            y[i]=y[1]+tempX;
            x[i]=x[1]-tempY;
            while(y[i]>height-1){for(int j=0;j<4;j++) y[j]--;}
            while(y[i]<0){for(int j=0;j<4;j++) y[j]++;}
            while(x[i]>width-1){for(int j=0;j<4;j++) x[j]--;}
            while(x[i]<0){for(int j=0;j<4;j++) x[j]++;}
       }
       for(int i=0;i<4;i++)
       {
           if(tileMap[x[i]][y[i]].getIsSomething()) canRotate=false;
       }
       if(canRotate==false) {
           for(int i=0;i<4;i++) {
               x[i] = remX[i];
               y[i] = remY[i];
           }
       }
        for(int i=0;i<4;i++) {
            tileMap[x[i]][y[i]].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(24*typeOffset,0,24,24))));
        }
    }
    public static void newGameProcedure(){
        for(int j=0; j<height; j++)
        {
            for(int i=0; i<width; i++) {
                tileMap[i][j].setIcon(resizeIcon(new ImageIcon(spriteSheet.getSubimage(0, 0, 24, 24))));
                tileMap[i][j].cleanSomething();
            }
        }
        for(int j=0; j<height; j++)
        {freeInRow[j]=width;}
        for(int j=0; j<width; j++)
        {freeInKol[j]=height;}
    }
    public static Object makeIObject() {

        int middle=width/2;
        Object obj = new Object();
        obj.x[0]= middle-1;     obj.y[0]=0;
        obj.x[1]= middle;       obj.y[1]=0;
        obj.x[2]= middle+1;     obj.y[2]=0;
        obj.x[3]= middle+2;     obj.y[3]=0;
        obj.typeNum=objectType.I;
        obj.firstDraw();
        return obj;
    }
    public static Object makeLObject() {

        int middle=width/2;
        Object obj = new Object();
        obj.x[0]= middle-1;     obj.y[0]=0;
        obj.x[1]= middle;       obj.y[1]=0;
        obj.x[2]= middle+1;     obj.y[2]=0;
        obj.x[3]= middle+1;     obj.y[3]=1;
        obj.typeNum=objectType.L;
        obj.firstDraw();

        return obj;
    }
    public static Object makeJObject() {

        int middle=width/2;
        Object obj = new Object();
        obj.x[0]= middle-1;       obj.y[0]=0;
        obj.x[1]= middle;       obj.y[1]=0;
        obj.x[2]= middle+1;       obj.y[2]=0;
        obj.x[3]= middle-1;     obj.y[3]=1;
        obj.typeNum=objectType.J;
        obj.firstDraw();

        return obj;
    }
    public static Object makeTObject() {

        int middle=width/2;
        Object obj = new Object();
        obj.x[0]= middle-1;         obj.y[0]=0;
        obj.x[1]= middle;           obj.y[1]=0;
        obj.x[2]= middle+1;         obj.y[2]=0;
        obj.x[3]= middle;           obj.y[3]=1;
        obj.typeNum=objectType.T;
        obj.firstDraw();

        return obj;
    }
    public static Object makeSObject() {

        int middle=width/2;
        Object obj = new Object();
        obj.x[0]= middle+1;       obj.y[0]=0;
        obj.x[1]= middle;     obj.y[1]=0;
        obj.x[2]= middle;       obj.y[2]=1;
        obj.x[3]= middle-1;     obj.y[3]=1;
        obj.typeNum=objectType.S;
        obj.firstDraw();

        return obj;
    }
    public static Object makeZObject() {

        int middle=width/2;
        Object obj = new Object();
        obj.x[0]= middle;       obj.y[0]=1;
        obj.x[1]= middle;       obj.y[1]=0;
        obj.x[2]= middle-1;     obj.y[2]=0;
        obj.x[3]= middle+1;     obj.y[3]=1;
        obj.typeNum=objectType.Z;
        obj.firstDraw();

        return obj;
    }
    public static Object makeOObject() {

        int middle=width/2;
        Object obj = new Object();
        obj.x[0]= middle;       obj.y[0]=1;
        obj.x[1]= middle;       obj.y[1]=0;
        obj.x[2]= middle+1;     obj.y[2]=0;
        obj.x[3]= middle+1;     obj.y[3]=1;
        obj.typeNum=objectType.O;
        obj.firstDraw();

        return obj;
    }






}
