package main.view.elements.titleBar;

import main.view.View;
import main.view.elements.Panel;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.DisplayMode;
import main.view.elements.titleBar.elements.Exit;
import main.view.elements.titleBar.elements.Maximize;
import main.view.elements.titleBar.elements.Minimize;

import static main.Configuration.*;

public class TitleBar {

    public static Panel getInstance(View view){
        Panel titleBar = new Panel(view.getWindowWidth(), titleBarHeight, titleBarPaddingX, titleBarPaddingY, titleBarPaddingBetweenX, titleBarPaddingBetweenY);
        titleBar.addBorder(true);
        titleBar.setDisplayMode(DisplayMode.flex);
        titleBar.setAlignVer(AlignVer.right);
        titleBar.setAlignHor(AlignHor.center);
        titleBar.setWrap(false);

        titleBar.add(Exit.getInstance(view));
        titleBar.add(Maximize.getInstance(view));
        titleBar.add(Minimize.getInstance(view));

        return titleBar;
    }
}
