package window.view.prefabs;

import window.tools.ThemeManager;

import javax.swing.*;
import java.awt.*;

import static window.Configuration.primaryThemeColor;

public class CheckBox extends JCheckBox {

    private int size;
    private int borderRadius;
    private int borderLineWidth;
    private int factor;

    public CheckBox(int size){
        this.size = size;
        init();
    }

    public CheckBox(int size, int borderRadius, int borderLineWidth){
        this.factor = 8;
        this.size = (size / factor) * factor;
        this.borderRadius = borderRadius;
        this.borderLineWidth = borderLineWidth;
        init();
    }

    public void init(){
        super.setSize(size, size);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics){
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(isSelected()){
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, size, size, borderRadius, borderRadius);

            int x = size / factor;
            int[] px = {x * 2, x * 4, x * 7, x * 6, x * 4, x * 3};
            int[] py = {x * 4, x * 7, x * 3, x * 2, x * 5, x * 3};
            g2.setColor(primaryThemeColor.getValue());
            g2.fillPolygon(px, py, px.length);
        }else{
            g2.setColor(primaryThemeColor.getValue());
            g2.fillRoundRect(0, 0, size, size, borderRadius, borderRadius);
            g2.setColor(getBackground());
            g2.fillRoundRect(borderLineWidth, borderLineWidth,  (size - borderLineWidth * 2), (size - borderLineWidth * 2), borderRadius, borderRadius);
        }

        g2.dispose();
    }
}
