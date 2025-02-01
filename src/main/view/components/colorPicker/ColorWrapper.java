package main.view.components.colorPicker;

import java.awt.*;

import static main.Configuration.winAlphaComposite;

public class ColorWrapper {
    public Color color;

    public ColorWrapper(Color color){
        this.color = color;
    }

    public void addAlpha(Color color){
        this.color = new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, winAlphaComposite);
    }

    public void refresh(){
        addAlpha(new Color(color.getRed(), color.getGreen(), color.getBlue()));
    }
}
