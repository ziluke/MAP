package Model.Dictionary;

import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<K, V> implements MyIHeap<K, V> {
    private Map<K, V> heap;

    public MyHeap(){
        heap = new HashMap<>();
    }

    @Override
    public synchronized void  add(K key, V v) throws MyException {
        if(!isDefined(key)){
            heap.put(key, v);
        }
        else
            throw new MyException("Variable is already defined!\n");
    }

    @Override
    public synchronized void remove(K key) throws MyException {
        if(!isDefined(key)){
            throw new MyException("Key is not defined!\n");
        }else{
            heap.remove(key);
        }
    }

    @Override
    public synchronized V lookup(K key) throws MyException {
        if(!isDefined(key))
            throw new MyException("Key is not defined!\n");
        else{
            return heap.get(key);
        }
    }

    @Override
    public boolean isDefined(K id) {
        return heap.containsKey(id);
    }

    @Override
    public synchronized Type getType(K id) throws MyException {
        if(!isDefined(id))
            throw new MyException("Key is not defined!\n");
        else{
            Value v = (Value) heap.get(id);
            return v.getType();
        }
    }

    @Override
    public synchronized void update(K id, V val) throws MyException {
        if(!isDefined(id))
            throw new MyException("Key is not defined!\n");
        else{
            heap.put(id, val);
        }
    }

    @Override
    public String toString() {
        StringBuilder output= new StringBuilder();
        for(HashMap.Entry<K, V> e : this.heap.entrySet()) {
            output.append("Key: ").append(e.getKey().toString()).append(", Value: ").append(e.getValue().toString()).append("\n");

        }
        return output.toString();
    }

    @Override
    public synchronized void setContent(Map<K, V> map) {
        heap = map;
    }

    @Override
    public synchronized Map<K, V> getContent() {
        return heap;
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
