package Model.OutList;

import java.util.List;

public interface MyIList<T> {
    void add(T v);
    T pop();
    String toString();
    List<T> getContent();
}
