package main.view.scenes;

import main.view.elements.Panel;
import main.tools.ThemeManager;
import main.view.enums.AlignHor;
import main.view.enums.AlignVer;
import main.view.enums.DisplayMode;
import main.view.prefabs.Prefabs;
import main.view.interfaces.MethodBody;
import main.view.View;
import javax.swing.*;

import static main.Configuration.*;

public class SettingsScene extends Scene {

    public Panel createScene(View view) {
        scene = new Panel(view.getWindowWidth(), view.getWindowHeight() - toolbarHeight - titleBarHeight, displayPaddingX, displayPaddingY, displayPaddingBetweenX, displayPaddingBetweenY);
        scene.setDisplayMode(DisplayMode.blockInline);
        scene.setAlignVer(AlignVer.left);
        scene.setAlignHor(AlignHor.top);
        scene.addBorder(false, true, true, true);

        // opacitySliderPanel
        JLabel opacityLabelPercentage = Prefabs.createLabel(winAlphaComposite.getValue() + "%  ", false);
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
        scene.add(alwaysOnTopPanel);
        scene.add(primaryColor);
        scene.add(secondaryColor);
        scene.add(borderColor);
        scene.add(linePanel);
        scene.add(sliderPanel);

        return scene;
    }
}
