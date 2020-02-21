package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expresions.Exp;
import Model.State.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt {
    private Exp e;

    public closeRFile(Exp exp){
        e = exp;
    }

    @Override
    public String toString() {
        return "closeFile("+e.toString()+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = e.typecheck(typeEnv);
        if(t.equals(new StringType())){
            return typeEnv;
        }
        else throw new MyException("Expression value is not String type!\n");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIDictionary<StringValue, BufferedReader> fltbl = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        Value v = e.eval(tbl, heap);
        if(v.getType().equals(new StringType())){
            StringValue s = (StringValue)v;
            if(fltbl.isDefined(s)){
                try{
                BufferedReader buff = fltbl.lookup(s);
                buff.close();
                fltbl.remove(s);
                return null;
                }catch (IOException io){
                    throw new MyException("Cannot close file!\n");
                }
            }
            else{
                throw new MyException("File is not defined!\n");
            }
        }
        else{
            throw new MyException("Expression value is not String type!\n");
        }
    }
}
