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

import java.io.*;

public class openRFile implements IStmt {
    private Exp e;

    public openRFile(Exp e){
        this.e = e;
    }

    @Override
    public String toString(){
        return "open_RFile("+e.toString()+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t = e.typecheck(typeEnv);

        if(t.equals(new StringType())){
            return typeEnv;
        }
        else throw new MyException("Expression is not a String type!\n");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<String, Value> tbl = state.getSymTable();
        Value v = e.eval(tbl, heap);
        if(!v.getType().equals(new StringType())){
            throw new MyException("Expression is not a String type!\n");
        }
        else{
            StringValue s = (StringValue)v;
            if(fileTable.isDefined(s)){
                throw new MyException("The filename is already defined!\n");
            }
            else{
                try{
                Reader reader = new FileReader(s.getVal());
                BufferedReader buff = new BufferedReader(reader);
                fileTable.add(s, buff);
                return null;
                }catch(FileNotFoundException f){
                    throw new MyException("File not found!\n");
                }
            }
        }
    }
}
