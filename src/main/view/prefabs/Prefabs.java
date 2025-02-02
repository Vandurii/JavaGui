package main.view.prefabs;

import main.view.components.colorPicker.ThemeColor;
import main.view.interfaces.MethodBody;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static main.Configuration.*;

public class Prefabs {

    public static JButton createImageButton(int width, int height, int scale, String imagePath, MethodBody method){
        JButton button = new JButton();
        button.setSize(new Dimension(width, height));
        BufferedImage image = null;
        try{
            image = ImageIO.read(new File(imagePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        button.setIcon(new ImageIcon(image.getScaledInstance(scale, scale, Image.SCALE_SMOOTH)));
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setOpaque(false);
        addMethod(button, method);

        return button;
    }

    public static JButton createTextButton(String text, MethodBody method){
        JButton button = new JButton(text);
        button.setSize(button.getPreferredSize());
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setOpaque(false);
        addMethod(button, method);

        return button;
    }

    public static JButton createShrinkTextButton(String text, MethodBody method){
        JButton button = new JButton(text);
        button.setMargin(new Insets(0,0,0,0));
        button.setSize(button.getPreferredSize());
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorder(null);
        addMethod(button, method);

        return button;
    }

    public static void addMethod(JButton button, MethodBody method){

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                method.cast();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Color color = button.getParent().getBackground();
                button.setOpaque(true);

                if(color.getRed() > buttonThreshold && color.getGreen() > buttonThreshold && color.getBlue() > buttonThreshold){
                    color = color.darker();
                }else{
                    color = color.brighter();
                }

                button.setBackground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
                ThemeColor.resetParentColor(button);
            }
        });
    }
}


