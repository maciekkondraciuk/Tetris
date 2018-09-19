import javax.swing.*;

public class GameTile extends JLabel{
    private int x, y;
    private boolean isSomething = false;

    GameTile() {
        super();
    }

    public void putSomething() {
        isSomething = true;
    }

    public void cleanSomething() {
        isSomething = false;
    }

    public boolean getIsSomething() {
        return isSomething;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}