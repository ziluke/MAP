package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.State.PrgState;
import Model.Types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    String toString();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
