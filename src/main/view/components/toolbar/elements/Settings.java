package main.view.components.toolbar.elements;

import main.view.components.Panel;
import main.view.components.colorPicker.ThemeManager;
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

            JLabel opacityLabelPercentage = Prefabs.createLabel(winAlphaComposite.getValue() + "%  ", false);

            // opacitySliderPanel
            MethodBody opacitySliderMethod = ()-> {
                int value = winAlphaComposite.getValue();
                value = Math.max(value, minOpacityValue);
                winAlphaComposite.setValue(value);
                opacityLabelPercentage.setText(value + "%");

                primaryThemeColor.refresh();
                secondaryThemeColor.refresh();
                view.resetThemeColor();
            };

            JSlider opacitySlider = Prefabs.createSlider(sliderWidth, sliderHeight, 100, winAlphaComposite, opacitySliderMethod);
            JLabel opacityLabel = Prefabs.createLabel("Window opacity: ", true);

            Panel sliderPanel = Prefabs.createPanel(0, 0, opacityLabel, opacitySlider, opacityLabelPercentage);

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

            // borderLineWidthSlider
            JLabel borderWidthVal = Prefabs.createLabel(borderLineWidth.getValue() + "px  ", false);
            MethodBody lineSliderMethod = ()-> {
                borderWidthVal.setText(borderLineWidth.getValue() + "px");
                primaryThemeColor.refresh();
                secondaryThemeColor.refresh();
                view.resetThemeColor();
            };

            JLabel lineLabel = Prefabs.createLabel("Border Line Width: ", true);
            JSlider lineSlider = Prefabs.createSlider(sliderWidth, sliderHeight, 10, borderLineWidth, lineSliderMethod);
            Panel linePanel = Prefabs.createPanel(0, 0, lineLabel, lineSlider, borderWidthVal);

            // colors options
            JButton primaryColor = Prefabs.createTextButton("Primary Theme Color", ThemeManager.createMethodForColor(primaryThemeColor, view));
            JButton secondaryColor = Prefabs.createTextButton("Secondary Theme Color", ThemeManager.createMethodForColor(secondaryThemeColor, view));
            JButton borderColor = Prefabs.createTextButton("Border Line Color", ThemeManager.createMethodForColor(borderLineColor, view));

            // add elements
            view.getDisplay().add(alwaysOnTopPanel);
            view.getDisplay().add(primaryColor);
            view.getDisplay().add(secondaryColor);
            view.getDisplay().add(borderColor);
            view.getDisplay().add(linePanel);
            view.getDisplay().add(sliderPanel);
        };

        return () ->{view.switchDisplay(createSettingMenu);};
    }
}
