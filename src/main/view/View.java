package main.view;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import main.Helper;
import main.controlls.ML;
import main.view.components.Panel;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import static main.Configuration.*;

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

        titleBar = createTitleBar();
        toolbar = createToolbar();
        display = new Panel(windowWidth, windowHeight - toolbarHeight - titleBarHeight);

        mainPanel = new Panel(windowWidth, windowHeight);
        mainPanel.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor.color));
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

    private Panel createTitleBar(){
        titleBar = new Panel(windowWidth, titleBarHeight);
        titleBar.setBorder(new MatteBorder(borderLineSize, borderLineSize, borderLineSize, borderLineSize, borderLineColor.color));
        titleBar.setDisplay(Display.flex);
        titleBar.setAlignVer(AlignVer.right);
        titleBar.setAlignHor(AlignHor.center);

        MethodBody exitPressed = () ->{System.exit(0);};
        JButton exitButton = Helper.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, exitButtonPath, exitPressed);
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

        JButton resizeButton = Helper.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, resizeButtonPath, resizeWindow);
        titleBar.add(resizeButton);

        MethodBody minimizePressed = () ->{this.setState(ColorPicker.ICONIFIED);};
        JButton minimizeButton = Helper.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, hideButtonPath, minimizePressed);
        titleBar.add(minimizeButton);

        return titleBar;
    }

    private Panel createToolbar(){
        toolbar = new Panel(windowWidth , toolbarHeight);
        toolbar.setBorder(new MatteBorder(0, borderLineSize, 0, borderLineSize, borderLineColor.color));
        toolbar.setDisplay(Display.flex);
        toolbar.setAlignVer(AlignVer.left);

        MethodBody empty = () -> {System.out.println("I'm just empty method body");};
        JButton addButton = Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, addPressed());
        toolbar.add(addButton);

        JButton editButton = Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, editButtonPath, empty);
        toolbar.add(editButton);

        JButton deleteButton = Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, empty);
        toolbar.add(deleteButton);

        JButton settingsButton = Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, settingsPressed());
        toolbar.add(settingsButton);

        return toolbar;
    }

    private MethodBody settingsPressed() {
        MethodBody createSettingMenu = () ->{
            JButton first = Helper.createTextButton("Primary Theme Color", Helper.createMethodForColor(firstThemeColor, this));
            JButton second = Helper.createTextButton("Secondary Theme Color", Helper.createMethodForColor(secondThemeColor, this));
            JButton third = Helper.createTextButton("Border Line Color", Helper.createMethodForColor(borderLineColor, this)); // todo make it work

            display.add(first);
            display.add(second);
            display.add(third);
        };

        return () ->{creteDisplay(createSettingMenu);};
    }

    private MethodBody addPressed(){
        MethodBody clearDisplay = () ->{

        };

        return () -> creteDisplay(clearDisplay);
    }

    public void creteDisplay(MethodBody method){
        mainPanel.remove(display);
        display = new Panel(windowWidth, windowHeight - toolbarHeight - titleBarHeight);
        display.setBorder(new MatteBorder(0, borderLineSize, borderLineSize, borderLineSize, borderLineColor.color));
        display.setDisplay(Display.block);
        display.setAlignVer(AlignVer.left);

        method.cast();
        resetThemeColor();
        mainPanel.add(display);
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

    public boolean isMaximized(){
        return isMaximized;
    }

    public void resetThemeColor(){
        Helper.resetColor(this, firstThemeColor);
        Helper.resetColor(display, secondThemeColor);
        Helper.resetColor(toolbar, secondThemeColor);
    }
}
