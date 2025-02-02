package main.tools.saver;

import java.awt.*;

import static main.Configuration.winAlphaComposite;

public class SaveColor<T extends Color> extends Savable<T> {

    public SaveColor(String idName, Color initVal){
        super(idName, (T)initVal);
    }

    public void addAlpha(Color color){
        setValue((T) new Color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, winAlphaComposite.getValue()));
    }

    public void refresh(){
        addAlpha(new Color(value.getRed(), value.getGreen(), value.getBlue()));
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T val) {
        value = val;
    }
}
