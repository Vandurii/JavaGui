package main.view;

import main.Helper;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import static main.Constant.*;

public class ColorPicker extends JFrame {
    private Image image;
    private View view;

    public ColorPicker(int width, int height, BufferedImage bufImage, View view){
        Panel mainPanel = new Panel(width, height);
        mainPanel.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor));
        mainPanel.setDisplay(Display.block);

        Panel titleBar = new Panel(width, titleBarHeight);
        titleBar.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor));
        titleBar.setDisplay(Display.flex);
        titleBar.setAlign(Align.right);

        MethodBody closeMethod = ()->{
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            view.deleteFirstCP();
        };
        JButton closeButton = Helper.createButton(titleBarButWidth, titleBarButHeight, titleBarButScale, closeButtonPath, closeMethod);
        titleBar.add(closeButton);

        this.image = bufImage.getScaledInstance(width, height - titleBarHeight, bufImage.SCALE_SMOOTH);;
        this.view = view;

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

    public class MListener extends MouseAdapter implements MouseMotionListener {
        private ColorPicker frame;
        private BufferedImage image;
        private View view;
        private JButton pointer;

        private double x;
        private double y;

        public MListener(ColorPicker frame, Image image, View view, JButton pointer){
            BufferedImage bufImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D graph = bufImage.createGraphics();
            graph.drawImage(image, 0, 0, null);
            graph.dispose();

            this.frame = frame;
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

            frame.setLocation(cPosS.x - (int) x, cPosS.y - (int) y);
        }

        public void pickColor(MouseEvent e){
            int posY = (e.getY() - titleBarHeight);
            int posX = e.getX();

            if(posY < 0){
                return;
            }else if(e.getY() + pointer.getHeight() > frame.getHeight()){
                posY = frame.getHeight() - titleBarHeight - pointer.getHeight() ;
            }

            if(posX < 0){
                posX = 0;
            }else if(posX  > frame.getWidth() - pointer.getWidth()){
                posX = frame.getWidth() - pointer.getWidth();
            }

            firstThemeColor = new Color(image.getRGB(posX, posY));

            view.resetThemeColor();
            Helper.resetColor(frame, firstThemeColor);
            pointer.setLocation(posX, posY);
        }
    }
}
