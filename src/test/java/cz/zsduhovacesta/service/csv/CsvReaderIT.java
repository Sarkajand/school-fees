package cz.zsduhovacesta.service.csv;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderIT {

//    Path path = Paths.get("D:\\Java\\MyProjects\\csv\\Vypis_z_uctu-55332010_20190711-20190711_cislo-150.csv");
//    Paths path = "D:\\Java\\MyProjects\\csv\\Vypis_z_uctu-55332010_20190711-20190711_cislo-150.csv";
    CsvReader csvReader = new CsvReader();
    String path = "D:\\Java\\MyProjects\\csv\\Vypis_z_uctu-55332010_20190711-20190711_cislo-150.csv";


    @Test
    void readNewBankStatement() {
        try {
            csvReader.readNewBankStatement(path);
        } catch (Exception e) {
            fail();
        }
    }
}