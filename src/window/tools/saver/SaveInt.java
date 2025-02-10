package window.tools.saver;

public class SaveInt<T extends Integer> extends Savable<T> {
    public SaveInt(String idName, T initialVal) {
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


