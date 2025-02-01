package main.view.components;

import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.Configuration.*;

public class Panel extends JPanel {
    private Display display;
    private AlignVer alignVer;
    private AlignHor alignHor;

    private int nextCompX;
    private int nextCompY;

    private int padding;

    public Panel(int width, int height){
        super.setLayout(null);
        super.setSize(width, height);

        this.display = Display.none;
        this.alignVer = AlignVer.left;
        this.alignHor = AlignHor.top;

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
            int y = 0;
            if(alignHor == AlignHor.center) {
                y = (super.getHeight() - c.getHeight()) / 2;
            }else if(alignHor == AlignHor.top){
                y = 0;
            }

            switch (display) {
                case Display.flex:
                    if (alignVer == AlignVer.left) {
                        c.setLocation(nextCompX + padding, y);
                        nextCompX += c.getWidth() + padding;
                    } else if (alignVer == AlignVer.right) {
                        nextCompX += c.getWidth() + padding;
                        c.setLocation(super.getWidth() - nextCompX, y);
                    } else if(alignVer == AlignVer.center){
                       // nextCompX += c.getWidth();
                        reCalcFlex(this, padding, padding);
                    }

                    break;

                case Display.block:
                    c.setLocation(0, nextCompY);
                    nextCompY += c.getHeight();
                    break;

                case Display.max:
                    int width = super.getWidth() - nextCompX;
                    int height = super.getHeight() - nextCompY;
                    c.setSize(width, height);
                    break;
            }
        }
    }

    public void reCalcFlex(Container container, int paddingX, int paddingY){
        List<Component> componentList = new ArrayList<>();
        int startY = centerHor(container, paddingX, paddingY);
        int nextX = paddingX;

        for(Component c: container.getComponents()){
            if(nextX + c.getWidth() + paddingX > this.getWidth()){
                startY  += c.getHeight() + paddingY;
                nextX = c.getWidth() + paddingX;
                centerVer(componentList, paddingX, startY);
                componentList.clear();
            }else{
                componentList.add(c);
                nextX += c.getWidth() + paddingX;
            }
        }
    }

    public int centerHor(Container container, int paddingX, int paddingY){
        // start from top
        int startFromY = 0;

        if(alignHor == AlignHor.bottom){
            startFromY = this.getHeight();
        }else if(alignHor == AlignHor.center){
            int totalWidth = 0;
            for(Component c: container.getComponents()){
                if(totalWidth + c.getWidth() + paddingX > this.getWidth()){
                    totalWidth = paddingX + c.getWidth();
                    startFromY += paddingY + c.getHeight();
                }else{
                    totalWidth += c.getWidth() + paddingX;
                }
            }
        }

        startFromY = (this.getHeight() - startFromY) / 2;

        return startFromY;
    }

    public void centerVer(List<Component> components, int padding, int posY){
        int totalWidth = padding;
        for(Component c: components) totalWidth += c.getWidth() + padding;

        int startX = (this.getWidth() - totalWidth) / 2;

        for(Component c: components){
            c.setLocation(startX, posY);
            startX += c.getWidth() + padding;
        }

    }

    public void setDisplay(Display display){
        this.display = display;
    }

    public void setAlignVer(AlignVer alignVer){
        this.alignVer = alignVer;
    }

    public void setAlignHor(AlignHor alignHor){
        this.alignHor = alignHor;
    }
}
