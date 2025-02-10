package window.view.elements;

import window.interfaces.MethodBody;
import window.tools.ColorPicker;
import window.tools.saver.Saver;
import window.view.Window;
import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;
import window.view.prefabs.Prefabs;

import javax.swing.*;

import java.awt.*;

import static window.Configuration.*;

public class TitleBar {

    public Panel createTitleBar(Window window){
        Panel titleBar = new Panel(window.getWindowWidth(), titleBarHeight, titleBarPaddingX, titleBarPaddingY, titleBarPaddingBetweenX, titleBarPaddingBetweenY);
        titleBar.addBorder(true);
        titleBar.setDisplayMode(DisplayMode.flex);
        titleBar.setAlignVer(AlignVer.right);
        titleBar.setAlignHor(AlignHor.center);
        titleBar.setWrap(false);

        // exit Button
        MethodBody exitPressed = () -> {
            Saver.save();
            System.exit(0);
        };

        JButton exitButton = Prefabs.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, exitButtonPath, exitPressed);

        // resize Button
        MethodBody resizePressed = () -> {
            if(window.isWindowResizable()) {
                if (window.isMaximized()) {
                    window.restoreWindowDimension();
                } else {
                    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    int screenMaxWidth = graphicsDevice.getDisplayMode().getWidth();
                    int screenMaxHeight = graphicsDevice.getDisplayMode().getHeight();

                    window.memoWindowDimension();
                    window.setWindowWidth(screenMaxWidth);
                    window.setWindowHeight(screenMaxHeight);
                }

                window.centerWindow();
                window.setCompLocation();
                window.switchMaximized();
            }
        };

        JButton resizeButton = Prefabs.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, resizeButtonPath, resizePressed);

        // minimize Button
        MethodBody minimizePressed = () -> window.setState(JFrame.ICONIFIED);
        JButton minimizeButton = Prefabs.createImageButton(titleBarButWidth, titleBarButHeight, titleBarButScale, hideButtonPath, minimizePressed);

        titleBar.add(exitButton);
        titleBar.add(resizeButton);
        titleBar.add(minimizeButton);

        return titleBar;
    }
}
