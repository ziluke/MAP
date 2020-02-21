package Model.Dictionary;

import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,T> implements MyIDictionary<K, T> {
    private Map<K,T> dict;

    public MyDictionary(){
        dict = new HashMap<>();
    }

    public MyDictionary(MyIDictionary<K, T> d){
        this.dict = new HashMap<>(d.getContent());
    }

    @Override
    public boolean isEmpty(){
        return dict.isEmpty();
    }

    @Override
    public void remove(K key) throws MyException {
        if(!isDefined(key)){
            throw new MyException("Key is not defined!\n");
        }else{
            dict.remove(key);
        }
    }
    @Override
    public void add(K key, T v) throws MyException {
        if(!isDefined(key)){
            dict.put(key, v);
        }
        else
            throw new MyException("Variable is already defined!\n");
    }

    @Override
    public T lookup(K key) throws MyException {
        if(!isDefined(key))
            throw new MyException("Key is not defined!\n");
        else{
            return dict.get(key);
        }
    }

    @Override
    public boolean isDefined(K id) {
        return dict.containsKey(id);
    }

    @Override
    public Type getType(K id) throws MyException {
        if(!isDefined(id))
            throw new MyException("Key is not defined!\n");
        else{
            Value v = (Value)dict.get(id);
            return v.getType();
        }
    }

    @Override
    public void update(K id, T val) throws MyException {
        if(!isDefined(id))
            throw new MyException("Key is not defined!\n");
        else{
            dict.put(id, val);
        }
    }

    @Override
    public String toString() {
        StringBuilder output= new StringBuilder();
        for(HashMap.Entry<K, T> e : this.dict.entrySet()) {
            output.append("Key: ").append(e.getKey().toString()).append(", Value: ").append(e.getValue().toString()).append("\n");

        }
        return output.toString();
    }

    @Override
    public void setContent(Map<K, T> map) {
        dict = map;
    }

    @Override
    public Map<K, T> getContent() {
        return dict;
    }
}
