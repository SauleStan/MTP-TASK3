package sample;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.w3c.dom.Attr;
import weka.core.Attribute;

public class Controller implements Initializable {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane anchorid;
    @FXML private TableView<Attribute> attributesTable;
    @FXML private TableColumn<ObservableList<Attribute>, Attribute> attributesColumn;
    @FXML private TextArea availableAttributesTextArea;
    @FXML private Button chooseFolderButton;
    @FXML private Button extractButton;
    @FXML private Button pauseButton;
    @FXML private Button resumeButton;
    @FXML private Button cancelButton;
    @FXML private ProgressBar progressBar;

    // Observable List for attributes
    private ObservableList<Attribute> attrObservableList = FXCollections.observableArrayList();

    /*==========================DIRECTORY SELECTION==========================*/
    public void chooseFolderAction(javafx.event.ActionEvent event) throws IOException {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if(selectedDirectory != null) {
            ArffReader arffReader = new ArffReader();

            String path = selectedDirectory.getAbsolutePath();
            System.out.println("Path: " + path);

            // Reads through files in the chosen directory
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    // Gets array list of attributes from the file
                     ArrayList<Attribute> attrArray = arffReader.getAttributes(listOfFiles[i].getAbsolutePath());

                     // Checks if the attribute exists in the observable list and if not - adds it
                     for(Attribute a: attrArray){
                         if(!attrObservableList.contains(a)){
                             attrObservableList.add(a);

                         }
                     }
                     
                    break;
                }
            }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //attributesColumn.setCellValueFactory(cellData -> cellData.getValue().);
    }
}
