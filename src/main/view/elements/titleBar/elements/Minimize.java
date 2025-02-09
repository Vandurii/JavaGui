package main.view.elements.titleBar.elements;

import main.view.prefabs.Prefabs;
import main.tools.ColorPicker;
import main.view.interfaces.MethodBody;
import main.view.View;

import javax.swing.*;

import static main.Configuration.*;

public class Minimize {

    public static JButton getInstance(View view) {
     return Prefabs.createImageButton(titleBarButWidth, titleBarButHeight, titleBarButScale, hideButtonPath, minimizePressed(view));
    }

    private static MethodBody minimizePressed(View view){
        return () -> view.setState(ColorPicker.ICONIFIED);};
}
