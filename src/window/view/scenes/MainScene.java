package window.view.scenes;

import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;
import window.view.Window;
import window.view.elements.Panel;
import window.view.prefabs.Prefabs;

import javax.swing.*;
import java.awt.*;

public class MainScene extends Scene{
    @Override
    public Panel createScene(Window window) {
        Dimension dimension = window.getSceneDimension();
        scene = new Panel(dimension.width, dimension.height);
        scene.addBorder(false, true, true, true);
        scene.setDisplayMode(DisplayMode.blockInline);
        scene.setAlignVer(AlignVer.center);
        scene.setAlignHor(AlignHor.center);

        JLabel welcome = Prefabs.createLabel("Welcome to Pretty Window!", false);
        scene.add(welcome);

        return scene;
    }
}
