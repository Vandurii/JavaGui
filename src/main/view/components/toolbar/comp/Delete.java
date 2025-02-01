package main.view.components.toolbar.comp;

import main.Helper;
import main.view.View;
import main.view.components.colorPicker.ColorWrapper;
import main.view.interfaces.MethodBody;

import javax.swing.*;

import java.awt.*;

import static main.Configuration.*;

public class Delete {

    public static JButton getInstance(View view){
      //  return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, deletePressed(view));
        return Helper.createShrinkTextButton("Delete", deletePressed(view));
    }

    private static MethodBody deletePressed(View view) {
        MethodBody delete = () ->{
            secondaryThemeColor = new ColorWrapper(Color.blue);
        };

        return () ->{view.switchDisplay(delete);};
    }

}
