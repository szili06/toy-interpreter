package interpreter.model.adt;

import interpreter.exceptions.ADTException;

public interface IList<T> {
    void add(T val);
    T get(int pos) throws ADTException;
    int size();
}
