package window.controlls;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public abstract class ML extends MouseAdapter implements MouseMotionListener {
    protected double x;
    protected double y;

    protected double startDragX;
    protected double startDragY;
    protected int startWinPosX;
    protected int startWinPosY;
    protected int mouseButton;

    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
    }

    public abstract void moveWindow(Point cPos);

}
