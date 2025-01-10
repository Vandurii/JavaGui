package main.view;

import javax.swing.*;
import java.awt.*;

import static main.Constant.*;

public class Panel extends JPanel {
    private Display display;
    private Align align;

    private int nextCompX;
    private int nextCompY;

    private int padding;

    public Panel(int width, int height){
        super.setLayout(null);
        super.setSize(width, height);
        super.setBackground(THEME_COLOR);

        this.display = Display.none;
        this.align = Align.left;

        this.padding = initialPadding;
    }

    @Override
    public Component add(Component c){
        super.add(c);
        calcComp();
        return c;
    }

    public void calcComp() {
        nextCompX = 0;
        nextCompY = 0;

        for (Component c : super.getComponents()) {
            int y = (super.getHeight() - c.getHeight()) / 2;

            switch (display) {
                case Display.flex:
                    if (align == Align.left) {
                        c.setLocation(nextCompX + padding, y);
                        nextCompX += c.getWidth() + padding;
                    } else if (align == Align.right) {
                        nextCompX += c.getWidth() + padding;
                        c.setLocation(super.getWidth() - nextCompX, y);
                    }
                    break;

                case Display.block:
                    c.setLocation(0, nextCompY);
                    nextCompY += c.getHeight();
                    break;
            }
        }
    }

    public void setDisplay(Display display){
        this.display = display;
    }

    public void setAlign(Align align){
        this.align = align;
    }
}
