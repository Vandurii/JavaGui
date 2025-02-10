package window.view.elements;

import window.view.Window;

import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;
import window.view.prefabs.Prefabs;
import window.view.scenes.MainScene;
import window.view.scenes.SettingsScene;

import javax.swing.*;

import static window.Configuration.*;


public class Toolbar {
    private static Panel toolbar;

    public Panel crateToolbar(Window window) {
        toolbar = new Panel(window.getWindowWidth(), toolbarHeight, toolBarPaddingX, toolBarPaddingY, toolBarPaddingBetweenX, toolBarPaddingBetweenY);
        toolbar.addBorder(false, true, false, true);
        toolbar.setDisplayMode(DisplayMode.flex);
        toolbar.setAlignVer(AlignVer.right);
        toolbar.setAlignHor(AlignHor.top);
        toolbar.setWrap(false);

        JButton settingsButton = Prefabs.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, () -> {
            Panel newScene = new SettingsScene().createScene(window);
            Panel oldScene = new MainScene().createScene(window);
            window.toggleScene(newScene, oldScene);
        });
        toolbar.add(settingsButton);

        return toolbar;
    }

    public static void add(JButton button){
        toolbar.add(button);
    }
}
