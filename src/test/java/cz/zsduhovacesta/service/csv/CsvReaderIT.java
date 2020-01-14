package cz.zsduhovacesta.service.csv;

import cz.zsduhovacesta.model.BankStatement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderIT {

    private CsvReader csvReader = new CsvReader();

    @Test
    void readNewBankStatement() {
        try {
            String path = "src\\test\\resources\\csv\\150.csv";
            csvReader.readNewBankStatement(path);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void wrongOwnerThrowsException() {
        try {
            String pathToFileWithWrongOwner = "src\\test\\resources\\csv\\wrong_owner.csv";
            csvReader.readNewBankStatement(pathToFileWithWrongOwner);
            fail();
        } catch (Exception e) {
            assertEquals("Owner of account doesn't match expecting", e.getMessage());
        }
    }

    @Test
    void wrongHeadersThrowException() {
        try {
            String pathToFileWithWrongHeaders = "src\\test\\resources\\csv\\wrong_headers.csv";
            csvReader.readNewBankStatement(pathToFileWithWrongHeaders);
            fail();
        } catch (Exception e) {
            assertEquals("Headers don´t match expecting", e.getMessage());
        }
    }

    @Test
    void checkReadBankStatement() {
        try {
            String pathToFile = "src\\test\\resources\\csv\\265.csv";
            BankStatement bankStatement = csvReader.readNewBankStatement(pathToFile);
            assertEquals(2652019, bankStatement.getId());
            assertEquals("22.11.2019", bankStatement.getStringDate());
            int size = bankStatement.getTransactions().size();
            assertEquals(5, size);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void readAnotherFiles() {
        String pathToFileWithTransactionWithoutVs = "src\\test\\resources\\csv\\202.csv";
        String anotherFile = "src\\test\\resources\\csv\\264.csv";
        try {
            csvReader.readNewBankStatement(pathToFileWithTransactionWithoutVs);
            csvReader.readNewBankStatement(anotherFile);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}