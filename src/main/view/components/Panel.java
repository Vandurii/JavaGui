package main.view.components;

import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import javax.swing.*;
import java.awt.*;
import java.util.*;
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
            switch (display) {
                case Display.flex:
                    if (alignVer == AlignVer.left) {
                        c.setLocation(nextCompX + padding, getY(c));
                        nextCompX += c.getWidth() + padding;
                    } else if (alignVer == AlignVer.right) {
                        nextCompX += c.getWidth() + padding;
                        c.setLocation(super.getWidth() - nextCompX, getY(c));
                    } else if(alignVer == AlignVer.center){
                        reCalcFlexCenter(padding, padding);
                    }
                    break;

                case Display.block:
                    c.setLocation(getX(c), nextCompY);
                    nextCompY += c.getHeight();
                    break;

                case Display.maxWidth:
                    int width = c.getParent().getWidth() - nextCompX;
                    c.setSize(width, c.getHeight());
                    c.setLocation(0, nextCompY);
                    nextCompY += c.getHeight();
                    break;
            }
        }
    }

    public int getX(Component c){
        int x= 0;

        if(alignVer == AlignVer.right) {
            x = c.getParent().getWidth() - c.getWidth();
        }else if(alignVer == AlignVer.left){
            x = 0;
        }else if(alignVer == AlignVer.center){
            x = (c.getParent().getWidth() - c.getWidth()) / 2;
        }

        return x;
    }

    public int getY(Component c){
        int y = 0;

        if(alignHor == AlignHor.bottom) {
            y = c.getParent().getHeight() - c.getHeight();
        }else if(alignHor == AlignHor.top){
            y = 0;
        }else if(alignHor == AlignHor.center){
            y = (c.getParent().getHeight() - c.getHeight()) / 2;
        }

        return y;
    }

    public void reCalcFlexCenter(int paddingX, int paddingY){
        int currentWidth = 0;
        int maxWidth = getWidth();
        List<List<Component>> compList = new ArrayList<>();
        Component[] components = getComponents();

        int index = 0;
        for(Component c: components){
            int nextWidth = c.getWidth() + padding;
            if(currentWidth + nextWidth < maxWidth){
                currentWidth += nextWidth;
                getList(index, compList).add(c);
            }else{
                currentWidth = nextWidth;
                index++;
                getList(index, compList).add(c);
            }
        }

        int nextY = 0;
        for(List<Component> list: compList){
            setLocation(list, nextY);
            nextY += findHighest(list);
        }
    }

    public int findHighest(List<Component> list){
        int max = 0;
        for(Component c: list){
            if(c.getHeight() > max) max = c.getHeight();
        }

        return max;
    }

    public int getCompWidth(List<Component> list){
        int totalWidth = 0;
        for(Component c: list){
            totalWidth += c.getWidth();
        }

        return totalWidth;
    }

    public void setLocation(List<Component> compList, int y){
        int margin = (getWidth() - getCompWidth(compList)) / 2;
        int width = margin;
        for(Component c: compList){
            c.setLocation(width, y);
            width += c.getWidth();
        }
    }

    public List<Component> getList(int index, List<List<Component>> comList){
        if(comList.size() - 1 < index){
            comList.add(new ArrayList<>());
        }

        return comList.get(index);
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
