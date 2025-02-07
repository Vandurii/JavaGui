package main.view.components.titleBar.comp;

import main.view.prefabs.Prefabs;
import main.view.interfaces.MethodBody;
import main.view.View;

import javax.swing.*;
import java.awt.*;

import static main.Configuration.*;
import static main.Configuration.resizeButtonPath;

public class Maximize {

    public static JButton getInstance(View view){
        return Prefabs.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, resizeButtonPath, resizeWindow(view));
    }

  private static MethodBody resizeWindow(View view){
        return () -> {

            if(view.isMaximized()){
                view.restoreWindowDimension();
            }else {
                GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int screenMaxWidth = graphicsDevice.getDisplayMode().getWidth();
                int screenMaxHeight = graphicsDevice.getDisplayMode().getHeight();

                view.memoWindowDimension();
                view.setWindowWidth(screenMaxWidth);
                view.setWindowHeight(screenMaxHeight);
            }

            view.centerWindow();
            view.setCompLocation();
            view.switchMaximized();
    };}
}
