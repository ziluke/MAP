package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.State.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements IStmt {
    private String name;
    private Type typ;

    public VarDeclStmt(String name, Type typ){
        this.name = name;
        this.typ = typ;
    }

    @Override
    public String toString(){
        return "Name: "+name + ", Type: "+typ.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(name, typ);
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> table = state.getSymTable();
        if(table.isDefined(name))
            throw new MyException("Variable already declared!\n");
        else {
            table.add(name, typ.defaultValue());
        }
        return null;
    }
}
