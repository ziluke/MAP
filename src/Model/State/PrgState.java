package Model.State;

import Model.Dictionary.MyDictionary;
import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.Exceptions.MyException;
import Model.Exceptions.MyStmtExecException;
import Model.ExecStack.MyIStack;
import Model.OutList.MyIList;
import Model.Statements.IStmt;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIList<Value> out;
    private MyIHeap<Integer, Value> heap;
    private IStmt originalProgram;
    private static int ct=0;
    private int id;


    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, MyIDictionary<StringValue, BufferedReader> fltbl, MyIHeap<Integer, Value> heap, IStmt prg){
        exeStack = stk;
        symTable = symtbl;
        out = ot;
        fileTable = fltbl;
        this.heap = heap;
        originalProgram = prg;
        //todo: originalProgram = deepCopy(prg);
        stk.push(prg);
        id = PrgState.newId();
    }

    @Override
    public String toString(){
        return "Id: "+ id +"\n"+"ExeStack:\n"+exeStack.toString() + "SymTable:\n"+symTable.toString()+"Output:\n"+out.toString()+"FileTable:\n"+fileTable.toString()+"Heap:\n"+heap.toString();
    }

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()){
            throw new MyException("Execution stack is empty!\n");
        }
        else{
            IStmt crtStmt = exeStack.pop();
            //System.out.println(prg.toString());
            return crtStmt.execute(this);
        }
    }

    public MyIStack<IStmt> getStk(){
        return this.exeStack;
    }

    public int getId(){
        return id;
    }

    private static synchronized int newId(){
        return ++ct;
    }

    public MyIDictionary<String, Value> getSymTable(){
        return this.symTable;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }
    public MyIList<Value> getOut(){
        return this.out;
    }
    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }
}
