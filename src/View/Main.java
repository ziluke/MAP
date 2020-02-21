package View;

import Controller.Controller;
import Model.Dictionary.*;
import Model.Exceptions.MyException;
import Model.Expresions.*;
import Model.Types.*;
import Model.Values.*;
import View.Commands.ExitCommand;
import View.Commands.RunExample;
import Model.ExecStack.MyIStack;
import Model.ExecStack.MyStack;
import Model.OutList.MyIList;
import Model.OutList.MyList;
import Model.State.PrgState;
import Model.Statements.*;
import Repository.IRepository;
import Repository.InMemoryRepository;

import java.io.BufferedReader;

public class Main {

    public static void main(String[] args){

        //error: bool v; v=2;Print(v)
        IStmt err = new CompStmt(new VarDeclStmt("v",new BoolType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            err.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(err.toString()+"\n"+e.getMessage());
            //return;
        }

        //int v; v=2;Print(v)
        IStmt s1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s1.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s1.toString()+"\n"+e.getMessage());
            return;
        }

        MyIStack<IStmt> stk1 = new MyStack<>();
        MyIDictionary<String, Value> dict1= new MyDictionary<>();
        MyIHeap<Integer, Value> heap1 = new MyHeap<>();
        MyIList<Value> out1 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl1 = new MyFileTable<>();

        PrgState state1 = new PrgState(stk1, dict1, out1, fltbl1, heap1, s1);
        IRepository repo1 = new InMemoryRepository("log1.txt");
        Controller ctrl1 = new Controller(repo1);
        ctrl1.addPrg(state1);

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        IStmt s2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1,new ValueExp(new IntValue(2)),new
                                ArithExp(3,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(1,new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s2.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s2.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk2 = new MyStack<>();
        MyIDictionary<String, Value> dict2= new MyDictionary<>();
        MyIHeap<Integer, Value> heap2 = new MyHeap<>();
        MyIList<Value> out2 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl2 = new MyFileTable<>();

        PrgState state2 = new PrgState(stk2, dict2, out2, fltbl2, heap2, s2);
        IRepository repo2 = new InMemoryRepository("log2.txt");
        Controller ctrl2 = new Controller(repo2);
        ctrl2.addPrg(state2);

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt s3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s3.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s3.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk3 = new MyStack<>();
        MyIDictionary<String, Value> dict3= new MyDictionary<>();
        MyIHeap<Integer, Value> heap3 = new MyHeap<>();
        MyIList<Value> out3 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl3 = new MyFileTable<>();

        PrgState state3 = new PrgState(stk3, dict3, out3, fltbl3, heap3, s3);
        IRepository repo3 = new InMemoryRepository("log3.txt");
        Controller ctrl3 = new Controller(repo3);
        ctrl3.addPrg(state3);

        //String varf; varf="test.in"; openRFile(varf); int varc, readFile(varf, varc); print(varc)
        IStmt file = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                new CompStmt(new openRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                        new CompStmt( new readFile(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")), new closeRFile(new VarExp("varf"))))))))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            file.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(file.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk4 = new MyStack<>();
        MyIDictionary<String, Value> dict4= new MyDictionary<>();
        MyIHeap<Integer, Value> heap4 = new MyHeap<>();
        MyIList<Value> out4 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl4 = new MyFileTable<>();

        PrgState state4 = new PrgState(stk4, dict4, out4, fltbl4, heap4, file);
        IRepository repo4 = new InMemoryRepository("log4.txt");
        Controller ctrl4 = new Controller(repo4);
        ctrl4.addPrg(state4);

        //int v; v=4; (while(v>0) print(v); v=v-1); print(v)
        IStmt wh = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new While(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), 5),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                        new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            wh.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(wh.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk5 = new MyStack<>();
        MyIDictionary<String, Value> dict5 = new MyDictionary<>();
        MyIHeap<Integer, Value> heap5 = new MyHeap<>();
        MyIList<Value> out5 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl5 = new MyFileTable<>();

        PrgState state5 = new PrgState(stk5, dict5, out5, fltbl5, heap5, wh);
        IRepository repo5 = new InMemoryRepository("log5.txt");
        Controller ctrl5 = new Controller(repo5);
        ctrl5.addPrg(state5);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt s6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new New("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));

        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s6.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s6.toString()+"\n"+e.getMessage());
        }

        MyIStack<IStmt> stk6 = new MyStack<>();
        MyIDictionary<String, Value> dict6 = new MyDictionary<>();
        MyIHeap<Integer, Value> heap6 = new MyHeap<>();
        MyIList<Value> out6 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl6 = new MyFileTable<>();

        PrgState state6 = new PrgState(stk6, dict6, out6, fltbl6, heap6, s6);
        IRepository repo6 = new InMemoryRepository("log6.txt");
        Controller ctrl6 = new Controller(repo6);
        ctrl6.addPrg(state6);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt s7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new New("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))), new PrintStmt(new ArithExp(1, new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s7.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s7.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk7 = new MyStack<>();
        MyIDictionary<String, Value> dict7 = new MyDictionary<>();
        MyIHeap<Integer, Value> heap7 = new MyHeap<>();
        MyIList<Value> out7 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl7 = new MyFileTable<>();

        PrgState state7 = new PrgState(stk7, dict7, out7, fltbl7, heap7, s7);
        IRepository repo7 = new InMemoryRepository( "log7.txt");
        Controller ctrl7 = new Controller(repo7);
        ctrl7.addPrg(state7);

        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt s8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",new ValueExp(new IntValue(20))),
                new CompStmt(new PrintStmt(new rH(new VarExp("v"))), new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                        new PrintStmt(new ArithExp(1,new rH(new VarExp("v")), new ValueExp(new IntValue(5))))))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s8.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s8.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk8 = new MyStack<>();
        MyIDictionary<String, Value> dict8 = new MyDictionary<>();
        MyIHeap<Integer, Value> heap8 = new MyHeap<>();
        MyIList<Value> out8 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl8 = new MyFileTable<>();

        PrgState state8 = new PrgState(stk8, dict8, out8, fltbl8, heap8, s8);
        IRepository repo8 = new InMemoryRepository( "log8.txt");
        Controller ctrl8 = new Controller(repo8);
        ctrl8.addPrg(state8);

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
         IStmt s9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                 new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                         new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                 new CompStmt(new New("a", new VarExp("v")), new CompStmt(new New("v", new ValueExp(new IntValue(30))),
                                         new PrintStmt(new rH(new rH(new VarExp("a")))))))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s9.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s9.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk9 = new MyStack<>();
        MyIDictionary<String, Value> dict9 = new MyDictionary<>();
        MyIHeap<Integer, Value> heap9 = new MyHeap<>();
        MyIList<Value> out9 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl9 = new MyFileTable<>();

        PrgState state9 = new PrgState(stk9, dict9, out9, fltbl9, heap9, s9);
        IRepository repo9 = new InMemoryRepository( "log9.txt");
        Controller ctrl9 = new Controller(repo9);
        ctrl9.addPrg(state9);

        //int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a)));   print(v);print(rH(a))
        IStmt s10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new New("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new Fork(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));
        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            s10.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(s10.toString()+"\n"+e.getMessage());
        }
        MyIStack<IStmt> stk10 = new MyStack<>();
        MyIDictionary<String, Value> dict10 = new MyDictionary<>();
        MyIHeap<Integer, Value> heap10 = new MyHeap<>();
        MyIList<Value> out10 = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl10 = new MyFileTable<>();

        PrgState state10 = new PrgState(stk10, dict10, out10, fltbl10, heap10, s10);
        IRepository repo10 = new InMemoryRepository( "log10.txt");
        Controller ctrl10 = new Controller(repo10);
        ctrl10.addPrg(state10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", s1.toString(), ctrl1));
        menu.addCommand(new RunExample("2", s2.toString(), ctrl2));
        menu.addCommand(new RunExample("3", s3.toString(), ctrl3));
        menu.addCommand(new RunExample("4", file.toString(), ctrl4));
        menu.addCommand(new RunExample("5", wh.toString(), ctrl5));
        menu.addCommand(new RunExample("6", s6.toString(), ctrl6));
        menu.addCommand(new RunExample("7", s7.toString(), ctrl7));
        menu.addCommand(new RunExample("8", s8.toString(), ctrl8));
        menu.addCommand(new RunExample("9", s9.toString(), ctrl9));
        menu.addCommand(new RunExample("10", s10.toString(), ctrl10));
        menu.show();
    }
}
