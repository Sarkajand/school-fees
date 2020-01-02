package cz.zsduhovacesta.service.csv;

import cz.zsduhovacesta.model.BankStatement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderIT {

    private CsvReader csvReader = new CsvReader();

    @Test
    void readNewBankStatement() {
        try {
            String path = "D:\\Java\\Projects\\schoolFees\\csv\\Vypis_z_uctu-55332010_20190711-20190711_cislo-150.csv";
            csvReader.readNewBankStatement(path);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void wrongOwnerThrowsException () {
        try {
            String pathToFileWithWrongOwner = "D:\\Java\\Projects\\schoolFees\\csv\\wrong_owner.csv";
            csvReader.readNewBankStatement(pathToFileWithWrongOwner);
            fail();
        } catch (Exception e) {
            assertEquals("Owner of account doesn't match expecting", e.getMessage());
        }
    }

    @Test
    void wrongHeadersThrowException () {
        try {
            String pathToFileWithWrongHeaders = "D:\\Java\\Projects\\schoolFees\\csv\\wrong_headers.csv";
            csvReader.readNewBankStatement(pathToFileWithWrongHeaders);
            fail();
        } catch (Exception e) {
            assertEquals("Headers don´t match expecting", e.getMessage());
        }
    }

    @Test
    void checkReadBankStatement () {
        try {
            String pathToFile = "D:\\Java\\Projects\\schoolFees\\csv\\Vypis_z_uctu-55332010_20191122-20191122_cislo-265.csv";
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
    void readAnotherFiles () {
        String pathToFileWithTransactionWithoutVs = "D:\\Java\\Projects\\schoolFees\\csv\\Vypis_z_uctu-55332010_20190909-20190909_cislo-202.csv";
        String anotherFile = "D:\\Java\\Projects\\schoolFees\\csv\\Vypis_z_uctu-55332010_20191121-20191121_cislo-264.csv";
        try{
            csvReader.readNewBankStatement(pathToFileWithTransactionWithoutVs);
            csvReader.readNewBankStatement(anotherFile);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}