package main;

import main.view.ColorPicker;
import main.view.ColorWrapper;
import main.view.MethodBody;
import main.view.View;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import static main.Configuration.*;

public class Helper {

    private static ColorPicker picker;

    public static String getFromRes(String name){
        return resPath + name;
    }

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
        addMethod(button, method);

        return button;
    }

    public static JButton createTextButton(String text, MethodBody method){
        JButton button = new JButton(text);
        button.setSize(button.getPreferredSize());
        button.setBorder(null);
        button.setFocusPainted(false);
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

                if(color.getRed() > buttonThreshold && color.getGreen() > buttonThreshold && color.getBlue() > buttonThreshold){
                    color = color.darker();
                }else{
                    color = color.brighter();
                }

                button.setBackground(color);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Color c = button.getParent().getBackground();
                button.setBackground(c);
            }
        });
    }

    public static void resetColor(Component c, ColorWrapper color){
        if(c instanceof Container container){
            for(Component com: container.getComponents()){
                resetColor(com, color);
            }
        }

        c.setBackground(color.color);
    }

    public static MethodBody createMethodForColor(ColorWrapper colorWrapper, View view){
        return () -> {
            if(picker != null){
                picker.destroy();
            }

         picker = new ColorPicker(colorPickerSize, colorPickerSize, colorSquaresPath, view, colorWrapper);
        };
    }
}


