package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Expresions.Exp;
import Model.State.PrgState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt {
    private Exp exp;
    private String var_name;

    public readFile(Exp exp, String var_name){
        this.exp = exp;
        this.var_name = var_name;
    }
    @Override
    public String toString(){
        return "readFile("+exp.toString()+")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = typeEnv.lookup(var_name);
        Type t2 = exp.typecheck(typeEnv);
        if(t1.equals(new IntType())){
            if(t2.equals(new StringType()))
                return typeEnv;
            else throw new MyException("Expression value not String type!\n");
        }
        else throw new MyException("Variable is not defined or not Int type!\n");
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        MyIDictionary<StringValue, BufferedReader> fltbl = state.getFileTable();
        if(tbl.isDefined(var_name) && tbl.getType(var_name).equals(new IntType())){
            Value v = exp.eval(tbl, heap);
            if(v.getType().equals(new StringType())){
                StringValue s = (StringValue)v;
                if(fltbl.isDefined(s)){
                    BufferedReader buff = fltbl.lookup(s);
                    try{
                        String line = buff.readLine();
                        IntValue v1;
                        if(line.equals("")){
                            v1 = new IntValue(0);
                        }
                        else{
                            v1 = new IntValue(Integer.parseInt(line));
                        }
                        tbl.update(var_name, v1);
                        return null;
                    }catch(IOException io){
                        throw new MyException("Cannot read from BufferedReader!\n");
                    }
                }
                else{
                    throw new MyException("File is not defined!\n");
                }
            }
            else{
                throw new MyException("Expression value not String type!\n");
            }
        }
        else{
            throw new MyException("Variable is not defined or not Int type!\n");
        }
    }
}
