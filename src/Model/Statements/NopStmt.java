package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.State.PrgState;
import Model.Types.Type;

public class NopStmt implements IStmt {

    public NopStmt(){}

    @Override
    public String toString() {
        return "NOP";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state){
        return null;
    }
}
