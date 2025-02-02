package main.view.components.colorPicker;

import main.view.View;
import main.view.interfaces.MethodBody;

import java.awt.*;

import static main.Configuration.colorPickerSize;
import static main.Configuration.colorSquaresPath;

public class ThemeColor {
    private static ColorPicker picker;

    public static void resetColorSingle(Component c, ColorWrapper color){
        c.setBackground(color.color);
    }

    public static void resetColor(Component c, ColorWrapper color){
        if(c instanceof Container container){
            for(Component com: container.getComponents()){
                resetColor(com, color);
            }
        }

        c.setBackground(color.color);
    }

    public static void resetParentColor(Component c){
        Component parent = c.getParent();
        if(parent != null){
            parent.repaint();
            resetParentColor(parent);
        }
    }

    public static MethodBody createMethodForColor(ColorWrapper colorWrapper, View view){
        return () -> {
            if(picker != null){
                picker.destroy();
            }

            picker = new ColorPicker(colorPickerSize, colorPickerSize, colorSquaresPath, view, colorWrapper);
        };
    }
}
