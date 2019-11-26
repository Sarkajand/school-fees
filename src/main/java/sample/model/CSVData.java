package sample.model;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVData {

//    todo todle tady možná nepotřebuju
//    private String CSVFilePath;

    private static CSVData instance = new CSVData();

    private CSVData() {

    }

    public static CSVData getInstance() {
        return instance;
    }

    public BankStatement readNewBankStatement(String CSVFilePath) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(CSVFilePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            int numberOfStatement = 0;
            String date = "";
            List<Transaction> transactions = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.getRecordNumber() == 0) {
                    String stringWithNumber = csvRecord.get(0);
                    String[] words = stringWithNumber.split(" ");
                    String number = words[2];
                    number.replaceAll("/", "");
                    numberOfStatement = Integer.parseInt(number);
                }
                if (csvRecord.getRecordNumber() > 8) {
                    date = csvRecord.get(1);
                    Transaction transaction = new Transaction();
                    transaction.setDate(date);
                    transaction.setVS(Integer.parseInt(csvRecord.get(9)));
                    transaction.setAmount(Double.parseDouble(csvRecord.get(2)));
                    transaction.setPaymentMethod("převodem na účet");
                    transaction.setNotes(csvRecord.get(12));
                    transaction.setBankStatement(numberOfStatement);
                    transactions.add(transaction);
                }
            }
            BankStatement bankStatement = new BankStatement();
            bankStatement.setId(numberOfStatement);
            bankStatement.setDate(date);
            bankStatement.setTransactions(transactions);
            return bankStatement;
        } catch (IOException e) {
            System.out.println("Reading file failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
