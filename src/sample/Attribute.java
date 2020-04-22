package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Attribute {
    private SimpleStringProperty attributeName;

    public Attribute(){
        this(null);
    };

    public Attribute(String attributeName) {
        this.attributeName = new SimpleStringProperty(attributeName);
    }

    public String getAttributeName() {
        return attributeName.get();
    }

    public SimpleStringProperty attributeNameProperty() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName.set(attributeName);
    }
}
