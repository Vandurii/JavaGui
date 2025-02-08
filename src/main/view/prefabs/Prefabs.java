package main.view.prefabs;

import main.tools.LayoutManager;
import main.tools.saver.SaveBoolean;
import main.tools.saver.SaveFloat;
import main.view.View;
import main.view.components.colorPicker.ThemeColor;
import main.view.interfaces.MethodBody;
import main.view.components.Panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

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
        button.setMargin(new Insets(0,0,0,0));
        button.setFont(settingsFont);
        button.setSize(button.getPreferredSize());
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setOpaque(false);
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

    public static JSlider createSlider(int width, int height, SaveFloat<Float> saveFloat, MethodBody method) {
        int startValue = (int)(saveFloat.getValue() * 100);

        JSlider slider = new JSlider(0, 100, startValue);
        slider.setSize(width, height);
        slider.setOpaque(false);

        ChangeListener changeListener = e -> {
            saveFloat.setValue(slider.getValue() / 100f);
            method.cast();
        };
        slider.addChangeListener(changeListener);
        return slider;
    }

    public static JCheckBox createDisabledBox(int size, SaveBoolean<Boolean> saveBol, MethodBody method) {
        JCheckBox box = new JCheckBox();
        box.setSelected(saveBol.getValue());
        box.setOpaque(false);
        box.setSize(size, size);
        box.addActionListener(e -> method.cast());

        return box;
    }

    public static JCheckBox createDisabledBox(int size) {
        JCheckBox box = new JCheckBox();
        box.setFocusable(false);
        box.setEnabled(false);
        box.setOpaque(false);
        box.setSize(size, size);

        return box;
    }

    public static Panel createPanel(int paddingX, int paddingBetweenX, Component ...components){
        List<Component>  componentList = Arrays.asList(components);
        int height = LayoutManager.findHighest(componentList);
        int width = LayoutManager.getMinPanelWidth(paddingX, paddingBetweenX, componentList);

        Panel panel = new Panel(width, height);
        panel.setOpaque(false);
        for(Component c: components){
            panel.add(c);
        }

        return panel;
    }
}


