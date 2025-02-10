package window.tools;

import window.view.prefabs.Prefabs;
import window.controlls.ColorPickerML;
import window.interfaces.MethodBody;
import window.view.Window;
import window.view.elements.Panel;
import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;
import window.tools.saver.SaveColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static window.Configuration.*;

public class ColorPicker extends JFrame {
    private Image image;

    public ColorPicker(int width, int height, String imagePath, Window window, SaveColor<Color> color) {
        Panel mainPanel = new Panel(width, height);
        mainPanel.addBorder(true);
        mainPanel.setDisplayMode(DisplayMode.block);

        window.view.elements.Panel titleBar = new Panel(width, titleBarHeight, pickerPaddingX, pickerPaddingY);
        titleBar.addBorder(true);
        titleBar.setDisplayMode(DisplayMode.flex);
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

        ColorPickerML mouseListener = new ColorPickerML(this, this.image, window, pointer, color);

        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setOpacity(winAlphaComposite.getValue() / 100f);
        this.setSize(width, height);
        this.setVisible(true);

        this.add(mainPanel);

        ThemeManager.repaintChildTree(this, primaryThemeColor);
    }

    public void destroy(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
