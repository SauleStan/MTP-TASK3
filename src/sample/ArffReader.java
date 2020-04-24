package sample;

import javafx.collections.ObservableList;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArffReader {

    public void readArff(String folderName, ArrayList<Attribute> selectedAttributes) throws IOException {


        // Read all the instances in the file(ARFF)
        BufferedReader reader = new BufferedReader(new FileReader(folderName));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();

        // Make the last attribute be the class
        data.setClassIndex(data.numAttributes() - 1);

        // TODO: figure out how to make proper attributes from the selected ones
        // TODO: problem. how do you know attribute type once you turn it into sample.Attribute? (Save type info in the class?)


        // Return data
        // TODO: return some sort of data for insertion in file?

    }
    public String getRelation(String filename) throws IOException {
        // Read all the instances in the file(ARFF)
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();

        return data.relationName();
    }

    public ArrayList<Attribute> getAttributes(String filename) throws IOException {
        // String filename = "arff\\fixed_1 Dziaugsmas_01.arff";

        // Attribute array
        ArrayList<Attribute> attrArray = new ArrayList<Attribute>();

        // Read all the instances in the file(ARFF)
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();

        // Make the last attribute be the class
        data.setClassIndex(data.numAttributes() - 1);
        // Adds attributes to the array list that will be sent back
        for(int i = 0; i < data.numAttributes()-1; i++) {
            attrArray.add(data.attribute(i));
        }
        /*
        for(int i = 0; i < attrArray.size(); i++) {
            System.out.println(attrArray.get(i));
        }
         */
        reader.close();

        return attrArray;
    }

    public List<String> getData(File file, ObservableList<sample.Attribute> attributeList) throws IOException {
        // Read all the instances in the file(ARFF)

        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();

        // List of instance to be sent back
        List<String> instances = new ArrayList<String>();

        // Make the last attribute be the class
        data.setClassIndex(data.numAttributes() - 1);

        // Assigns a specific attribute to an Attribute variable
        //Attribute att = data.attribute(0);

        // Checks attribute type
        //System.out.println(att.isString());

        // Takes the value from the instance by the attribute
        for(sample.Attribute attr: attributeList){
            instances.add(data.instance(0).toString(attr.getWekaAttr()));
        }
        System.out.println(instances);

        return instances;

        // Takes the value from the instance by index value
        //System.out.println(data.instance(0).value(1));

    }
}
