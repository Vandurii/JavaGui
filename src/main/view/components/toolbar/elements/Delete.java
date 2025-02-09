package main.view.components.toolbar.elements;

import main.view.prefabs.Prefabs;
import main.view.View;
import main.view.interfaces.MethodBody;

import javax.swing.*;

public class Delete {

    public static JButton getInstance(View view){
      //  return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, deleteButtonPath, deletePressed(view));
        return Prefabs.createTextButton("Delete", deletePressed(view));
    }

    private static MethodBody deletePressed(View view) {
        MethodBody delete = () ->{

        };

        return () ->{view.switchDisplay(delete);};
    }

}
