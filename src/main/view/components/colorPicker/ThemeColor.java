package main.view.components.colorPicker;

import main.view.View;
import main.view.interfaces.MethodBody;
import main.tools.saver.SaveColor;

import java.awt.*;

import static main.Configuration.colorPickerSize;
import static main.Configuration.colorSquaresPath;

public class ThemeColor {
    private static ColorPicker picker;

    public static void resetColorSingle(Component c, SaveColor color){
        c.setBackground(color.getValue());
    }

    public static void resetColor(Component c, SaveColor color){
        if(c instanceof Container container){
            for(Component com: container.getComponents()){
                resetColor(com, color);
            }
        }

        c.setBackground(color.getValue());
    }

    public static void resetParentColor(Component c){
        Component parent = c.getParent();
        if(parent != null){
            parent.repaint();
            resetParentColor(parent);
        }
    }

    public static MethodBody createMethodForColor(SaveColor colorWrapper, View view){
        return () -> {
            if(picker != null){
                picker.destroy();
            }

            picker = new ColorPicker(colorPickerSize, colorPickerSize, colorSquaresPath, view, colorWrapper);
        };
    }
}
