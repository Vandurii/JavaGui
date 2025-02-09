package main.view;

import javax.swing.*;
import java.awt.*;

import main.tools.ThemeManager;
import main.controlls.MainWindowML;
import main.view.elements.Panel;
import main.view.elements.Toolbar;
import main.view.enums.DisplayMode;
import main.view.elements.titleBar.TitleBar;

import static main.Configuration.*;

public class View extends JFrame {

    private int windowWidth;
    private int windowHeight;
    private int winWidthMem;
    private int winHeightMem;
    private boolean isMaximized;

    private MainWindowML mouseListener;

    private Panel mainPanel;
    private Panel titleBar;
    private Panel toolbar;
    private Panel scene;

    public View(){
        windowWidth = INITIAL_WINDOW_WIDTH;
        windowHeight = INITIAL_WINDOW_HEIGHT;
        init();
    }

    public void init() {
        mouseListener = new MainWindowML(this);

        titleBar = TitleBar.getInstance(this);
        toolbar = Toolbar.getInstance(this);
        scene = new Panel(windowWidth, windowHeight - toolbarHeight - titleBarHeight);
        scene.addBorder(false, true, true, true);

        mainPanel = new Panel(windowWidth, windowHeight);
        mainPanel.addBorder(false, true, true, true);
        mainPanel.setDisplayMode(DisplayMode.block);

        mainPanel.add(titleBar);
        mainPanel.add(toolbar);
        mainPanel.add(scene);

        this.setUndecorated(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setBackground(new Color(1f, 1f, 1f, 0f));
        this.setSize(windowWidth, windowHeight);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(alwaysOnTop.getValue());
        this.setVisible(true);

        this.add(mainPanel);

        resetThemeColor();
    }

    public void changeScene(Panel newScene){
        mainPanel.remove(scene);
        scene = newScene;
        mainPanel.add(scene);
        resetThemeColor();
    }

    public void setCompLocation(){
        setSize(windowWidth, windowHeight);
        titleBar.setSize(windowWidth, titleBarHeight);
        titleBar.calcComp();
        toolbar.setSize(windowWidth, toolbarHeight);
        toolbar.calcComp();
        mainPanel.calcComp();
        scene.setSize(windowWidth, windowHeight - toolbarHeight - titleBarHeight);
        scene.calcComp();
    }

    public boolean isMaximized(){
        return isMaximized;
    }

    public void switchMaximized(){
        this.isMaximized = !isMaximized;
    }

    public void resetThemeColor(){
        ThemeManager.resetColor(mainPanel, secondaryThemeColor);
        ThemeManager.resetColor(titleBar, primaryThemeColor);
        ThemeManager.resetColor(toolbar, secondaryThemeColor);
        ThemeManager.resetColor(scene, secondaryThemeColor);
        this.setOpacity(winAlphaComposite.getValue() / 100f);
        mainPanel.repaint();
    }

    public void memoWindowDimension(){
        this.winWidthMem = windowWidth;
        this.winHeightMem = windowHeight;
    }

    public void restoreWindowDimension(){
        windowWidth = winWidthMem;
        windowHeight = winHeightMem;
    }

    public void centerWindow(){
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenMaxWidth = graphicsDevice.getDisplayMode().getWidth();
        int screenMaxHeight = graphicsDevice.getDisplayMode().getHeight();

        int screenX = (screenMaxWidth - getWindowWidth()) / 2;
        int screenY = (screenMaxHeight - getWindowHeight()) / 2;
        setLocation(screenX, screenY);
    }

    public Panel getScene(){
        return scene;
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
}
