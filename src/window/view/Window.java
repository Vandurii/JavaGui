package window.view;

import javax.swing.*;
import java.awt.*;

import window.enums.AlignHor;
import window.enums.AlignVer;
import window.tools.Loader;
import window.tools.ThemeManager;
import window.controlls.MainWindowML;
import window.view.elements.Panel;
import window.view.elements.Toolbar;
import window.enums.DisplayMode;
import window.view.elements.TitleBar;
import window.view.prefabs.Prefabs;
import window.view.scenes.EmptySceen;
import window.view.scenes.Scene;

import static window.Configuration.*;

public class Window extends JFrame {

    private int windowWidth;
    private int windowHeight;
    private int winWidthMem;
    private int winHeightMem;
    private boolean isMaximized;
    private boolean isWindowResizable;

    private MainWindowML mouseListener;

    private Panel mainPanel;
    private Panel titleBar;
    private Panel toolbar;

    private Panel scene;
    private Scene mainScene;

    public Window(){
        windowWidth = INITIAL_WINDOW_WIDTH;
        windowHeight = INITIAL_WINDOW_HEIGHT;
        init();
    }

    public Window(int width, int height){
        windowWidth = width;
        windowHeight = height;
        init();
    }

    private void init() {
        this.isWindowResizable = true;

        Loader.load();
        mouseListener = new MainWindowML(this);

        titleBar = new TitleBar().createTitleBar(this);
        toolbar = new Toolbar().crateToolbar(this);

        mainScene = new EmptySceen(this);
        mainScene.setDisplayMode(DisplayMode.blockInline);
        mainScene.setAlignVer(AlignVer.center);
        mainScene.setAlignHor(AlignHor.center);

        JLabel welcome = Prefabs.createLabel("Welcome to Pretty Window!", false);
        mainScene.add(welcome);

        mainPanel = new Panel(windowWidth, windowHeight);
        mainPanel.addBorder(false, true, true, true);
        mainPanel.setOpaque(false);
        mainPanel.setDisplayMode(DisplayMode.block);

        mainPanel.add(titleBar);
        mainPanel.add(toolbar);
        mainPanel.add(mainScene);

        this.setUndecorated(true);
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.setOpacity(winAlphaComposite.getValue() / 100f);
        this.setSize(windowWidth, windowHeight);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(alwaysOnTop.getValue());
        this.setVisible(true);

        this.add(mainPanel);

        scene = mainScene;
        resetThemeColor();
    }

    private void changeScene(Panel newScene){
        mainPanel.remove(scene);
     //   clear();
        scene = newScene;
        mainPanel.add(scene);
        resetThemeColor();
    }

    public void toggleScene(Panel newScene){
        if(mainPanel.getComponent(mainPanel.getComponentCount() -1) instanceof Panel panel){
            if(panel.getName().equals(newScene.getName())){
                changeScene(mainScene);
            }else{
                changeScene(newScene);
            }
        }
    }

    public void switchToMainScene(){
        changeScene(mainScene);
    }

    public void loadScene(Scene scene){
        this.mainScene = scene;
        changeScene(mainScene);
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
   //     ThemeManager.repaintChildTree(this);
        ThemeManager.repaintChildTree(titleBar, primaryThemeColor);
        ThemeManager.repaintChildTree(toolbar, secondaryThemeColor);
        ThemeManager.repaintChildTree(scene, secondaryThemeColor);
        this.setOpacity(winAlphaComposite.getValue() / 100f);
    }

    public void clear(){
        ThemeManager.repaintChildTree(this);
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

    public Dimension getSceneDimension(){
        int height = windowHeight - toolbarHeight - titleBarHeight;
        return new Dimension(windowWidth, height);
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

    public void setResizable(boolean val){
        this.isWindowResizable = val;
    }

    public boolean isWindowResizable(){
        return isWindowResizable;
    }
}
