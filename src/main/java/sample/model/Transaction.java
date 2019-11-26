package sample.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {

    private SimpleStringProperty date;
    private SimpleStringProperty className;
    private SimpleIntegerProperty VS;
    private SimpleStringProperty last_name;
    private SimpleStringProperty first_name;
    private SimpleDoubleProperty amount;
    private SimpleStringProperty paymentMethod;
    private SimpleStringProperty notes;
    private SimpleIntegerProperty bankStatement;

    public Transaction() {
        this.date = new SimpleStringProperty();
        this.className = new SimpleStringProperty();
        this.VS = new SimpleIntegerProperty();
        this.last_name = new SimpleStringProperty();
        this.first_name = new SimpleStringProperty();
        this.amount = new SimpleDoubleProperty();
        this.paymentMethod = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
        this.bankStatement = new SimpleIntegerProperty();
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public int getVS() {
        return VS.get();
    }

    public void setVS(int VS) {
        this.VS.set(VS);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
    }

    public String getFirst_name() {
        return first_name.get();
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
    }

    public double getAmount() {
        return amount.get();
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod.set(paymentMethod);
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public int getBankStatement() {
        return bankStatement.get();
    }

    public void setBankStatement(int bankStatement) {
        this.bankStatement.set(bankStatement);
    }
}
