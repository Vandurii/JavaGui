package window.view.elements;

import window.tools.LayoutManager;
import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static window.Configuration.borderLineColor;
import static window.Configuration.borderLineWidth;

public class Panel extends JPanel {
    private DisplayMode displayMode;
    private AlignVer alignVer;
    private AlignHor alignHor;

    private int paddingBetweenX;
    private int paddingBetweenY;
    private int paddingX;
    private int paddingY;

    private boolean wrap;
    private boolean hasBorder;
    private boolean top, left, bottom, right;
    private String name;


    public Panel(int width, int height){
        super.setSize(width, height);
        init();
    }

    public Panel(int width, int height, int paddingX, int paddingY){
        super.setSize(width, height);
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        init();
    }

    public Panel(int width, int height, int paddingX, int paddingY, int paddingBetweenX, int paddingBetweenY){
        super.setSize(width, height);
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        this.paddingBetweenX = paddingBetweenX;
        this.paddingBetweenY = paddingBetweenY;
        init();
    }

    public void init(){
        super.setLayout(null);
        this.displayMode = DisplayMode.flex;
        this.alignVer = AlignVer.left;
        this.alignHor = AlignHor.top;
        this.wrap = true;
        this.hasBorder = false;
        this.name = "panel";
    }

    public void calcComp(){
        LayoutManager.calcComp(paddingX, paddingBetweenX, alignVer, paddingY, paddingBetweenY, alignHor, displayMode, wrap, this);
    }

    @Override
    public Component add(Component c){
        super.add(c);
        calcComp();
        return c;
    }

    public void addBorder(boolean top, boolean left, boolean bottom, boolean right){
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;

        this.hasBorder = true;
    }

    public void addBorder(boolean val){
        this.top = val;
        this.left = val;
        this.bottom = val;
        this.right = val;

        this.hasBorder = val;
    }

    public void repaintBorder(){
        int v = borderLineWidth.getValue();
        this.setBorder(new MatteBorder(top ? v : 0, left ? v: 0, bottom ? v : 0, right ? v : 0, borderLineColor.getValue()));
    }

    public void setDisplayMode(DisplayMode displayMode){
        this.displayMode = displayMode;
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

    public boolean hasBorder() {
        return hasBorder;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
