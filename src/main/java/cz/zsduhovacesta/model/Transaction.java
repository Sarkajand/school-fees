package cz.zsduhovacesta.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class Transaction {

    private int id;
    private SimpleObjectProperty<CustomDate> date;
    private SimpleStringProperty className;
    private SimpleIntegerProperty vs;
    private SimpleStringProperty lastName;
    private SimpleStringProperty firstName;
    private SimpleIntegerProperty amount;
    private SimpleStringProperty paymentMethod;
    private SimpleStringProperty transactionNotes;
    private SimpleIntegerProperty bankStatement;

    public Transaction() {
        this.date = new SimpleObjectProperty<>();
        this.className = new SimpleStringProperty();
        this.vs = new SimpleIntegerProperty();
        this.lastName = new SimpleStringProperty();
        this.firstName = new SimpleStringProperty();
        this.amount = new SimpleIntegerProperty();
        this.paymentMethod = new SimpleStringProperty();
        this.transactionNotes = new SimpleStringProperty();
        this.bankStatement = new SimpleIntegerProperty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CustomDate getDate() {
        return date.get();
    }

    public String getStringDate() {
        return date.get().toString();
    }

    public void setDate(String date) {
        CustomDate myDate = CustomDate.fromString(date);
        this.date.set(myDate);
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public int getVs() {
        return vs.get();
    }

    public void setVs(int vs) {
        this.vs.set(vs);
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

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public String getPaymentMethod() {
        return paymentMethod.get();
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod.set(paymentMethod);
    }

    public String getTransactionNotes() {
        return transactionNotes.get();
    }

    public void setTransactionNotes(String notes) {
        this.transactionNotes.set(notes);
    }

    public int getBankStatement() {
        return bankStatement.get();
    }

    public void setBankStatement(int bankStatement) {
        this.bankStatement.set(bankStatement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getClassName(), that.getClassName()) &&
                Objects.equals(getVs(), that.getVs()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getPaymentMethod(), that.getPaymentMethod()) &&
                Objects.equals(getTransactionNotes(), that.getTransactionNotes()) &&
                Objects.equals(getBankStatement(), that.getBankStatement());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getClassName(), getVs(), getLastName(), getFirstName(),
                getAmount(), getPaymentMethod(), getTransactionNotes(), getBankStatement());
    }
}
