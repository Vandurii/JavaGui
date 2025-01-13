package main.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import main.Helper;
import main.controlls.ML;

import static main.Constant.*;

public class View extends JFrame {

    private int windowWidth;
    private int windowHeight;
    private int winWidthMem;
    private int winHeightMem;
    private boolean isMaximized;

    private ML mouseListener;
    private ML secMouseListener;

    private Panel mainPanel;
    private Panel titleBar;
    private Panel toolbar;
    private Panel display;

    private ColorPicker firstColorPicker;

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
        JButton exitButton = Helper.createButton(titleBarButWidth,titleBarButHeight, titleBarButScale, exitButtonPath, exitPressed);
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

        JButton resizeButton = Helper.createButton(titleBarButWidth,titleBarButHeight, titleBarButScale, resizeButtonPath, resizeWindow);
        titleBar.add(resizeButton);

        MethodBody minimizePressed = () ->{this.setState(ColorPicker.ICONIFIED);};
        JButton minimizeButton = Helper.createButton(titleBarButWidth,titleBarButHeight, titleBarButScale, hideButtonPath, minimizePressed);
        titleBar.add(minimizeButton);

        toolbar = new Panel(windowWidth , toolbarHeight);
        toolbar.setBorder(new MatteBorder(0, borderLineSize, 0, borderLineSize, borderLineColor));
        toolbar.setDisplay(Display.flex);
        toolbar.setAlign(Align.left);

        MethodBody empty = () -> {System.out.println("I'm just empty method body");};
        JButton addButton = Helper.createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, empty);
        toolbar.add(addButton);

        JButton editButton = Helper.createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, editButtonPath, empty);
        toolbar.add(editButton);

        JButton deleteButton = Helper.createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, empty);
        toolbar.add(deleteButton);

        MethodBody settingsPressed = () -> {setttings();};
        JButton settingsButton = Helper.createButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, settingsPressed);
        toolbar.add(settingsButton);

        display = new Panel(windowWidth, windowHeight - toolbarHeight - titleBarHeight);
        display.setBorder(new MatteBorder(0, borderLineSize, borderLineSize, borderLineSize, borderLineColor));
        display.setDisplay(Display.flex);
        display.setAlign(Align.left);

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

        resetThemeColor();
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

    public void setttings(){
        MethodBody m = () -> {
            try{
                BufferedImage bufImage = ImageIO.read(new File(colorSquaresPath));
                if(firstColorPicker == null) {
                    firstColorPicker =  new ColorPicker(colorPickerSize, colorPickerSize, bufImage, this);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            resetThemeColor();
        };

        JLabel text = new JLabel("Change the Color: ");
        text.setSize(120, 30);
        display.add(text);

        JButton button = Helper.createButton(20, 20, 20, colorWheelsPath, m);
        button.setFocusable(false);
        display.add(button);

    }

    public boolean isMaximized(){
        return isMaximized;
    }

    public void resetThemeColor(){
        Helper.resetColor(this, firstThemeColor);
        Helper.resetColor(display, secondThemeColor);
        Helper.resetColor(toolbar, secondThemeColor);
    }

    public void deleteFirstCP(){
        firstColorPicker = null;
    }
}
