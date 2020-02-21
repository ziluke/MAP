package Model.Statements;

import Model.Dictionary.MyDictionary;
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

import java.util.Map;

public class IfStmt implements IStmt {
    private Exp exp;
    private IStmt thenS;
    private IStmt elseS;

    public IfStmt(Exp e, IStmt t, IStmt el){
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString(){
        return "(IF("+exp.toString()+") THEN("+thenS.toString()+") ELSE("+elseS.toString()+"))";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = exp.typecheck(typeEnv);
        if(t1.equals(new BoolType())){
            MyIDictionary<String, Type> newTypeEnv = new MyDictionary<>();
            for(Map.Entry<String, Type> e : typeEnv.getContent().entrySet()){
                newTypeEnv.add(e.getKey(), e.getValue());
            }
            thenS.typecheck(newTypeEnv);
            elseS.typecheck(newTypeEnv);
            return typeEnv;
        }
        else throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIStack<IStmt> stk = state.getStk();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value cond = exp.eval(tbl,heap);
        if(!cond.getType().equals(new BoolType())){
            throw new MyException("Condition is not a boolean!\n");
        }
        else{
            BoolValue i = (BoolValue)cond;
            if(i.getVal()){
                stk.push(thenS);
            }
            else {
                stk.push(elseS);
            }
        }
        return null;
    }
}
