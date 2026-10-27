package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;

public class MyList<T> implements IList<T> {
    private final ArrayList<T> list;
    public MyList() {
        list = new ArrayList<>();
    }
    @Override
    public void add(T val) {
        list.add(val);
    }

    @Override
    public T get(int pos) throws ADTException {
        if(pos >= list.size() || pos < 0) throw new ADTException("position out of range");
        return list.get(pos);
    }

    @Override
    public int size() {
        return list.size();
    }

    public String toString() {
        return list.toString();
    }
}
