package main.view.components.toolbar;

import main.view.View;
import main.view.components.Panel;
import main.view.components.toolbar.elements.Add;
import main.view.components.toolbar.elements.Delete;
import main.view.components.toolbar.elements.Edit;
import main.view.components.toolbar.elements.Settings;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import javax.swing.border.MatteBorder;

import static main.Configuration.*;

public class Toolbar {

    public static Panel getInstance(View view) {
        Panel toolbar = new Panel(view.getWindowWidth(), toolbarHeight, toolBarPaddingX, toolBarPaddingY, toolBarPaddingBetweenX, toolBarPaddingBetweenY);
        toolbar.addBorder(false, true, false, true);
        toolbar.setDisplay(Display.flex);
        toolbar.setAlignVer(AlignVer.left);
        toolbar.setAlignHor(AlignHor.top);
        toolbar.setWrap(false);

        toolbar.add(Add.getInstance(view));
        toolbar.add(Edit.getInstance(view));
        toolbar.add(Delete.getInstance(view));
        toolbar.add(Settings.getInstance(view));

        return toolbar;
    }
}
