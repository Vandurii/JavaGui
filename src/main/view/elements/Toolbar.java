package main.view.elements;

import main.view.View;

import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.DisplayMode;
import main.view.prefabs.Prefabs;
import main.view.scenes.MenuScene;
import main.view.scenes.SettingsScene;

import javax.swing.*;

import static main.Configuration.*;


public class Toolbar {
    private static Panel settingScene;
    private static Panel menuScene;

    public static Panel getInstance(View view) {
        Panel toolbar = new Panel(view.getWindowWidth(), toolbarHeight, toolBarPaddingX, toolBarPaddingY, toolBarPaddingBetweenX, toolBarPaddingBetweenY);
        toolbar.addBorder(false, true, false, true);
        toolbar.setDisplayMode(DisplayMode.flex);
        toolbar.setAlignVer(AlignVer.left);
        toolbar.setAlignHor(AlignHor.top);
        toolbar.setWrap(false);

        JButton settingsButton = Prefabs.createTextButton("Settings", () -> {view.changeScene(new SettingsScene().createScene(view));});
        JButton menuButton = Prefabs.createTextButton("Menu", () -> {view.changeScene(new MenuScene().createScene(view));});

        toolbar.add(menuButton);
        toolbar.add(settingsButton);

        return toolbar;
    }
}
