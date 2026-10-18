package interpreter.model.adt;

public interface IList<T> {
    public void add(T val);
    public T get(int pos);
    public int size();
}
