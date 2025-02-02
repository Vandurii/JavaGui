package main.tools.saver;

public class SaveFloat<T extends Float> extends Savable<T>{

    public SaveFloat(String idName, T initialVal) {
        super(idName, initialVal);
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
