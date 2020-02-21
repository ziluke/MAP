package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expresions.Exp;
import Model.State.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class wH implements IStmt {
    private String var_name;
    private Exp e;

    public wH(String name, Exp ex){
        var_name = name;
        e = ex;
    }

    @Override
    public String toString() {
        return "wH("+var_name+", "+e.toString()+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = typeEnv.lookup(var_name);
        Type t2 = e.typecheck(typeEnv);
        if(t1 instanceof RefType){
            if(t2.equals(((RefType) t1).getInner())){
                return typeEnv;
            }
            else throw new MyException("LocationType and variable type not the same!\n");
        }
        else throw new MyException("Variable not of type RefType!\n");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if(tbl.isDefined(var_name)){
            Value v = tbl.lookup(var_name) ;
            if(v.getType() instanceof RefType){
                RefValue ref = (RefValue)v;
                if(heap.isDefined(ref.getAddress())){
                    Value val = e.eval(tbl, heap);
                    RefType t = (RefType) ref.getType();
                    if(t.getInner().equals(val.getType())){
                        heap.update(ref.getAddress(), val);
                        return null;
                    }
                    else{
                        throw new MyException("LocationType and variable type not the same!\n");
                    }
                }
                else{
                    throw new MyException("Address not defined in heap!\n");
                }
            }
            else{
                throw new MyException("Variable not of type RefType!\n");
            }
        }
        else{
            throw new MyException("Variable not defined!\n");
        }
    }
}
