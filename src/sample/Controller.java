package sample;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.w3c.dom.Attr;
import weka.core.Attribute;
import weka.core.Instance;

public class Controller implements Initializable{

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane anchorid;
    @FXML private TableView<sample.Attribute> attributesTable;
    @FXML private TableColumn<sample.Attribute, String> attributesColumn;
    @FXML private TableView<sample.Attribute> selectedAttributesTable;
    @FXML private TableColumn<sample.Attribute, String> selectedAttributesColumn;
    @FXML private TextArea availableAttributesTextArea;
    @FXML private Button chooseFolderButton;
    @FXML private Button extractButton;
    @FXML private Button pauseButton;
    @FXML private Button resumeButton;
    @FXML private Button cancelButton;
    @FXML private Button buttonRight;
    @FXML private Button buttonLeft;


    // Observable List for sample.attributes for attribute tableview
    private ObservableList<sample.Attribute> attrObservableList = FXCollections.observableArrayList();

    // List of selected attributes
    // Gotta keep this as sample.Attribute to populate the selected attributes tableview
    private ObservableList<sample.Attribute> selectedAttributesArray = FXCollections.observableArrayList();

    // Relation name
    private String relationName;

    // String of absolute path to the selected folder of arff files
    // 'Tis here cause during extraction we need the same path
    private String selectedFolder;


    Thread thread2; // Thread for extracting and generating new file


    /*==========================DIRECTORY SELECTION==========================*/
    public void chooseFolderAction(javafx.event.ActionEvent event) throws IOException {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            ArffReader arffReader = new ArffReader();

            selectedFolder = selectedDirectory.getAbsolutePath();
            System.out.println("Path: " + selectedFolder);

            // Reads through files in the chosen directory
            File folder = new File(selectedFolder);
            File[] listOfFiles = folder.listFiles();

            // Takes the first file and reads the attributes CAUSE THEY ALL ARE THE SAME
            if (listOfFiles[0].isFile()) {

                // Gets array list of weka attributes from the file
                ArrayList<Attribute> attrArray = null;
                try {
                    attrArray = arffReader.getAttributes(listOfFiles[0].getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Puts all the weka attributes from the file into an observable list as sample.Attribute objects
                // Cause otherwise it doesn't work with tableview
                for (Attribute a : attrArray) {
                    System.out.println(a);
                    attrObservableList.add(new sample.Attribute(a));
                }

                // Prints the attributes observable list to console TODO:delete this probably, idc
                System.out.println("Observable List: ");
                for (int x = 0; x < attrObservableList.size(); x++) {
                    System.out.println(attrObservableList.get(x).getAttributeName());
                }
                try {
                    relationName = arffReader.getRelation(listOfFiles[0].getAbsolutePath());
                } catch (IOException e) {

                }
            }
        }
    }

    public void extractButtonAction(javafx.event.ActionEvent event) throws IOException {
        thread2 = new Thread() {
            @Override
            public void run() {
                // while (keepRunning) {
                // Array list of selected attributes
                ArrayList<Attribute> atts = new ArrayList<Attribute>();

                // List of instances taken from the files in the selected folder
                List<String> instances = new ArrayList<String>();

                // TODO: figure out how to generate file names
                String fileName = "newArffFile.arff";

                String folderName = "D:\\MY FILES\\Studies\\4 SEMESTER\\MTP-TASK3\\generatedFiles";


                ArffReader arffReader = new ArffReader();

                // Reads through files in the chosen directory
                File folder = new File(selectedFolder);
                File[] listOfFiles = folder.listFiles();

                // Creates file where extracted data will be written
                File fileToWrite = new File(folderName + "\\" + fileName);
                try {
                    // Prepares writer to write into the output file
                    FileWriter writer = new FileWriter(fileToWrite);

                    // Writes the relation and attributes into the file
                    writer.write("@relation " + relationName + "\n");
                    writer.write("\n");
                    for (sample.Attribute attr : selectedAttributesArray) {
                        if (attr.getWekaAttr().isNumeric())
                            writer.write("@attribute " + attr.getAttributeName() + " numeric\n");
                        if (attr.getWekaAttr().isNominal())
                            writer.write("@attribute " + attr.getAttributeName() + " nominal\n");
                        if (attr.getWekaAttr().isString())
                            writer.write("@attribute " + attr.getAttributeName() + " string\n");
                        if (attr.getWekaAttr().isDate())
                            writer.write("@attribute " + attr.getAttributeName() + " date\n");
                        if (attr.getWekaAttr().isRelationValued())
                            writer.write("@attribute " + attr.getAttributeName() + " relational\n");
                    }
                    writer.write("\n");
                    writer.write("@data" + "\n");
                    writer.write("\n");

                    // Extracts the list of instances from the files in selected folder
                    for (File file : listOfFiles) {
                        if (file.isFile()) {
                            instances = arffReader.getData(file, selectedAttributesArray);

                            // Writes the extracted list of values into the file
                            for (int i = 0; i < instances.size(); i++) {
                                if (i > 0)
                                    writer.write(",");
                                writer.write(instances.get(i));
                            }

                            writer.write("\n");

                        }
                    }

                    // Closes the writer to file
                    writer.close();


                } catch (IOException e) {
                    System.out.println("Something wrong with your extraction method uwu");
                }
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        Alert alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialogue");
                        alert.setHeaderText(null);
                        alert.setContentText("Extraction is Completed and Saved in the folder");
                        alert.showAndWait();
                    }});

            }
        };
        thread2.start();
    }

    public void pauseButtonAction(javafx.event.ActionEvent event) {
        thread2.suspend();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialogue");
        alert.setHeaderText(null);
        alert.setContentText("Extraction is Paused");
        alert.showAndWait();
    }

    public void cancelButtonAction(javafx.event.ActionEvent event) {
        thread2.stop();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialogue");
        alert.setHeaderText(null);
        alert.setContentText("Extraction is Canceled");
        alert.showAndWait();
    }

    public void resumeButtonAction(javafx.event.ActionEvent event) {
        thread2.resume();
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialogue");
        alert.setHeaderText(null);
        alert.setContentText("Extraction is Resumed");
        alert.showAndWait();
    }

    public void buttonRightAction(javafx.event.ActionEvent event) {
        // Makes a new weka attribute of selected sample.attribute :I
        sample.Attribute selectedAttribute = attributesTable.getSelectionModel().getSelectedItem();
        selectedAttributesArray.add(selectedAttribute);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Preps the available attributes table
        attributesColumn.setCellValueFactory(cellData -> cellData.getValue().attributeNameProperty());
        // Actually puts the attrObservableList into the table, SAULE. istg. smh. don't forget this again.
        attributesTable.setItems(attrObservableList);
        //attributesColumn.setCellValueFactory(new PropertyValueFactory<sample.Attribute, String>("attributeName"));

        // Everything the same as above, but for the selected attributes
        selectedAttributesColumn.setCellValueFactory(cellData -> cellData.getValue().attributeNameProperty());
        selectedAttributesTable.setItems(selectedAttributesArray);
    }
}
