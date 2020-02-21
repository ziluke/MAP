package Model.ExecStack;

import java.util.LinkedList;

public class MyStack<T> implements MyIStack<T> {
    private LinkedList<T> stack;

    public MyStack(){
        stack = new LinkedList<>();
    }

    @Override
    public boolean isEmpty(){
        return stack.isEmpty();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public String toString() {
        StringBuilder output= new StringBuilder();
        for(T e : stack) {
            output.append(e.toString()).append("\n");
        }
        return output.toString();
    }

    @Override
    public LinkedList<T> getStack() {
        return stack;
    }
}
