package main.view.components.colorPicker;

import main.view.prefabs.Prefabs;
import main.controlls.ColorPickerML;
import main.view.interfaces.MethodBody;
import main.view.View;
import main.view.components.Panel;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;
import main.tools.saver.SaveColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static main.Configuration.*;

public class ColorPicker extends JFrame {
    private Image image;

    public ColorPicker(int width, int height, String imagePath, View view, SaveColor<Color> color) {
        Panel mainPanel = new Panel(width, height);
        int lineVal = borderLineWidth.getValue();
        mainPanel.setBorder(new MatteBorder(lineVal, lineVal, lineVal, lineVal, borderLineColor.getValue()));
        mainPanel.setDisplay(Display.block);

        main.view.components.Panel titleBar = new Panel(width, titleBarHeight, pickerPaddingX, pickerPaddingY);
        titleBar.setBorder(new MatteBorder(lineVal, lineVal, lineVal, lineVal, borderLineColor.getValue()));
        titleBar.setDisplay(Display.flex);
        titleBar.setAlignVer(AlignVer.right);
        titleBar.setAlignHor(AlignHor.center);

        MethodBody closeMethod = this::destroy;
        JButton closeButton = Prefabs.createImageButton(titleBarButWidth, titleBarButHeight, titleBarButScale, closeButtonPath, closeMethod);
        titleBar.add(closeButton);

        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(new File(imagePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        this.image = bufImage.getScaledInstance(width, height - titleBarHeight, bufImage.SCALE_SMOOTH);;

        Panel colorPanel = new Panel(width, height - titleBarHeight){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(image, 0, 0, null);
            }
        };

        JButton pointer = new JButton();
        pointer.setSize(pointerSize, pointerSize);

        colorPanel.add(pointer);

        mainPanel.add(titleBar);
        mainPanel.add(colorPanel);

        ColorPickerML mouseListener = new ColorPickerML(this, this.image, view, pointer, color);

        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setOpacity(winAlphaComposite.getValue() / 100f);
        this.setSize(width, height);
        this.setVisible(true);

        this.add(mainPanel);

        ThemeManager.resetColor(this, primaryThemeColor);
    }

    public void destroy(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
