package cz.zsduhovacesta.service.csv;

import cz.zsduhovacesta.model.BankStatement;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderIT {

    CsvReader csvReader = new CsvReader();
    String path = "D:\\Java\\MyProjects\\csv\\Vypis_z_uctu-55332010_20190711-20190711_cislo-150.csv";
    String pathToFileWithWrongOwner = "D:\\Java\\MyProjects\\csv\\wrong_owner.csv";
    String pathToFileWithWrongHeaders = "D:\\Java\\MyProjects\\csv\\wrong_headers.csv";
    String pathToAnotherFile = "D:\\Java\\MyProjects\\csv\\Vypis_z_uctu-55332010_20191122-20191122_cislo-265.csv";

    @Test
    void readNewBankStatement() {
        try {
            csvReader.readNewBankStatement(path);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void wrongOwnerThrowsException () {
        try {
            csvReader.readNewBankStatement(pathToFileWithWrongOwner);
            fail();
        } catch (Exception e) {
            assertEquals("Owner of account doesn't match expecting", e.getMessage());
        }
    }

    @Test
    void wrongHeadersThrowException () {
        try {
            csvReader.readNewBankStatement(pathToFileWithWrongHeaders);
            fail();
        } catch (Exception e) {
            assertEquals("Headers don´t match expecting", e.getMessage());
        }
    }

    @Test
    void checkReadBankStatement () {
        try {
            BankStatement bankStatement = csvReader.readNewBankStatement(pathToAnotherFile);
            assertEquals(2652019, bankStatement.getId());
            assertEquals("22.11.2019", bankStatement.getDate());
            int size = bankStatement.getTransactions().size();
            assertEquals(5, size);
        } catch (Exception e) {
            fail();
        }
    }




}