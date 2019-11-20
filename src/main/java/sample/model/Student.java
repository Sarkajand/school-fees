package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

    private SimpleStringProperty schoolStage;
    private SimpleStringProperty className;
    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleDoubleProperty fees;
    private SimpleIntegerProperty VS;
    private SimpleStringProperty motherPhone;
    private SimpleStringProperty fatherPhone;
    private SimpleStringProperty motherEmail;
    private SimpleStringProperty fatherEmail;
    private SimpleStringProperty notes;

    public Student() {
        this.schoolStage = new SimpleStringProperty();
        this.className = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.fees = new SimpleDoubleProperty();
        this.VS = new SimpleIntegerProperty();
        this.motherPhone = new SimpleStringProperty();
        this.fatherPhone = new SimpleStringProperty();
        this.motherEmail = new SimpleStringProperty();
        this.fatherEmail = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
    }

    public String getSchoolStage() {
        return schoolStage.get();
    }

    public void setSchoolStage(String schoolStage) {
        this.schoolStage.set(schoolStage);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public double getFees() {
        return fees.get();
    }

    public void setFees(double fees) {
        this.fees.set(fees);
    }

    public int getVS() {
        return VS.get();
    }

    public void setVS(int VS) {
        this.VS.set(VS);
    }

    public String getMotherPhone() {
        return motherPhone.get();
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone.set(motherPhone);
    }

    public String getFatherPhone() {
        return fatherPhone.get();
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone.set(fatherPhone);
    }

    public String getMotherEmail() {
        return motherEmail.get();
    }

    public void setMotherEmail(String motherEmail) {
        this.motherEmail.set(motherEmail);
    }

    public String getFatherEmail() {
        return fatherEmail.get();
    }

    public void setFatherEmail(String fatherEmail) {
        this.fatherEmail.set(fatherEmail);
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
}
