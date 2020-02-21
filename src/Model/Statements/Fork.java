package Model.Statements;

import Model.Dictionary.MyDictionary;
import Model.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.ExecStack.MyIStack;
import Model.ExecStack.MyStack;
import Model.State.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class Fork implements IStmt {
    private IStmt prg;

    public Fork(IStmt p){
        prg = p;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        PrgState newState;
        MyIDictionary<String, Value> newSymTbl = new MyDictionary<>(state.getSymTable());
//        for(Map.Entry<String, Value> e : state.getSymTable().getContent().entrySet()){
//            newSymTbl.add(e.getKey(), e.getValue());
//        }
        MyIStack<IStmt> stk = new MyStack<>();
        //stk.push(prg);
        newState = new PrgState(stk, newSymTbl, state.getOut(), state.getFileTable(), state.getHeap(), prg);
        return newState;
    }

    @Override
    public String toString(){
        return "Fork("+prg.toString()+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        prg.typecheck(typeEnv);
        return typeEnv;
    }
}
