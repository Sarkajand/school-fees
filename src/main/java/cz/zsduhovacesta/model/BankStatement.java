package cz.zsduhovacesta.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BankStatement {

    private SimpleIntegerProperty id;
    private SimpleObjectProperty<LocalDate> date;
    private List<Transaction> transactions;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.UK);

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

    public LocalDate getDate() {
        return date.get();
    }

    public String getStringDate() {
        return date.get().format(formatter);
    }

    public void setDate(String date) {
        LocalDate localDate = LocalDate.from(formatter.parse(date));
        this.date.set(localDate);
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
                Objects.equals(getStringDate(), that.getStringDate()) &&
                Objects.equals(getTransactions(), that.getTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStringDate(), getTransactions());
    }
}
