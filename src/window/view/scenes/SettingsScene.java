package window.view.scenes;

import window.tools.saver.Saver;
import window.view.elements.Panel;
import window.tools.ThemeManager;
import window.enums.AlignHor;
import window.enums.AlignVer;
import window.enums.DisplayMode;
import window.view.prefabs.CheckBox;
import window.view.prefabs.Prefabs;
import window.interfaces.MethodBody;
import window.view.Window;
import javax.swing.*;

import static window.Configuration.*;

public class SettingsScene extends Scene {

    public SettingsScene(Window window) {
        super(window);
        customize(window);
    }

    public SettingsScene(int paddingX, int paddingY, Window window) {
        super(paddingX, paddingY, window);
        customize(window);
    }

    public SettingsScene(int paddingX, int paddingY, int paddingBetweenX, int paddingBetweenY, Window window) {
        super(paddingX, paddingY, paddingBetweenX, paddingBetweenY, window);
        customize(window);
    }

    public void customize(Window window) {
        this.setDisplayMode(DisplayMode.blockInline);
        this.setAlignVer(AlignVer.left);
        this.setAlignHor(AlignHor.top);
        this.setName("settings");

        // opacitySliderPanel
        JLabel opacityLabelPercentage = Prefabs.createLabel(winAlphaComposite.getValue() + "%  ", false);
        MethodBody opacitySliderMethod = ()-> {
            int value = winAlphaComposite.getValue();
            value = Math.max(value, minOpacityValue);
            winAlphaComposite.setValue(value);
            opacityLabelPercentage.setText(value + "%");

            primaryThemeColor.refresh();
            secondaryThemeColor.refresh();
            window.resetThemeColor();
        };

        JSlider opacitySlider = Prefabs.createSlider(sliderWidth, sliderHeight, 100, winAlphaComposite, opacitySliderMethod);
        JLabel opacityLabel = Prefabs.createLabel("Window opacity: ", true);

        Panel sliderPanel = Prefabs.createPanel(0, 0, opacityLabel, opacitySlider, opacityLabelPercentage);

        // OnTopBox
        CheckBox onTopBox = Prefabs.createDisabledBox(checkBoxSize, checkBoxBorderRadius, checkBoxBorderWidth);
        onTopBox.setSelected(alwaysOnTop.getValue());

        MethodBody alwaysOnTopMethod = () -> {
            alwaysOnTop.setValue(!alwaysOnTop.getValue());
            window.setAlwaysOnTop(alwaysOnTop.getValue());
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
            window.resetThemeColor();
        };

        JLabel lineLabel = Prefabs.createLabel("Border Line Width: ", true);
        JSlider lineSlider = Prefabs.createSlider(sliderWidth, sliderHeight, 10, borderLineWidth, lineSliderMethod);
        Panel linePanel = Prefabs.createPanel(0, 0, lineLabel, lineSlider, borderWidthVal);

        // colors options
        JButton primaryColor = Prefabs.createTextButton("Primary Theme Color", ThemeManager.changeColor(primaryThemeColor, window));
        JButton secondaryColor = Prefabs.createTextButton("Secondary Theme Color", ThemeManager.changeColor(secondaryThemeColor, window));
        JButton borderColor = Prefabs.createTextButton("Border Line Color", ThemeManager.changeColor(borderLineColor, window));

        // save option
        JButton save = Prefabs.createColoredTextButton("Save", () ->{Saver.save();window.switchToMainScene();}, primaryThemeColor);

        // add elements
        this.add(alwaysOnTopPanel);
        this.add(primaryColor);
        this.add(secondaryColor);
        this.add(borderColor);
        this.add(linePanel);
        this.add(sliderPanel);
        this.add(new Panel(0, 20));
        this.add(save);
    }
}
