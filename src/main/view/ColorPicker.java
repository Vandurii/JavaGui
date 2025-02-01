package main.view;

import main.Helper;
import main.view.components.Panel;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static main.Configuration.*;

public class ColorPicker extends JFrame {
    private Image image;
    private ColorWrapper color;

    public ColorPicker(int width, int height, String imagePath, View view, ColorWrapper color) {
        main.view.components.Panel mainPanel = new main.view.components.Panel(width, height);
        mainPanel.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor.color));
        mainPanel.setDisplay(Display.block);

        main.view.components.Panel titleBar = new main.view.components.Panel(width, titleBarHeight);
        titleBar.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor.color));
        titleBar.setDisplay(Display.flex);
        titleBar.setAlignVer(AlignVer.right);
        titleBar.setAlignHor(AlignHor.center);

        MethodBody closeMethod = this::destroy;
        JButton closeButton = Helper.createImageButton(titleBarButWidth, titleBarButHeight, titleBarButScale, closeButtonPath, closeMethod);
        titleBar.add(closeButton);

        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(new File(imagePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        this.image = bufImage.getScaledInstance(width, height - titleBarHeight, bufImage.SCALE_SMOOTH);;
        this.color = color;

        main.view.components.Panel colorPanel = new Panel(width, height - titleBarHeight){
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

        MListener m = new MListener(this, this.image, view, pointer);

        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.addMouseListener(m);
        this.addMouseMotionListener(m);
        this.setSize(width, height);
        this.setVisible(true);

        this.add(mainPanel);

        Helper.resetColor(this, firstThemeColor);
    }

    public void destroy(){
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public class MListener extends MouseAdapter implements MouseMotionListener {
        private ColorPicker pickerFrame;
        private BufferedImage image;
        private View view;
        private JButton pointer;

        private double x;
        private double y;

        public MListener(ColorPicker pickerFrame, Image image, View view, JButton pointer){
            BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D graph = bufImage.createGraphics();
            graph.drawImage(image, 0, 0, null);
            graph.dispose();

            this.pickerFrame = pickerFrame;
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

        @Override
        public void mouseMoved(MouseEvent e){
            x = e.getX();
            y = e.getY();
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

            color.color = new Color(image.getRGB(posX, posY));

            view.resetThemeColor();
            Helper.resetColor(pickerFrame, firstThemeColor);
            pointer.setLocation(posX, posY);
        }
    }
}
