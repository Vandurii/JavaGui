package window.tools.saver;

import window.tools.Loader;

import java.util.Map;

public abstract class Savable<T> {
    protected String idName;
    protected T initialVal;
    protected T value;

    public Savable(String idName, T initialVal){
        this.idName = idName;
        this.initialVal =  initialVal;
        Loader.add(this);
    }

    public void set(Map<String, T> resMap) {
        T obj = resMap.get(idName);

        if(obj != null) {
            value = obj;
        }else{
            value = initialVal;
        }
    }

    public abstract T getValue();

    public abstract void setValue(T val);
}
