package main.view.components;

import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Panel extends JPanel {
    private Display display;
    private AlignVer alignVer;
    private AlignHor alignHor;

    private int paddingBetweenX = 20;
    private int paddingBetweenY = 0;
    private int paddingx;
    private int paddingy;

    public Panel(int width, int height){
        super.setLayout(null);
        super.setSize(width, height);

        this.display = Display.none;
        this.alignVer = AlignVer.left;
        this.alignHor = AlignHor.top;
    }

    public void calcComp() {
        for (Component c : super.getComponents()) {
            switch (display) {
                case Display.maxWidth:
                    c.setSize(getWidth(), c.getHeight());
                case Display.flex:
                case Display.block:
                    reCalc();
                    break;
            }
        }
    }

    public void reCalc(){
        List<List<Component>> compList = new ArrayList<>();

        int index = 0;
        if(display == Display.block || display == Display.maxWidth) index = -1;
        int currentWidth = 0;
        int maxWidth = getWidth();
        List<Component> components = List.of(getComponents());
        Iterator<Component> i = components.iterator();
        while(i.hasNext()){
            Component c = i.next();
            int nextWidth = c.getWidth();
            if(i.hasNext()) nextWidth += paddingBetweenX;

            if(currentWidth + nextWidth < maxWidth && display != Display.block){
                currentWidth += nextWidth;
            }else{
                currentWidth = nextWidth;
                index++;
            }

            getOrCreateList(index, compList).add(c);
        }

        int nextY = 0;
        if(alignHor == AlignHor.top){
            nextY = 0;
        }else if(alignHor == AlignHor.bottom){
            nextY = getHeight() - findHighest(compList.getFirst());
        }else if(alignHor == AlignHor.center){
            nextY = (getHeight() /2)- (getCompHeight(compList) / 2);
        }

        Iterator<List<Component>> it = compList.iterator();
        while(it.hasNext()){
            List<Component> list = it.next();
            setCompLocation(list, nextY);
            if(alignHor == AlignHor.top || alignHor == AlignHor.center) {
                nextY += findHighest(list);
                if(it.hasNext()) nextY += paddingBetweenY;
            }else if(alignHor == AlignHor.bottom){
                nextY -= findHighest(list);
                if(it.hasNext())nextY -= paddingBetweenY;
            }
        }
    }

    public int getCompHeight(List<List<Component>> list){
        int totalHeight = 0;
        Iterator<List<Component>> i = list.iterator();
        while(i.hasNext()){
            List<Component> compList = i.next();
            totalHeight += findHighest(compList);
            if(i.hasNext()) totalHeight += paddingBetweenY;
        }

        return totalHeight;
    }

    public int getCompWidth(List<Component> list){

        int totalWidth = 0;
        Iterator<Component> i = list.iterator();
        while(i.hasNext()){
            Component c = i.next();
            totalWidth += c.getWidth();
            if(i.hasNext()) totalWidth += paddingBetweenX;
        }

        return totalWidth;
    }

    public int findHighest(List<Component> list){
        int max = 0;
        for(Component c: list){
            if(c.getHeight() > max) max = c.getHeight();
        }

        return max;
    }

    public void setCompLocation(List<Component> compList, int y){
        int nextCompX = 0;
        if(alignVer == AlignVer.left){
            nextCompX = 0;
        }else if(alignVer == AlignVer.right){
            nextCompX = getWidth() - compList.getFirst().getWidth();
        }else if(alignVer == AlignVer.center){
            int freeSpace = getWidth() - getCompWidth(compList);
            nextCompX = freeSpace / 2;
        }

        for(Component comp: compList){
            comp.setLocation(nextCompX, y);
            if(alignVer == AlignVer.left || alignVer == AlignVer.center) {
                nextCompX += comp.getWidth();
                nextCompX += paddingBetweenX;
            }else if(alignVer == AlignVer.right){
                nextCompX -= comp.getWidth();
                nextCompX -= paddingBetweenX;
            }
        }
    }

    public List<Component> getOrCreateList(int index, List<List<Component>> comList){
        if(comList.size() - 1 < index){
            comList.add(new ArrayList<>());
        }

        return comList.get(index);
    }

    @Override
    public Component add(Component c){
        super.add(c);
        calcComp();
        return c;
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
