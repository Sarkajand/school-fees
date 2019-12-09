package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.model.BankStatement;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankStatementDaoIT {

    private static final String DB_NAME = "schoolFees.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:src\\test\\resources\\database\\" + DB_NAME;
    private static Connection connection;
    private static BankStatementDao bankStatementDao;

    @BeforeAll
    public static void setup () {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("connection to database failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown () {
        try{
            connection.close();
        } catch (SQLException e) {
            System.out.println("Closing failed: " + e.getMessage());
        }
    }

    @BeforeEach
    public void startTransaction () {
        try {
            bankStatementDao = new BankStatementDao(connection);
            connection.beginRequest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void rollback () {
        try {
            connection.rollback();
            bankStatementDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void listBankStatements () {
        try{
            List<BankStatement> bankStatements = bankStatementDao.queryBankStatements();
            int size = bankStatements.size();
            assertEquals(2, size);
            assertEquals(firstBankStatement(), bankStatements.get(0));
        } catch (Exception e) {
            fail();
        }
    }

    private BankStatement firstBankStatement () {
        BankStatement bankStatement = new BankStatement();
        bankStatement.setDate("1.12.2019");
        bankStatement.setId(1);
        return bankStatement;
    }

}