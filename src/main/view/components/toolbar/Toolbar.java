package main.view.components.toolbar;

import main.view.View;
import main.view.components.Panel;
import main.view.components.toolbar.comp.Add;
import main.view.components.toolbar.comp.Delete;
import main.view.components.toolbar.comp.Edit;
import main.view.components.toolbar.comp.Settings;
import main.view.enums.AlignVer;
import main.view.enums.Display;

import javax.swing.border.MatteBorder;

import static main.Configuration.*;

public class Toolbar {

    public static Panel getInstance(View view) {
        Panel toolbar = new Panel(view.getWindowWidth(), toolbarHeight);
        toolbar.setBorder(new MatteBorder(0,borderLineWidth, 0,borderLineWidth, borderLineColor.getValue()));
        toolbar.setDisplay(Display.flex);
        toolbar.setAlignVer(AlignVer.left);

        toolbar.add(Add.getInstance(view));
        toolbar.add(Edit.getInstance(view));
        toolbar.add(Delete.getInstance(view));
        toolbar.add(Settings.getInstance(view));

        return toolbar;
    }
}
