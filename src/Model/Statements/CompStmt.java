package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.ExecStack.MyIStack;
import Model.State.PrgState;
import Model.Types.Type;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt snd;

    public CompStmt(IStmt first, IStmt snd){
        this.first = first;
        this.snd = snd;
    }
    @Override
    public String toString(){

        return "("+first.toString() + ";" + snd.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return snd.typecheck(first.typecheck(typeEnv));
    }

    @Override
    public PrgState execute(PrgState state){
        MyIStack<IStmt> stk = state.getStk();
        stk.push(snd);
        stk.push(first);
        return null;
    }
}
