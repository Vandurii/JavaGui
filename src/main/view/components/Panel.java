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

    private int paddingBetweenX;
    private int paddingBetweenY;
    private int paddingX;
    private int paddingY;

    private boolean wrap;

    public Panel(int width, int height){
        super.setSize(width, height);
        init();
    }

    public Panel(int width, int height, int paddingX, int paddingY){
        super.setSize(width, height);
        init();
        this.paddingX = paddingX;
        this.paddingY = paddingY;
    }

    public Panel(int width, int height, int paddingX, int paddingY, int paddingBetweenX, int paddingBetweenY){
        super.setSize(width, height);
        init();
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        this.paddingBetweenX = paddingBetweenX;
        this.paddingBetweenY = paddingBetweenY;
    }

    public void init(){
        super.setLayout(null);
        this.display = Display.none;
        this.alignVer = AlignVer.left;
        this.alignHor = AlignHor.top;
        this.wrap = true;
    }

    public void calcComp() {
        for (Component c : super.getComponents()) {
            switch (display) {
                case Display.maxWidth:
                    c.setSize(getWidth(), c.getHeight());
                    calc();
                    break;
                case Display.maxHeight:
                    c.setSize(c.getWidth(), getHeight());
                    calc();
                    break;
                case Display.max:
                    c.setSize(getWidth(), getHeight());
                    calc();
                    break;
                case Display.flex:
                case Display.blockInline:
                case Display.block:
                    calc();
                    break;
            }
        }
    }

    public void calc(){
        List<List<Component>> compLineList;

        if(wrap){
            compLineList = createLineList();
        }else{
            List<Component> childList = new ArrayList<>(Arrays.asList(getComponents()));
            List<List<Component>> parentList = new ArrayList<>();
            parentList.add(childList);
            compLineList = parentList;
        }

        int nextY = 0;
        if(alignHor == AlignHor.top){
            nextY = paddingY;
        }else if(alignHor == AlignHor.bottom){
            nextY = getHeight() - findHighest(compLineList.getFirst()) - paddingY;
        }else if(alignHor == AlignHor.center){
            nextY = (getHeight() / 2 ) - (getCompHeight(compLineList) / 2);
        }

        Iterator<List<Component>> it = compLineList.iterator();
        while(it.hasNext()){
            List<Component> list = it.next();

            if(display == Display.blockInline){
                int freeSpace = getWidth() - findWidest(compLineList);
                int nextCompX = freeSpace / 2;
                setCompLocation(list, nextCompX, nextY);
            }else{
                setCompLocation(list, nextY);
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

    public List<List<Component>> createLineList(){
        List<List<Component>> compLineList = new ArrayList<>();

        int index = 0;
        if(display == Display.block || display == Display.blockInline || display == Display.maxWidth || display == Display.max) index = -1;

        int currentWidth = 0;
        if(alignVer == AlignVer.left || alignVer == AlignVer.right) currentWidth = paddingX;

        int maxWidth = getWidth();
        List<Component> components = List.of(getComponents());

        for (Component c : components) {
            int width = c.getWidth();
            if (currentWidth + width < maxWidth && display != Display.block && display != Display.blockInline) {
                currentWidth += width + paddingBetweenX;
            } else {
                currentWidth = width + paddingX + paddingBetweenX;
                index++;
            }
            getOrCreateList(index, compLineList).add(c);
        }

        return compLineList;
    }

    public int getCompHeight(List<List<Component>> list){
        int totalHeight = paddingBetweenY;
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

    public int findWidest(List<List<Component>> list){
        int max = 0;
        for(List<Component> li: list) {
            for (Component c : li) {
                if (c.getWidth() > max) max = c.getWidth();
            }
        }

        return max;
    }

    public void setCompLocation(List<Component> compList, int y){
        int nextCompX = 0;
        if(alignVer == AlignVer.left){
            nextCompX = paddingX;
        }else if(alignVer == AlignVer.right){
            nextCompX = getWidth() - paddingX;
        }else if(alignVer == AlignVer.center){
            int freeSpace = getWidth() - getCompWidth(compList);
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

    public void setCompLocation(List<Component> compList, int x, int y){
        for(Component comp: compList){
            comp.setLocation(x, y);
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

    public int getPaddingY() {
        return paddingY;
    }

    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
    }

    public int getPaddingX() {
        return paddingX;
    }

    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    public int getPaddingBetweenY() {
        return paddingBetweenY;
    }

    public void setPaddingBetweenY(int paddingBetweenY) {
        this.paddingBetweenY = paddingBetweenY;
    }

    public int getPaddingBetweenX() {
        return paddingBetweenX;
    }

    public void setPaddingBetweenX(int paddingBetweenX) {
        this.paddingBetweenX = paddingBetweenX;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }
}
