package cz.zsduhovacesta.service.csv;

import cz.zsduhovacesta.model.BankStatement;
import cz.zsduhovacesta.model.Transaction;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private String date;
    private int bankStatementId;

    public BankStatement readNewBankStatement(String CSVFilePath) throws Exception {
        BankStatement bankStatement = new BankStatement();
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(CSVFilePath), StandardCharsets.UTF_8)) {
            String lineWithNumber = bufferedReader.readLine();
            bankStatementId = getId(lineWithNumber);
            String owner = bufferedReader.readLine();
            checkOwner(owner);
            for (int i = 0; i < 7; i++) {
                bufferedReader.readLine();
            }
            String headers = bufferedReader.readLine();
            checkHeaders(headers);

            String line = bufferedReader.readLine();
            while (line != null) {
                Transaction transaction = setTransaction(line);
                transactions.add(transaction);
                line = bufferedReader.readLine();
            }
            bankStatement.setId(bankStatementId);
            bankStatement.setDate(date);
            bankStatement.setTransactions(transactions);
        }
        return bankStatement;
    }

    private int getId(String lineWithNumber) {
        String[] words = lineWithNumber.split(" ");
        String number = words[2];
        return Integer.parseInt(number.replace("/", ""));
    }

    private void checkOwner(String owner) throws Exception {
        if (!owner.equals("\"Majitel účtu: ZÁKLADNÍ ŠKOLA A MATEŘSKÁ ŠKOLA DUHOVÁ CESTA, s.r.o., Havlíčkova 3675, Chomutov, 43003, Česká republika\"")) {
            throw new Exception("Owner of account doesn't match expecting");
        }
    }

    private void checkHeaders(String headers) throws Exception {
        String expectingHeaders = "\"ID operace\";\"Datum\";\"Objem\";\"Měna\";\"Protiúčet\";\"Název protiúčtu\";\"Kód banky\";\"Název banky\";\"KS\";\"VS\";\"SS\";\"Poznámka\";\"Zpráva pro příjemce\";\"Typ\";\"Provedl\";\"Upřesnění\";\"Poznámka\";\"BIC\";\"ID pokynu\"";
        if (!headers.equals(expectingHeaders)) {
            throw new Exception("Headers don´t match expecting");
        }
    }

    private Transaction setTransaction(String line) {
        Transaction transaction = new Transaction();
        String editedLine = line.replaceAll("\"", "");
        String[] attributes = editedLine.split(";");
        date = attributes[1];
        transaction.setDate(date);
        String variableSymbol = attributes[9];
        if (variableSymbol.length() > 0) {
            transaction.setVs(Integer.parseInt(attributes[9]));
        }
        String amountString = attributes[2].replace(",", ".");
        double amount = Double.parseDouble(amountString);
        transaction.setAmount((int) Math.round(amount));
        transaction.setPaymentMethod("převodem na účet");
        transaction.setTransactionNotes(attributes[12]);
        transaction.setBankStatement(bankStatementId);
        return transaction;
    }
}
