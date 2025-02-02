package main.view.components.toolbar.comp;

import main.view.prefabs.Prefabs;
import main.view.View;
import main.view.interfaces.MethodBody;

import javax.swing.*;

public class Edit {
    public static JButton getInstance(View view){
        //return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, editButtonPath, editPressed(view));
        return Prefabs.createShrinkTextButton("Edit", editPressed(view));
    }

    private static MethodBody editPressed(View view) {
        MethodBody edit = () ->{

        };

        return () ->{view.switchDisplay(edit);};
    }
}
