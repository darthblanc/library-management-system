package finalproject.datastructures;

public interface DSList<T> {
    public void add(T x);

    // If x is not in the list, do nothing.
    // If x appears more than once on the list, delete first
    public void remove(T x);

    public T get(int i);
    public boolean contains(T x);

    public void clear();

    public int length();
}