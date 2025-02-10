package window.tools;

import window.view.Window;
import window.interfaces.MethodBody;
import window.tools.saver.SaveColor;
import window.view.elements.Panel;

import java.awt.*;

import static window.Configuration.*;

public class ThemeManager {
    private static ColorPicker picker;

    public static void repaintChildTree(Component c, SaveColor<Color> color){
        if(c instanceof Container container){
            for(Component com: container.getComponents()){
                repaintChildTree(com, color);
            }
        }

        if(c instanceof Panel panel){
            if(panel.hasBorder()) panel.repaintBorder();
        }

        c.setBackground(color.getValue());
    }

    public static void repaintChildTree(Component c){
        if(c instanceof Container container){
            for(Component com: container.getComponents()){
                repaintChildTree(com);
            }
        }

        if(c instanceof Panel panel){
            if(panel.hasBorder()) panel.repaintBorder();
        }

        c.setBackground(new Color(1f, 1f, 1f, 0f));
        c.repaint();
    }

    public static void repaintParentTree(Component c){
        Component parent = c.getParent();
        if(parent != null){
            parent.repaint();
            repaintParentTree(parent);
        }
    }

    public static MethodBody changeColor(SaveColor<Color> colorWrapper, Window window){
        return () -> {
            if(picker != null){
                picker.destroy();
            }

            picker = new ColorPicker(colorPickerSize, colorPickerSize, colorWheelsPath, window, colorWrapper);
        };
    }
}
