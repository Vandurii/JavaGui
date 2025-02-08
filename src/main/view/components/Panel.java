package main.view.components;

import main.tools.LayoutManager;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import javax.swing.*;
import java.awt.*;

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
        this.display = Display.flex;
        this.alignVer = AlignVer.left;
        this.alignHor = AlignHor.top;
        this.wrap = true;
    }

    public void calcComp(){
        LayoutManager.calcComp(paddingX, paddingBetweenX, alignVer, paddingY, paddingBetweenY, alignHor, display, wrap, this);
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

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }
}
