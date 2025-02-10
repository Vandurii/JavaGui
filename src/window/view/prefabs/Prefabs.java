package window.view.prefabs;

import window.tools.LayoutManager;
import window.tools.saver.SaveBoolean;
import window.tools.saver.SaveColor;
import window.tools.saver.SaveInt;
import window.tools.ThemeManager;
import window.interfaces.MethodBody;
import window.view.elements.Panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.plaf.basic.BasicSliderUI;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

import static window.Configuration.*;

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
        JButton button = createDefaultButton(text);
        addMethod(button, method);

        return button;
    }

    public static JButton createColoredTextButton(String text, MethodBody method, SaveColor<Color> color){
        JButton button = createDefaultButton(text);
        button.setForeground(color.getValue());
        addMethod(button, method, color);

        return button;
    }

    private static JButton createDefaultButton(String text){
        JButton button = new JButton(text);
        button.setMargin(new Insets(0,0,0,0));
        button.setFont(settingsFont);
        button.setSize(button.getPreferredSize());
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setOpaque(false);

        return button;
    }

    public static void addMethod(JButton button, MethodBody method, SaveColor<Color> col){

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ThemeManager.repaintParentTree(button);
                method.cast();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setOpaque(true);

                Color color = col.getValue();

                if(color.getRed() > buttonThreshold && color.getGreen() > buttonThreshold && color.getBlue() > buttonThreshold){
                    color = color.darker();
                }else{
                    color = color.brighter();
                }

                button.setBackground(color);
                button.setForeground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
                button.setForeground(col.getValue());
                ThemeManager.repaintParentTree(button);
            }
        });
    }


    public static void addMethod(JButton button, MethodBody method){

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ThemeManager.repaintParentTree(button);
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
                ThemeManager.repaintParentTree(button);
            }
        });
    }

    public static JSlider createSlider(int width, int height, int maxVal, SaveInt<Integer> saveInt, MethodBody method) {
        return Slider.createSlider(width, height, maxVal, saveInt, method);
    }

    public static JCheckBox createBox(int size, int borderRadius, int borderLineWidth, SaveBoolean<Boolean> saveBol, MethodBody method) {
        CheckBox box = new CheckBox(size, borderRadius, borderLineWidth);
        box.setSelected(saveBol.getValue());
        box.addActionListener(e -> method.cast());

        return box;
    }

    public static CheckBox createDisabledBox(int size, int borderRadius, int borderLineWidth) {
        CheckBox box = new CheckBox(size, borderRadius, borderLineWidth);
        box.setFocusable(false);
        box.setEnabled(false);

        return box;
    }

    public static JLabel createLabel(String text){
        return createDefaultLabel(text);
    }

    public static JLabel createLabel(String text, boolean highLight){
        JLabel label = createDefaultLabel(text);
        if(highLight) addHighlight(label);

        return label;
    }

    private static JLabel createDefaultLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(settingsFont);
        label.setBorder(labelBorder);
        label.setSize(label.getPreferredSize());

        return label;
    }

    public static void addHighlight(JLabel label){
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color color = label.getParent().getBackground();
                label.setOpaque(true);

                if (color.getRed() > buttonThreshold && color.getGreen() > buttonThreshold && color.getBlue() > buttonThreshold) {
                    color = color.darker();
                } else {
                    color = color.brighter();
                }

                label.setBackground(color);
                label.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setOpaque(false);
                ThemeManager.repaintParentTree(label);
            }
        });
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


