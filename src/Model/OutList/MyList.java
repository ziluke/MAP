package Model.OutList;

import java.util.LinkedList;
import java.util.List;

public class MyList<T> implements MyIList<T> {
    private LinkedList<T> out;

    public MyList(){
        out = new LinkedList<>();
    }

    @Override
    public void add(T v) {
        out.add(v);
    }

    @Override
    public T pop() {
        return out.pop();
    }

    @Override
    public String toString() {
        StringBuilder output= new StringBuilder();
        for(T e : out) {
            output.append(e.toString()).append("\n");
        }
        return output.toString();
    }

    @Override
    public List<T> getContent() {
        return out;
    }
}
