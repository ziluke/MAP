package GUI;

import Controller.Controller;
import Model.Dictionary.*;
import Model.Exceptions.MyException;
import Model.ExecStack.MyIStack;
import Model.ExecStack.MyStack;
import Model.Expresions.*;
import Model.OutList.MyIList;
import Model.OutList.MyList;
import Model.State.PrgState;
import Model.Statements.*;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.IRepository;
import Repository.InMemoryRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RunCtrl implements Initializable {

    private List<IStmt> stmts;
    static Controller ctrl;

    @FXML
    private ListView<String> runView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createStmt();
        runView.setItems(FXCollections.observableArrayList(stmts.stream().map(IStmt::toString).collect(Collectors.toList())));
        runView.setOnMouseClicked(click -> {

            if (click.getClickCount() == 2) {
                int item = runView.getSelectionModel()
                        .getSelectedIndex();
                runProgram(item);
            }
        });
    }
    private void runProgram(int item){
        IStmt ex = stmts.get(item);

        try {
            MyIDictionary<String, Type> typeEnv = new MyDictionary<>();
            ex.typecheck(typeEnv);
        }catch (MyException e){
            System.out.println(ex.toString()+"\n"+e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(ex.toString()+"\n"+e.getMessage());

            alert.showAndWait();
            return;
        }
        MyIStack<IStmt> stk = new MyStack<>();
        MyIDictionary<String, Value> dict = new MyDictionary<>();
        MyIHeap<Integer, Value> heap = new MyHeap<>();
        MyIList<Value> out = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fltbl = new MyFileTable<>();

        PrgState state = new PrgState(stk, dict, out, fltbl, heap, ex);
        IRepository repo = new InMemoryRepository("log"+(item+1)+".txt");
        ctrl = new Controller(repo);
        ctrl.addPrg(state);

        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("main.fxml"));
            BorderPane root = loader.load();
            Scene scene =  new Scene(root, root.getWidth(), root.getHeight());
            Stage stg = new Stage();
            stg.setScene(scene);
            stg.setTitle("Interpreter");
            stg.showAndWait();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    private void createStmt(){
        /*Ref int a; new(a,20);(for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));print(rh(a))*/
        IStmt forstmt = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new New("a", new ValueExp(new IntValue(20))),
                        new CompStmt(new ForStmt("v", new ValueExp(new IntValue(0)), new ValueExp(new IntValue(3)), new ArithExp(1, new VarExp("v"), new ValueExp(new IntValue(1))),
                                new Fork(new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(3, new VarExp("v"), new rH(new VarExp("a"))))))),
                                        new PrintStmt(new rH(new VarExp("a"))))));

        //error: bool v; v=2;Print(v)
        IStmt err = new CompStmt(new VarDeclStmt("v",new BoolType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));
        //int v; v=2;Print(v)
        IStmt s1 = new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new
                        VarExp("v"))));

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        IStmt s2 = new CompStmt( new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(1,new ValueExp(new IntValue(2)),new
                                ArithExp(3,new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",new ArithExp(1,new VarExp("a"), new
                                        ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));

        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt s3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        //String varf; varf="test.in"; openRFile(varf); int varc, readFile(varf, varc); print(varc)
        IStmt file = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new openRFile(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                                new CompStmt(new readFile(new VarExp("varf"), "varc"),
                                        new CompStmt(new PrintStmt(new VarExp("varc")),
                                                new CompStmt( new readFile(new VarExp("varf"), "varc"),new CompStmt(new PrintStmt(new VarExp("varc")), new closeRFile(new VarExp("varf"))))))))));

        //int v; v=4; (while(v>0) print(v); v=v-1); print(v)
        IStmt wh = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new While(new RelExp(new VarExp("v"), new ValueExp(new IntValue(0)), 5),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
                                        new ArithExp(2, new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt s6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new New("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));


        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt s7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new New("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))), new PrintStmt(new ArithExp(1, new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));


        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt s8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new New("v",new ValueExp(new IntValue(20))),
                new CompStmt(new PrintStmt(new rH(new VarExp("v"))), new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
                        new PrintStmt(new ArithExp(1,new rH(new VarExp("v")), new ValueExp(new IntValue(5))))))));


        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt s9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new New("a", new VarExp("v")), new CompStmt(new New("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new rH(new rH(new VarExp("a")))))))));


        //int v; Ref int a; v=10;new(a,22);   fork(wH(a,30);v=32;print(v);print(rH(a)));   print(v);print(rH(a))
        IStmt s10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new New("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new Fork(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));

        stmts = new ArrayList<>();
        stmts.addAll(Arrays.asList(forstmt, err,s1,s2,s3,file, wh, s6, s7, s8, s9, s10));
    }
}
