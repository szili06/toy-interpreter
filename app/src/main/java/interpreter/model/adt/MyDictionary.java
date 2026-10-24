package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;

import java.util.HashMap;

public class MyDictionary<K, V> implements IDictionary<K,V> {
    private final HashMap<K, V> map;
    public MyDictionary() {
        map = new HashMap<>();
    }
    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public V lookup(K key) throws MyException {
        if(!map.containsKey(key)) throw new ADTException("key is not in dictionary");
        return map.get(key);
    }

    @Override
    public void put(K key, V val) throws MyException {
        if(map.containsKey(key)) throw new ADTException("dictionary already contains key");
        map.put(key, val);
    }

    @Override
    public void update(K key, V val) throws MyException {
        if(!map.containsKey(key)) throw new ADTException("dictionary doesn't contain key");
        map.replace(key, val);
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
