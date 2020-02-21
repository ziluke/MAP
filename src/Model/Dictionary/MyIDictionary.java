package Model.Dictionary;

import Model.Exceptions.MyException;
import Model.Types.Type;

import java.util.Map;

public interface MyIDictionary<K, T> {

    void add(K key, T v) throws MyException;

    void remove(K key) throws MyException;

    T lookup(K key) throws MyException;

    boolean isDefined(K id);

    Type getType(K id) throws MyException;

    void update(K id, T val) throws MyException;

    String toString();

    public void setContent(Map<K,T> map);

    public Map<K,T> getContent();

    boolean isEmpty();
}


