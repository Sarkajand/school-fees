package cz.zsduhovacesta.model;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Classes {

    private int classId;
    private SimpleStringProperty className;
    private SimpleStringProperty stage;

    public Classes() {
        this.className = new SimpleStringProperty();
        this.stage = new SimpleStringProperty();
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classes classes = (Classes) o;
        return getClassId() == classes.getClassId() &&
                Objects.equals(getClassName(), classes.getClassName()) &&
                Objects.equals(getStage(), classes.getStage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClassId(), getClassName(), getStage());
    }
}
