package sample;

import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArffReader {

    public void readArff() throws IOException {
        String filename = "arff\\fixed_1 Dziaugsmas_01.arff";

        // Read all the instances in the file(ARFF)
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();

        // Make the last attribute be the class
        data.setClassIndex(data.numAttributes() - 1);


        // Print data
        System.out.println(data);

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

        return attrArray;
    }

    public void getData() throws IOException {
        String filename = "arff\\fixed_1 Dziaugsmas_01.arff";

        // Read all the instances in the file(ARFF)
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();

        // Make the last attribute be the class
        data.setClassIndex(data.numAttributes() - 1);

        // Assigns a specific attribute to an Attribute variable
        Attribute att = data.attribute(0);

        // Checks attribute type
        System.out.println(att.isString());

        // Takes the value from the instance by the attribute
        System.out.println(data.instance(0).stringValue(att));

        // Takes the value from the instance by index value
        System.out.println(data.instance(0).value(1));

    }
}
