package window.tools;

import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LayoutManager {

    public static void calcComp(int paddingX, int paddingBetweenX, AlignVer alignVer, int paddingY, int paddingBetweenY, AlignHor alignHor, DisplayMode displayMode, boolean wrap, Container parent) {
        for (Component c : parent.getComponents()) {
            switch (displayMode) {
                case DisplayMode.maxWidth:
                    c.setSize(parent.getWidth(), c.getHeight());
                    calc(paddingX, paddingBetweenX, alignVer, paddingY, paddingBetweenY, alignHor, displayMode, wrap, parent);
                    break;
                case DisplayMode.maxHeight:
                    c.setSize(c.getWidth(), parent.getHeight());
                    calc(paddingX, paddingBetweenX, alignVer, paddingY, paddingBetweenY, alignHor, displayMode, wrap, parent);
                    break;
                case DisplayMode.max:
                    c.setSize(parent.getWidth(), parent.getHeight());
                    calc(paddingX, paddingBetweenX, alignVer, paddingY, paddingBetweenY, alignHor, displayMode, wrap, parent);
                    break;
                case DisplayMode.flex:
                case DisplayMode.blockInline:
                case DisplayMode.block:
                    calc(paddingX, paddingBetweenX, alignVer, paddingY, paddingBetweenY, alignHor, displayMode, wrap, parent);
                    break;
            }
        }
    }

    public static void calc(int paddingX, int paddingBetweenX, AlignVer alignVer, int paddingY, int paddingBetweenY, AlignHor alignHor, DisplayMode displayMode, boolean wrap, Container parent){
        List<List<Component>> compLineList;

        if(wrap){
            compLineList = createLineList(paddingX, paddingBetweenX, alignVer, displayMode, parent);
        }else{
            List<Component> childList = new ArrayList<>(Arrays.asList(parent.getComponents()));
            List<List<Component>> parentList = new ArrayList<>();
            parentList.add(childList);
            compLineList = parentList;
        }

        int nextY = 0;
        if(alignHor == AlignHor.top){
            nextY = paddingY;
        }else if(alignHor == AlignHor.bottom){
            nextY = parent.getHeight() - findHighest(compLineList.getFirst()) - paddingY;
        }else if(alignHor == AlignHor.center){
            nextY = (parent.getHeight() / 2 ) - (getCompHeight(compLineList, paddingBetweenY) / 2);
        }

        Iterator<List<Component>> it = compLineList.iterator();
        while(it.hasNext()){
            List<Component> list = it.next();

            if(displayMode == DisplayMode.blockInline){
                int freeSpace = parent.getWidth() - findWidest(compLineList);
                int nextCompX = freeSpace / 2;
                setCompLocation(list, nextCompX, nextY);
            }else{
                setCompLocation(paddingX, paddingBetweenX, alignVer, nextY, parent, list);
            }

            if(alignHor == AlignHor.top || alignHor == AlignHor.center) {
                nextY += findHighest(list);
                if(it.hasNext()) nextY += paddingBetweenY;
            }else if(alignHor == AlignHor.bottom){
                nextY -= findHighest(list);
                if(it.hasNext())nextY -= paddingBetweenY;
            }
        }
    }

    public static List<List<Component>> createLineList(int paddingX, int paddingBetweenX, AlignVer alignVer, DisplayMode displayMode, Container parent){
        List<List<Component>> compLineList = new ArrayList<>();

        int index = 0;
        if(displayMode == DisplayMode.block || displayMode == DisplayMode.blockInline ) index = -1;

        int currentWidth = 0;
        if(alignVer == AlignVer.left || alignVer == AlignVer.right) currentWidth = paddingX;

        int maxWidth = parent.getWidth();
        List<Component> components = List.of(parent.getComponents());

        for (Component c : components) {
            int width = c.getWidth();
            if (currentWidth + width <= maxWidth && displayMode != DisplayMode.block && displayMode != DisplayMode.blockInline) {
                currentWidth += width + paddingBetweenX;
            } else {
                currentWidth = width + paddingX + paddingBetweenX;
                index++;
            }
            getOrCreateList(index, compLineList).add(c);
        }

        return compLineList;
    }

    public static int getCompWidth(List<Component> list, int paddingBetweenX){

        int totalWidth = 0;
        Iterator<Component> i = list.iterator();
        while(i.hasNext()){
            Component c = i.next();
            totalWidth += c.getWidth();
            if(i.hasNext()) totalWidth += paddingBetweenX;
        }

        return totalWidth;
    }

    public static int getCompHeight(List<List<Component>> list, int paddingBetweenY){
        int totalHeight = paddingBetweenY;
        Iterator<List<Component>> i = list.iterator();
        while(i.hasNext()){
            List<Component> compList = i.next();
            totalHeight += findHighest(compList);
            if(i.hasNext()) totalHeight += paddingBetweenY;
        }

        return totalHeight;
    }

    public static List<Component> getOrCreateList(int index, List<List<Component>> comList){
        if(comList.size() - 1 < index){
            comList.add(new ArrayList<>());
        }

        return comList.get(index);
    }

    public static int findHighest(List<Component> list){
        int max = 0;
        for(Component c: list){
            if(c.getHeight() > max) max = c.getHeight();
        }

        return max;
    }

    public static int findWidest(List<List<Component>> list){
        int max = 0;
        for(List<Component> li: list) {
            for (Component c : li) {
                if (c.getWidth() > max) max = c.getWidth();
            }
        }

        return max;
    }

    public static void setCompLocation(int paddingX, int paddingBetweenX, AlignVer alignVer, int y, Container parent, List<Component> compList){
        int nextCompX = 0;
        if(alignVer == AlignVer.left){
            nextCompX = paddingX;
        }else if(alignVer == AlignVer.right){
            nextCompX = parent.getWidth() - paddingX;
        }else if(alignVer == AlignVer.center){
            int freeSpace = parent.getWidth() - getCompWidth(compList, paddingBetweenX);
            nextCompX = freeSpace / 2;
        }

        for(Component comp: compList){
            if(alignVer == AlignVer.left || alignVer == AlignVer.center) {
                comp.setLocation(nextCompX, y);
                nextCompX += comp.getWidth();
                nextCompX += paddingBetweenX;
            }else if(alignVer == AlignVer.right){
                nextCompX -= comp.getWidth();
                comp.setLocation(nextCompX, y);
                nextCompX -= paddingBetweenX;
            }
        }
    }

    public static void setCompLocation(List<Component> compList, int x, int y){
        for(Component comp: compList){
            comp.setLocation(x, y);
        }
    }

    public static int getMinPanelWidth(int paddingX, int paddingBetweenX, List<Component> components){
        return getCompWidth(components, paddingBetweenX) + paddingX;
    }
}
