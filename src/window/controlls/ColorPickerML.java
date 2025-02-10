package window.controlls;

import window.tools.ThemeManager;
import window.tools.ColorPicker;
import window.tools.saver.SaveColor;
import window.view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static window.Configuration.*;

public class ColorPickerML extends ML {
    private ColorPicker pickerFrame;
    private SaveColor<Color> color;
    private BufferedImage image;
    private Window window;
    private JButton pointer;

    public ColorPickerML(ColorPicker pickerFrame, Image image, Window window, JButton pointer, SaveColor<Color> color){
        BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = bufImage.createGraphics();
        graph.drawImage(image, 0, 0, null);
        graph.dispose();

        this.pickerFrame = pickerFrame;
        this.color = color;
        this.image = bufImage;
        this.window = window;
        this.pointer = pointer;
    }

    @Override
    public void mousePressed(MouseEvent e){
        pickColor(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        moveWindow(e.getLocationOnScreen());
        pickColor(e);
    }

    public void moveWindow(Point cPosS){
        if(y > titleBarHeight) return;

        pickerFrame.setLocation(cPosS.x - (int) x, cPosS.y - (int) y);
    }

    public void pickColor(MouseEvent e){
        int posY = (e.getY() - titleBarHeight);
        int posX = e.getX();

        if(posY < 0){
            return;
        }else if(e.getY() + pointer.getHeight() > pickerFrame.getHeight()){
            posY = pickerFrame.getHeight() - titleBarHeight - pointer.getHeight() ;
        }

        if(posX < 0){
            posX = 0;
        }else if(posX  > pickerFrame.getWidth() - pointer.getWidth()){
            posX = pickerFrame.getWidth() - pointer.getWidth();
        }

        color.addAlpha(new Color(image.getRGB(posX, posY)));;
        window.resetThemeColor();
        ThemeManager.repaintChildTree(pickerFrame, primaryThemeColor);

        pointer.setLocation(posX, posY);
    }
}