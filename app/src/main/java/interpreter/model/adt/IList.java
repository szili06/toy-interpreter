package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.List;

public interface IList<T> {
    void add(T val);
    T get(int pos) throws ADTException;
    int size();
    List<T> toList();
}
