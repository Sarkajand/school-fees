package cz.zsduhovacesta.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.Objects;

public class BankStatement {

    private SimpleIntegerProperty id;
    private SimpleObjectProperty<CustomDate> date;
    private List<Transaction> transactions;


    public BankStatement() {
        this.id = new SimpleIntegerProperty();
        this.date = new SimpleObjectProperty<>();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankStatement that = (BankStatement) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getTransactions(), that.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getTransactions());
    }
}
