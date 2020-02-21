package Model.Statements;

import Model.Dictionary.MyIDictionary;
import Model.Exceptions.MyException;
import Model.ExecStack.MyIStack;
import Model.Expresions.Exp;
import Model.Expresions.RelExp;
import Model.Expresions.VarExp;
import Model.State.PrgState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Value;

public class ForStmt implements IStmt {
    private String var;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt prog;

    public ForStmt(String v, Exp e1, Exp e2, Exp e3, IStmt prg){
        var = v;
        exp2 = e2;
        exp1 = e1;
        exp3 = e3;
        prog = prg;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl = state.getSymTable();
        MyIStack<IStmt> stk = state.getStk();
        if(tbl.isDefined(var)){
            throw new MyException("Variable already defined!\n");
        }
        else
        {
            IStmt stmt = new CompStmt(new VarDeclStmt(var, new IntType()),
                    new CompStmt(new AssignStmt(var, exp1),
                            new While(new RelExp(new VarExp(var), exp2, 1), new CompStmt(prog, new AssignStmt(var, exp3)))));
            stk.push(stmt);
        }
        return null;
    }

    @Override
    public String toString() {
        return "for("+var+"="+exp1.toString()+";"+exp2.toString()+";"+var+"="+exp3.toString()+")"+prog.toString();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.add(var, new IntType());
        Type t1 = exp1.typecheck(typeEnv);
        Type t2 = exp2.typecheck(typeEnv);
        Type t3 = exp3.typecheck(typeEnv);
        if(!(t1.equals(new IntType()) && t2.equals(new IntType()) && t3.equals(new IntType()))){
            throw new MyException("Expressions do not return Int type!\n");
        }
        return typeEnv;

    }
}
