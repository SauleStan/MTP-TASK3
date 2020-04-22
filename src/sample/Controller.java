package sample;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane anchorid;
    @FXML private TableView<?> attributesTable;
    @FXML private TableColumn<?, ?> attributesColumn;
    @FXML private TextArea availableAttributesTextArea;
    @FXML private Button chooseFolderButton;
    @FXML private Button extractButton;
    @FXML private Button pauseButton;
    @FXML private Button resumeButton;
    @FXML private Button cancelButton;
    @FXML private ProgressBar progressBar;


    /*==========================DIRECTORY SELECTION==========================*/
    public void chooseFolderAction(javafx.event.ActionEvent event) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if(selectedDirectory != null) {
            System.out.println("Path: " + selectedDirectory.getAbsolutePath());
//            folderNameInput.setText(file.getAbsolutePath());
        }
    }

    public void extractButtonAction(javafx.event.ActionEvent event) {

    }

    public void pauseButtonAction(javafx.event.ActionEvent event) {

    }

    public void cancelButtonAction(javafx.event.ActionEvent event) {

    }

    public void resumeButtonAction(javafx.event.ActionEvent event) {

    }
}
