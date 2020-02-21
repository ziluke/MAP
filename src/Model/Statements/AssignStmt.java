package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Expresions.Exp;
import Model.Exceptions.MyException;
import Model.State.PrgState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString(){return id+"="+exp.toString();}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = typeEnv.lookup(id);
        Type t2 = exp.typecheck(typeEnv);
        if(t1.equals(t2)){
            return typeEnv;
        }
        else throw new MyException("Right hand side and left hand side have different assignment types!\n");
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value val = exp.eval(symTbl, heap);
        if(symTbl.isDefined(id)){
            Value v = symTbl.lookup(id);
            Type typId = v.getType();
            if((val.getType()).equals(typId)){
                symTbl.update(id, val);
            }
            else {
                throw new MyException("Declared type of variable '" + id + "' and type of assigned expression do not match!\n");
            }
        }
        else{
            throw new MyException("The used variable '"+id+"' was not declared before!\n");
        }
        return null;
    }
}
