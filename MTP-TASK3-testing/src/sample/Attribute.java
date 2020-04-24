package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import weka.core.Instances;

public class Attribute {
    private SimpleStringProperty attributeName;
    private int attrType;
    private weka.core.Attribute wekaAttr;

    public Attribute(weka.core.Attribute wekaAttribute) {
        this.wekaAttr = wekaAttribute;
        this.attributeName = new SimpleStringProperty(wekaAttribute.name());
        this.attrType = wekaAttribute.type();
    }

    public weka.core.Attribute getWekaAttr() {
        return wekaAttr;
    }

    public void setWekaAttr(weka.core.Attribute wekaAttr) {
        this.wekaAttr = wekaAttr;
    }

    public int getAttrType() {
        return attrType;
    }

    public void setAttrType(int attrType) {
        this.attrType = attrType;
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
