import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

public class ObjectController extends Thread{

    private Object obj;

    private double speed = 1;
    private double resSpeed = 1;

    private long time;
    private long lastTime;
    private long moveTime = 1000;

    private boolean isGamePaused = false;
    private boolean canMove = false;
    private boolean restart = false;
    private boolean isGameLost = false;

    private int currentObjType = 0;
    private int nextObjType = 0;

    private static nextPeekWindow peekWindow;
    private Queue<Character> moveQueue = new PriorityQueue<>();

    public void run(){
        while(true)
        {
            restart = false;
            lastTime = System.currentTimeMillis();
            time = 0;
            while(!isGameLost) {
                lastTime = System.currentTimeMillis();
                time = 0;
                if(firstBuildLoop() == 1) break;

                if(!obj.canBeBuild()){
                    isGameLost = true;
                    break;
                }

                objectMovingLoop();

                if(restart) break;
            }
            if(restart) continue;

            while(true) {
                try {this.sleep(50 );}
                catch (InterruptedException e) {

                    System.out.print("watek obslugi objektow zostal przerwany");
                    return;
                }
                if(moveQueue.isEmpty() == false)
                {
                    char c = moveQueue.poll();
                    if(c == 'O'){
                        newGameProcedure();
                        break;
                    }
                }
            }
        }

    }
    private int firstBuildLoop(){
        while(true) {
            if(!isGamePaused) time += (System.currentTimeMillis() - lastTime) * speed;
            lastTime = System.currentTimeMillis();

            try { this.sleep(50 ); }
            catch (InterruptedException e) {

                System.out.print("watek obslugi objektow zostal przerwany");
                return -1;
            }
            if(time > moveTime / 2)
            {
                speed = Object.getSpeed();
                resSpeed = Object.getResSpeed();

                obj = rollAndBuildObj();

                while(!moveQueue.isEmpty()){
                    if( moveQueue.peek() == 'O') break;
                    moveQueue.poll();
                }

                if(!moveQueue.isEmpty() && moveQueue.peek() == 'O')
                {
                    examineMoves();
                    return 1;
                }
                canMove = true;
                time -= moveTime/2;
                break;
            }
        }
        return 0;
    }
    private void objectMovingLoop(){
        while(!obj.getStopMoving())
        {
            if(!isGamePaused) time += (System.currentTimeMillis() - lastTime) * speed;
            lastTime = System.currentTimeMillis();
            examineMoves();
            if(restart) return;
            try {this.sleep(50);}
            catch (InterruptedException e) {

                System.out.print("watek obslugi objektow zostal przerwany");
                return;
            }
            if(time > moveTime) {
                time -= moveTime;
                obj.moveDown();
            }

        }
    }

    private Object rollAndBuildObj(){
        Random rand = new Random();

        if (currentObjType == 0){nextObjType = rand.nextInt(7) + 1;}
        currentObjType = nextObjType;

        nextObjType = rand.nextInt(7) + 1;
        switch (nextObjType)
        {
            case 1: {peekWindow.drawIObject(); break;}
            case 2: {peekWindow.drawJObject(); break;}
            case 3: {peekWindow.drawLObject(); break;}
            case 4: {peekWindow.drawTObject(); break;}
            case 5: {peekWindow.drawSObject(); break;}
            case 6: {peekWindow.drawZObject(); break;}
            case 7: {peekWindow.drawOObject(); break;}
        }
        switch (currentObjType)
        {
            case 1: {return Object.makeIObject();}
            case 2: {return Object.makeJObject();}
            case 3: {return Object.makeLObject();}
            case 4: {return Object.makeTObject();}
            case 5: {return Object.makeSObject();}
            case 6: {return Object.makeZObject();}
            case 7: {return Object.makeOObject();}
        }
        System.out.print("ObjectController.RollAndBuildObj() error, random number out of bound, I will build I object by default");
        return Object.makeIObject();
    }

    public void moveRight() {if(canMove && !isGamePaused) moveQueue.add('R');}
    public void moveLeft()  {if(canMove && !isGamePaused) moveQueue.add('L');}

    public void rotate()    {if(canMove && !isGamePaused) moveQueue.add('E');}
    public void restartGame()   {moveQueue.add('O');}

    private void examineMoves() {
        while(!moveQueue.isEmpty())
        {
            switch(moveQueue.poll())
            {
                case 'R': {obj.moveRight(); break;}
                case 'L': {obj.moveLeft();  break;}
                case 'E': {obj.rotate();    break;}
                case 'O': {this.newGameProcedure(); break; }
            }
        }
    }
    public void moveFaster(){speed = 15;}

    public void moveNormal(){speed = resSpeed;}

    public static void connectWithPeekWindow(nextPeekWindow inPeekWindow){peekWindow=inPeekWindow; }
    private void newGameProcedure(){
        Object.newGameProcedure();
        restart = true;
        isGamePaused = false;
        isGameLost = false;

        moveQueue.clear();

        Object.restartSpeed();
        speed = Object.getSpeed();
        resSpeed = Object.getResSpeed();

        CountersController.restartCounters();
    }
    public void pauseGame(){isGamePaused = !isGamePaused; moveQueue.clear();}
}
