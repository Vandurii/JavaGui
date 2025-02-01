package main.view.components.toolbar.comp;

import main.Helper;
import main.view.View;
import main.view.components.colorPicker.ColorWrapper;
import main.view.interfaces.MethodBody;

import javax.swing.*;

import java.awt.*;

import static main.Configuration.*;

public class Edit {
    public static JButton getInstance(View view){
        //return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, editButtonPath, editPressed(view));
        return Helper.createShrinkTextButton("Edit", editPressed(view));
    }

    private static MethodBody editPressed(View view) {
        MethodBody edit = () ->{
            secondaryThemeColor = new ColorWrapper(Color.pink);
        };

        return () ->{view.switchDisplay(edit);};
    }
}
