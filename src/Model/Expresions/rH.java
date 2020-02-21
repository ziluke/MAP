package Model.Expresions;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class rH implements Exp {

    private Exp e;

    public rH(Exp ex){
        e= ex;
    }

    @Override
    public String toString() {
        return "rH("+e.toString()+")";
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v = e.eval(tbl, heap);
        if(v.getType() instanceof RefType){
            RefValue ref = (RefValue) v;
            if(heap.isDefined(ref.getAddress())){
                return heap.lookup(ref.getAddress());
            }
            else{
                throw new MyException("Address is not a defined in heap!\n");
            }
        }
        else{
            throw new MyException("Expression does not evaluate to RefValue!\n");
        }
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = e.typecheck(typeEnv);
        if(t instanceof RefType){
            RefType ref = (RefType) t;
            return ref.getInner();
        }
        else{
            throw new MyException("The rH argument is not a RefType!\n");
        }
    }
}
