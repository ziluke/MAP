package GUI;

import Controller.Controller;
import Model.State.PrgState;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.io.BufferedReader;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MainCtrl implements Initializable {

    private Controller ctrl;

    @FXML
    private TextField prgField;

    @FXML
    private TableView<Map.Entry<String, Value>> symView;
    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symName;
    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symValue;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapView;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddr;
    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> heapValue;

    @FXML
    private ListView<Integer> prgView;

    @FXML
    private ListView<Map.Entry<StringValue, BufferedReader>> fileView;
    @FXML
    private ListView<Value> outView;
    @FXML
    private ListView<IStmt> execView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl = RunCtrl.ctrl;
        symName.setCellValueFactory(n -> new SimpleStringProperty(n.getValue().getKey()));
        symValue.setCellValueFactory(v -> new SimpleObjectProperty<>(v.getValue().getValue()));
        heapAddr.setCellValueFactory(a -> new SimpleObjectProperty<>(a.getValue().getKey()));
        heapValue.setCellValueFactory(v -> new SimpleObjectProperty<>(v.getValue().getValue()));
        populatePrg();
        prgView.setOnMouseClicked(mouseEvent -> switchPrgState(getCurrentState()));
        prgView.getSelectionModel().select(0);
        switchPrgState(getCurrentState());
    }

    public void oneStep(){
        PrgState state = getCurrentState();
        if(state == null)
            return;
        try {
            if(state.getStk().getStack().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Stack is empty!");
                alert.setContentText("Program has finished executing");

                alert.showAndWait();
                return;
            }
            ctrl.oneStep();
            switchPrgState(getCurrentState());
        }catch (InterruptedException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    private PrgState getCurrentState(){
        if(prgView.getSelectionModel().getSelectedIndex() == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No program state has been selected!");
            alert.setContentText("Please select a program state");

            alert.showAndWait();
            return null;
        }
        int index = prgView.getSelectionModel().getSelectedIndex();
        return ctrl.getPrgState(index);
    }

    private void switchPrgState(PrgState state){
        if(state == null)
            return;

        populatePrg();
        populateSym(state);
        populateFile(state);
        populateHeap(state);
        populateOut(state);
        populateStack(state);
    }

    private void populatePrg(){
        prgView.setItems(ctrl.getIDs());
        prgView.refresh();
        prgField.setText(String.valueOf(ctrl.getIDs().size()));
    }

    private void populateSym(PrgState state){
        ObservableList<Map.Entry<String, Value>> data = FXCollections.observableArrayList();
        data.addAll(state.getSymTable().getContent().entrySet());
        symView.setItems(data);
        symView.refresh();
    }

    private void populateHeap(PrgState state){
        ObservableList<Map.Entry<Integer, Value>> data = FXCollections.observableArrayList();
        data.addAll(state.getHeap().getContent().entrySet());
        heapView.setItems(data);
        heapView.refresh();
    }

    private void populateOut(PrgState state){
        ObservableList<Value> data = FXCollections.observableArrayList();
        data.addAll(state.getOut().getContent());
        outView.setItems(data);
        outView.refresh();
    }

    private void populateFile(PrgState state){
        ObservableList<Map.Entry<StringValue, BufferedReader>> data = FXCollections.observableArrayList();
        data.addAll(state.getFileTable().getContent().entrySet());
        fileView.setItems(data);
        fileView.refresh();
    }

    private void populateStack(PrgState state){
        ObservableList<IStmt> data = FXCollections.observableArrayList();
        data.addAll(state.getStk().getStack());
        execView.setItems(data);
        execView.refresh();
    }

}
