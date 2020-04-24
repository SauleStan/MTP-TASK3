package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*================================OPENING MAIN WINDOW================================*/
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage window = new Stage();
        window.setTitle("TASK3");
        window.initModality(Modality.APPLICATION_MODAL);

        window.setScene(new Scene(root));
        window.setResizable(false);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
