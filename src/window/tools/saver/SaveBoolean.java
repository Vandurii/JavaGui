package window.tools.saver;

public class SaveBoolean<T extends Boolean> extends Savable<T> {
    public SaveBoolean(String idName, T initialVal) {
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
