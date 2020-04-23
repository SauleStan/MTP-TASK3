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
import java.util.stream.Stream;

import javafx.beans.property.SimpleStringProperty;
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
import weka.core.Instance;

public class Controller implements Initializable {

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
    @FXML private ProgressBar progressBar;

    // Observable List for sample.attributes for attribute tableview
    private ObservableList<sample.Attribute> attrObservableList = FXCollections.observableArrayList();

    // List of selected attributes
    // Gotta keep this as sample.Attribute to populate the selected attributes tableview
    private ObservableList<sample.Attribute> selectedAttributesArray = FXCollections.observableArrayList();

    // String of absolute path to the selected folder of arff files
    // 'Tis here cause during extraction we need the same path
    String selectedFolder;

    /*==========================DIRECTORY SELECTION==========================*/
    public void chooseFolderAction(javafx.event.ActionEvent event) throws IOException {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if(selectedDirectory != null) {
            ArffReader arffReader = new ArffReader();

            selectedFolder = selectedDirectory.getAbsolutePath();
            System.out.println("Path: " + selectedFolder);

            // Reads through files in the chosen directory
            File folder = new File(selectedFolder);
            File[] listOfFiles = folder.listFiles();

            // Takes the first file and reads the attributes CAUSE THEY ALL ARE THE SAME
            if(listOfFiles[0].isFile()){

                // Gets array list of weka attributes from the file
                ArrayList<Attribute> attrArray = null;
                try {
                    attrArray = arffReader.getAttributes(listOfFiles[0].getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Puts all the weka attributes from the file into an observable list as sample.Attribute objects
                // Cause otherwise it doesn't work with tableview
                for(Attribute a: attrArray){
                    System.out.println(a);
                    attrObservableList.add(new sample.Attribute(a.name()));
                }

                // Prints the attributes observable list to console TODO:delete this probably, idc
                System.out.println("Observable List: ");
                for(int x=0; x<attrObservableList.size(); x++) {
                    System.out.println(attrObservableList.get(x).getAttributeName());
                }
            }

                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        // TODO: get only the data? DO you need to get it now or jsut parse thourgh agian?
                        // TODO:sleep. idc about grammar
                    }
                }


        }
    }

    public void extractButtonAction(javafx.event.ActionEvent event) throws IOException {
        // Array list of selected attributes
        ArrayList<Attribute> atts = new ArrayList<Attribute>();

        // List of instances taken from the files in the selected folder
        List<Instance> instances = new ArrayList<Instance>();

        // TODO: figure out how to generate file names
        String fileName = "newArffFile.arff";

        // TODO: get data from the selected folder only by using the selected attributes (Hint: getData() in ArffReader has everything u need. probs.)

        // Creates file with the data
        File file = new File(selectedFolder + "\\" + fileName);

        FileWriter writer = new FileWriter(file);
        writer.write("");//TODO: figure out how to send the data in one big chunk (AKA in format of data from weka library)
        writer.close();

    }

    public void pauseButtonAction(javafx.event.ActionEvent event) {

    }

    public void cancelButtonAction(javafx.event.ActionEvent event) {

    }

    public void resumeButtonAction(javafx.event.ActionEvent event) {

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
