package main.view.components.titleBar.comp;

import main.tools.saver.Saver;
import main.view.prefabs.Prefabs;
import main.view.interfaces.MethodBody;
import main.view.View;

import javax.swing.*;

import static main.Configuration.*;

public class Exit {

    public static JButton getInstance(View view) {
        return Prefabs.createImageButton(titleBarButWidth,titleBarButHeight, titleBarButScale, exitButtonPath, exitPressed(view));
    }

    private static MethodBody exitPressed(View view){
        return () -> {
            new Saver().save();
            System.exit(0);
        };
    }
}