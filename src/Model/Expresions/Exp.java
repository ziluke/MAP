package Model.Expresions;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Types.Type;
import Model.Values.Value;

public interface Exp {

    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException;
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
    String toString();
}
