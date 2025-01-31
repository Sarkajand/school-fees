package cz.zsduhovacesta.service.database;

import cz.zsduhovacesta.exceptions.EditRecordException;
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
    public static void setup() {
        try {
            connection = DriverManager.getConnection(CONNECTION_STRING);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("connection to database failed: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Closing failed: " + e.getMessage());
        }
    }

    @BeforeEach
    public void startTransaction() {
        try {
            bankStatementDao = new BankStatementDao(connection);
            connection.beginRequest();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void rollback() {
        try {
            connection.rollback();
            bankStatementDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void listBankStatements() {
        try {
            List<BankStatement> bankStatements = bankStatementDao.queryBankStatements();
            int size = bankStatements.size();
            assertEquals(2, size);
            assertEquals(firstBankStatement(), bankStatements.get(0));
        } catch (Exception e) {
            fail();
        }
    }

    private BankStatement firstBankStatement() {
        BankStatement bankStatement = new BankStatement();
        bankStatement.setDate("01.12.2019");
        bankStatement.setId(1);
        return bankStatement;
    }

    @Test
    void insertBankStatement() {
        BankStatement bankStatement = new BankStatement();
        bankStatement.setId(111);
        bankStatement.setDate("01.01.2020");
        try {
            bankStatementDao.insertBankStatement(bankStatement);
            List<BankStatement> bankStatements = bankStatementDao.queryBankStatements();
            assertEquals(3, bankStatements.size());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void insertExistingBankStatement() {
        try {
            bankStatementDao.insertBankStatement(firstBankStatement());
            fail("Should throw exception");
        } catch (SQLException e) {
            assertEquals("[SQLITE_CONSTRAINT_PRIMARYKEY]  A PRIMARY KEY constraint failed " +
                    "(UNIQUE constraint failed: bank_statement._id)", e.getMessage());
        } catch (EditRecordException e) {
            fail("Should throw SQLException");
        }
    }

    @Test
    void insertEmptyBankStatement() {
        try {
            bankStatementDao.insertBankStatement(new BankStatement());
            fail("Should throw exception");
        } catch (SQLException | EditRecordException e) {
            fail("should throw NullPointerException");
        } catch (NullPointerException e) {
            assertNotNull(e);
        }
    }

}