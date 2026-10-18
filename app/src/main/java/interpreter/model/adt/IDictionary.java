package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;

public interface IDictionary<K,V> {
    public boolean isDefined(K key);
    public V lookup(K key);
    public void put(K key, V val) throws MyException;
    public void update(K key, V val) throws MyException;
}
