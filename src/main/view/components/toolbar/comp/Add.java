package main.view.components.toolbar.comp;

import main.view.prefabs.Prefabs;
import main.view.View;
import main.view.interfaces.MethodBody;

import javax.swing.*;

public class Add {

    public static JButton getInstance(View view){
        //return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, addButtonPath, addPressed(view));
        return Prefabs.createShrinkTextButton("Add", addPressed(view));
    }

    private static MethodBody addPressed(View view) {
        MethodBody add = () ->{

        };

        return () ->{view.switchDisplay(add);};
    }
}
