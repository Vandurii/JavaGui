package main.controlls;

import main.view.View;

import java.awt.*;
import java.awt.event.MouseEvent;

import static main.Configuration.*;

public class MainWindowML extends ML {
    private View view;

    private Edge currentEdge;
    private int windowWidth;
    private int windowHeight;

    public MainWindowML(View view){
        this.view = view;
        this.mouseButton = -1;
    }

    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();

        if(!view.isMaximized()){
        scanEdge();
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        mouseButton = e.getButton();
        startDragX = e.getLocationOnScreen().x;
        startDragY = e.getLocationOnScreen().y;

        if(currentEdge != Edge.none){
            windowWidth = view.getWindowWidth();
            windowHeight = view.getWindowHeight();
            startWinPosX = view.getX();
            startWinPosY = view.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
        reset();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point cPosS = e.getLocationOnScreen();

        if(!view.isMaximized()) {
            moveWindow(cPosS);
            resizeWindow(cPosS);
        }
    }

    public void moveWindow(Point cPosS){
        if(currentEdge != Edge.none) return;
        if(y > titleBarHeight) return;

        view.setLocation(cPosS.x - (int) x, cPosS.y - (int) y);
    }

    public void scanEdge(){
        int width = view.getWidth();
        int height = view.getHeight();
        int cursor = Cursor.DEFAULT_CURSOR;

        boolean leftEdge =  x > 0 && x < edgeMargin;
        boolean rightEdge = x > width - edgeMargin && x < width;
        boolean topEdge = y > 0 && y < edgeMargin;
        boolean bottomEdge = y > height - edgeMargin && y < height;

        if(leftEdge && topEdge){
            cursor = Cursor.NW_RESIZE_CURSOR;
            currentEdge = Edge.leftTop;
        }else if(rightEdge && topEdge){
            cursor = Cursor.NE_RESIZE_CURSOR;
            currentEdge = Edge.rightTop;
        }else if(bottomEdge && rightEdge){
            cursor = Cursor.SE_RESIZE_CURSOR;
            currentEdge = Edge.rightBottom;
        }else if(bottomEdge && leftEdge){
            cursor = Cursor.SW_RESIZE_CURSOR;
            currentEdge = Edge.leftBottom;
        }else if(leftEdge){
            cursor = Cursor.W_RESIZE_CURSOR;
            currentEdge = Edge.left;
        }else if(rightEdge){
            cursor = Cursor.E_RESIZE_CURSOR;
            currentEdge = Edge.right;
        }else if(topEdge){
            cursor = Cursor.N_RESIZE_CURSOR;
            currentEdge = Edge.top;
        }else if(bottomEdge){
            cursor = Cursor.N_RESIZE_CURSOR;
            currentEdge = Edge.bottom;
        }else{
            currentEdge = Edge.none;
        }

        view.setCursor(cursor);
    }

    public void resizeWindow(Point cPosS){
        if(currentEdge == Edge.none) return;
        if(mouseButton != 1) return;

        int dx = (int) (cPosS.x - startDragX);
        int dy = (int) (cPosS.y - startDragY);
        int width = windowWidth;
        int height = windowHeight;
        int startX = startWinPosX;
        int startY = startWinPosY;

        switch (currentEdge){
            case Edge.leftTop:
                startY += dy;
                height -= dy;
                width -= dx;
                startX += dx;
                break;
            case Edge.rightTop:
                startY += dy;
                height -= dy;
                width += dx;
                break;
            case Edge.rightBottom:
                height += dy;
                width += dx;
                break;
            case Edge.leftBottom:
                width -= dx;
                startX += dx;
                height += dy;
                break;
            case Edge.left:
                width -= dx;
                startX += dx;
                break;
            case Edge.right:
                width += dx;
                break;
            case Edge.top:
                startY += dy;
                height -= dy;
                break;
            case Edge.bottom:
                height += dy;
                break;
        }

        // don't decrease window size when the window reached minimum size
        if(width < minWinWidth) return;
        if(height < minWinHeight) return;

        view.setLocation(startX, startY);
        view.setWindowWidth(width);
        view.setWindowHeight(height);
        view.refresh();
    }

    public void reset(){
        mouseButton = -1;
        windowWidth = 0;
        windowHeight = 0;
        startWinPosX = 0;
        startWinPosY = 0;

        startDragY = 0;
        startDragX = 0;
    }
}
