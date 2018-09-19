import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameTile extends JLabel implements MouseListener {
    private int x, y;
    private boolean isSomething = false;

    GameTile() {
        super();
    }
    GameTile(String str) {
        super(str);
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

    @Override
    public void mouseClicked(MouseEvent e) {
    System.out.print(x+" "+y+"\n");
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}