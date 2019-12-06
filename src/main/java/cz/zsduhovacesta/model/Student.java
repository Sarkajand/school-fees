package cz.zsduhovacesta.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

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
    private SimpleStringProperty paymentNotes;
    private SimpleDoubleProperty shouldPay;
    private SimpleDoubleProperty payed;
    private SimpleDoubleProperty summaryLastYear;


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
        this.paymentNotes = new SimpleStringProperty();
        this.shouldPay = new SimpleDoubleProperty();
        this.payed = new SimpleDoubleProperty();
        this.summaryLastYear = new SimpleDoubleProperty();
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

    public String getPaymentNotes() {
        return paymentNotes.get();
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes.set(paymentNotes);
    }

    public double getShouldPay() {
        return shouldPay.get();
    }

    public void setShouldPay(double shouldPay) {
        this.shouldPay.set(shouldPay);
    }

    public double getPayed() {
        return payed.get();
    }

    public void setPayed(double payed) {
        this.payed.set(payed);
    }

    public double getSummaryLastYear () {
        return summaryLastYear.get();
    }

    public void setSummaryLastYear (double summaryLastYear) {
        this.summaryLastYear.set(summaryLastYear);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getSchoolStage(), student.getSchoolStage()) &&
                Objects.equals(getClassName(), student.getClassName()) &&
                Objects.equals(getLastName(), student.getLastName()) &&
                Objects.equals(getFirstName(), student.getFirstName()) &&
                Objects.equals(getFees(), student.getFees()) &&
                Objects.equals(getVS(),student.getVS()) &&
                Objects.equals(getMotherPhone(), student.getMotherPhone()) &&
                Objects.equals(getFatherPhone(), student.getFatherPhone()) &&
                Objects.equals(getMotherEmail(), student.getMotherEmail()) &&
                Objects.equals(getFatherEmail(), student.getFatherEmail()) &&
                Objects.equals(getNotes(), student.getNotes()) &&
                Objects.equals(getPaymentNotes(), student.getPaymentNotes()) &&
                Objects.equals(getShouldPay(), student.getShouldPay()) &&
                Objects.equals(getPayed(), student.getPayed()) &&
                Objects.equals(getSummaryLastYear(), student.getSummaryLastYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSchoolStage(), getClassName(), getLastName(), getFirstName(), getFees(), getVS(), getMotherPhone(), getFatherPhone(), getMotherEmail(), getFatherEmail(), getNotes(), getPaymentNotes(), getShouldPay(), getPayed(), getSummaryLastYear());
    }
}
