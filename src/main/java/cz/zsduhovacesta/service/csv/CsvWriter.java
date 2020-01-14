package cz.zsduhovacesta.service.csv;

import cz.zsduhovacesta.model.Student;
import cz.zsduhovacesta.model.Transaction;
import cz.zsduhovacesta.service.database.DaoManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CsvWriter {

    private DaoManager daoManager = DaoManager.getInstance();

    public void writeNewCsv(File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            List<List<String>> rows = getRows();
            for (List<String> row : rows) {
                fileWriter.append(String.join(",", row));
                fileWriter.append("\n");
            }
            fileWriter.flush();
        }
    }

    private List<List<String>> getRows() {
        List<List<String>> rows = new ArrayList<>();
        rows.add(getFirstRow());
        List<Student> students = getSortedStudents();
        for (Student student : students) {
            List<String> row = getRow(student);
            rows.add(row);
        }
        return rows;
    }

    private List<String> getFirstRow() {
        return Arrays.asList("ZŠ/MŠ", "Třída", "Příjmení", "Jméno", "Variabilní symbol", "Měl dát",
                "Dal", "Přeplatek", "Nedoplatek", "Datum Platby", "Částka", "Způsob platby",
                "Datum Platby", "Částka", "Způsob platby", "Datum Platby", "Částka", "Způsob platby");
    }

    private List<Student> getSortedStudents() {
        List<Student> students = daoManager.listAllStudents();
        Comparator<Student> byStage = Comparator.comparing(Student::getSchoolStage);
        Comparator<Student> byClass = Comparator.comparing(Student::getClassName);
        Comparator<Student> byLastName = Comparator.comparing(Student::getLastName);
        students.sort(byStage.thenComparing(byClass).thenComparing(byLastName));
        return students;
    }

    private List<String> getRow(Student student) {
        List<String> row = new ArrayList<>();
        row.add(student.getSchoolStage());
        row.add(student.getClassName());
        row.add(student.getLastName());
        row.add(student.getFirstName());
        row.add(Integer.toString(student.getVS()));
        row.add(Integer.toString(student.getShouldPay()));
        row.add(Integer.toString(student.getPayed()));
        row.add(Integer.toString(student.getOverPayment()));
        row.add(Integer.toString(student.getUnderPayment()));

        List<Transaction> transactions = daoManager.listTransactionByVs(student.getVS());
        Comparator<Transaction> byDate = Comparator.comparing(Transaction::getDate);
        transactions.sort(byDate.reversed());

        for (int i = 0; i < 3; i++) {
            if ((transactions.size() - 1) >= i) {
                Transaction transaction = transactions.get(i);
                row.add(transaction.getStringDate());
                row.add(Integer.toString(transaction.getAmount()));
                row.add(transaction.getPaymentMethod());
            }
        }
        return row;
    }
}
