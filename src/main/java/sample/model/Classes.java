package sample.model;

import javafx.beans.property.SimpleStringProperty;

public class Classes {

    private SimpleStringProperty className;
    private SimpleStringProperty stage;

    public Classes() {
        this.className = new SimpleStringProperty();
        this.stage = new SimpleStringProperty();
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public String getStage() {
        return stage.get();
    }

    public void setStage(String stage) {
        this.stage.set(stage);
    }
}
