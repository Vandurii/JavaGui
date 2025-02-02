package main.controlls;

import main.view.components.colorPicker.ThemeColor;
import main.view.prefabs.Prefabs;
import main.view.components.colorPicker.ColorPicker;
import main.view.components.colorPicker.ColorWrapper;
import main.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Configuration.*;

public class ColorPickerML extends ML {
    private ColorPicker pickerFrame;
    private ColorWrapper color;
    private BufferedImage image;
    private View view;
    private JButton pointer;

    public ColorPickerML(ColorPicker pickerFrame, Image image, View view, JButton pointer, ColorWrapper color){
        BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = bufImage.createGraphics();
        graph.drawImage(image, 0, 0, null);
        graph.dispose();

        this.pickerFrame = pickerFrame;
        this.color = color;
        this.image = bufImage;
        this.view = view;
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

        view.resetThemeColor();
        ThemeColor.resetColor(pickerFrame, primaryThemeColor);
        pointer.setLocation(posX, posY);
    }
}