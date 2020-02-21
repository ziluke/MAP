package Model.Expresions;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;


public class RelExp implements Exp{

    private Exp e1;
    private Exp e2;
    private int op;

    public RelExp(Exp e1, Exp e2, int op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString(){
        String output="";
        output = output+e1.toString();
        if(op == 1) {
            output += "<" + e2.toString();
            return output;
        }
        if(op == 2) {
            output += "<=" + e2.toString();
            return output;
        }
        if(op == 3) {
            output += "==" + e2.toString();
            return output;
        }
        if(op == 4) {
            output += "!=" + e2.toString();
            return output;
        }
        if(op == 5) {
            output += ">" + e2.toString();
            return output;
        }
        if(op == 6) {
            output += ">=" + e2.toString();
            return output;
        }
        return output;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value v1, v2;
        v1 = e1.eval(tbl, heap);
        if(v1.getType().equals(new IntType())){
            v2=e2.eval(tbl, heap);
            if(v2.getType().equals(new IntType()))
            {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(op == 1){
                    return new BoolValue(n1<n2);
                }
                if(op == 2){
                    return new BoolValue(n1<=n2);
                }
                if(op == 3){
                    return new BoolValue(n1==n2);
                }
                if(op == 4){
                    return new BoolValue(n1!=n2);
                }
                if(op == 5){
                    return new BoolValue(n1>n2);
                }
                if(op == 6){
                    return new BoolValue(n1>=n2);
                }
            }
            else
            {
                throw new MyException("Second operand is not integer!\n");
            }
        }
        else{
            throw new MyException("First opernad is not integer");
        }
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1 = e1.typecheck(typeEnv);
        Type t2 = e2.typecheck(typeEnv);
        if(t1.equals(new IntType())) {
            if (t2.equals(new IntType())) {
                return new BoolType();
            } else throw new MyException("Second operand is not int!\n");
        }
        else throw new MyException("First operand is not integer!\n");
    }
}
