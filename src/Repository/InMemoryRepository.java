package Repository;

import Model.Dictionary.MyIDictionary;
import Model.Dictionary.MyIHeap;
import Model.ExecStack.MyIStack;
import Model.OutList.MyIList;
import Model.State.PrgState;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class InMemoryRepository implements IRepository {

    private List<PrgState> states;
    private String logFilePath;

    public InMemoryRepository(String logFilePath){
        states = new LinkedList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void logPrgStateExec(PrgState prgState){
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        MyIStack<IStmt> stack = prgState.getStk();
        MyIDictionary<String, Value> dict = prgState.getSymTable();
        MyIList<Value> out = prgState.getOut();
        MyIDictionary<StringValue, BufferedReader> fltbl = prgState.getFileTable();
        MyIHeap<Integer, Value> heap = prgState.getHeap();
        logFile.write("Id: ");
        logFile.print(prgState.getId());
        logFile.write("\nExecution Stack:\n");
        logFile.print(stack);
        logFile.write("Sym Table:\n");
        logFile.print(dict);
        logFile.write("Out Table:\n");
        logFile.print(out);
        logFile.write("FileTable:\n");
        logFile.print(fltbl);
        logFile.write("Heap:\n");
        logFile.print(heap);
        logFile.write("\n");

        logFile.close();
        }catch(IOException ignored){}
    }

    @Override
    public void addPrg(PrgState prg) {
        states.add(prg);
    }

    @Override
    public List<PrgState> getPrgList() {
        return states;
    }

    @Override
    public void setPrgList(List<PrgState> states) {
        this.states = states;
    }
}
