package main.view.components.titleBar.comp;

import main.Helper;
import main.view.interfaces.MethodBody;
import main.view.View;

import javax.swing.*;

import static main.Configuration.*;

public class Exit {

    public static JButton getInstance(View view) {
        return Helper.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, exitButtonPath, exitPressed(view));
    }

    private static MethodBody exitPressed(View view){
        return () -> System.exit(0);
    }
}