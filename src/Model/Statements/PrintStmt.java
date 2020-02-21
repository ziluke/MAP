package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expresions.Exp;
import Model.OutList.MyIList;
import Model.State.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp expr){
        exp = expr;
    }

    @Override
    public String toString(){
        return "print("+exp.toString()+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();

        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIList<Value> out = state.getOut();

        Value v = exp.eval(tbl, heap);
        out.add(v);
        //System.out.println(v.toString());
        return null;
    }
}
