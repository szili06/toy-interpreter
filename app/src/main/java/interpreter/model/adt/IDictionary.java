package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;

import java.util.Map;

public interface IDictionary<K,V> {
    boolean isDefined(K key);
    V lookup(K key) throws MyException;
    void put(K key, V val) throws MyException;
    void update(K key, V val) throws MyException;
    void remove(K key) throws ADTException;
    Map<K,V> toMap();
}
