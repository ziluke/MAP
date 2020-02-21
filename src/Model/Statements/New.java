package Model.Statements;

import Model.Dictionary.Empty;
import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expresions.Exp;
import Model.State.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

import java.util.concurrent.atomic.AtomicInteger;

public class New implements IStmt {
    private String var_name;
    private Exp exp;
    private static AtomicInteger emptySpace;

    public New(String v, Exp e){
        var_name = v;
        exp = e;
        emptySpace = new AtomicInteger();
    }

    @Override
    public String toString() {
        return "new("+var_name+", "+ exp.toString()+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(tbl.isDefined(var_name)) {
            Value v = tbl.lookup(var_name);
            if (v.getType() instanceof RefType) {
                RefValue r = (RefValue) v;
                Value v1 = exp.eval(tbl, heap);
                RefType refType = (RefType) r.getType();
                if(refType.getInner().equals(v1.getType())){
                    int addr = emptySpace.addAndGet(1);
                    heap.add(addr, v1);
                    tbl.update(var_name, new RefValue(addr, refType.getInner()));
                    return null;
                } else {
                    throw new MyException("Types do not match!\n");
                }
            } else {
                throw new MyException("Variable not of Ref type!\n");
            }
        }else{
            throw new MyException("Variable name not declared!\n");
        }
    }
}
