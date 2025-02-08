package main.view.components.toolbar.comp;

import main.view.components.Panel;
import main.view.components.colorPicker.ThemeColor;
import main.view.prefabs.Prefabs;
import main.view.interfaces.MethodBody;
import main.view.View;
import javax.swing.*;

import static main.Configuration.*;

public class Settings {

    public static JButton getInstance(View view){
       // return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, settingsPressed(view));
        return Prefabs.createTextButton("Settings", settingsPressed(view));
    }

    private static MethodBody settingsPressed(View view) {
        MethodBody createSettingMenu = () ->{

            // slider Panel
            MethodBody opacitySliderMethod = ()-> {
                float value = winAlphaComposite.getValue();
                value = Math.max(value, minOpacityValue / 100f);
                winAlphaComposite.setValue(value);

                primaryThemeColor.refresh();
                secondaryThemeColor.refresh();
                view.resetThemeColor();
            };

            JSlider opacitySlider = Prefabs.createSlider(sliderWidth, sliderHeight, winAlphaComposite, opacitySliderMethod);
            JButton opacityButton = Prefabs.createTextButton("Window opacity: ", ()->{});

            Panel sliderPanel = Prefabs.createPanel(0, 0, opacityButton, opacitySlider);

            // OnTopBox
            JCheckBox onTopBox = Prefabs.createDisabledBox(checkBoxSize);
            onTopBox.setSelected(alwaysOnTop.getValue());

            MethodBody alwaysOnTopMethod = () -> {
                alwaysOnTop.setValue(!alwaysOnTop.getValue());
                view.setAlwaysOnTop(alwaysOnTop.getValue());
                onTopBox.setSelected(alwaysOnTop.getValue());
            };
            JButton topButton = Prefabs.createTextButton("Display always on top: ", alwaysOnTopMethod);

            Panel alwaysOnTopPanel = Prefabs.createPanel(0, 0, topButton, onTopBox);


            JButton primaryColor = Prefabs.createTextButton("Primary Theme Color", ThemeColor.createMethodForColor(primaryThemeColor, view));
            JButton secondaryColor = Prefabs.createTextButton("Secondary Theme Color", ThemeColor.createMethodForColor(secondaryThemeColor, view));
            JButton borderColor = Prefabs.createTextButton("Border Line Color", ThemeColor.createMethodForColor(borderLineColor, view)); // todo make it work

            view.getDisplay().add(alwaysOnTopPanel);
            view.getDisplay().add(primaryColor);
            view.getDisplay().add(secondaryColor);
            view.getDisplay().add(borderColor);
            view.getDisplay().add(sliderPanel);
        };

        return () ->{view.switchDisplay(createSettingMenu);};
    }
}
