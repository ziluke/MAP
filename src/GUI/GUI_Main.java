package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI_Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("run.fxml"));
            Pane root = loader.load();
            Scene scene =  new Scene(root, root.getWidth(), root.getHeight());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Run");
            primaryStage.show();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
