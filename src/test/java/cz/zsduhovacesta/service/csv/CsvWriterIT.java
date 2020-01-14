package cz.zsduhovacesta.service.csv;

import cz.zsduhovacesta.service.database.DaoManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CsvWriterIT {

    private CsvWriter csvWriter = new CsvWriter();


    @BeforeEach
    public void setup() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\test\\resources\\database\\schoolFees.db");
            DaoManager daoManager = DaoManager.getInstance();
            daoManager.open(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTextInFileIsEqualExpectedText() {
        try {
            File file = File.createTempFile("csvWriterTest", ",tmp");
            csvWriter.writeNewCsv(file);

            Path newFilePath = Paths.get(file.getAbsolutePath());
            BufferedReader newFileBufferedReader = Files.newBufferedReader(newFilePath, StandardCharsets.UTF_8);

            Path path = Paths.get("src\\test\\resources\\csv\\expected.csv");
            BufferedReader expectedFileBufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
            String expectedLine = expectedFileBufferedReader.readLine();
            while (expectedLine != null) {
                String line = newFileBufferedReader.readLine();
                assertEquals(expectedLine, line);
                expectedLine = expectedFileBufferedReader.readLine();
            }
        } catch (IOException e) {
            fail();
        }
    }
}