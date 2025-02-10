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

import java.awt.*;

import static window.Configuration.*;

public class SettingsScene extends Scene {

    public Panel createScene(Window window) {
        Dimension dimension = window.getSceneDimension();
        scene = new Panel(dimension.width, dimension.height, displayPaddingX, displayPaddingY, displayPaddingBetweenX, displayPaddingBetweenY);
        scene.setDisplayMode(DisplayMode.blockInline);
        scene.setAlignVer(AlignVer.left);
        scene.setAlignHor(AlignHor.top);
        scene.addBorder(false, true, true, true);
        scene.setName("settings");

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
        JButton save = Prefabs.createColoredTextButton("Save", () ->{
            Saver.save();
            window.changeScene(new MainScene().createScene(window));}, primaryThemeColor);

        // add elements
        scene.add(alwaysOnTopPanel);
        scene.add(primaryColor);
        scene.add(secondaryColor);
        scene.add(borderColor);
        scene.add(linePanel);
        scene.add(sliderPanel);
        scene.add(new Panel(0, 20));
        scene.add(save);

        return scene;
    }
}
