package main.view.components.toolbar.comp;

import main.view.components.colorPicker.ThemeColor;
import main.view.prefabs.Prefabs;
import main.view.interfaces.MethodBody;
import main.view.View;
import javax.swing.*;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static main.Configuration.*;

public class Settings {

    public static JButton getInstance(View view){
       // return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, settingsPressed(view));
        return Prefabs.createShrinkTextButton("Settings", settingsPressed(view));
    }

    private static MethodBody settingsPressed(View view) {
        MethodBody createSettingMenu = () ->{
            JButton primaryColor = Prefabs.createTextButton("Primary Theme Color", ThemeColor.createMethodForColor(primaryThemeColor, view));
            JButton secondaryColor = Prefabs.createTextButton("Secondary Theme Color", ThemeColor.createMethodForColor(secondaryThemeColor, view));
            JButton borderColor = Prefabs.createTextButton("Border Line Color", ThemeColor.createMethodForColor(borderLineColor, view)); // todo make it work
            JSlider opacitySlider = Prefabs.createSlider(view);
            JCheckBox onTopBox = Prefabs.createBox(view);

            view.getDisplay().add(primaryColor);
            view.getDisplay().add(secondaryColor);
            view.getDisplay().add(borderColor);
            view.getDisplay().add(opacitySlider);
            view.getDisplay().add(onTopBox);
        };

        return () ->{view.switchDisplay(createSettingMenu);};
    }
}
