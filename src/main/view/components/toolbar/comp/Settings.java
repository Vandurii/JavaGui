package main.view.components.toolbar.comp;

import main.Helper;
import main.view.interfaces.MethodBody;
import main.view.View;
import javax.swing.*;
import javax.swing.event.ChangeListener;

import static main.Configuration.*;

public class Settings {

    public static JButton getInstance(View view){
       // return Helper.createImageButton(toolbarButWidth, toolbarButHeight, toolbarButScale, settingsButtonPath, settingsPressed(view));
        return Helper.createShrinkTextButton("Settings", settingsPressed(view));
    }

    private static MethodBody settingsPressed(View view) {
        MethodBody createSettingMenu = () ->{
            JButton primaryColor = Helper.createTextButton("Primary Theme Color", Helper.createMethodForColor(primaryThemeColor, view));
            JButton secondaryColor = Helper.createTextButton("Secondary Theme Color", Helper.createMethodForColor(secondaryThemeColor, view));
            JButton borderColor = Helper.createTextButton("Border Line Color", Helper.createMethodForColor(borderLineColor, view)); // todo make it work
            JSlider opacitySlider = createSlider(view);

            view.getDisplay().add(primaryColor);
            view.getDisplay().add(secondaryColor);
            view.getDisplay().add(borderColor);
            view.getDisplay().add(opacitySlider);
        };

        return () ->{view.switchDisplay(createSettingMenu);};
    }

    private static JSlider createSlider(View view) {
        int minOpacityValue = 5;
        int sliderWidth = 100;
        int sliderHeight = 50;
        int startValue = (int)(winAlphaComposite * 100);

        JSlider opacitySlider = new JSlider(0, 100, startValue);
        opacitySlider.setSize(sliderWidth, sliderHeight);

        ChangeListener changeListener = e -> {
            int value = opacitySlider.getValue();
            value = Math.max(value, minOpacityValue);
            winAlphaComposite = value / 100f;
            primaryThemeColor.refresh();
            secondaryThemeColor.refresh();
            view.resetThemeColor();
        };

        opacitySlider.addChangeListener(changeListener);
        return opacitySlider;
    }
}
