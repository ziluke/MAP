package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.ExecStack.MyIStack;
import Model.Expresions.Exp;
import Model.State.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class While implements IStmt {
    private Exp exp;
    private IStmt st;

    public While(Exp e, IStmt statem){
        exp = e;
        st = statem;
    }

    @Override
    public String toString() {
        return "While("+exp.toString()+") "+st.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = exp.typecheck(typeEnv);
        if(t.equals(new BoolType())){
            st.typecheck(typeEnv);
            return typeEnv;
        }
        else throw new MyException("Condition not bool!\n");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIStack<IStmt> stk = state.getStk();
        Value v = exp.eval(tbl, heap);
        if(v.getType().equals(new BoolType())){
            BoolValue i = (BoolValue) v;
            if(i.getVal()){
                stk.push(this);
                stk.push(st);
                return null;
            }
            else {
                return state;
            }
        }
        else{
            throw new MyException("Expression not Bool Type!\n");
        }
    }
}
