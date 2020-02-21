package Model.Dictionary;

import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;
import java.util.HashMap;
import java.util.Map;

public class MyFileTable<K,T> implements MyIDictionary<K,T> {
    private Map<K,T> fileTable;
    public MyFileTable(){
        fileTable = new HashMap<>();
    }

    @Override
    public void add(K key, T v) throws MyException {
        if(isDefined(key)){
            throw new MyException("Variable is already defined!\n");
        }
        else{
            fileTable.put(key, v);
        }
    }

    @Override
    public void remove(K key) throws MyException {
        if(!isDefined(key)){
            throw new MyException("Key is not defined!\n");
        }else{
            fileTable.remove(key);
        }
    }

    @Override
    public T lookup(K key) throws MyException {
        if(!isDefined(key)){
            throw new MyException("Key is not defined!\n");
        }else
            return fileTable.get(key);
    }

    @Override
    public boolean isEmpty(){
        return fileTable.isEmpty();
    }

    @Override
    public boolean isDefined(K id) {
        return fileTable.containsKey(id);
    }

    @Override
    public Type getType(K id) throws MyException {
        if(!isDefined(id))
            throw new MyException("Key is not defined!\n");
        else{
            Value v = (Value)fileTable.get(id);
            return v.getType();
        }
    }

    @Override
    public void update(K id, T val) throws MyException {
        if(!isDefined(id))
            throw new MyException("Key is not defined!\n");
        else{
            fileTable.put(id, val);
        }
    }

    @Override
    public String toString() {
        StringBuilder output= new StringBuilder();
        for(HashMap.Entry<K, T> e : this.fileTable.entrySet()) {
            output.append("Key: ").append(e.getKey().toString()).append(", Value: ").append(e.getValue().toString()).append("\n");

        }
        return output.toString();
    }

    @Override
    public void setContent(Map<K, T> map) {
        fileTable = map;
    }

    @Override
    public Map<K, T> getContent() {
        return fileTable;
    }
}
