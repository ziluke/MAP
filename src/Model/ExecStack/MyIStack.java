package Model.ExecStack;

import java.util.LinkedList;

public interface MyIStack<T> {
    boolean isEmpty();
    T pop();
    void push(T v);
    String toString();
    LinkedList<T> getStack();
}
