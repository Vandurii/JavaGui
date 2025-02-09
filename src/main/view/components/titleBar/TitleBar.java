package main.view.components.titleBar;

import main.view.View;
import main.view.components.Panel;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;
import main.view.components.titleBar.elements.Exit;
import main.view.components.titleBar.elements.Maximize;
import main.view.components.titleBar.elements.Minimize;

import javax.swing.border.MatteBorder;

import static main.Configuration.*;

public class TitleBar {

    public static Panel getInstance(View view){
        Panel titleBar = new Panel(view.getWindowWidth(), titleBarHeight, titleBarPaddingX, titleBarPaddingY, titleBarPaddingBetweenX, titleBarPaddingBetweenY);
        titleBar.addBorder(true);
        titleBar.setDisplay(Display.flex);
        titleBar.setAlignVer(AlignVer.right);
        titleBar.setAlignHor(AlignHor.center);
        titleBar.setWrap(false);

        titleBar.add(Exit.getInstance(view));
        titleBar.add(Maximize.getInstance(view));
        titleBar.add(Minimize.getInstance(view));

        return titleBar;
    }
}
