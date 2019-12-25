package cz.zsduhovacesta.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


import java.util.Objects;

public class Student {

    private SimpleStringProperty schoolStage;
    private SimpleStringProperty className;
    private SimpleIntegerProperty classId;
    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleIntegerProperty fees;
    private SimpleIntegerProperty VS;
    private SimpleStringProperty motherPhone;
    private SimpleStringProperty fatherPhone;
    private SimpleStringProperty motherEmail;
    private SimpleStringProperty fatherEmail;
    private SimpleStringProperty notes;
    private SimpleStringProperty paymentNotes;
    private SimpleIntegerProperty shouldPay;
    private SimpleIntegerProperty payed;
    private SimpleIntegerProperty summaryLastYear;
    private SimpleIntegerProperty overPayment;
    private SimpleIntegerProperty underPayment;


    public Student() {
        this.schoolStage = new SimpleStringProperty();
        this.className = new SimpleStringProperty();
        this.classId = new SimpleIntegerProperty();
        this.lastName = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.fees = new SimpleIntegerProperty();
        this.VS = new SimpleIntegerProperty();
        this.motherPhone = new SimpleStringProperty();
        this.fatherPhone = new SimpleStringProperty();
        this.motherEmail = new SimpleStringProperty();
        this.fatherEmail = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.paymentNotes = new SimpleStringProperty();
        this.shouldPay = new SimpleIntegerProperty();
        this.payed = new SimpleIntegerProperty();
        this.summaryLastYear = new SimpleIntegerProperty();
        this.overPayment = new SimpleIntegerProperty();
        this.underPayment = new SimpleIntegerProperty();
    }

    public void countOverPayment () {
        int overPayment = summaryLastYear.get() + payed.get() - shouldPay.get();
        if (overPayment > 0) {
            this.overPayment.set(overPayment);
        } else this.overPayment.set(0);
    }

    public void countUnderPayment() {
        int underPayment = shouldPay.get() - summaryLastYear.get() + payed.get();
        if (underPayment > 0) {
            this.underPayment.set(underPayment);
        } else this.underPayment.set(0);
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

    public int getClassId () {
        return this.classId.get();
    }

    public void setClassId (int classId) {
        this.classId.set(classId);
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

    public int getFees() {
        return fees.get();
    }

    public void setFees(int fees) {
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

    public int getShouldPay() {
        return shouldPay.get();
    }

    public void setShouldPay(int shouldPay) {
        this.shouldPay.set(shouldPay);
    }

    public int getPayed() {
        return payed.get();
    }

    public void setPayed(int payed) {
        this.payed.set(payed);
    }

    public int getSummaryLastYear () {
        return summaryLastYear.get();
    }

    public void setSummaryLastYear (int summaryLastYear) {
        this.summaryLastYear.set(summaryLastYear);
    }

    public int getOverPayment() {
        return overPayment.get();
    }

    public void setOverPayment(int overPayment) {
        this.overPayment.set(overPayment);
    }

    public int getUnderPayment() {
        return underPayment.get();
    }

    public void setUnderPayment(int underPayment) {
        this.underPayment.set(underPayment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getSchoolStage(), student.getSchoolStage()) &&
                Objects.equals(getClassName(), student.getClassName()) &&
                Objects.equals(getClassId(), student.getClassId()) &&
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
        return Objects.hash(getSchoolStage(), getClassName(), getClassId(), getLastName(), getFirstName(), getFees(), getVS(), getMotherPhone(), getFatherPhone(), getMotherEmail(), getFatherEmail(), getNotes(), getPaymentNotes(), getShouldPay(), getPayed(), getSummaryLastYear());
    }
}
