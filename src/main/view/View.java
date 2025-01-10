package main.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import main.controlls.ML;

import static main.Constant.*;

public class View extends JFrame {

    private int windowWidth;
    private int windowHeight;

    private ML mouseListener;

    private Panel mainPanel;
    private Panel titleBar;
    private Panel toolbar;

    public View(){
        windowWidth = INITIAL_WINDOW_WIDTH;
        windowHeight = INITIAL_WINDOW_HEIGHT;
        init();
    }

    public void init() {
        mouseListener = new ML(this);

        titleBar = new Panel(windowWidth, titleBarHeight);
        titleBar.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor));
        titleBar.setDisplay(Display.flex);
        titleBar.setAlign(Align.right);

        MethodBody exitPressed = () ->{System.exit(0);};
        JButton exitButton = createButton(titleBarButWidth,titleBarButHeight, titleBarButScale, exitButtonPath, exitPressed);
        titleBar.add(exitButton);

        MethodBody minimizePressed = () ->{this.setState(Frame.ICONIFIED);};
        JButton minimizeButton = createButton(titleBarButWidth,titleBarButHeight, titleBarButScale, minimizeButtonPath, minimizePressed);
        titleBar.add(minimizeButton);

        toolbar = new Panel(windowWidth , toolbarHeight);
        toolbar.setDisplay(Display.flex);
        toolbar.setAlign(Align.right);

        MethodBody empty = () -> {System.out.println("I'm just empty method body");};
        JButton addButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, empty);
        toolbar.add(addButton);

        JButton editButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, editButtonPath, empty);
        toolbar.add(editButton);

        JButton deleteButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, empty);
        toolbar.add(deleteButton);

        JButton settingsButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, empty);
        toolbar.add(settingsButton);

        mainPanel = new Panel(windowWidth, windowHeight);
        mainPanel.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor));
        mainPanel.setDisplay(Display.block);

        mainPanel.add(titleBar);
        mainPanel.add(toolbar);

        this.setUndecorated(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setSize(windowWidth, windowHeight);
        this.setOpacity(winAlpha);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        this.add(mainPanel);
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public void refresh(){
        titleBar.setSize(windowWidth, titleBarHeight);
        titleBar.calcComp();
        toolbar.setSize(windowWidth, toolbarHeight);
        toolbar.calcComp();
        mainPanel.calcComp();
    }

    public JButton createButton(int width, int height, int scale, String imagePath, MethodBody method){
        JButton button = new JButton();
        button.setSize(new Dimension(width, height));
        BufferedImage image = null;
        try{
            image = ImageIO.read(new File(imagePath));
        }catch (Exception e){
            e.printStackTrace();
        }
        button.setIcon(new ImageIcon(image.getScaledInstance(scale, scale, Image.SCALE_SMOOTH)));
        button.setBackground(titleBarButColNor);
        button.setBorder(null);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                method.cast();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(titleBarButColHov);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(titleBarButColNor);
            }
        });

        return button;
    }
}
