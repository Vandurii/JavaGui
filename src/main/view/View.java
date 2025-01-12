package main.view;

import javax.imageio.ImageIO;
import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import javax.swing.border.BevelBorder;
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
    private int winWidthMem;
    private int winHeightMem;
    private boolean isMaximized;

    private ML mouseListener;

    private Panel mainPanel;
    private Panel titleBar;
    private Panel toolbar;
    private Panel display;

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

        MethodBody resizeWindow = () ->{
            GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            int screenMaxWidth = graphicsDevice.getDisplayMode().getWidth();
            int screenMaxHeight = graphicsDevice.getDisplayMode().getHeight();

            if(isMaximized){
                windowWidth = winWidthMem;
                windowHeight = winHeightMem;
                int screenX = (screenMaxWidth - windowWidth) / 2;
                int screenY = (screenMaxHeight - windowHeight) / 2;
                setLocation(screenX, screenY);
            }else {
                winHeightMem = windowHeight;
                winWidthMem = windowWidth;
                windowWidth = screenMaxWidth;
                windowHeight = screenMaxHeight;
                setLocation(0, 0);
            }

            refresh();
            isMaximized = !isMaximized;
        };

        JButton resizeButton = createButton(titleBarButWidth,titleBarButHeight, titleBarButScale, resizeButtonPath, resizeWindow);
        titleBar.add(resizeButton);

        MethodBody minimizePressed = () ->{this.setState(Frame.ICONIFIED);};
        JButton minimizeButton = createButton(titleBarButWidth,titleBarButHeight, titleBarButScale, hideButtonPath, minimizePressed);
        titleBar.add(minimizeButton);

        toolbar = new Panel(windowWidth , toolbarHeight);
        toolbar.setBorder(new MatteBorder(0, borderLineSize, 0, borderLineSize, borderLineColor));
        toolbar.setDisplay(Display.flex);
        toolbar.setAlign(Align.left);

        MethodBody empty = () -> {System.out.println("I'm just empty method body");};
        JButton addButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, empty);
        toolbar.add(addButton);

        JButton editButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, editButtonPath, empty);
        toolbar.add(editButton);

        JButton deleteButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, empty);
        toolbar.add(deleteButton);

        MethodBody settingsPressed = () -> {setttings();};
        JButton settingsButton = createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, settingsPressed);
        toolbar.add(settingsButton);

        display = new Panel(windowWidth, windowHeight - toolbarHeight - titleBarHeight);
        display.setBorder(new MatteBorder(0, borderLineSize, borderLineSize, borderLineSize, borderLineColor));

        mainPanel = new Panel(windowWidth, windowHeight);
        mainPanel.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor));
        mainPanel.setDisplay(Display.block);

        mainPanel.add(titleBar);
        mainPanel.add(toolbar);
        mainPanel.add(display);

        this.setUndecorated(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setSize(windowWidth, windowHeight);
        this.setOpacity(winAlpha);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        this.add(mainPanel);
        resetColor(this);
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
        setSize(windowWidth, windowHeight);
        titleBar.setSize(windowWidth, titleBarHeight);
        titleBar.calcComp();
        toolbar.setSize(windowWidth, toolbarHeight);
        toolbar.calcComp();
        mainPanel.calcComp();
        display.setSize(windowWidth, windowHeight - toolbarHeight - titleBarHeight);
        display.calcComp();
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
        button.setBorder(null);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                method.cast();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                int r = Math.max(themeColor.getRed() - butHovScalar, 0);
                button.setBackground(new Color(r, themeColor.getGreen(), themeColor.getBlue(), themeColor.getAlpha()));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(themeColor);
            }
        });

        return button;
    }

    public void setttings(){
//        JFrame colorPicker = new JFrame();
//        colorPicker.setSize(200, 200);
//        setVisible(true);

        int startX = 20;
        int startY = 20;
        int padding = 5;
        int buttonSize = 20;

        int compSize = padding + buttonSize;
        int count = display.getWidth() / compSize;
        System.out.println(count);
        System.out.println(display.getWidth());
        for(int i = 0; i < 255; i++){
            int r = 0;
            int g = 0;
            int b = 0;

            if(i < 90){
                r = i;
            }else if(i < 170 ){
                g = i;
            }else{
                b = i;
            }

            int x = ((i % count) * compSize) + padding;
            int y = ((i / count) * compSize) + padding;

            JButton but = new JButton();
            but.setBackground(new Color(r, g, b));
            but.setBorder(null);

            display.add(but);
            but.setBounds(x, y, buttonSize, buttonSize);
        }

        JColorChooser cc = new JColorChooser();

        Color c = cc.showDialog(display, "title", Color.white);
        themeColor = new Color((float)c.getRed() / 255, (float)c.getGreen() / 255, (float)c.getBlue() / 255, winAlpha);

        resetColor(this);
    }

    public void resetColor(Component c){
    if(c instanceof Container container){
        for(Component com: container.getComponents()){
                resetColor(com);
            }
        }

        c.setBackground(themeColor);
    }

    public boolean isMaximized(){
        return isMaximized;
    }
}
