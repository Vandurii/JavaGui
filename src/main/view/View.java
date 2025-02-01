package main.view;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import main.Helper;
import main.controlls.MainWindowML;
import main.view.components.Panel;
import main.view.components.toolbar.Toolbar;
import main.view.enums.AlignVer;
import main.view.enums.Display;
import main.view.components.titleBar.TitleBar;
import main.view.interfaces.MethodBody;

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
    private Panel display;

    public View(){
        windowWidth = INITIAL_WINDOW_WIDTH;
        windowHeight = INITIAL_WINDOW_HEIGHT;
        init();
    }

    public void init() {
        mouseListener = new MainWindowML(this);

        titleBar = TitleBar.getInstance(this);
        toolbar = Toolbar.getInstance(this);
        display = new Panel(windowWidth, windowHeight - toolbarHeight - titleBarHeight);

        mainPanel = new Panel(windowWidth, windowHeight);
        mainPanel.setBorder(new MatteBorder(borderLineWidth, borderLineWidth, borderLineWidth, borderLineWidth, borderLineColor.color));
        mainPanel.setDisplay(Display.block);

        mainPanel.add(titleBar);
        mainPanel.add(toolbar);
        mainPanel.add(display);

        this.setUndecorated(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setSize(windowWidth, windowHeight);

        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);

        this.add(mainPanel);

        resetThemeColor();
    }

    public void switchDisplay(MethodBody method){
        mainPanel.remove(display);
        display = new Panel(windowWidth, windowHeight - toolbarHeight - titleBarHeight);
        display.setBorder(new MatteBorder(0, borderLineWidth, borderLineWidth, borderLineWidth, borderLineColor.color));
        display.setDisplay(Display.block);
        display.setAlignVer(AlignVer.left);

        method.cast();
        resetThemeColor();

        mainPanel.add(display);
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

    public void switchMaximized(){
        this.isMaximized = !isMaximized;
    }

    public void resetThemeColor(){
        Helper.resetColor(this, primaryThemeColor);
        Helper.resetColor(display, secondaryThemeColor);
        Helper.resetColor(toolbar, secondaryThemeColor);
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

    public Panel getDisplay(){
        return  display;
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
