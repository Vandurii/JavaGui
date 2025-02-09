package main.tools;

import main.view.View;
import main.view.interfaces.MethodBody;
import main.tools.saver.SaveColor;
import main.view.elements.Panel;

import java.awt.*;

import static main.Configuration.*;

public class ThemeManager {
    private static ColorPicker picker;

    public static void resetColorSingle(Component c, SaveColor<Color> color){
        c.setBackground(color.getValue());
    }

    public static void resetColor(Component c, SaveColor<Color> color){
        if(c instanceof Container container){
            for(Component com: container.getComponents()){
                resetColor(com, color);
            }
        }

        if(c instanceof Panel panel){
            if(panel.hasBorder()) panel.repaintBorder();
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

    public static MethodBody createMethodForColor(SaveColor<Color> colorWrapper, View view){
        return () -> {
            if(picker != null){
                picker.destroy();
            }

            picker = new ColorPicker(colorPickerSize, colorPickerSize, colorWheelsPath, view, colorWrapper);
        };
    }
}
